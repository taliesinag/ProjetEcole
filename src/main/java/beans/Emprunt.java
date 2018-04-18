package beans;

import java.sql.ResultSet;
import java.util.Date;

import org.joda.time.DateTime;

import helpers.PDO;

public class Emprunt extends Tables implements Comparable<Emprunt>{

	private final static String NOMTABLE = "Emprunt";
	private final String CHAMPSID = "id";
	private final String CHAMPSIDLIVRE = "id_livre";
	private final String CHAMPSIDUSAGER = "id_usager";
	private final String CHAMPSEMPRUNT = "date_emprunt";
	private final String CHAMPSRENDU = "date_rendu";

	private int id;
	private Livre livre;
	private Usager usager;
	private DateTime date_emprunt;
	private DateTime date_rendu;

	/**
	 * 
	 * @param id_livre
	 * @param id_usager
	 * @param date_emprunt
	 */
	public Emprunt(Livre livre, Usager usager, DateTime date_emprunt) {
		this.livre = livre;
		this.usager = usager;
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
	public Emprunt(Livre livre, Usager usager, DateTime date_emprunt, DateTime date_rendu) {
		this.livre =livre;
		this.usager = usager;
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
	public Emprunt(int id, Livre livre, Usager usager, DateTime date_emprunt, DateTime date_rendu) {
		this.livre = livre;
		this.usager = usager;
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

	public Livre getLivre() {
		return this.livre;
	}

	public void setLivre(Livre livre) {
		this.livre = livre;
	}

	public Usager getUsager() {
		return this.usager;
	}

	public void setUsager(Usager usager) {
		this.usager = usager;
	}

	public DateTime getDate_emprunt() {
		return this.date_emprunt;
	}

	public void setDate_emprunt(DateTime date_emprunt) {
		this.date_emprunt = date_emprunt;
	}

	public DateTime getDate_rendu() {
		return this.date_rendu;
	}

	public void setDate_rendu(DateTime date_rendu) {
		this.date_rendu = date_rendu;
	}

	@Override
	protected int enregistrerBdd() {
		int result = 0;
		String insert = "INSERT INTO " + NOMTABLE + " (" + this.CHAMPSIDLIVRE + "," + this.CHAMPSIDUSAGER + ","
				+ this.CHAMPSEMPRUNT + "," + this.CHAMPSRENDU + ") VALUES ('" + this.livre.getId() + "','" + this.usager.getId()
				+ "','" + this.date_emprunt.toString() + "','" + (this.date_rendu == null ? null : this.date_rendu.toString()) + "')";
		PDO.create(insert);

		result = this.maxId();

		return result;
	}

	@Override
	public void modifierBdd() {
		// TODO Auto-generated method stub
		String update = "UPDATE " + NOMTABLE + " SET " + this.CHAMPSIDLIVRE + " =" + this.livre.getId() + ", "
				+ this.CHAMPSIDUSAGER + " =" + this.usager.getId() + ", " + this.CHAMPSEMPRUNT + " ='" + this.date_emprunt
				+ "', " + this.CHAMPSRENDU + " ='" + this.date_rendu + "' " + "WHERE " + this.CHAMPSID + " ="
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
		String SELECT = "SELECT MAX(" + this.CHAMPSID + ") AS id FROM " + NOMTABLE;

		ResultSet result = PDO.sql(SELECT);

		try {
			while (result.next()) {
				maxId = result.getInt(this.CHAMPSID);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		System.out.println(maxId);
		return maxId;

	}

	@Override
	public String toString() {
		return "" + this.livre.getTitre();
	}

	public int compareTo(Emprunt emprunt) {
		
		return this.getDate_emprunt().compareTo(emprunt.getDate_emprunt());
	}
}
