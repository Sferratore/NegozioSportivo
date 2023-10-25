package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.*;

public class NegozioDao {

	private ArrayList<Prodotto> negozio;
	private ArrayList<Utente> listaUtenti;
	private Utente UtenteLoggato;
	private Connection connection;

	public static String JConnectionClass = "com.mysql.cj.jdbc.Driver";
	public static String dbUsername = "root";
	public static String dbPassword = "DB09Gennaio";
	public static String dbName = "NegozioSportivo";

	public NegozioDao() {
		this.negozio = new ArrayList<Prodotto>();
		connettiAlDb();
	}

	public String connettiAlDb() {

		String connectionString = String.format("jdbc:mysql://localhost:3306/%s", dbName);

		try {

			Class.forName(JConnectionClass); // Getting driver's class inside the program. Important to let the driver
												// work.

			this.connection = DriverManager.getConnection(connectionString, dbUsername, dbPassword); // Creating
																										// connection

			if (!syncDb()) { // Syncing with DB data
				throw new SQLException();
			}
		} catch (ClassNotFoundException e) {
			return "The class " + JConnectionClass + " has not been found by the program.";
		} catch (SQLException e) {
			return "SQLException happened: " + e.toString();
		} catch (Exception e) {
			return "Connection not extabilished with the database." + e.toString();
		}

		return "Connection estabilished with database: \"" + dbName + "\"";

	}

	
	public boolean syncDb() {

		// Table data to add to the store
		ArrayList<Prodotto> prodottiDaAggiungere = new ArrayList<Prodotto>();
		ArrayList<Utente> utentiDaAggiungere = new ArrayList<Utente>();

		// Utilities
		PreparedStatement prpSt;
		ResultSet rs;

		// Connection check
		if (this.connection == null) {
			System.err.println("You are not connected to any Db!");
			return false;
		}

		try {


			prpSt = this.connection.prepareStatement("SELECT * FROM prodotto");
			rs = prpSt.executeQuery();

			while (rs.next()) {
				Prodotto p = new Prodotto();
				p.setId(rs.getInt("id"));
				p.setNome(rs.getString("nome"));
				p.setDescrizione(rs.getString("descrizione"));
				p.setPrezzo(rs.getDouble("prezzo"));
				p.setQuantità(rs.getInt("quantità"));
				prodottiDaAggiungere.add(p);
			}

			prpSt = this.connection.prepareStatement("SELECT * FROM utente");
			rs = prpSt.executeQuery();
			while (rs.next()) {
				Utente u = new Utente();
				u.setUsername(rs.getString("username"));
		        u.setEmail(rs.getString("email"));
		        u.setPassword(rs.getString("password"));
		        u.setNome(rs.getString("nome"));
		        u.setCognome(rs.getString("cognome"));
		        u.setAdmin(rs.getBoolean("isAdmin"));
		        
		        utentiDaAggiungere.add(u);
		        
			}
			
			this.negozio = prodottiDaAggiungere;
			this.listaUtenti = utentiDaAggiungere;

			return true;

		} catch (SQLException e) {
			System.err.println("Something went wrong with SQL operations: " + e.toString());
			return false;
		} catch (Exception e) {
			System.err.println("Something went wrong with SQL operations: " + e.toString());
			return false;
		}

	}

	public String mostraProdotti() {
		return "Prodotti [negozio=" + this.negozio + "]";
	}
	
	

}
