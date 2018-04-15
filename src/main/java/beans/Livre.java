package beans;

import java.sql.ResultSet;

import helpers.PDO;

public class Livre extends Tables {

	private static final String NOMTABLE = "Livre";

	private final String CHAMPSID = "id";
	private final String CHAMPSTITRE = "titre";
	private final String CHAMPSANNEE = "annee";
	private final String CHAMPSNAUTEUR = "nom_auteur";
	private final String CHAMPSPAUTEUR = "prenom_auteur";
	private final String CHAMPSEDITEUR = "editeur";

	private int id;
	private String titre;
	private int annee;
	private String nom_auteur;
	private String prenom_auteur;
	private String editeur;

	/**
	 * 
	 * @param titre
	 * @param annee
	 * @param nom_auteur
	 * @param prenom_auteur
	 * @param editeur
	 */
	public Livre(String titre, int annee, String nom_auteur, String prenom_auteur, String editeur) {
		this.titre = titre;
		this.annee = annee;
		this.nom_auteur = nom_auteur;
		this.prenom_auteur = prenom_auteur;
		this.editeur = editeur;
		this.id = this.enregistrerBdd();
	}

	/**
	 * 
	 * @param id
	 * @param titre
	 * @param annee
	 * @param nom_auteur
	 * @param prenom_auteur
	 * @param editeur
	 */
	public Livre(int id, String titre, int annee, String nom_auteur, String prenom_auteur, String editeur) {
		this.titre = titre;
		this.annee = annee;
		this.nom_auteur = nom_auteur;
		this.prenom_auteur = prenom_auteur;
		this.editeur = editeur;
		this.id = id;
	}

	@Override
	protected int enregistrerBdd() {

		int result = 0;
		String insert = "INSERT INTO " + NOMTABLE + " (" + this.CHAMPSTITRE + "," + this.CHAMPSANNEE + ","
				+ this.CHAMPSNAUTEUR + "," + this.CHAMPSPAUTEUR + "," + this.CHAMPSEDITEUR + ") VALUES ('" + this.titre
				+ "','" + this.annee + "','" + this.nom_auteur + "','" + this.prenom_auteur + "','" + this.editeur
				+ "')";
		PDO.create(insert);

		result = this.maxId();

		return result;
	}

	@Override
	protected void modifierBdd() {
		// TODO Auto-generated method stub
		String update = "UPDATE " + NOMTABLE + " SET " + this.CHAMPSTITRE + " ='" + this.titre + "', "
				+ this.CHAMPSANNEE + " =" + this.annee + ", " + this.CHAMPSNAUTEUR + " ='" + this.nom_auteur + "', "
				+ this.CHAMPSPAUTEUR + " ='" + this.prenom_auteur + "', " + this.CHAMPSEDITEUR + " ='" + this.editeur
				+ "' " + "WHERE " + this.CHAMPSID + " ='" + this.id;
		PDO.create(update);
	}

	@Override
	protected void supprimerBdd() {
		// TODO Auto-generated method stub
		String delete = "DELETE FROM " + NOMTABLE + " WHERE " + this.CHAMPSID + " ='" + this.id;
		PDO.create(delete);
	}

	@Override
	protected int maxId() {

		int maxId = 0;
		String SELECT = "SELECT MAX('" + this.CHAMPSID + "') AS 'id' FROM " + NOMTABLE;

		ResultSet result = PDO.sql(SELECT);

		try {
			while (result.next()) {
				maxId = result.getInt(this.CHAMPSID);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return maxId;

	}

	/**
	 * 
	 * @return
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public String getTitre() {
		return this.titre;
	}

	/**
	 * 
	 * @param titre
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}

	/**
	 * 
	 * @return
	 */
	public int getAnnee() {
		return this.annee;
	}

	/**
	 * 
	 * @param annee
	 */
	public void setAnnee(int annee) {
		this.annee = annee;
	}

	/**
	 * 
	 * @return
	 */
	public String getNom_auteur() {
		return this.nom_auteur;
	}

	/**
	 * 
	 * @param nom_auteur
	 */
	public void setNom_auteur(String nom_auteur) {
		this.nom_auteur = nom_auteur;
	}

	/**
	 * 
	 * @return
	 */
	public String getPrenom_auteur() {
		return this.prenom_auteur;
	}

	/**
	 * 
	 * @param prenom_auteur
	 */
	public void setPrenom_auteur(String prenom_auteur) {
		this.prenom_auteur = prenom_auteur;
	}

	/**
	 * 
	 * @return
	 */
	public String getEditeur() {
		return this.editeur;
	}

	/**
	 * 
	 * @param editeur
	 */
	public void setEditeur(String editeur) {
		this.editeur = editeur;
	}

	@Override
	public String toString() {
		return this.titre;
	}

}
