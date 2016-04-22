package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by JeCisC on 22/04/2016.
 */
public class Server {

    protected static final int SOCKET = 8000;

    protected ServerRepertoire repertoire;

    protected Boolean stop;

    protected ServerSocket socket;

    protected Socket connexionSocket;

    public Server() throws IOException {
        this.repertoire = new ServerRepertoire();
        this.initCommands();
        this.initSocket();
        this.stop = false;
    }

    public void initCommands() {
        //TODO
    }

    public void initSocket() throws IOException {
        System.out.println("Open socket");
        this.socket = new ServerSocket(SOCKET);
    }

    public void launch() throws IOException {
        while (!this.stop) {
            System.out.println("Open connection");
            this.connexionSocket = this.socket.accept();
            new Thread(new ClientHandler(connexionSocket)).start();
        }
    }

    public class ClientHandler implements Runnable {

        protected Socket connectionSocket;

        public ClientHandler(Socket socket) {
            this.connectionSocket = socket;
        }

        public void run() {

        }


    }

}
