package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PDO {

	private final static String URL = "jdbc:sqlite:bibliotheque.db";

	private static Connection connexion;

	/**
	 *
	 * @param sql
	 * @return
	 */
	public static int create(String sql) {
		PreparedStatement stmt = null;
		int result = 0;

		try {
			stmt = connexion.prepareStatement(sql);
			result = stmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ERROR :" + e);
		}
		return result;

	}

	public static ResultSet sql(String sql) {
		PreparedStatement stmt = null;

		try {
			stmt = connexion.prepareStatement(sql);
			ResultSet result = stmt.executeQuery();
			return result;

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ERROR :" + e);
		}

		return null;
	}

	/**
	 * Initialisation du singleton qui gère la connexion à la base de données
	 * sqlite.
	 *
	 * @return Void
	 */
	public static void init() {

		try {
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection(URL);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static Connection connexion() {
		return connexion;
	}
}
