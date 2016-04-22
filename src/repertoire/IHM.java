
package repertoire;

public class IHM
       extends javax.swing.JFrame
implements java.awt.event.ActionListener, javax.swing.event.ListSelectionListener
{
  //
  // Etat interne.
  //
  // Reference sur le repertoire courant.
  //
  protected Repertoire le_repertoire;

  //
  // Composantes de l'interface Homme Machine.
  //

  // Liste des personnes.
  //
  protected javax.swing.JList l_personnes;

  // Champs composant la fiche Personne.
  //
  protected javax.swing.JTextField c_nom;
  protected javax.swing.JTextField c_email;
  protected javax.swing.JTextField c_url;
  protected javax.swing.JTextArea c_info;

  // Les boutons.
  //
  protected javax.swing.JButton b_vider;
  protected javax.swing.JButton b_ajouter;
  protected javax.swing.JButton b_modifier;
  protected javax.swing.JButton b_retirer;
  protected javax.swing.JButton b_lister;
  protected javax.swing.JButton b_chercher;

  // Label pour afficher les problemes.
  //
  javax.swing.JLabel l_probleme;

  // Constructeur.
  //
  public IHM ()
  {
    // Initialisation du Panel herite.
    super ("Repertoires");
  
    // Aucun repertoire courant.
    //
    fixerRepertoire (null);

	//obtenir le panel contenu par la fenÍtre.
	java.awt.Container contentPane = super.getContentPane();
	// Ce panel est organise en 5 zones.
    contentPane.setLayout (new java.awt.BorderLayout());

    // Creation des boutons. La selection de ces boutons
    // est geree par l'objet IHM methode actionPerformed.
    //
    // Panel pour les boutons.
    // Celui-ci est visuellement place a gauche.
    //
    java.awt.Panel pb = new java.awt.Panel();
    pb.setLayout (new java.awt.GridLayout(6,1));
    contentPane.add("West",pb);

    this.b_vider = new javax.swing.JButton("Effacer la fiche");
    pb.add(this.b_vider);
    this.b_vider.addActionListener (this);

    this.b_ajouter = new javax.swing.JButton("Ajouter une personne");
    pb.add(this.b_ajouter);
    this.b_ajouter.addActionListener (this);

    this.b_modifier = new javax.swing.JButton("Modifier une personne");
    pb.add(this.b_modifier);
    this.b_modifier.addActionListener (this);

    this.b_retirer = new javax.swing.JButton("Retirer une personne");
    pb.add(this.b_retirer);
    this.b_retirer.addActionListener (this);

    this.b_chercher = new javax.swing.JButton("Chercher une personne");
    pb.add(this.b_chercher);
    this.b_chercher.addActionListener (this);

    this.b_lister = new javax.swing.JButton("Lister les personnes");
    pb.add(this.b_lister);
    this.b_lister.addActionListener (this);

    // Cette partie contient la fiche Personne.
    // Elle est placee au milieu.
    //
    java.awt.Panel pc = new java.awt.Panel();
    pc.setLayout (new java.awt.GridLayout(2,1));
    contentPane.add("Center",pc);

    // Creation des labels et des champs de saisies de la fiche Personne.
    //
    java.awt.Panel pc1 = new java.awt.Panel();
    pc1.setLayout (new java.awt.GridLayout(3,2));
    pc.add(pc1);

    pc1.add(new javax.swing.JLabel("Nom :"));
    this.c_nom = new javax.swing.JTextField(20);
    pc1.add(this.c_nom);

    pc1.add(new javax.swing.JLabel("Email :"));
    this.c_email = new javax.swing.JTextField(20);
    pc1.add(this.c_email);

    pc1.add(new javax.swing.JLabel("URL :"));
    this.c_url = new javax.swing.JTextField(20);
    pc1.add(this.c_url);

    java.awt.Panel pc2 = new java.awt.Panel();
    pc2.setLayout (new java.awt.GridLayout(1,2));
    pc.add(pc2);

    pc2.add(new javax.swing.JLabel("Informations :"));
    this.c_info = new javax.swing.JTextArea(5,20);
    pc2.add(this.c_info);

    // Cette partie affiche la liste des personnes contenues dans le repertoire.
    // Elle est placee a droite.
    //
    this.l_personnes = new javax.swing.JList();
    this.l_personnes.addListSelectionListener(this);
    javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(this.l_personnes);
	contentPane.add("East", scrollPane);

    // Creation du label pour afficher les possibles problemes.
    // Ce label est mis en bas de l'IHM.
    //
    this.l_probleme = new javax.swing.JLabel("");
    contentPane.add ("South", this.l_probleme);

    // Fixer l'erreur avec le message par defaut.
    //
    fixerErreur (null);
	  
    addWindowListener(
				new java.awt.event.WindowAdapter() {
						public void windowClosing(java.awt.event.WindowEvent e)
						{
						  // Arreter le programme.
						  System.exit(0);
						}
				}
			);
  }

  // Gestion de la validation des boutons.
  //
  public void actionPerformed (java.awt.event.ActionEvent e)
  {
    Object source = e.getSource();
    if (source == this.b_vider)
	viderFichePersonne();
    if (source == this.b_ajouter)
	ajouterPersonne();
    if (source == this.b_modifier)
	modifierPersonne(this.c_nom.getText());
    if (source == this.b_retirer)
	retirerPersonne(this.c_nom.getText());
    if (source == this.b_chercher)
	chercherPersonne(this.c_nom.getText());
    if (source == this.b_lister)
	listerPersonnes();
  }


  public void 	valueChanged(javax.swing.event.ListSelectionEvent e) 
  {
	  chercherPersonne ((String)l_personnes.getSelectedValue());
  }
	
  //
  // Permet de fixer le repertoire courant.
  //
  public void fixerRepertoire (Repertoire repertoire)
  {
    this.le_repertoire = repertoire;
    if (repertoire != null) listerPersonnes ();
  }

  // Fixer le message d'erreur.
  // Si message == null alors afficher le message par defaut.
  //
  public void fixerErreur (String message)
  {
    if (message == null)
      this.l_probleme.setText ("Aucun probleme a signaler.");
    else
      this.l_probleme.setText (message + " !");
  }

  // Obtenir la reference sur le Repertoire.
  // Si le repertoire n'est pas encore fixe alors fixer l'erreur.
  //
  public Repertoire leRepertoire()
  {
    if ( this.le_repertoire == null )
      fixerErreur ("Vous n'avez pas fixe le repertoire courant");
    return this.le_repertoire;
  }

  //
  // Creer un objet Personne a partir de la fiche saisie.
  //
  public Personne creerPersonne ()
  {
    return new Personne(c_nom.getText(),
			c_email.getText(),
			c_url.getText(),
			c_info.getText()
	);
  }

  //
  // Remplir la fiche Personne a partir des champs d'un objet Personne.
  //
  public void remplirFichePersonne (Personne p)
  {
    this.c_nom.setText(p.getNom());
    this.c_email.setText(p.getEmail());
    this.c_url.setText(p.getUrl());
    this.c_info.setText(p.getInfo());
  }

  //
  // Lorsque le bouton Vider est selectionne alors cette methode
  // remet a blanc les champs de la fiche Personne.
  //
  public void viderFichePersonne ()
  {
    this.c_nom.setText("");
    this.c_email.setText("");
    this.c_url.setText("");
    this.c_info.setText("");
  }

  //
  // Lorsque le bouton Ajouter est selectionne alors cette methode
  // cree un objet Personne et l'ajoute au Repertoire.
  //
  public void ajouterPersonne ()
  {
    System.out.println("IHM.ajouterPersonne()");

    if ( leRepertoire().ajouterPersonne (creerPersonne()) ) {
      listerPersonnes ();
      viderFichePersonne ();
      fixerErreur(null);
    } else
      fixerErreur ("Cette personne existe deja");
  }

  //
  // Lorsque le bouton Modifier est selectionne alors cette methode
  // cree un objet Personne et remplace celle existante dans le Repertoire.
  //
  public void modifierPersonne (String nomPersonne)
  {
    System.out.println("IHM.modifierPersonne(" + nomPersonne + ")");

    if ( leRepertoire().modifierPersonne (creerPersonne()) )
      fixerErreur(null);
    else
      fixerErreur("La personne `" + nomPersonne + "' n'existe pas");
  }

  //
  // Lorsque le bouton Retirer est selectionne alors cette methode
  // retire une Personne du Repertoire.
  //
  public void retirerPersonne (String nomPersonne)
  {
    System.out.println("IHM.retirerPersonne(" + nomPersonne + ")");

    if ( leRepertoire().retirerPersonne (nomPersonne) ) {
      listerPersonnes ();
      fixerErreur(null);
    } else
      fixerErreur("La personne `" + nomPersonne + "' n'existe pas");
  }

  //
  // Lorsque le bouton Chercher est selectionne alors cette methode
  // cherche une Personne dans le Repertoire.
  //
  public void chercherPersonne (String nomPersonne)
 {
	 if (nomPersonne == null) return;
	 
    System.out.println("IHM.chercherPersonne(" + nomPersonne + ")");

    Personne p = leRepertoire().chercherPersonne (nomPersonne);
    if (p == null) {
      fixerErreur("La personne `" + nomPersonne + "' n'existe pas");
    } else {
      remplirFichePersonne (p);
      fixerErreur(null);
    }
  }

  //
  // Lorsque le bouton Lister est selectionne alors cette methode
  // met a jour la liste des noms des personnes du repertoire courant.
  //
  public void listerPersonnes ()
  {
    System.out.println("IHM.listerPersonnes()");

    String [] noms = leRepertoire().listerPersonnes ();

    this.l_personnes.removeAll ();

    this.l_personnes.setListData(noms);

    fixerErreur(null);
  }

}
