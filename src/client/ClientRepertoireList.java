package client;

import repertoire.ListeRepertoire;
import server.ServerRepertoire;

import java.io.*;
import java.net.Socket;

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
            ObjectOutputStream oos = new ObjectOutputStream(this.socket().getOutputStream());
            oos.writeUTF("ajouterRep");
            oos.flush();
            if (new ObjectInputStream(this.socket().getInputStream()).readBoolean()) {
                new ObjectOutputStream(this.socket().getOutputStream()).writeObject(repertoire);
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
    public boolean retirerRepertoire(String nom) {
        this.log("retirerRep");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(this.socket().getOutputStream());
            oos.writeUTF("retirerRep");
            oos.flush();
            if (new ObjectInputStream(this.socket().getInputStream()).readBoolean()) {
                oos = new ObjectOutputStream(this.socket().getOutputStream());
                oos.writeUTF(nom);
                oos.flush();
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
    public ServerRepertoire chercherRepertoire(String nom) {
        this.log("chercheRep");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(this.socket().getOutputStream());
            oos.writeUTF("chercheRep");
            oos.flush();
            if (new ObjectInputStream(this.socket().getInputStream()).readBoolean()) {
                oos = new ObjectOutputStream(this.socket().getOutputStream());
                oos.writeUTF(nom);
                oos.flush();
                return (ServerRepertoire) new ObjectInputStream(this.socket().getInputStream()).readObject();
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
            ObjectOutputStream oos = new ObjectOutputStream(this.socket().getOutputStream());
            oos.writeUTF("listeRep");
            oos.flush();
            return (new ObjectInputStream(this.socket().getInputStream())).readUTF().split("\\s+");
        } catch (IOException e) {
            this.log("Error");
            return new String[0];
        }
    }

    public Boolean accederRepertoire(String nom) {
        this.log("accederRep");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(this.socket().getOutputStream());
            oos.writeUTF("accederRep");
            oos.flush();
            if (new ObjectInputStream(this.socket().getInputStream()).readBoolean()) {
                oos = new ObjectOutputStream(this.socket().getOutputStream());
                oos.writeUTF(nom);
                oos.flush();
                return new ObjectInputStream(this.socket().getInputStream()).readBoolean();
            }
            this.log("Error");
            return null;
        } catch (IOException e) {
            this.log("Error");
            return null;
        }
    }

    public Socket socket() {
        return client.socket();
    }
}
