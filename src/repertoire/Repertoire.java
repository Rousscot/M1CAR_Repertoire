package repertoire;

public interface Repertoire {
    /**
     * * Ajouter une personne dans le repertoire.
     * *
     * * @param personne La personne a ajouter.
     * * @return false Si deja presente.
     * *
     **/
    public boolean ajouterPersonne(Personne personne);

    /**
     * * Modifier une personne dans le repertoire.
     * *
     * * @param personne la personne a modifier.
     * * @return false Si le nom de la personne n'existe pas.
     * *
     **/
    public boolean modifierPersonne(Personne personne);

    /**
     * * Retirer une personne du repertoire.
     * *
     * * @param nom Le nom de la personne a supprimer.
     * * @return false Si le nom de la personne n'existe pas.
     * *
     **/
    public boolean retirerPersonne(String nom);

    /**
     * * Rechercher une personne dans le repertoire.
     * *
     * * @param nom Le nom de la personne a rechercher.
     * * @return null Si la personne n'existe pas.
     * *
     **/
    public Personne chercherPersonne(String nom);

    /**
     * * Lister les noms des personnes.
     * *
     * * @return Un tableau des noms des personnes.
     * *
     **/
    public String[] listerPersonnes();
}


