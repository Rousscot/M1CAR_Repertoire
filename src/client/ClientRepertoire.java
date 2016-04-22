package client;

import repertoire.Personne;
import repertoire.Repertoire;

import java.io.*;
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
        System.out.println("Client : " + message);
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
        this.log("cherche");
        try {
            (new DataOutputStream(this.socket.getOutputStream())).writeBytes("cherche\n");
            Boolean shouldSendName = (Boolean) (new ObjectInputStream(this.socket.getInputStream())).readObject(); //TODO: Check why the hell I cannot use readBoolean
            if (shouldSendName) {
                (new DataOutputStream(this.socket.getOutputStream())).writeBytes(nom + "\n");
                return (Personne) (new ObjectInputStream(this.socket.getInputStream())).readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();//TODO
        }
        return null;
    }

    @Override
    public String[] listerPersonnes() {
        this.log("liste");
        try {
            (new DataOutputStream(this.socket.getOutputStream())).writeBytes("liste\n");
            return (new BufferedReader(new InputStreamReader(this.socket.getInputStream())).readLine()).split("\\s+");
        } catch (IOException e) {
            e.printStackTrace();//TODO
        }
        return new String[0];
    }
}
