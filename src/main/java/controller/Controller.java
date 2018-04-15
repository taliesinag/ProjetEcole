package controller;

import java.io.File;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import beans.Emprunt;
import beans.Livre;
import beans.Usager;
import helpers.PDO;
import ihm.IhmMain;

public class Controller {

	private ArrayList<Usager> listeUsager;
	private ArrayList<Livre> listeLivre;
	private ArrayList<Livre> listeLivreARendre;
	private ArrayList<Emprunt> listeEmprunts;

	private IhmMain ihm;

	/**
	 * 
	 * @param USAGERBDD
	 */
	public Controller() {
		boolean dbExist = new File("bibliotheque.db").exists();
		PDO.init();
		if (!dbExist) {
			this.newBdd();
		}
	}

	/**
	 * 
	 * @param ihm
	 */
	public void setIhm(IhmMain ihm) {
		this.ihm = ihm;
		this.setListeUsager();
		this.setListeLivreDisponible();
		this.setListeLivreARendre();
		this.setListeEmprunts();

	}

	private void newBdd() {
		PDO.create(
				"CREATE TABLE IF NOT EXISTS Usager(id INTEGER PRIMARY KEY Autoincrement,nom Varchar (50) NOT NULL);");
		PDO.create(
				"CREATE TABLE IF NOT EXISTS Livre(id INTEGER PRIMARY KEY Autoincrement,titre Varchar (25) NOT NULL ,annee Int NOT NULL ,nom_auteur Varchar (50) NOT NULL ,prenom_auteur Varchar (50) ,editeur Varchar (50) NOT NULL);");
		PDO.create(
				"CREATE TABLE IF NOT EXISTS Emprunt (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, date_emprunt DATE NOT NULL, date_rendu DATE NOT NULL, id_usager INTEGER NOT NULL REFERENCES Livre (id), id_livre INTEGER NOT NULL REFERENCES Livre (id), FOREIGN KEY (id_usager) REFERENCES Usager (id), FOREIGN KEY (id_livre) REFERENCES Livre (id));");

	}

	/**
	 * Créée 12 utilisateurs anonymes.
	 */
	public void usagerBdd() {
		String usagers[] = { "Allan", "Imene", "Jean", "Guillaume", "Aissatou", "Aymeric", "Matthieu", "Samuel",
				"Stephane", "Yasmmina", "Mustafa", "Abdou" };
		for (String nom : usagers) {
			new Usager(nom);
		}
		this.setListeUsager();
	}

	public void RendreLivre(Livre livre) {
		this.ihm.comboBoxLivreARendre.removeItem(livre);
		this.listeLivreARendre.remove(livre);
		this.ihm.comboBoxLivre.addItem(livre);
		this.listeLivre.add(livre);
		Emprunt remove = null;
		for (Emprunt emprunt : this.listeEmprunts) {
			System.out.println(emprunt.getId_livre() + " : " + livre.getId());
			if (emprunt.getId_livre() == livre.getId()) {
				emprunt.setDate_rendu(new Date());
				emprunt.modifierBdd();
				remove = emprunt;
				System.out.println(remove.toString());
			}
		}
		this.listeEmprunts.remove(remove);
	}

	public void AjouterEmprunt(Livre livre, Usager usager, Date date_emprunt, Date date_rendu) {
		this.ihm.comboBoxLivre.removeItem(livre);
		this.listeLivre.remove(livre);
		this.ihm.comboBoxLivreARendre.addItem(livre);
		this.listeLivreARendre.add(livre);
		new Emprunt(livre.getId(), usager.getId(), date_emprunt, date_rendu);
	}

	public void AjouterUsager(String nom) {
		this.listeUsager.add(new Usager(nom));

	}

	public void AjouterLivre(String titre, int annee, String nom_auteur, String prenom_auteur, String editeur) {

		this.listeLivre.add(new Livre(titre, annee, nom_auteur, prenom_auteur, editeur));
	}

	private void setListeUsager() {
		this.listeUsager = new ArrayList<Usager>();
		ResultSet result = PDO.sql("SELECT * FROM Usager");
		try {
			while (result.next()) {
				Usager user = new Usager(result.getInt("id"), result.getString("nom"));
				this.listeUsager.add(user);
				this.ihm.comboBoxUsager.addItem(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void setListeLivreDisponible() {
		this.listeLivre = new ArrayList<Livre>();

		ResultSet result = PDO.sql(
				"SELECT * FROM Livre WHERE Livre.id IN(SELECT id_livre FROM Emprunt WHERE Emprunt.date_rendu != 'null') OR Livre.id NOT IN(SELECT id_livre FROM Emprunt );");

		try {
			while (result.next()) {
				Livre livre = new Livre(result.getInt("id"), result.getString("titre"), result.getInt("annee"),
						result.getString("nom_auteur"), result.getString("prenom_auteur"), result.getString("editeur"));
				this.listeLivre.add(livre);
				this.ihm.comboBoxLivre.addItem(livre);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void setListeEmprunts() {
		this.listeEmprunts = new ArrayList<Emprunt>();
		DateFormat formatDate = new SimpleDateFormat("dd--MMMM--yyyy");
		ResultSet result = PDO.sql("SELECT * FROM Emprunt WHERE date_rendu = 'null'");
		// TODO Mauvaise gestion de la Date SQLITE par JAVA ou inversement, à
		// corriger.
		try {
			while (result.next()) {
				Emprunt emprunt = new Emprunt(result.getInt("id"), result.getInt("id_livre"),
						result.getInt("id_usager"), result.getDate("date_emprunt"), result.getDate("date_rendu"));
				this.listeEmprunts.add(emprunt);
				emprunt.toString();

			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ERROR LISTE EMPRUNT : " + e);
		}
	}

	private void setListeLivreARendre() {
		this.listeLivreARendre = new ArrayList<Livre>();

		ResultSet result = PDO.sql(
				"SELECT * FROM Livre WHERE Livre.id IN(SELECT id_livre FROM Emprunt WHERE Emprunt.date_rendu = 'null');");

		try {
			while (result.next()) {
				Livre livre = new Livre(result.getInt("id"), result.getString("titre"), result.getInt("annee"),
						result.getString("nom_auteur"), result.getString("prenom_auteur"), result.getString("editeur"));
				this.listeLivreARendre.add(livre);
				this.ihm.comboBoxLivreARendre.addItem(livre);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
