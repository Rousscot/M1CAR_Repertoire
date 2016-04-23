package repertoire;

import server.ServerRepertoire;

/**
 * Created by JeCisC on 23/04/2016.
 */
public interface ListeRepertoire {

    /**
     * * Ajouter un repertoire dans la liste.
     * *
     * * @param repertoire Le repertoire a ajouter.
     * * @return false Si deja present.
     * *
     **/
    boolean ajouterRepertoire(ServerRepertoire repertoire);

    /**
     * * Retirer un repertoire de la liste.
     * *
     * * @param nom Le nom du repertoire a supprimer.
     * * @return false Si le nom du repertoire n'existe pas.
     * *
     **/
    boolean retirerRepertoire(String nom);

    /**
     * * Rechercher un repertoire dans la liste.
     * *
     * * @param nom Le nom du repertoire a rechercher.
     * * @return null Si le repertoire n'existe pas.
     * *
     **/
    ServerRepertoire chercherRepertoire(String nom);

    /**
     * * Lister les noms des repertoires.
     * *
     * * @return Un tableau des noms des repertoires.
     * *
     **/
    String[] listerRepertoires();
}
