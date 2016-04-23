package server;

import repertoire.ListeRepertoire;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by JeCisC on 23/04/2016.
 */
public class ServerRepertoireList implements ListeRepertoire {

    protected Set<ServerRepertoire> repertoires;

    public ServerRepertoireList() {
        this.repertoires = new HashSet<>();
        repertoires.add(new ServerRepertoire("test"));
        repertoires.add(new ServerRepertoire("testons"));
        repertoires.add(new ServerRepertoire("Yao_Repertoire"));
    }

    @Override
    public boolean ajouterRepertoire(ServerRepertoire repertoire) {
        return this.repertoires.add(repertoire);
    }

    @Override
    public boolean retirerRepertoire(String nom) {
        for (ServerRepertoire repertoire : this.repertoires) {
            if (repertoire.getId().equals(nom)) {
                System.out.println("Return user");
                return this.repertoires.remove(repertoire);
            }
        }
        return false;
    }

    @Override
    public ServerRepertoire chercherRepertoire(String nom) {
        for (ServerRepertoire repertoire : this.repertoires) {
            if (repertoire.getId().equals(nom)) {
                return repertoire;
            }
        }
        return null;
    }

    @Override
    public String[] listerRepertoires() {
        List<String> list = new ArrayList<>();
        this.repertoires.forEach((repertoire) -> list.add(repertoire.getId()));
        return list.toArray(new String[list.size()]);
    }
}
