package client;

import repertoire.ListeRepertoire;
import server.ServerRepertoire;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by JeCisC on 23/04/2016.
 */
public class ClientRepertoireList implements ListeRepertoire {

    protected Client client;

    public ClientRepertoireList(Client client) throws IOException {
        this.client = client;
    }

    public void log(String message) {
        System.out.println("Client Repertoire List : " + message);
    }

    @Override
    public boolean ajouterRepertoire(ServerRepertoire repertoire) {
        this.log("ajouterRep");
        try {
            this.oos().writeUTF("ajouterRep");
            this.oos().writeObject(repertoire);
            this.oos().flush();
            return this.ois().readBoolean();
        } catch (IOException e) {
            this.log("Error");
            return false;
        }
    }

    @Override
    public boolean retirerRepertoire(String nom) {
        this.log("retirerRep");
        try {
            this.oos().writeUTF("retirerRep");
            this.oos().writeUTF(nom);
            this.oos().flush();
            return this.ois().readBoolean();
        } catch (IOException e) {
            this.log("Error");
            return false;
        }
    }

    @Override
    public ServerRepertoire chercherRepertoire(String nom) {
        this.log("chercheRep");
        try {
            this.oos().writeUTF("chercheRep");
            this.oos().writeUTF(nom);
            this.oos().flush();
            return (ServerRepertoire) this.ois().readObject();
        } catch (IOException | ClassNotFoundException e) {
            this.log("Error");
            return null;
        }
    }

    @Override
    public String[] listerRepertoires() {
        this.log("listeRep");
        try {
            this.oos().writeUTF("listeRep");
            this.oos().flush();
            return this.ois().readUTF().split("\\s+");
        } catch (IOException e) {
            this.log("Error");
            return new String[0];
        }
    }

    public Boolean accederRepertoire(String nom) {
        this.log("accederRep");
        try {
            this.oos().writeUTF("accederRep");
            this.oos().writeUTF(nom);
            this.oos().flush();
            return this.ois().readBoolean();
        } catch (IOException e) {
            this.log("Error");
            return null;
        }
    }

    public ObjectOutputStream oos() {
        return client.oos();
    }

    public ObjectInputStream ois() {
        return client.ois();
    }
}
