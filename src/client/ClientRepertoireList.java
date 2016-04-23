package client;

import repertoire.ListeRepertoire;
import repertoire.Personne;
import repertoire.Repertoire;
import server.ServerRepertoire;

import java.io.IOException;
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
    public boolean ajouterRepertoire(Repertoire repertoire) {
        //TODO
        return false;
    }

    @Override
    public boolean retirerRepertoire(String nom) {
        //TODO
        return false;
    }

    @Override
    public ServerRepertoire chercherRepertoire(String nom) {
        //TODO
        return null;
    }

    @Override
    public String[] listerRepertoires() {
        //TODO
        return new String[0];
    }
}
