package server;

import repertoire.Personne;
import repertoire.Repertoire;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by JeCisC on 22/04/2016.
 */
public class ServerRepertoire implements Repertoire {

    protected Set<Personne> contacts;

    public ServerRepertoire(){
        this.contacts = new HashSet<>();
        contacts.add(new Personne("toto", "toto@gmail.com", "www.toto.fr", "I am a test :)"));
    }

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
        List<String> list = new ArrayList<>();
        this.contacts.forEach((contact) -> list.add(contact.getNom()));
        return list.toArray(new String[list.size()]);
    }
}
