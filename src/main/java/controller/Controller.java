package controller;

import java.awt.EventQueue;
import java.io.File;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;


import org.joda.time.DateTime;

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
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IhmMain window = new IhmMain(Controller.this);
					Controller.this.ihm = window;
					window.getframe().setVisible(true);
					boolean dbExist = new File("bibliotheque.db").exists();
					PDO.init();
					if (!dbExist) {
						newBdd();
					}
					init();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * 
	 * @param ihm
	 */
	private void init() {
		this.setListeUsager();
		this.setListeLivreDisponible();
		
		this.setListeEmprunts();
		this.setListeLivreARendre();

	}

	private void newBdd() {
		PDO.create(
				"CREATE TABLE IF NOT EXISTS Usager(id INTEGER PRIMARY KEY Autoincrement,nom Varchar (50) NOT NULL);");
		PDO.create(
				"CREATE TABLE IF NOT EXISTS Livre(id INTEGER PRIMARY KEY Autoincrement,titre Varchar (25) NOT NULL ,annee Int NOT NULL ,nom_auteur Varchar (50) NOT NULL ,prenom_auteur Varchar (50) ,editeur Varchar (50) NOT NULL);");
		PDO.create(
				"CREATE TABLE IF NOT EXISTS Emprunt (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, date_emprunt VARCHAR NOT NULL, date_rendu VARCHAR NOT NULL, id_usager INTEGER NOT NULL REFERENCES Livre (id), id_livre INTEGER NOT NULL REFERENCES Livre (id), FOREIGN KEY (id_usager) REFERENCES Usager (id), FOREIGN KEY (id_livre) REFERENCES Livre (id));");

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

	public void RendreLivre(Emprunt emprunt) {
		this.listeLivreARendre.remove(emprunt.getLivre());
		this.listeLivre.add(emprunt.getLivre());
		this.listeEmprunts.remove(emprunt);
		this.setCmbLivreARendre();
		this.setCmbLivreDispo();
	}

	public void AjouterEmprunt(Livre livre, Usager usager, DateTime date_emprunt, DateTime date_rendu) {
		this.listeLivre.remove(livre);
		this.listeLivreARendre.add(livre);
		this.setCmbLivreARendre();
		this.setCmbLivreDispo();
		Emprunt emprunt = new Emprunt(livre, usager, date_emprunt, date_rendu);
		this.listeEmprunts.add(emprunt);
	}

	public void AjouterUsager(String nom) {
		Usager usager = new Usager(nom);
		this.listeUsager.add(usager);
		this.ihm.comboBoxUsager.addItem(usager);
		

	}

	public void AjouterLivre(String titre, int annee, String nom_auteur, String prenom_auteur, String editeur) {
        Livre livre = new Livre(titre, annee, nom_auteur, prenom_auteur, editeur);
		this.listeLivre.add(livre);
		this.ihm.comboBoxLivre.addItem(livre);
	}

	private void setListeUsager() {
		this.listeUsager = new ArrayList<Usager>();
		ResultSet result = PDO.sql("SELECT * FROM Usager");
		try {
			while (result.next()) {
				Usager user = new Usager(result.getInt("id"), result.getString("nom"));
				this.listeUsager.add(user);
				
			}
			this.setCmbUsager();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void setListeLivreDisponible() {
		this.listeLivre = new ArrayList<Livre>();

		ResultSet result = PDO.sql("SELECT * FROM Livre WHERE Livre.id NOT IN(SELECT id_livre FROM Emprunt WHERE Emprunt.date_rendu = 'null');");
		/*"SELECT * FROM Livre WHERE Livre.id NOT IN(SELECT id_livre FROM Emprunt WHERE Emprunt.date_rendu = 'null') OR Livre.id NOT IN(SELECT id_livre FROM Emprunt )*/

		try {
			while (result.next()) {
				Livre livre = new Livre(result.getInt("id"), result.getString("titre"), result.getInt("annee"),
						result.getString("nom_auteur"), result.getString("prenom_auteur"), result.getString("editeur"));
				this.listeLivre.add(livre);
			}
			this.setCmbLivreDispo();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	private void setListeEmprunts() {
		this.listeEmprunts = new ArrayList<Emprunt>();
		SimpleDateFormat formatDate = new SimpleDateFormat("EEE MMM d hh:mm:ss z yyyy");
		ResultSet result = PDO.sql("SELECT * FROM Emprunt WHERE date_rendu = 'null'");
		// TODO Mauvaise gestion de la Date SQLITE par JAVA ou inversement, à
		// corriger.
		try {
			while (result.next()) {
				ResultSet UsagerResult = PDO.sql("SELECT * FROM Usager WHERE id = "+result.getInt("id_usager"));
				Usager usager = new Usager(UsagerResult);
				ResultSet LivreResult = PDO.sql("SELECT * FROM Livre WHERE id = "+result.getInt("id_livre"));
				Livre livre = new Livre(LivreResult);
				Emprunt emprunt = new Emprunt(result.getInt("id"), livre,
						usager, DateTime.parse(result.getString("date_emprunt")), (result.getString("date_rendu")).compareTo("null") == 0 ? null : DateTime.parse(result.getString("date_rendu")));
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
			}
			this.setCmbLivreARendre();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	private void setCmbLivreARendre() {
		Collections.sort(listeEmprunts);
		
		this.ihm.comboBoxLivreARendre.removeAllItems();
		for (Emprunt emprunt : listeEmprunts) {
			this.ihm.comboBoxLivreARendre.addItem(emprunt);
		}
		
	}
	private void setCmbLivreDispo() {
		Collections.sort(listeLivre);
		this.ihm.comboBoxLivre.removeAllItems();
		for (Livre livre : listeLivre) {
			this.ihm.comboBoxLivre.addItem(livre);
		}
	}
	private void setCmbUsager() {
		Collections.sort(listeUsager);
		this.ihm.comboBoxUsager.removeAllItems();
		for (Usager usager : listeUsager) {
			this.ihm.comboBoxUsager.addItem(usager);
		}
	}

}
