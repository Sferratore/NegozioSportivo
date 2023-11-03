package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import entities.*;

/**
 * Questa classe rappresenta il data access object (DAO) per il negozio.
 * Gestisce l'interazione con il database e fornisce metodi per eseguire
 * operazioni sul negozio e sugli utenti.
 */
public class NegozioDao {

	private ArrayList<Prodotto> negozio;
	private ArrayList<Utente> listaUtenti;
	private Utente UtenteLoggato;
	private Connection connection;

	public static String JConnectionClass = "com.mysql.cj.jdbc.Driver";
	public static String dbUsername = "root";
	public static String dbPassword = "DB09Gennaio";
	public static String dbName = "NegozioSportivo";

	/**
	 * Costruttore della classe NegozioDao. Inizializza le liste per il negozio e
	 * gli utenti e si connette al database.
	 */
	public NegozioDao() {
		this.negozio = new ArrayList<Prodotto>();
		connettiAlDb();
	}

	// Getter e setter
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

	/**
	 * Effettua il login di un utente.
	 *
	 * @param username Username dell'utente.
	 * @param password Password dell'utente.
	 * @return True se il login è riuscito, altrimenti False.
	 */
	/**
	 * Effettua il login di un utente nel sistema verificando le credenziali
	 * fornite.
	 *
	 * @param username Il nome utente dell'utente che sta cercando di effettuare il
	 *                 login.
	 * @param password La password associata all'account dell'utente.
	 * @return True se il login è riuscito, altrimenti False.
	 */
	public Boolean login(String username, String password) {
		// Creazione della query SQL per verificare le credenziali dell'utente
		String checkString = "SELECT * FROM Utente WHERE username = '" + username + "' AND password = '" + password
				+ "'";
		Utente u = new Utente();

		try {
			// Creazione di una PreparedStatement per query
			PreparedStatement prpSt = this.connection.prepareStatement(checkString);

			// Esecuzione query
			ResultSet rs = prpSt.executeQuery();

			// Se esiste un risultato nella query
			if (rs.next()) {
				u.setUsername(rs.getString("username"));
				u.setEmail(rs.getString("email"));
				u.setPassword(rs.getString("password"));
				u.setNome(rs.getString("nome"));
				u.setCognome(rs.getString("cognome"));
				u.setAdmin(rs.getBoolean("isAdmin"));
			} else {
				// Nessun utente trovato con le credenziali fornite
				System.err.println("Nessun utente con queste credenziali.");
				return false;
			}
		} catch (SQLException e) {
			// Errore durante l'operazione SQL
			System.out.println("Si è verificato un problema con l'operazione SQL: " + e.toString());
			return false;
		} catch (Exception e) {
			// Errore generico durante l'operazione
			System.out.println("Si è verificato un problema con l'operazione: " + e.toString());
			return false;
		}

		// Imposta l'utente loggato e sincronizza con il database
		setUtenteLoggato(u);
		syncDb();

		return true;
	}

	/**
	 * Registra un nuovo utente nel sistema inserendo i suoi dati nel database.
	 *
	 * @param u L'oggetto Utente che rappresenta l'utente da registrare.
	 * @return True se la registrazione è riuscita, altrimenti False.
	 */
	public Boolean registra(Utente u) {
		// Creazione della query SQL per inserire un nuovo utente nel database
		String insertString = "INSERT INTO utente (username, email, password, nome, cognome, isAdmin) VALUES (?, ?, ?, ?, ?, ?)";

		try {
			// Creazione di una PreparedStatement per eseguire la query SQL
			PreparedStatement prpSt = this.connection.prepareStatement(insertString);

			// Impostazione dei parametri della PreparedStatement con i dati dell'utente
			prpSt.setString(1, u.getUsername());
			prpSt.setString(2, u.getEmail());
			prpSt.setString(3, u.getPassword());
			prpSt.setString(4, u.getNome());
			prpSt.setString(5, u.getCognome());
			prpSt.setBoolean(6, u.isAdmin());

			// Esecuzione della query di inserimento
			prpSt.executeUpdate();
		} catch (SQLException e) {
			// Errore durante l'operazione SQL
			System.out.println("Si è verificato un problema con l'operazione SQL: " + e.toString());
			return false;
		} catch (Exception e) {
			// Errore generico durante l'operazione
			System.out.println("Si è verificato un problema con l'operazione: " + e.toString());
			return false;
		}

		// Imposta l'utente loggato e sincronizza con il database
		setUtenteLoggato(u);
		syncDb(); // Riesegue la sincronizzazione con il database

		return true;
	}

