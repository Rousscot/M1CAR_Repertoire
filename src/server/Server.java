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
    protected Map<String, Integer> users;

    public Server() throws IOException {
        this.repertoires = new ServerRepertoireList();
        this.initSocket();
        this.stop = false;
        this.users = new HashMap<>();
        this.users.put("test", "test".hashCode());
    }

    public void initSocket() throws IOException {
        System.out.println("SERVER : Open socket");
        this.socket = new ServerSocket(SOCKET);
    }

    public void launch() throws IOException {
        while (!this.stop) {
            new Thread(new ClientHandler(this.socket.accept(), this.repertoires, this.users)).start();
        }
    }

    public class ClientHandler implements Runnable {

        protected Socket connectionSocket;
        protected ServerRepertoireList repertoires;
        protected ServerRepertoire currentRepertoire;
        protected Map<String, Runnable> commands;
        protected Map<String, Integer> users;
        protected ObjectInputStream ois;

        public ClientHandler(Socket socket, ServerRepertoireList repertoires, Map<String, Integer> users) {
            this.log("Open connection from " + socket.getInetAddress() + " port " + socket.getPort());
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
                    String[] messages = this.ois.readUTF().split(" ");
                    ObjectOutputStream oos = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    oos.writeBoolean(this.users.getOrDefault(messages[0], 0) == messages[1].hashCode());
                    oos.flush();
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
                    ObjectOutputStream oos = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    oos.writeUTF(builder.toString());
                    oos.flush();
                } catch (IOException e) {
                    this.log("Error during list repertoires action");
                }
            });

            this.commands.put("chercheRep", () -> {
                try {
                    Repertoire found = this.repertoires.chercherRepertoire(this.ois.readUTF());
                    new ObjectOutputStream(this.connectionSocket.getOutputStream()).writeObject(found);
                } catch (IOException e) {
                    this.log("Error during search repertoire action");
                }
            });

            this.commands.put("retirerRep", () -> {
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    oos.writeBoolean(this.repertoires.retirerRepertoire(this.ois.readUTF()));
                    oos.flush();
                } catch (IOException e) {
                    this.log("Error during remove repertoire action");
                }
            });

            this.commands.put("ajouterRep", () -> {
                try {
                    ServerRepertoire repertoire = (ServerRepertoire) this.ois.readObject();
                    ObjectOutputStream oos = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    oos.writeBoolean(this.repertoires.ajouterRepertoire(repertoire));
                    oos.flush();
                } catch (IOException | ClassNotFoundException e) {
                    this.log("Error during add repertoire action");
                }
            });

            this.commands.put("accederRep", () -> {
                try {
                    this.currentRepertoire = this.repertoires.chercherRepertoire(this.ois.readUTF());
                    ObjectOutputStream oos = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    oos.writeBoolean(this.currentRepertoire != null);
                    oos.flush();
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
                    ObjectOutputStream oos = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    oos.writeUTF(builder.toString());
                    oos.flush();
                } catch (IOException e) {
                    this.log("Error during list action");
                }
            });

            this.commands.put("cherche", () -> {
                try {
                    Personne found = this.currentRepertoire.chercherPersonne(this.ois.readUTF());
                    new ObjectOutputStream(this.connectionSocket.getOutputStream()).writeObject(found);
                } catch (IOException e) {
                    this.log("Error during search action");
                }
            });

            this.commands.put("retirer", () -> {
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    oos.writeBoolean(this.currentRepertoire.retirerPersonne(this.ois.readUTF()));
                    oos.flush();
                } catch (IOException e) {
                    this.log("Error during remove action");
                }
            });

            this.commands.put("ajouter", () -> {
                try {
                    Personne personne = (Personne) this.ois.readObject();
                    ObjectOutputStream oos = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    oos.writeBoolean(this.currentRepertoire.ajouterPersonne(personne));
                    oos.flush();
                } catch (IOException | ClassNotFoundException e) {
                    this.log("Error during add action");
                }
            });

            this.commands.put("modifier", () -> {
                try {
                    Personne personne = (Personne) this.ois.readObject();
                    ObjectOutputStream oos = new ObjectOutputStream(this.connectionSocket.getOutputStream());
                    oos.writeBoolean(this.currentRepertoire.modifierPersonne(personne));
                    oos.flush();
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
                this.ois = new ObjectInputStream(this.connectionSocket.getInputStream());
                receivedMessage = this.ois.readUTF().split(" ", 2)[0];
            } catch (IOException e) {
                receivedMessage = "error";
            }

            this.executeAction(receivedMessage);

            this.run();
        }

        public void executeAction(String receivedMessage) {
            this.log("Execute action: " + receivedMessage);
            commands.getOrDefault(receivedMessage, this.commands.get("error")).run();
        }

        public void log(String message) {
            System.out.println("SERVER : " + message);
        }

    }

}
