package repertoire;

public class Personne implements java.io.Serializable {
    /**
     * *  Son nom.
     **/
    protected String nom;

    /**
     * *  Son adresse email.
     **/
    protected String email;

    /**
     * *  L'URL de sa page personnelle.
     **/
    protected String url;

    /**
     * *  Un texte d'information.
     **/
    protected String info;

    /**
     * * Le constructeur.
     * *
     * * @param nom Le nom de la personne.
     * * @param email L'adresse email de la personne.
     * * @param url L'URL de sa page personnelle.
     * * @param info Un texte d'information.
     * *
     **/
    public Personne(String nom, String email, String url, String info) {
        this.nom = nom;
        this.email = email;
        this.url = url;
        this.info = info;
    }

    /**
     * * Modifier l'etat de la personne.
     * *
     * * @param p La personne a recopier.
     * *
     **/
    public void modifier(Personne p) {
        this.nom = p.nom;
        this.email = p.email;
        this.url = p.url;
        this.info = p.info;
    }

    /**
     * * Obtenir le nom de la personne.
     * *
     * * @return Son nom.
     * *
     **/
    public String getNom() {
        return this.nom;
    }

    /**
     * * Obtenir l'adresse email de la personne.
     * *
     * * @return Son adresse Email.
     * *
     **/
    public String getEmail() {
        return this.email;
    }

    /**
     * * Obtenir l'URL de la page WWW personnelle.
     * *
     * * @return L'URL de sa page WWW.
     * *
     **/
    public String getUrl() {
        return this.url;
    }

    /**
     * * Obtenir le texte d'information associe a cette personne.
     * *
     * * @return Le texte d'information.
     * *
     **/
    public String getInfo() {
        return this.info;
    }

    /**
     * * Obtenir une representation sous la forme d'une chaine de caracteres.
     * *
     * * @return La representation sous la forme d'une chaine.
     * *
     **/
    public String toString() {
        return "Personne[nom=" + nom +
                ",email=" + email +
                ",url=" + url +
                ",info=" + info +
                "]";
    }
}
