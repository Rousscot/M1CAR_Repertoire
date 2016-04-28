package client;

import repertoire.Personne;
import repertoire.Repertoire;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by JeCisC on 22/04/2016.
 */
public class ClientRepertoire implements Repertoire {

    protected Client client;

    public ClientRepertoire(Client client) throws IOException {
        this.client = client;
    }

    public void log(String message) {
        System.out.println("Client : " + message);
    }

    @Override
    public boolean ajouterPersonne(Personne personne) {
        this.log("ajouter");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(this.socket().getOutputStream());
            oos.writeUTF("ajouter");
            oos.writeObject(personne);
            oos.flush();
            return new ObjectInputStream(this.socket().getInputStream()).readBoolean();
        } catch (IOException e) {
            this.log("Error");
            return false;
        }
    }

    @Override
    public boolean modifierPersonne(Personne personne) {
        this.log("modifier");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(this.socket().getOutputStream());
            oos.writeUTF("modifier");
            oos.writeObject(personne);
            oos.flush();
            return new ObjectInputStream(this.socket().getInputStream()).readBoolean();
        } catch (IOException e) {
            this.log("Error");
            return false;
        }
    }

    @Override
    public boolean retirerPersonne(String nom) {
        this.log("retirer");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(this.socket().getOutputStream());
            oos.writeUTF("retirer");
            oos.writeUTF(nom);
            oos.flush();
            return new ObjectInputStream(this.socket().getInputStream()).readBoolean();
        } catch (IOException e) {
            this.log("Error");
            return false;
        }
    }

    @Override
    public Personne chercherPersonne(String nom) {
        this.log("cherche");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(this.socket().getOutputStream());
            oos.writeUTF("cherche");
            oos.writeUTF(nom);
            oos.flush();
            return (Personne) new ObjectInputStream(this.socket().getInputStream()).readObject();
        } catch (IOException | ClassNotFoundException e) {
            this.log("Error");
            return null;
        }
    }

    @Override
    public String[] listerPersonnes() {
        this.log("liste");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(this.socket().getOutputStream());
            oos.writeUTF("liste");
            oos.flush();
            return (new ObjectInputStream(this.socket().getInputStream())).readUTF().split("\\s+");
        } catch (IOException e) {
            this.log("Error");
            return new String[0];
        }
    }

    public Socket socket() {
        return client.socket();
    }
}
