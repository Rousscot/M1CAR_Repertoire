package server;

import repertoire.Personne;
import repertoire.Repertoire;

/**
 * Created by JeCisC on 22/04/2016.
 */
public class ServerRepertoire implements Repertoire {
    @Override
    public boolean ajouterPersonne(Personne personne) {
        return false; //TODO
    }

    @Override
    public boolean modifierPersonne(Personne personne) {
        return false; //TODO
    }

    @Override
    public boolean retirerPersonne(String nom) {
        return false; //TODO
    }

    @Override
    public Personne chercherPersonne(String nom) {
        return null; //TODO
    }

    @Override
    public String[] listerPersonnes() {
        return new String[0]; //TODO
    }
}
