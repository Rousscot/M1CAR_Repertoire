package client;

import repertoire.Personne;
import repertoire.Repertoire;

import java.io.*;
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
            (new DataOutputStream(this.socket().getOutputStream())).writeBytes("ajouter\n");
            if (new ObjectInputStream(this.socket().getInputStream()).readBoolean()) {
                new ObjectOutputStream(this.socket().getOutputStream()).writeObject(personne);
                return new ObjectInputStream(this.socket().getInputStream()).readBoolean();
            }
            this.log("Error");
            return false;
        } catch (IOException e) {
            this.log("Error");
            return false;
        }
    }

    @Override
    public boolean modifierPersonne(Personne personne) {
        this.log("modifier");
        try {
            (new DataOutputStream(this.socket().getOutputStream())).writeBytes("modifier\n");
            if (new ObjectInputStream(this.socket().getInputStream()).readBoolean()) {
                new ObjectOutputStream(this.socket().getOutputStream()).writeObject(personne);
                return new ObjectInputStream(this.socket().getInputStream()).readBoolean();
            }
            this.log("Error");
            return false;
        } catch (IOException e) {
            this.log("Error");
            return false;
        }
    }

    @Override
    public boolean retirerPersonne(String nom) {
        this.log("retirer");
        try {
            (new DataOutputStream(this.socket().getOutputStream())).writeBytes("retirer\n");
            if (new ObjectInputStream(this.socket().getInputStream()).readBoolean()) {
                new DataOutputStream(this.socket().getOutputStream()).writeBytes(nom + "\n");
                return new ObjectInputStream(this.socket().getInputStream()).readBoolean();
            }
            this.log("Error");
            return false;
        } catch (IOException e) {
            this.log("Error");
            return false;
        }
    }

    @Override
    public Personne chercherPersonne(String nom) {
        this.log("cherche");
        try {
            new DataOutputStream(this.socket().getOutputStream()).writeBytes("cherche\n");
            if (new ObjectInputStream(this.socket().getInputStream()).readBoolean()) {
                new DataOutputStream(this.socket().getOutputStream()).writeBytes(nom + "\n");
                return (Personne) new ObjectInputStream(this.socket().getInputStream()).readObject();
            }
            this.log("Error");
            return null;
        } catch (IOException | ClassNotFoundException e) {
            this.log("Error");
            return null;
        }
    }

    @Override
    public String[] listerPersonnes() {
        this.log("liste");
        try {
            new DataOutputStream(this.socket().getOutputStream()).writeBytes("liste\n");
            return (new BufferedReader(new InputStreamReader(this.socket().getInputStream())).readLine()).split("\\s+");
        } catch (IOException e) {
            this.log("Error");
            return new String[0];
        }
    }

    public Socket socket() {
        return client.socket();
    }
}
