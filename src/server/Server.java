package server;

import repertoire.Personne;
import repertoire.Repertoire;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JeCisC on 22/04/2016.
 */
public class Server {

    protected static final int SOCKET = 8000;

    protected ServerRepertoireList repertoires;
    protected Boolean stop;
    protected ServerSocket socket;
    protected Socket connexionSocket;
    protected Map<String, Integer> users;

    public Server() throws IOException {
        this.repertoires = new ServerRepertoireList();
        this.initSocket();
        this.stop = false;
        this.users = new HashMap<>();
        this.users.put("test", "test".hashCode());
    }

    public void initSocket() throws IOException {
        this.log("Open socket");
        this.socket = new ServerSocket(SOCKET);
    }

    public void launch() throws IOException {
        while (!this.stop) {
            this.log("Open connection");
            this.connexionSocket = this.socket.accept();
            new Thread(new ClientHandler(connexionSocket, this.repertoires, this.users)).start();
        }
    }

    public void log(String message) {
        System.out.println("SERVER : " + message);
    }

    public class ClientHandler implements Runnable {

        protected Socket connectionSocket;
        protected ServerRepertoireList repertoires;
        protected ServerRepertoire currentRepertoire;
        protected Map<String, Runnable> commands;
        protected Map<String, Integer> users;

        public ClientHandler(Socket socket, ServerRepertoireList repertoires, Map<String, Integer> users) {
            this.connectionSocket = socket;
            this.repertoires = repertoires;
            this.users = users;
            this.initCommands();
        }

        public void initCommands() {
            this.log("Init commands");
            this.commands = new HashMap<>();

            this.commands.put("connexion", () -> {
                try {
                    ObjectOutputStream ops = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    ops.writeBoolean(true);
                    ops.flush();
                    String[] messages = new BufferedReader(new InputStreamReader(this.connectionSocket.getInputStream())).readLine().split(" ");
                    ops = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    ops.writeBoolean(this.users.getOrDefault(messages[0], 0) == messages[1].hashCode());
                    ops.flush();
                } catch (IOException e) {
                    this.log("Error during login");
                }
            });

            this.commands.put("listeRep", () -> {
                StringBuilder builder = new StringBuilder();
                for (String name : this.repertoires.listerRepertoires()) {
                    builder.append(name);
                    builder.append(" ");
                }
                builder.append("\n");
                try {
                    new DataOutputStream(this.connectionSocket.getOutputStream()).writeBytes(builder.toString());
                } catch (IOException e) {
                    this.log("Error during list repertoires action");
                }
            });

            this.commands.put("chercheRep", () -> {
                try {
                    ObjectOutputStream ops = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    ops.writeBoolean(true);
                    ops.flush();
                    Repertoire found = this.repertoires.chercherRepertoire(new BufferedReader(new InputStreamReader(this.connectionSocket.getInputStream())).readLine());
                    new ObjectOutputStream(this.connectionSocket.getOutputStream()).writeObject(found);
                } catch (IOException e) {
                    this.log("Error during search repertoire action");
                }
            });

            this.commands.put("retirerRep", () -> {
                try {
                    ObjectOutputStream ops = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    ops.writeBoolean(true);
                    ops.flush();
                    ops = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    ops.writeBoolean(this.repertoires.retirerRepertoire(new BufferedReader(new InputStreamReader(this.connectionSocket.getInputStream())).readLine()));
                    ops.flush();
                } catch (IOException e) {
                    this.log("Error during remove repertoire action");
                }
            });

            this.commands.put("ajouterRep", () -> {
                try {
                    ObjectOutputStream ops = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    ops.writeBoolean(true);
                    ops.flush();
                    ServerRepertoire repertoire = (ServerRepertoire) new ObjectInputStream(this.connectionSocket.getInputStream()).readObject();
                    ops = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    ops.writeBoolean(this.repertoires.ajouterRepertoire(repertoire));
                    ops.flush();
                } catch (IOException | ClassNotFoundException e) {
                    this.log("Error during add repertoire action");
                }
            });

            this.commands.put("accederRep", () -> {
                try {
                    ObjectOutputStream ops = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    ops.writeBoolean(true);
                    ops.flush();
                    this.currentRepertoire = this.repertoires.chercherRepertoire(new BufferedReader(new InputStreamReader(this.connectionSocket.getInputStream())).readLine());
                    ops = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    ops.writeBoolean(this.currentRepertoire != null);
                    ops.flush();
                } catch (IOException e) {
                    this.log("Error during access repertoire action");
                }
            });

            this.commands.put("liste", () -> {
                StringBuilder builder = new StringBuilder();
                for (String name : this.currentRepertoire.listerPersonnes()) {
                    builder.append(name);
                    builder.append(" ");
                }
                builder.append("\n");
                try {
                    new DataOutputStream(this.connectionSocket.getOutputStream()).writeBytes(builder.toString());
                } catch (IOException e) {
                    this.log("Error during list action");
                }
            });

            this.commands.put("cherche", () -> {
                try {
                    ObjectOutputStream ops = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    ops.writeBoolean(true);
                    ops.flush();
                    Personne found = this.currentRepertoire.chercherPersonne(new BufferedReader(new InputStreamReader(this.connectionSocket.getInputStream())).readLine());
                    new ObjectOutputStream(this.connectionSocket.getOutputStream()).writeObject(found);
                } catch (IOException e) {
                    this.log("Error during search action");
                }
            });

            this.commands.put("retirer", () -> {
                try {
                    ObjectOutputStream ops = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    ops.writeBoolean(true);
                    ops.flush();
                    ops = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    ops.writeBoolean(this.currentRepertoire.retirerPersonne(new BufferedReader(new InputStreamReader(this.connectionSocket.getInputStream())).readLine()));
                    ops.flush();
                } catch (IOException e) {
                    this.log("Error during remove action");
                }
            });

            this.commands.put("ajouter", () -> {
                try {
                    ObjectOutputStream ops = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    ops.writeBoolean(true);
                    ops.flush();
                    Personne personne = (Personne) new ObjectInputStream(this.connectionSocket.getInputStream()).readObject();
                    ops = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    ops.writeBoolean(this.currentRepertoire.ajouterPersonne(personne));
                    ops.flush();
                } catch (IOException | ClassNotFoundException e) {
                    this.log("Error during add action");
                }
            });

            this.commands.put("modifier", () -> {
                try {
                    ObjectOutputStream ops = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    ops.writeBoolean(true);
                    ops.flush();
                    Personne personne = (Personne) new ObjectInputStream(this.connectionSocket.getInputStream()).readObject();
                    ops = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    ops.writeBoolean(this.currentRepertoire.modifierPersonne(personne));
                    ops.flush();
                } catch (IOException | ClassNotFoundException e) {
                    this.log("Error during update action");
                }
            });

            this.commands.put("error", () -> this.log("Error no action of this name"));
        }

        public void run() {
            this.log("Get data");
            String receivedMessage;
            try {
                receivedMessage = (new BufferedReader(new InputStreamReader(this.connectionSocket.getInputStream()))).readLine().split(" ", 2)[0];
            } catch (IOException e) {
                receivedMessage = "error";
            }

            this.executeAction(receivedMessage);

            this.run();
        }

        public void executeAction(String receivedMessage) {
            this.log("Execute action");
            commands.getOrDefault(receivedMessage, this.commands.get("error")).run();
        }

        public void log(String message) {
            System.out.println("SERVER : " + message);
        }

    }

}
