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

    protected String id;

    public String getId() {
        return id;
    }

    public ServerRepertoire(String id) {
        this.id = id;
        this.contacts = new HashSet<>();
        contacts.add(new Personne("toto", "toto@gmail.com", "www.toto.fr", "I am a test :)"));
        contacts.add(new Personne("AurelienRousseau", "yao@south.park", "www.test.fr", "Hello !"));
        contacts.add(new Personne("toto2", "toto2@gmail.com", "www.toto2.fr", "I am a test2 :)"));
        contacts.add(new Personne("toto3", "toto3@gmail.com", "www.toto3.fr", "I am a test3 :)"));
        contacts.add(new Personne("toto4", "toto4@gmail.com", "www.toto4.fr", "I am a test4 :)"));
    }

    @Override
    public boolean ajouterPersonne(Personne personne) {
        return this.contacts.add(personne);
    }

    @Override
    public boolean modifierPersonne(Personne personne) {
        Personne actual = this.chercherPersonne(personne.getNom());
        return this.contacts.remove(actual) && this.contacts.add(personne);
    }

    @Override
    public boolean retirerPersonne(String nom) {
        for (Personne contact : this.contacts) {
            if (contact.getNom().equals(nom)) {
                System.out.println("Return user");
                return this.contacts.remove(contact);
            }
        }
        return false;
    }

    @Override
    public Personne chercherPersonne(String nom) {
        for (Personne contact : this.contacts) {
            if (contact.getNom().equals(nom)) {
                return contact;
            }
        }
        return null;
    }

    @Override
    public String[] listerPersonnes() {
        List<String> list = new ArrayList<>();
        this.contacts.forEach((contact) -> list.add(contact.getNom()));
        return list.toArray(new String[list.size()]);
    }
}
