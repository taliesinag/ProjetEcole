package ihm;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import org.joda.time.DateTime;

import beans.Emprunt;
import beans.Livre;
import beans.Usager;
import controller.Controller;

public class IhmMain {

	private Controller ctrl;

	private JFrame frmMaPetiteBibliothque;
	private JTextField txtFieldTitre;

	private JMenuBar menuBar;
	private JMenu mnLivres;
	private JMenu mnUsagers;
	private JMenu mnEmprunts;
	private JPanel panelLivres;
	private JPanel panelUsager;
	private JPanel panelEmprunt;
	private JButton btnAjoutEmprunt;
	private JButton btnAjouterUsager;
	private JLabel lblTitre;
	private JMenu menuBlank1;
	private JMenu menuBlank2;
	private JLabel lblAnnee;
	private JFormattedTextField txtFieldAnnee;
	private JLabel lblNomAuteur;
	private JTextField txtFieldNomAuteur;
	private JLabel lblPrenomAuteur;
	private JTextField txtFieldPrenomAuteur;
	private JLabel lblEditeur;
	private JTextField txtFieldEditeur;
	private JTextField txtFieldNom;
	private JButton btnAjoutLivre;
	private JLayeredPane layeredPane;
	private JLabel lblNomUsager;
	public JComboBox<Usager> comboBoxUsager;
	public JComboBox<Livre> comboBoxLivre;
	private JFormattedTextField formatTxtFieldDateEmprunt;
	private JFormattedTextField formatTxtFieldDateRetour;
	private JLabel lblComboNomUsager;
	private JLabel lblComboTitreLivre;
	private DateFormat formatDate;
	private NumberFormat integerFieldFormatter;
	private JMenu menu;
	private JMenu mnNewUsagers;
	private JMenuItem mntmNouveau;
	private JMenuItem mntmRendre;
	private JPanel panelRendreEmprunt;
	private JButton btnRendreLeLivre;
	private JLabel lblNewLabel;
	private JTextField textFieldUsagerEmprunteur;
	private JMenuItem mnNouveauUsager;
	private JMenuItem mnNouveauLivre;
	public JComboBox<Emprunt> comboBoxLivreARendre;
	private JLabel lblUsager;
	private JMenuItem mntmJeeUsager;