	/**
	 * Aggiunge un prodotto al carrello dell'utente.
	 *
	 * @param idProdotto ID del prodotto da aggiungere al carrello.
	 * @param qt         Quantità del prodotto da aggiungere al carrello.
	 * @return True se l'aggiunta al carrello è riuscita, altrimenti False.
	 */
	public boolean aggiungiAlCarrello(int idProdotto, int qt) {
		try {

			// Creazione della query SQL per selezionare il prodotto con l'ID specificato e
			// la quantità disponibile sufficiente
			String selectString = "SELECT * FROM Prodotto WHERE id = ? AND quantità >= ?";
			PreparedStatement selectSt = this.connection.prepareStatement(selectString);
			selectSt.setInt(1, idProdotto);
			selectSt.setInt(2, qt);
			ResultSet result = selectSt.executeQuery();

			if (result.next()) {
				// Creazione di un oggetto Prodotto con i dati recuperati dal database
				Prodotto p = new Prodotto();
				p.setId(result.getInt("id"));
				p.setNome(result.getString("nome"));
				p.setDescrizione(result.getString("descrizione"));
				p.setPrezzo(result.getDouble("prezzo"));
				p.setQuantità(qt);

				// Aggiunta del prodotto al carrello dell'utente
				this.UtenteLoggato.getCarrello().add(p);

				// Creazione della query SQL per inserire il prodotto nel carrello
				String dbOperation = "INSERT INTO Carrello (username, prodotto_id, quantita_carrello) VALUES (?, ?, ?);";
				PreparedStatement prpSt = this.connection.prepareStatement(dbOperation);
				prpSt.setString(1, this.UtenteLoggato.getUsername());
				prpSt.setInt(2, p.getId());
				prpSt.setInt(3, qt);
				prpSt.executeUpdate();

				// Aggiorna la quantità del prodotto nel negozio
				dbOperation = "UPDATE Prodotto SET quantità = quantità - ? WHERE id = ?";
				prpSt = connection.prepareStatement(dbOperation);
				prpSt.setInt(1, qt);
				prpSt.setInt(2, idProdotto);
				prpSt.executeUpdate();

				// Sincronizzazione con il database
				syncDb(); // Riesegue la sincronizzazione con il database

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

	/**
	 * Rimuove un prodotto dal carrello dell'utente.
	 *
	 * @param idProdotto ID del prodotto da rimuovere dal carrello.
	 * @return True se la rimozione dal carrello è riuscita, altrimenti False.
	 */
	public boolean rimuoviDalCarrello(int idProdotto) {
		try {
			// Creazione della query SQL per selezionare il prodotto con l'ID specificato
			String selectString = "SELECT * FROM Prodotto WHERE id = ?";
			PreparedStatement selectSt = this.connection.prepareStatement(selectString);
			selectSt.setInt(1, idProdotto);
			ResultSet result = selectSt.executeQuery();

			if (result.next()) {
				// Creazione della query SQL per eliminare il prodotto dal carrello
				String deleteString = "DELETE FROM Carrello WHERE username = ? AND prodotto_id = ?";
				PreparedStatement prpSt = this.connection.prepareStatement(deleteString);
				prpSt.setString(1, this.UtenteLoggato.getUsername());
				prpSt.setInt(2, idProdotto);
				prpSt.executeUpdate();


				// Aggiorna la quantità del prodotto nel negozio
				deleteString = "UPDATE Prodotto SET quantità = quantità + ? WHERE id = ?";
				prpSt = connection.prepareStatement(deleteString);
				
				Prodotto p = new Prodotto();
				for(int i = 0; i < this.UtenteLoggato.getCarrello().size(); i++) {
					if(this.UtenteLoggato.getCarrello().get(i).getId() == idProdotto) {
						p = this.UtenteLoggato.getCarrello().get(i);
					}
				}
				
				prpSt.setInt(1, p.getQuantità());
				prpSt.setInt(2, idProdotto);
				prpSt.executeUpdate();

				syncDb();
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

	/**
	 * Aggiunge un nuovo prodotto al negozio.
	 *
	 * @param p Prodotto da aggiungere al negozio.
	 * @return True se l'aggiunta del prodotto è riuscita, altrimenti False.
	 */
	public boolean aggiungiProdotto(Prodotto p) {
		try {
			Connection conn = this.connection;

			// Creazione della query SQL per inserire un nuovo prodotto nel negozio
			String query = "insert into prodotto (nome, descrizione, prezzo, " + "quantità) values(?, ?, ?, ?)";

			PreparedStatement prstmt = conn.prepareStatement(query);

			String nome = p.getNome();
			String descrizione = p.getDescrizione();
			double prezzo = p.getPrezzo();
			int quantita = p.getQuantità();

			// Impostazione dei parametri nella query SQL
			prstmt.setString(1, nome);
			prstmt.setString(2, descrizione);
			prstmt.setDouble(3, prezzo);
			prstmt.setInt(4, quantita);

			prstmt.execute(); // Esecuzione della query per inserire il prodotto

			syncDb(); // Sincronizzazione con il database

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Rimuove un prodotto dal negozio.
	 *
	 * @param prodottoId ID del prodotto da rimuovere dal negozio.
	 * @return True se la rimozione del prodotto è riuscita, altrimenti False.
	 */
	public boolean rimuoviProdotto(int prodottoId) {
		try {
			Connection conn = this.connection;

			// Creazione della query SQL per rimuovere un prodotto dalla tabella acquisti
			String query = "delete from acquisti where prodotto_id=?";

			PreparedStatement prstmt = conn.prepareStatement(query);

			prstmt.setInt(1, prodottoId);

			prstmt.execute(); // Esecuzione della query per rimuovere il prodotto

			// Creazione della query SQL per rimuovere un prodotto dal carrello
			query = "delete from carrello where id=?";

			prstmt = conn.prepareStatement(query);

			prstmt.setInt(1, prodottoId);

			prstmt.execute(); // Esecuzione della query per rimuovere il prodotto

			// Creazione della query SQL per rimuovere un prodotto dal negozio
			query = "delete from acquisti where id=?";

			prstmt = conn.prepareStatement(query);

			prstmt.setInt(1, prodottoId);

			prstmt.execute(); // Esecuzione della query per rimuovere il prodotto

			// Creazione della query SQL per rimuovere un prodotto dal negozio
			query = "delete from prodotto where id=?";

			prstmt = conn.prepareStatement(query);

			prstmt.setInt(1, prodottoId);

			prstmt.execute(); // Esecuzione della query per rimuovere il prodotto

			syncDb(); // Sincronizzazione con il database

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Esegue l'acquisto dei prodotti presenti nel carrello dell'utente.
	 *
	 * @return True se l'acquisto è riuscito, altrimenti False.
	 */
	public boolean acquista() {
		double totale = 0.0;
		boolean conferma;
		Scanner in = new Scanner(System.in);

		try {
			// Calcola il totale dell'acquisto sommando i prezzi dei prodotti nel carrello
			for (Prodotto p : this.UtenteLoggato.getCarrello()) {
				totale += p.getPrezzo() * p.getQuantità();
			}

			System.out.print("Confermi il pagamento di " + totale + "? (true/false): ");
			conferma = in.nextBoolean();

			if (conferma) {
				// Esegue l'acquisto
				for (Prodotto p : this.UtenteLoggato.getCarrello()) {
					int idProdotto = p.getId();
					int quantitaCarrello = p.getQuantità();

					// Registra l'acquisto dell'utente
					String insertAcquistoString = "INSERT INTO Acquisti (username, prodotto_id, quantita_acquistata) VALUES (?, ?, ?)";
					PreparedStatement insertAcquistoSt = connection.prepareStatement(insertAcquistoString);
					insertAcquistoSt.setString(1, this.UtenteLoggato.getUsername());
					insertAcquistoSt.setInt(2, idProdotto);
					insertAcquistoSt.setInt(3, quantitaCarrello);
					insertAcquistoSt.executeUpdate();
				}

				// Svuota il carrello dell'utente e aggiorna il database
				this.UtenteLoggato.getCarrello().clear();

				String deleteString = "DELETE FROM Carrello WHERE username = ?";
				PreparedStatement prpSt = this.connection.prepareStatement(deleteString);
				prpSt.setString(1, this.UtenteLoggato.getUsername());
				prpSt.executeUpdate();

				System.out.println("Pagamento di " + totale + " euro eseguito con successo.");

				syncDb(); // Sincronizza il database

				return true;
			} else {
				System.out.println("Acquisto annullato.");
				syncDb(); // Sincronizza il database
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

	/**
	 * Connette l'applicazione al database.
	 *
	 * @return Un messaggio di stato della connessione.
	 */
	public String connettiAlDb() {
		String connectionString = String.format("jdbc:mysql://localhost:3306/%s", dbName);

		try {
			Class.forName(JConnectionClass); // Ottenere la classe del driver all'interno del programma. Importante per
												// il funzionamento del driver.
			this.connection = DriverManager.getConnection(connectionString, dbUsername, dbPassword); // Creazione della
																										// connessione

			if (!syncDb()) { // Sincronizzazione con i dati del database
				throw new SQLException();
			}
		} catch (ClassNotFoundException e) {
			return "La classe " + JConnectionClass + " non è stata trovata dal programma.";
		} catch (SQLException e) {
			return "Si è verificata una SQLException: " + e.toString();
		} catch (Exception e) {
			return "Connessione non stabilita con il database: " + e.toString();
		}

		return "Connessione stabilita con il database: \"" + dbName + "\"";
	}

	/**
	 * Sincronizza le liste locali con i dati presenti nel database.
	 *
	 * @return True se la sincronizzazione è riuscita, altrimenti False.
	 */
	public boolean syncDb() {
		// Dati delle tabelle da aggiungere alla memoria locale
		ArrayList<Prodotto> prodottiDaAggiungere = new ArrayList<Prodotto>();
		ArrayList<Utente> utentiDaAggiungere = new ArrayList<Utente>();

		// Variabili per l'uso di PreparedStatement e ResultSet
		PreparedStatement prpSt;
		ResultSet rs;

		// Verifica della connessione al database
		if (this.connection == null) {
			System.err.println("Non sei connesso a nessun database!");
			return false;
		}

		try {
			// Sincronizzazione dei dati della tabella "prodotto"
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

			// Sincronizzazione dei dati della tabella "utente"
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

			// Aggiornamento delle liste locali
			this.negozio = prodottiDaAggiungere;
			this.listaUtenti = utentiDaAggiungere;

			return true;

		} catch (SQLException e) {
			System.err.println("Qualcosa è andato storto con le operazioni SQL: " + e.toString());
			return false;
		} catch (Exception e) {
			System.err.println("Qualcosa è andato storto con le operazioni SQL: " + e.toString());
			return false;
		}
	}

	/**
	 * Restituisce una rappresentazione in formato stringa del negozio.
	 *
	 * @return Stringa rappresentante il negozio.
	 */
	public String mostraProdotti() {
		return "Prodotti [negozio=" + this.negozio + "]";
	}

}
