package client;

import repertoire.Personne;
import repertoire.Repertoire;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by JeCisC on 22/04/2016.
 */
public class ClientRepertoire implements Repertoire {

    protected Socket socket;

    public ClientRepertoire(String address, Integer port) throws IOException {
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
        System.out.println("client.Client : " + message);
    }

    @Override
    public boolean ajouterPersonne(Personne personne) {
        //TODO
        return false;
    }

    @Override
    public boolean modifierPersonne(Personne personne) {
        //TODO
        return false;
    }

    @Override
    public boolean retirerPersonne(String nom) {
        //TODO
        return false;
    }

    @Override
    public Personne chercherPersonne(String nom) {
        //TODO
        return null;
    }

    @Override
    public String[] listerPersonnes() {
        //TODO
        return new String[0];
    }
}
