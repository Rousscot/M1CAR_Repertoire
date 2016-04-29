package repertoire;

import client.ClientRepertoireList;
import server.ServerRepertoire;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by JeCisC on 23/04/2016.
 */
public class RepertoireIHM extends JFrame implements ActionListener, ListSelectionListener {

    // Etat interne.
    // Reference sur la liste de repertoires courant.
    protected ClientRepertoireList repertoires;

    // Composantes de l'interface Homme Machine.

    // Liste des repertoires.
    protected JList l_repertoires;

    // Champs composant la fiche Repertoire.
    protected JTextField c_id;

    // Les boutons.
    protected JButton b_ajouter;
    protected JButton b_retirer;
    protected JButton b_lister;
    protected JButton b_chercher;
    protected JButton b_acceder;

    // Label pour afficher les problemes.
    protected JLabel l_probleme;

    // Constructeur.
    public RepertoireIHM() {
        // Initialisation du Panel herite.
        super("Repertoires");

        // Aucune liste de repertoires courant.
        //
        fixerRepertoires(null);

        //obtenir le panel contenu par la fenÍtre.
        Container contentPane = super.getContentPane();
        // Ce panel est organise en 5 zones.
        contentPane.setLayout(new BorderLayout());

        this.setSize(600, 400);

        // Creation des boutons. La selection de ces boutons
        // est geree par l'objet IHM methode actionPerformed.
        //
        // Panel pour les boutons.
        // Celui-ci est visuellement place a gauche.
        //
        Panel pb = new Panel();
        pb.setLayout(new GridLayout(6, 1));
        contentPane.add("West", pb);

        this.b_ajouter = new JButton("Ajouter un repertoire");
        pb.add(this.b_ajouter);
        this.b_ajouter.addActionListener(this);

        this.b_retirer = new JButton("Retirer un repertoire");
        pb.add(this.b_retirer);
        this.b_retirer.addActionListener(this);

        this.b_chercher = new JButton("Chercher un repertoire");
        pb.add(this.b_chercher);
        this.b_chercher.addActionListener(this);

        this.b_lister = new JButton("Lister les repertoires");
        pb.add(this.b_lister);
        this.b_lister.addActionListener(this);

        this.b_acceder = new JButton("Accéder à un repertoire");
        pb.add(this.b_acceder);
        this.b_acceder.addActionListener(this);

        // Cette partie contient la fiche Repertoire.
        // Elle est place au milieu.
        Panel pc = new Panel();
        pc.setLayout(new GridLayout(2, 1));
        contentPane.add("Center", pc);

        // Creation des labels et des champs de saisies de la fiche Repertoire.
        Panel pc1 = new Panel();
        pc1.setLayout(new GridLayout(3, 2));
        pc.add(pc1);

        pc1.add(new JLabel("Id :"));
        this.c_id = new JTextField(20);
        pc1.add(this.c_id);

        // Cette partie affiche la liste des repertoires contenues dans la liste.
        // Elle est placee a droite.
        this.l_repertoires = new JList();
        this.l_repertoires.addListSelectionListener(this);
        JScrollPane scrollPane = new JScrollPane(this.l_repertoires);
        contentPane.add("East", scrollPane);

        // Creation du label pour afficher les possibles problemes.
        // Ce label est mis en bas de l'IHM.
        this.l_probleme = new JLabel("");
        contentPane.add("South", this.l_probleme);

        // Fixer l'erreur avec le message par defaut.
        fixerErreur(null);

        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        // Arreter le programme.
                        System.exit(0);
                    }
                }
        );
    }

    // Gestion de la validation des boutons.
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == this.b_ajouter)
            ajouterRepertoire();
        if (source == this.b_retirer)
            retirerRepertoire(this.c_id.getText());
        if (source == this.b_chercher)
            chercherRepertoire(this.c_id.getText());
        if (source == this.b_lister)
            listerRepertoires();
        if (source == this.b_acceder)
            accederRepertoire(this.c_id.getText());
    }


    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            chercherRepertoire((String) l_repertoires.getSelectedValue());
        }
    }

    // Permet de fixer la liste de repertoires courant.
    public void fixerRepertoires(ClientRepertoireList repertoires) {
        this.repertoires = repertoires;
        if (repertoires != null) listerRepertoires();
    }

    // Fixer le message d'erreur.
    // Si message == null alors afficher le message par defaut.
    public void fixerErreur(String message) {
        if (message == null)
            this.l_probleme.setText("Aucun probleme a signaler.");
        else
            this.l_probleme.setText(message + " !");
    }

    // Obtenir la reference sur la liste de Repertoires.
    // Si la liste n'est pas encore fixe alors fixer l'erreur.
    public ClientRepertoireList repertoires() {
        if (this.repertoires == null)
            fixerErreur("Vous n'avez pas fixe la liste de repertoires courant");
        return this.repertoires;
    }

    // Creer un objet Repertoire a partir de la fiche saisie.
    public ServerRepertoire creerRepertoire() {
        return new ServerRepertoire(c_id.getText());
    }

    // Remplir la fiche Repertoire a partir des champs d'un objet Repertoire.
    public void remplirFicheRepertoire(ServerRepertoire r) {
        this.c_id.setText(r.getId());
    }

    // Lorsque le bouton Vider est selectionne alors cette methode
    // remet a blanc les champs de la fiche Repertoire.
    public void viderFicheRepertoire() {
        this.c_id.setText("");
    }

    // Lorsque le bouton Ajouter est selectionne alors cette methode
    // cree un objet Repertoire et l'ajoute à la liste.
    public void ajouterRepertoire() {
        System.out.println("IHM.ajouterRepertoire()");

        if (repertoires().ajouterRepertoire(creerRepertoire())) {
            listerRepertoires();
            viderFicheRepertoire();
            fixerErreur(null);
        } else
            fixerErreur("Ce repertoire existe deja");
    }

    // Lorsque le bouton Retirer est selectionne alors cette methode
    // retire un Repertoire.
    public void retirerRepertoire(String nom) {
        System.out.println("IHM.retirerRepertoire(" + nom + ")");

        if (repertoires().retirerRepertoire(nom)) {
            listerRepertoires();
            fixerErreur(null);
        } else
            fixerErreur("Le repertoire `" + nom + "' n'existe pas");
    }

    // Lorsque le bouton Chercher est selectionne alors cette methode
    // cherche un Repertoire.
    public void chercherRepertoire(String nom) {
        if (nom == null) return;

        System.out.println("IHM.chercherRepertoire(" + nom + ")");

        ServerRepertoire p = repertoires().chercherRepertoire(nom);
        if (p == null) {
            fixerErreur("Le repertoire `" + nom + "' n'existe pas");
        } else {
            remplirFicheRepertoire(p);
            fixerErreur(null);
        }
    }

    // Lorsque le bouton Lister est selectionne alors cette methode
    // met a jour la liste des noms des repretoires.
    public void listerRepertoires() {
        System.out.println("IHM.listerRepertoires()");

        String[] noms = repertoires().listerRepertoires();

        this.l_repertoires.removeAll();

        this.l_repertoires.setListData(noms);

        fixerErreur(null);
    }

    public void accederRepertoire(String nom) {
        if (nom == null) return;

        System.out.println("IHM.accederRepertoire(" + nom + ")");

        if(repertoires().accederRepertoire(nom)){
            this.launchRepertoire(nom);
        } else {
            fixerErreur("Le repertoire `" + nom + "' n'existe pas");
        }
    }
    public void launchRepertoire(String nom) {
        IHM ihm = new IHM();
        ihm.fixerRepertoire(this.repertoires.chercherRepertoire(nom));
        ihm.setVisible(true);

        this.setVisible(false);
        this.dispose();
    }

}
