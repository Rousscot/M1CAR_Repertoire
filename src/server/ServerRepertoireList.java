package server;

import repertoire.ListeRepertoire;
import repertoire.Repertoire;

/**
 * Created by JeCisC on 23/04/2016.
 */
public class ServerRepertoireList implements ListeRepertoire {
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
