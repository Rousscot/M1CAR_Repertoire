package repertoire;

import client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by JeCisC on 23/04/2016.
 */
public class AuthentificationIHM extends JFrame implements ActionListener {

    // Composantes de l'interface Homme Machine.

    // Champs composant l'authetification
    protected JTextField c_identifiant;
    protected JPasswordField c_password;

    // Les boutons.
    protected JButton b_valider;

    // Label pour afficher les problemes.
    protected JLabel l_probleme;

    protected Client client;

    // Constructeur.
    public AuthentificationIHM(Client client) {
        // Initialisation du Panel herite.
        super("Authetification");

        this.client = client;

        //obtenir le panel contenu par la fenÍtre.
        Container contentPane = super.getContentPane();
        // Ce panel est organise en 5 zones.
        contentPane.setLayout(new BorderLayout());

        this.setSize(400, 400);

        // Creation des boutons. La selection de ces boutons
        // est geree par l'objet IHM methode actionPerformed.
        //
        // Panel pour les boutons.
        // Celui-ci est visuellement place au centre.
        //
        Panel pb = new Panel();
        pb.setLayout(new GridLayout(6, 1));
        contentPane.add("Center", pb);

        this.b_valider = new JButton("Valider");
        pb.add(this.b_valider);
        this.b_valider.addActionListener(this);

        // Cette partie contient la fiche Personne.
        // Elle est placee au milieu.
        Panel pc = new Panel();
        pc.setLayout(new GridLayout(2, 1));
        contentPane.add("North", pc);

        // Creation des labels et des champs de saisies de la fiche Personne.
        Panel pc1 = new Panel();
        pc1.setLayout(new GridLayout(2, 2));
        pc.add(pc1);

        pc1.add(new JLabel("Identifiant :"));
        this.c_identifiant = new JTextField(20);
        pc1.add(this.c_identifiant);

        pc1.add(new JLabel("Password :"));
        this.c_password = new JPasswordField(20);
        pc1.add(this.c_password);

        // Creation du label pour afficher les possibles problemes.
        // Ce label est mis en bas de l'IHM.
        this.l_probleme = new JLabel("");
        contentPane.add("South", this.l_probleme);

        // Fixer l'erreur avec le message par defaut.
        fixerErreur(null);

    }

    // Gestion de la validation des boutons.
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == this.b_valider)
            this.valider();
    }

    // Fixer le message d'erreur.
    // Si message == null alors afficher le message par defaut.
    public void fixerErreur(String message) {
        if (message == null)
            this.l_probleme.setText("Aucun probleme a signaler.");
        else
            this.l_probleme.setText(message + " !");
    }

    public void valider() {
        if (isAuthorizedToLog()) {
            this.launchRepertoires();
        } else {
            this.l_probleme.setText("Wrong login or password!");
        }
    }

    public Boolean isAuthorizedToLog() {
        try {
            (new DataOutputStream(this.socket().getOutputStream())).writeBytes("connexion\n");
            if (new ObjectInputStream(this.socket().getInputStream()).readBoolean()) {
                (new DataOutputStream(this.socket().getOutputStream())).writeBytes(this.c_identifiant.getText() + " " + String.valueOf(this.c_password.getPassword()) + "\n");
                return new ObjectInputStream(this.socket().getInputStream()).readBoolean();
            }
        } catch (IOException e) {
            this.l_probleme.setText("NetworkError !");
        }
        return false;
    }

    public void launchRepertoires() {
        RepertoireIHM ihm = new RepertoireIHM();
        ihm.fixerRepertoires(this.client.getRepertoires());
        ihm.setVisible(true);

        this.setVisible(false);
        this.dispose();
    }

    public Socket socket() {
        return client.socket();
    }
}
