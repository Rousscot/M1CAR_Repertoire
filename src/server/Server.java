package server;

import repertoire.Personne;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 * Created by JeCisC on 22/04/2016.
 */
public class Server {

    protected static final int SOCKET = 8000;

    protected ServerRepertoire repertoire;
    protected Boolean stop;
    protected ServerSocket socket;
    protected Socket connexionSocket;
    protected HashMap<String, BiConsumer<ServerRepertoire, Socket>> commands;

    public Server() throws IOException {
        this.repertoire = new ServerRepertoire();
        this.initCommands();
        this.initSocket();
        this.stop = false;
    }

    public void initCommands() {
        this.log("Init commands");
        this.commands = new HashMap<>();

        this.commands.put("liste", (ServerRepertoire rep, Socket sock) -> {
            StringBuilder builder = new StringBuilder();
            for (String name : rep.listerPersonnes()) {
                builder.append(name);
                builder.append(" ");
            }
            builder.append("\n");
            try {
                new DataOutputStream(sock.getOutputStream()).writeBytes(builder.toString());
            } catch (IOException e) {
                this.log("Error during list action");
            }
        });

        this.commands.put("cherche", (ServerRepertoire rep, Socket sock) -> {
            try {
                ObjectOutputStream ops = new ObjectOutputStream(sock.getOutputStream());
                ops.writeBoolean(true);
                ops.flush();
                Personne found = rep.chercherPersonne(new BufferedReader(new InputStreamReader(sock.getInputStream())).readLine());
                new ObjectOutputStream(sock.getOutputStream()).writeObject(found);
            } catch (IOException e) {
                this.log("Error during search action");
            }
        });

        this.commands.put("retirer", (ServerRepertoire rep, Socket sock) -> {
            try {
                ObjectOutputStream ops = new ObjectOutputStream(sock.getOutputStream());
                ops.writeBoolean(true);
                ops.flush();
                ops = new ObjectOutputStream(sock.getOutputStream());
                ops.writeBoolean(rep.retirerPersonne(new BufferedReader(new InputStreamReader(sock.getInputStream())).readLine()));
                ops.flush();
            } catch (IOException e) {
                this.log("Error during remove action");
            }
        });

        this.commands.put("ajouter", (ServerRepertoire rep, Socket sock) -> {
            try {
                ObjectOutputStream ops = new ObjectOutputStream(sock.getOutputStream());
                ops.writeBoolean(true);
                ops.flush();
                Personne personne = (Personne) new ObjectInputStream(sock.getInputStream()).readObject();
                ops = new ObjectOutputStream(sock.getOutputStream());
                ops.writeBoolean(rep.ajouterPersonne(personne));
                ops.flush();
            } catch (IOException | ClassNotFoundException e) {
                this.log("Error during add action");
            }
        });

        this.commands.put("modifier", (ServerRepertoire rep, Socket sock) -> {
            try {
                ObjectOutputStream ops = new ObjectOutputStream(sock.getOutputStream());
                ops.writeBoolean(true);
                ops.flush();
                Personne personne = (Personne) new ObjectInputStream(sock.getInputStream()).readObject();
                ops = new ObjectOutputStream(sock.getOutputStream());
                ops.writeBoolean(rep.modifierPersonne(personne));
                ops.flush();
            } catch (IOException | ClassNotFoundException e) {
                this.log("Error during update action");
            }
        });

        this.commands.put("error", (ServerRepertoire rep, Socket sock) -> {
            this.log("Error no action of this name");
        });
    }

    public void initSocket() throws IOException {
        this.log("Open socket");
        this.socket = new ServerSocket(SOCKET);
    }

    public void launch() throws IOException {
        while (!this.stop) {
            this.log("Open connection");
            this.connexionSocket = this.socket.accept();
            new Thread(new ClientHandler(connexionSocket, this.repertoire, this.commands)).start();
        }
    }

    public void log(String message) {
        System.out.println("SERVER : " + message);
    }

    public class ClientHandler implements Runnable {

        protected Socket connectionSocket;
        protected ServerRepertoire repertoire;
        protected HashMap<String, BiConsumer<ServerRepertoire, Socket>> commands;

        public ClientHandler(Socket socket, ServerRepertoire repertoire, HashMap<String, BiConsumer<ServerRepertoire, Socket>> commands) {
            this.connectionSocket = socket;
            this.repertoire = repertoire;
            this.commands = commands;
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
            commands.getOrDefault(receivedMessage, this.commands.get("error")).accept(this.repertoire, this.connectionSocket);
        }

        public void log(String message) {
            System.out.println("SERVER : " + message);
        }

    }

}
