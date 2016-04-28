package client;

import repertoire.Personne;
import repertoire.Repertoire;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
            this.oos().writeUTF("ajouter");
            this.oos().writeObject(personne);
            this.oos().flush();
            return this.ois().readBoolean();
        } catch (IOException e) {
            this.log("Error");
            return false;
        }
    }

    @Override
    public boolean modifierPersonne(Personne personne) {
        this.log("modifier");
        try {
            this.oos().writeUTF("modifier");
            this.oos().writeObject(personne);
            this.oos().flush();
            return this.ois().readBoolean();
        } catch (IOException e) {
            this.log("Error");
            return false;
        }
    }

    @Override
    public boolean retirerPersonne(String nom) {
        this.log("retirer");
        try {
            this.oos().writeUTF("retirer");
            this.oos().writeUTF(nom);
            this.oos().flush();
            return this.ois().readBoolean();
        } catch (IOException e) {
            this.log("Error");
            return false;
        }
    }

    @Override
    public Personne chercherPersonne(String nom) {
        this.log("cherche");
        try {
            this.oos().writeUTF("cherche");
            this.oos().writeUTF(nom);
            this.oos().flush();
            return (Personne) this.ois().readObject();
        } catch (IOException | ClassNotFoundException e) {
            this.log("Error");
            return null;
        }
    }

    @Override
    public String[] listerPersonnes() {
        this.log("liste");
        try {
            this.oos().writeUTF("liste");
            this.oos().flush();
            return this.ois().readUTF().split("\\s+");
        } catch (IOException e) {
            this.log("Error");
            return new String[0];
        }
    }

    public ObjectOutputStream oos() {
        return client.oos();
    }

    public ObjectInputStream ois() {
        return client.ois();
    }
}
