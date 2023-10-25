package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

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

	public ArrayList<Prodotto> getNegozio() {
		return negozio;
	}

	public void setNegozio(ArrayList<Prodotto> negozio) {
		this.negozio = negozio;
	}

	public ArrayList<Utente> getListaUtenti() {
		return listaUtenti;
	}

	public void setListaUtenti(ArrayList<Utente> listaUtenti) {
		this.listaUtenti = listaUtenti;
	}

	public Utente getUtenteLoggato() {
		return UtenteLoggato;
	}

	public void setUtenteLoggato(Utente utenteLoggato) {
		UtenteLoggato = utenteLoggato;
	}

	
	public Boolean login(String username, String password) {

        String checkString = "SELECT * FROM Utente WHERE username = '" + username + "' AND password = '" + password + "'";
        Utente u = new Utente();

        try {
            PreparedStatement prpSt = this.connection.prepareStatement(checkString);
            
            ResultSet rs = prpSt.executeQuery();
            
            if(rs.next()) {
                u.setUsername(rs.getString("username"));
                u.setUsername(rs.getString("email"));
                u.setUsername(rs.getString("password"));
                u.setUsername(rs.getString("nome"));
                u.setUsername(rs.getString("cognome"));
                u.setAdmin(rs.getBoolean("isAdmin"));
            } else {
                System.err.println("nessun utente con queste credeziali");
                registra(u);
            }
        } 
        catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Something went wrong with the SQL operation: " + e.toString());
            return false;
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Something went wrong with the operation: " + e.toString());
            return false;
        }

        setUtenteLoggato(u);
        syncDb(); //resyncing

        return true;
    }
	
	
	public Boolean registra(Utente u) {

		String insertString = "INSERT INTO utente (username, email, password, nome, cognome, isAdmin) VALUES (?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement prpSt = this.connection.prepareStatement(insertString);

			prpSt.setString(1, u.getUsername());
			prpSt.setString(2, u.getEmail());
			prpSt.setString(3, u.getPassword());
			prpSt.setString(4, u.getNome());
			prpSt.setString(5, u.getCognome());
			prpSt.setBoolean(6, u.isAdmin());

			prpSt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Something went wrong with the SQL operation: " + e.toString());
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Something went wrong with the operation: " + e.toString());
			return false;
		}

		setUtenteLoggato(u);
		syncDb(); // resyncing

		return true;
	}

	public boolean aggiungiAlCarrello(int idProdotto) {
		try {
			String selectString = "SELECT * FROM Prodotto WHERE id = ?";
			PreparedStatement selectSt = this.connection.prepareStatement(selectString);
			selectSt.setInt(1, idProdotto);
			ResultSet result = selectSt.executeQuery();

			if (result.next()) {
				Prodotto p = new Prodotto();
				p.setId(result.getInt("id"));
				p.setNome(result.getString("nome"));
				p.setDescrizione(result.getString("descrizione"));
				p.setPrezzo(result.getDouble("prezzo"));
				p.setQuantità(result.getInt("quantità"));

				this.UtenteLoggato.getCarrello().add(p);

				String insertString = "INSERT INTO Carrello (username, prodotto_id, quantita_carrello) VALUES (?, ?, ?);";
				PreparedStatement prpSt = this.connection.prepareStatement(insertString);
				prpSt.setString(1, this.UtenteLoggato.getUsername());
				prpSt.setInt(2, p.getId());
				prpSt.setInt(3, 1);
				prpSt.executeUpdate();

				syncDb(); // resyncing

				return true;
			} else {
				System.out.println("Prodotto non trovato nel database.");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean rimuoviDalCarrello(int idProdotto) {
		try {
			String selectString = "SELECT * FROM Prodotto WHERE id = ?";
			PreparedStatement selectSt = this.connection.prepareStatement(selectString);
			selectSt.setInt(1, idProdotto);
			ResultSet result = selectSt.executeQuery();

			if (result.next()) {
				Prodotto p = new Prodotto();
				p.setId(result.getInt("id"));
				p.setNome(result.getString("nome"));
				p.setDescrizione(result.getString("descrizione"));
				p.setPrezzo(result.getDouble("prezzo"));
				p.setQuantità(result.getInt("quantità"));
				boolean removed = this.UtenteLoggato.getCarrello().remove(p);

				if (removed) {

					String deleteString = "DELETE FROM Carrello WHERE username = ? AND prodotto_id = ?";
					PreparedStatement prpSt = this.connection.prepareStatement(deleteString);
					prpSt.setString(1, this.UtenteLoggato.getUsername());
					prpSt.setInt(2, p.getId());
					int rowCount = prpSt.executeUpdate();

					if (rowCount > 0) {
						syncDb();
						return true;
					} else {
						return false;
					}
				} else {
					System.out.println("Il prodotto non è stato trovato nel carrello.");
					return false;
				}
			} else {
				System.out.println("Prodotto non trovato nel database.");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean aggiungiProdotto(Prodotto p) {
		try {
			Connection conn = this.connection;

			String query = "insert into prodotto (nome, descrizione, prezzo, " + "quantità) values(?, ?, ?, ?)";

			PreparedStatement prstmt = conn.prepareStatement(query);

			String nome = p.getNome();
			String descrizione = p.getDescrizione();
			double prezzo = p.getPrezzo();
			int quantita = p.getQuantità();

			prstmt.setString(1, nome);
			prstmt.setString(2, descrizione);
			prstmt.setDouble(3, prezzo);
			prstmt.setInt(4, quantita);

			prstmt.execute();

			syncDb();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean rimuoviProdotto(int prodottoId) {
		try {
			Connection conn = this.connection;

			String query = "delete from prodotto where id=?";

			PreparedStatement prstmt = conn.prepareStatement(query);

			prstmt.setInt(1, prodottoId);

			prstmt.execute();

			syncDb();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean acquista() {
		double totale = 0.0;
		boolean conferma;
		Scanner in = new Scanner(System.in);

		try {
			for (Prodotto p : this.UtenteLoggato.getCarrello()) {
				totale += p.getPrezzo() * p.getQuantità();
			}

			System.out.print("Confermi il pagamento di " + totale + "? (true/false): ");
			conferma = in.nextBoolean();

			if (conferma) {

				for (Prodotto p : this.UtenteLoggato.getCarrello()) {
					int idProdotto = p.getId();
					int quantitaCarrello = p.getQuantità();

					// Aggiorna la quantità nel database
					String updateString = "UPDATE Prodotto SET quantità = quantità - ? WHERE id = ?";
					PreparedStatement updateSt = connection.prepareStatement(updateString);
					updateSt.setInt(1, quantitaCarrello);
					updateSt.setInt(2, idProdotto);
					updateSt.executeUpdate();

				}

				this.UtenteLoggato.getCarrello().clear();

				String deleteString = "DELETE FROM Carrello WHERE username = ?";
				PreparedStatement prpSt = this.connection.prepareStatement(deleteString);
				prpSt.setString(1, this.UtenteLoggato.getUsername());
				prpSt.executeUpdate();

				System.out.println("Pagamento di " + totale + " euro eseguito con successo.");

				syncDb();
				
				return true;
			} else {
				System.out.println("Acquisto annullato.");
				syncDb();
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
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
