package beans;

import java.sql.ResultSet;

import helpers.PDO;

public class Usager extends Tables {
	private static final String NOMTABLE = "Usager";
	private final String CHAMPSID = "id";
	private final String CHAMPSNOM = "nom";

	private int id;
	private String nom;

	/**
	 * 
	 * @param nom
	 */
	public Usager(String nom) {
		this.nom = nom;
		this.id = this.enregistrerBdd();
	}

	/**
	 * 
	 * @param id
	 * @param nom
	 */
	public Usager(int id, String nom) {
		this.nom = nom;
		this.id = id;
	}

	@Override
	protected int enregistrerBdd() {

		int result = 0;
		String insert = "INSERT INTO " + NOMTABLE + " (" + this.CHAMPSNOM + ") VALUES ('" + this.nom + "')";
		PDO.create(insert);

		result = this.maxId();

		return result;
	}

	@Override
	protected void modifierBdd() {
		// TODO Auto-generated method stub
		String update = "UPDATE " + NOMTABLE + " SET " + this.CHAMPSNOM + " ='" + this.nom + "' WHERE " + this.CHAMPSID
				+ " ='" + this.id;
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
	public String getNom() {
		return this.nom;
	}

	/**
	 * 
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		return this.nom;
	}
}