	/**
	 * Create the application.
	 */
	public IhmMain(Controller ctrl) {
		this.ctrl = ctrl;
		this.initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.formatDate = new SimpleDateFormat("dd--MMMM--yyyy");

		this.integerFieldFormatter = NumberFormat.getIntegerInstance();
		this.integerFieldFormatter.setGroupingUsed(false);
		this.integerFieldFormatter.setMaximumFractionDigits(0);
		this.frmMaPetiteBibliothque = new JFrame();
		BorderLayout borderLayout = (BorderLayout) this.frmMaPetiteBibliothque.getContentPane().getLayout();
		this.frmMaPetiteBibliothque.setTitle("Ma petite bibliothèque");
		this.frmMaPetiteBibliothque.setBounds(100, 100, 1124, 900);
		this.frmMaPetiteBibliothque.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.menuBar = new JMenuBar();
		this.frmMaPetiteBibliothque.setJMenuBar(this.menuBar);

		this.mnLivres = new JMenu("Livres");
		this.menuBar.add(this.mnLivres);

		this.mnNouveauLivre = new JMenuItem("Nouveau");
		this.mnNouveauLivre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IhmMain.this.panelLivres.setVisible(true);
				IhmMain.this.panelUsager.setVisible(false);
				IhmMain.this.panelEmprunt.setVisible(false);
				IhmMain.this.panelRendreEmprunt.setVisible(false);
			}
		});
		this.mnLivres.add(this.mnNouveauLivre);

		this.mnUsagers = new JMenu("Usagers");
		this.menuBlank1 = new JMenu("          ");
		this.menuBlank1.setEnabled(false);
		this.menuBar.add(this.menuBlank1);
		this.menuBar.add(this.mnUsagers);

		this.mnNouveauUsager = new JMenuItem("Nouveau");
		this.mnNouveauUsager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IhmMain.this.panelLivres.setVisible(false);
				IhmMain.this.panelUsager.setVisible(true);
				IhmMain.this.panelEmprunt.setVisible(false);
				IhmMain.this.panelRendreEmprunt.setVisible(false);
			}
		});
		this.mnUsagers.add(this.mnNouveauUsager);

		this.mnEmprunts = new JMenu("Emprunts");

		this.menuBlank2 = new JMenu("          ");
		this.menuBlank2.setEnabled(false);
		this.menuBar.add(this.menuBlank2);
		this.menuBar.add(this.mnEmprunts);

		this.mntmNouveau = new JMenuItem("Nouveau");
		this.mntmNouveau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IhmMain.this.panelLivres.setVisible(false);
				IhmMain.this.panelUsager.setVisible(false);
				IhmMain.this.panelEmprunt.setVisible(true);
				IhmMain.this.panelRendreEmprunt.setVisible(false);
			}
		});

		this.mnEmprunts.add(this.mntmNouveau);

		this.mntmRendre = new JMenuItem("Rendre");
		this.mntmRendre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IhmMain.this.panelLivres.setVisible(false);
				IhmMain.this.panelUsager.setVisible(false);
				IhmMain.this.panelEmprunt.setVisible(false);
				IhmMain.this.panelRendreEmprunt.setVisible(true);
			}
		});

		this.mnEmprunts.add(this.mntmRendre);

		this.menu = new JMenu("          ");
		this.menu.setEnabled(false);
		this.menuBar.add(this.menu);

		this.mnNewUsagers = new JMenu("BDD");
		this.menuBar.add(this.mnNewUsagers);
		
		mntmJeeUsager = new JMenuItem("JEE Usager");
		mntmJeeUsager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IhmMain.this.ctrl.usagerBdd();
			}
		});
		mnNewUsagers.add(mntmJeeUsager);

		this.layeredPane = new JLayeredPane();
		this.frmMaPetiteBibliothque.getContentPane().add(this.layeredPane, BorderLayout.CENTER);

		this.panelRendreEmprunt = new JPanel();
		this.panelRendreEmprunt.setVisible(false);
		this.panelRendreEmprunt.setBounds(0, 0, 1108, 838);
		this.layeredPane.add(this.panelRendreEmprunt);
		this.panelRendreEmprunt.setLayout(null);

		this.btnRendreLeLivre = new JButton("Rendre le livre");
		this.btnRendreLeLivre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IhmMain.this.ctrl.RendreLivre(IhmMain.this.comboBoxLivreARendre
						.getItemAt(IhmMain.this.comboBoxLivreARendre.getSelectedIndex()));
			}
		});
		this.btnRendreLeLivre.setBounds(372, 250, 142, 23);
		this.panelRendreEmprunt.add(this.btnRendreLeLivre);

		this.comboBoxLivreARendre = new JComboBox<Emprunt>();
		comboBoxLivreARendre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IhmMain.this.textFieldUsagerEmprunteur.setText(comboBoxLivreARendre.getItemAt(comboBoxLivreARendre.getSelectedIndex()).getUsager().getNom());
			}
		});
		this.comboBoxLivreARendre.setBounds(291, 53, 301, 20);
		this.panelRendreEmprunt.add(this.comboBoxLivreARendre);

		this.lblNewLabel = new JLabel("Livres à rendre");
		this.lblNewLabel.setBounds(162, 56, 119, 14);
		this.panelRendreEmprunt.add(this.lblNewLabel);

		this.textFieldUsagerEmprunteur = new JTextField();
		this.textFieldUsagerEmprunteur.setEditable(false);
		this.textFieldUsagerEmprunteur.setBounds(291, 98, 178, 20);
		this.panelRendreEmprunt.add(this.textFieldUsagerEmprunteur);
		this.textFieldUsagerEmprunteur.setColumns(10);

		this.lblUsager = new JLabel("Usager");
		this.lblUsager.setBounds(164, 101, 76, 14);
		this.panelRendreEmprunt.add(this.lblUsager);

		this.panelLivres = new JPanel();
		this.panelLivres.setBounds(0, 0, 1108, 838);
		this.layeredPane.add(this.panelLivres);
		this.panelLivres.setVisible(false);
		this.panelLivres.setLayout(null);

		this.lblTitre = new JLabel("Titre");
		this.lblTitre.setBounds(84, 34, 59, 14);
		this.panelLivres.add(this.lblTitre);

		this.lblAnnee = new JLabel("Année de parution");
		this.lblAnnee.setBounds(83, 73, 137, 14);
		this.panelLivres.add(this.lblAnnee);

		this.txtFieldAnnee = new JFormattedTextField(this.integerFieldFormatter);
		this.txtFieldAnnee.setBounds(240, 73, 406, 14);
		this.txtFieldAnnee.setColumns(50);
		this.panelLivres.add(this.txtFieldAnnee);

		this.lblNomAuteur = new JLabel("Nom de l'auteur");
		this.lblNomAuteur.setBounds(84, 122, 111, 14);
		this.panelLivres.add(this.lblNomAuteur);

		this.txtFieldNomAuteur = new JTextField();
		this.txtFieldNomAuteur.setBounds(240, 119, 406, 20);
		this.txtFieldNomAuteur.setToolTipText("");
		this.txtFieldNomAuteur.setColumns(50);
		this.panelLivres.add(this.txtFieldNomAuteur);

		this.lblPrenomAuteur = new JLabel("Prénom de l'auteur");
		this.lblPrenomAuteur.setBounds(83, 170, 137, 14);
		this.panelLivres.add(this.lblPrenomAuteur);

		this.txtFieldPrenomAuteur = new JTextField();
		this.txtFieldPrenomAuteur.setBounds(240, 167, 406, 20);
		this.txtFieldPrenomAuteur.setToolTipText("");
		this.txtFieldPrenomAuteur.setColumns(50);
		this.panelLivres.add(this.txtFieldPrenomAuteur);

		this.btnAjoutLivre = new JButton("Créer le livre");
		this.btnAjoutLivre.setBounds(351, 383, 157, 23);
		this.btnAjoutLivre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IhmMain.this.ctrl.AjouterLivre(IhmMain.this.txtFieldTitre.getText(),
						Integer.parseInt(IhmMain.this.txtFieldAnnee.getText()),
						IhmMain.this.txtFieldNomAuteur.getText(), IhmMain.this.txtFieldPrenomAuteur.getText(),
						IhmMain.this.txtFieldEditeur.getText());
				IhmMain.this.txtFieldTitre.setText("");
				IhmMain.this.txtFieldAnnee.setText("");
				IhmMain.this.txtFieldNomAuteur.setText("");
				IhmMain.this.txtFieldPrenomAuteur.setText("");
				IhmMain.this.txtFieldEditeur.setText("");
			}
		});

		this.txtFieldTitre = new JTextField();
		this.txtFieldTitre.setBounds(240, 31, 406, 20);
		this.panelLivres.add(this.txtFieldTitre);
		this.txtFieldTitre.setToolTipText("");
		this.txtFieldTitre.setColumns(50);
		this.panelLivres.add(this.btnAjoutLivre);

		this.lblEditeur = new JLabel("Editeur");
		this.lblEditeur.setBounds(84, 224, 98, 14);
		this.panelLivres.add(this.lblEditeur);

		this.txtFieldEditeur = new JTextField();
		this.txtFieldEditeur.setBounds(240, 221, 406, 20);
		this.txtFieldEditeur.setToolTipText("");
		this.txtFieldEditeur.setColumns(50);
		this.panelLivres.add(this.txtFieldEditeur);
		this.panelLivres.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { this.lblTitre,
				this.txtFieldTitre, this.lblAnnee, this.lblNomAuteur, this.txtFieldNomAuteur, this.lblPrenomAuteur,
				this.txtFieldPrenomAuteur, this.lblEditeur, this.txtFieldEditeur, this.btnAjoutLivre }));

		this.panelEmprunt = new JPanel();
		this.panelEmprunt.setBounds(0, 0, 1108, 838);
		this.layeredPane.add(this.panelEmprunt);
		this.panelEmprunt.setLayout(null);

		this.btnAjoutEmprunt = new JButton("Valider l'emprunt");
		this.btnAjoutEmprunt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DateTime emprunt = new DateTime();
				try {

				} catch (Exception e) {
					// TODO: handle exception
				}
				IhmMain.this.ctrl.AjouterEmprunt(
						IhmMain.this.comboBoxLivre.getItemAt(IhmMain.this.comboBoxLivre.getSelectedIndex()),
						IhmMain.this.comboBoxUsager.getItemAt(IhmMain.this.comboBoxUsager.getSelectedIndex()), emprunt,
						null);
			}
		});
		this.btnAjoutEmprunt.setBounds(372, 314, 167, 23);
		this.panelEmprunt.add(this.btnAjoutEmprunt);

		this.comboBoxUsager = new JComboBox<Usager>();
		this.comboBoxUsager.setBounds(344, 70, 315, 20);
		this.panelEmprunt.add(this.comboBoxUsager);

		this.comboBoxLivre = new JComboBox<Livre>();
		this.comboBoxLivre.setEditable(false);
		this.comboBoxLivre.setBounds(344, 116, 317, 20);
		this.panelEmprunt.add(this.comboBoxLivre);

		this.formatTxtFieldDateEmprunt = new JFormattedTextField(this.formatDate);
		this.formatTxtFieldDateEmprunt.setBounds(438, 184, 141, 20);
		this.formatTxtFieldDateEmprunt.setVisible(false);
		this.panelEmprunt.add(this.formatTxtFieldDateEmprunt);

		this.formatTxtFieldDateRetour = new JFormattedTextField(this.formatDate);
		this.formatTxtFieldDateRetour.setBounds(438, 227, 141, 20);
		this.formatTxtFieldDateRetour.setVisible(false);
		this.panelEmprunt.add(this.formatTxtFieldDateRetour);

		this.lblComboNomUsager = new JLabel("Nom d'usager");
		this.lblComboNomUsager.setBounds(156, 76, 111, 14);
		this.panelEmprunt.add(this.lblComboNomUsager);

		this.lblComboTitreLivre = new JLabel("Titre de livre");
		this.lblComboTitreLivre.setBounds(156, 118, 91, 14);
		this.panelEmprunt.add(this.lblComboTitreLivre);
		this.panelEmprunt.setVisible(false);
		this.frmMaPetiteBibliothque.getContentPane().setFocusTraversalPolicy(
				new FocusTraversalOnArray(new Component[] { this.panelLivres, this.lblTitre, this.txtFieldTitre,
						this.lblAnnee, this.lblNomAuteur, this.txtFieldNomAuteur, this.lblPrenomAuteur,
						this.txtFieldPrenomAuteur, this.lblEditeur, this.txtFieldEditeur, this.btnAjoutLivre,
						this.panelUsager, this.btnAjouterUsager, this.panelEmprunt, this.btnAjoutEmprunt }));
		this.frmMaPetiteBibliothque.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] {
				this.frmMaPetiteBibliothque.getContentPane(), this.panelUsager, this.panelEmprunt, this.btnAjoutEmprunt,
				this.panelLivres, this.lblTitre, this.lblAnnee, this.lblNomAuteur, this.txtFieldNomAuteur,
				this.lblPrenomAuteur, this.txtFieldPrenomAuteur, this.txtFieldTitre, this.btnAjoutLivre,
				this.lblEditeur, this.txtFieldEditeur, this.btnAjouterUsager, this.txtFieldNom, this.lblNomUsager,
				this.menuBar, this.mnLivres, this.menuBlank1, this.mnUsagers, this.menuBlank2, this.mnEmprunts }));

		this.panelUsager = new JPanel();
		this.panelUsager.setBounds(0, 0, 1108, 839);
		this.layeredPane.add(this.panelUsager);
		this.panelUsager.setLayout(null);

		this.btnAjouterUsager = new JButton("Créer nouvel usager");
		this.btnAjouterUsager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IhmMain.this.ctrl.AjouterUsager(IhmMain.this.txtFieldNom.getText());
				IhmMain.this.txtFieldNom.setText("");
			}
		});
		this.btnAjouterUsager.setBounds(241, 151, 204, 23);
		this.panelUsager.add(this.btnAjouterUsager);

		this.txtFieldNom = new JTextField();
		this.txtFieldNom.setBounds(310, 75, 198, 20);
		this.panelUsager.add(this.txtFieldNom);
		this.txtFieldNom.setColumns(10);

		this.lblNomUsager = new JLabel("Nom de l'usager");
		this.lblNomUsager.setBounds(174, 78, 113, 14);
		this.panelUsager.add(this.lblNomUsager);
		this.panelUsager.setVisible(false);
	}

	public JFrame getframe() {
		return this.frmMaPetiteBibliothque;
	}
}
