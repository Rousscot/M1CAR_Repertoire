package client;

import repertoire.ListeRepertoire;
import server.ServerRepertoire;

import java.io.*;
import java.net.Socket;

/**
 * Created by JeCisC on 23/04/2016.
 */
public class ClientRepertoireList implements ListeRepertoire {

    protected Socket socket;

    public ClientRepertoireList(String address, Integer port) throws IOException {
        this.openSocket(address, port);
    }

    public void openSocket(String address, Integer port) throws IOException {
        this.log("Address: " + address);
        this.log("Port: " + port);
        this.log("Open Socket");
        this.socket = new Socket(address, port);
    }

    public void closeSocket() throws IOException {
        this.log("Close Socket");
        this.socket.close();
    }

    public void log(String message) {
        System.out.println("Client Repertoire List : " + message);
    }

    @Override
    public boolean ajouterRepertoire(ServerRepertoire repertoire) {
        this.log("ajouterRep");
        try {
            (new DataOutputStream(this.socket.getOutputStream())).writeBytes("ajouterRep\n");
            if (new ObjectInputStream(this.socket.getInputStream()).readBoolean()) {
                new ObjectOutputStream(this.socket.getOutputStream()).writeObject(repertoire);
                return new ObjectInputStream(this.socket.getInputStream()).readBoolean();
            }
            this.log("Error");
            return false;
        } catch (IOException e) {
            this.log("Error");
            return false;
        }
    }

    @Override
    public boolean retirerRepertoire(String nom) {
        this.log("retirerRep");
        try {
            (new DataOutputStream(this.socket.getOutputStream())).writeBytes("retirerRep\n");
            if (new ObjectInputStream(this.socket.getInputStream()).readBoolean()) {
                new DataOutputStream(this.socket.getOutputStream()).writeBytes(nom + "\n");
                return new ObjectInputStream(this.socket.getInputStream()).readBoolean();
            }
            this.log("Error");
            return false;
        } catch (IOException e) {
            this.log("Error");
            return false;
        }
    }

    @Override
    public ServerRepertoire chercherRepertoire(String nom) {
        this.log("chercheRep");
        try {
            new DataOutputStream(this.socket.getOutputStream()).writeBytes("chercheRep\n");
            if (new ObjectInputStream(this.socket.getInputStream()).readBoolean()) {
                new DataOutputStream(this.socket.getOutputStream()).writeBytes(nom + "\n");
                return (ServerRepertoire) new ObjectInputStream(this.socket.getInputStream()).readObject();
            }
            this.log("Error");
            return null;
        } catch (IOException | ClassNotFoundException e) {
            this.log("Error");
            return null;
        }
    }

    @Override
    public String[] listerRepertoires() {
        this.log("listeRep");
        try {
            new DataOutputStream(this.socket.getOutputStream()).writeBytes("listeRep\n");
            return (new BufferedReader(new InputStreamReader(this.socket.getInputStream())).readLine()).split("\\s+");
        } catch (IOException e) {
            this.log("Error");
            return new String[0];
        }
    }

    public Boolean accederRepertoire(String nom) {
        this.log("accederRep");
        try {
            new DataOutputStream(this.socket.getOutputStream()).writeBytes("accederRep\n");
            if (new ObjectInputStream(this.socket.getInputStream()).readBoolean()) {
                new DataOutputStream(this.socket.getOutputStream()).writeBytes(nom + "\n");
                return new ObjectInputStream(this.socket.getInputStream()).readBoolean();
            }
            this.log("Error");
            return null;
        } catch (IOException e) {
            this.log("Error");
            return null;
        }
    }
}
