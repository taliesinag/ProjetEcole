package beans;

import java.sql.ResultSet;
import java.util.Date;

import helpers.PDO;

public class Emprunt extends Tables {

	private final static String NOMTABLE = "Emprunt";
	private final String CHAMPSID = "id";
	private final String CHAMPSIDLIVRE = "id_livre";
	private final String CHAMPSIDUSAGER = "id_usager";
	private final String CHAMPSEMPRUNT = "date_emprunt";
	private final String CHAMPSRENDU = "date_rendu";

	private int id;
	private int id_livre;
	private int id_usager;
	private Date date_emprunt;
	private Date date_rendu;

	/**
	 * 
	 * @param id_livre
	 * @param id_usager
	 * @param date_emprunt
	 */
	public Emprunt(int id_livre, int id_usager, Date date_emprunt) {
		this.id_livre = id_livre;
		this.id_usager = id_usager;
		this.date_emprunt = date_emprunt;
		this.id = this.enregistrerBdd();
	}

	/**
	 * 
	 * @param id_livre
	 * @param id_usager
	 * @param date_emprunt
	 * @param date_rendu
	 */
	public Emprunt(int id_livre, int id_usager, Date date_emprunt, Date date_rendu) {
		this.id_livre = id_livre;
		this.id_usager = id_usager;
		this.date_emprunt = date_emprunt;
		this.date_rendu = date_rendu;
		this.id = this.enregistrerBdd();
	}

	/**
	 * 
	 * @param id
	 * @param id_livre
	 * @param id_usager
	 * @param date_emprunt
	 * @param date_rendu
	 */
	public Emprunt(int id, int id_livre, int id_usager, Date date_emprunt, Date date_rendu) {
		this.id_livre = id_livre;
		this.id_usager = id_usager;
		this.date_emprunt = date_emprunt;
		this.date_rendu = date_rendu;
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_livre() {
		return this.id_livre;
	}

	public void setId_livre(int id_livre) {
		this.id_livre = id_livre;
	}

	public int getId_usager() {
		return this.id_usager;
	}

	public void setId_usager(int id_usager) {
		this.id_usager = id_usager;
	}

	public Date getDate_emprunt() {
		return this.date_emprunt;
	}

	public void setDate_emprunt(Date date_emprunt) {
		this.date_emprunt = date_emprunt;
	}

	public Date getDate_rendu() {
		return this.date_rendu;
	}

	public void setDate_rendu(Date date_rendu) {
		this.date_rendu = date_rendu;
	}

	@Override
	protected int enregistrerBdd() {

		int result = 0;
		String insert = "INSERT INTO " + NOMTABLE + " (" + this.CHAMPSIDLIVRE + "," + this.CHAMPSIDUSAGER + ","
				+ this.CHAMPSEMPRUNT + "," + this.CHAMPSRENDU + ") VALUES ('" + this.id_livre + "','" + this.id_usager
				+ "','" + this.date_emprunt + "','" + this.date_rendu + "')";
		PDO.create(insert);

		result = this.maxId();

		return result;
	}

	@Override
	public void modifierBdd() {
		// TODO Auto-generated method stub
		String update = "UPDATE " + NOMTABLE + " SET " + this.CHAMPSIDLIVRE + " ='" + this.id_livre + "', "
				+ this.CHAMPSIDUSAGER + " ='" + this.id_usager + "', " + this.CHAMPSEMPRUNT + " ='" + this.date_emprunt
				+ "', " + this.CHAMPSRENDU + " ='" + this.date_rendu + "' " + "WHERE " + this.CHAMPSID + " ='"
				+ this.id;
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

	@Override
	public String toString() {
		return "" + this.id + " : " + this.date_emprunt + " : " + this.date_rendu + " : " + this.id_livre + " : "
				+ this.id_usager + " : ";
	}
}
