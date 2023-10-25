package entities;

import java.util.ArrayList;

/**
 * Questa classe rappresenta un utente di un negozio sportivo.
 */
public class Utente {
	
	private String username; // Chiave primaria (PK)
	private String email;
	private String password;
	private String nome;
	private String cognome;
	private ArrayList<Prodotto> carrello = new ArrayList<>();
	private boolean isAdmin;
	
	/**
	 * Costruttore vuoto per la classe Utente.
	 */
	public Utente() {
		
	}
	
	/**
	 * Costruttore per la classe Utente con parametri principali.
	 * 
	 * @param username L'username dell'utente.
	 * @param email L'email dell'utente.
	 * @param password La password dell'utente.
	 * @param nome Il nome dell'utente.
	 * @param cognome Il cognome dell'utente.
	 * @param isAdmin Indica se l'utente è un amministratore o no.
	 */
	public Utente(String username, String email, String password, String nome, String cognome,
			boolean isAdmin) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.isAdmin = isAdmin;
	}
	
	/**
	 * Costruttore per la classe Utente con tutti i parametri.
	 * 
	 * @param username L'username dell'utente.
	 * @param email L'email dell'utente.
	 * @param password La password dell'utente.
	 * @param nome Il nome dell'utente.
	 * @param cognome Il cognome dell'utente.
	 * @param carrello La lista dei prodotti nel carrello dell'utente.
	 * @param isAdmin Indica se l'utente è un amministratore o no.
	 */
	public Utente(String username, String email, String password, String nome, String cognome,
			ArrayList<Prodotto> carrello, boolean isAdmin) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.isAdmin = isAdmin;
		this.carrello = carrello;
	}

	/**
	 * Restituisce l'username dell'utente.
	 * 
	 * @return L'username dell'utente.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Imposta l'username dell'utente.
	 * 
	 * @param username L'username dell'utente da impostare.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Restituisce l'email dell'utente.
	 * 
	 * @return L'email dell'utente.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Imposta l'email dell'utente.
	 * 
	 * @param email L'email dell'utente da impostare.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Restituisce la password dell'utente.
	 * 
	 * @return La password dell'utente.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Imposta la password dell'utente.
	 * 
	 * @param password La password dell'utente da impostare.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Restituisce il nome dell'utente.
	 * 
	 * @return Il nome dell'utente.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Imposta il nome dell'utente.
	 * 
	 * @param nome Il nome dell'utente da impostare.
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Restituisce il cognome dell'utente.
	 * 
	 * @return Il cognome dell'utente.
	 */
	public String getCognome() {
		return cognome;
	}

	/**
	 * Imposta il cognome dell'utente.
	 * 
	 * @param cognome Il cognome dell'utente da impostare.
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	/**
	 * Restituisce la lista dei prodotti nel carrello dell'utente.
	 * 
	 * @return La lista dei prodotti nel carrello dell'utente.
	 */
	public ArrayList<Prodotto> getCarrello() {
		return carrello;
	}

	/**
	 * Imposta la lista dei prodotti nel carrello dell'utente.
	 * 
	 * @param carrello La lista dei prodotti nel carrello dell'utente da impostare.
	 */
	public void setCarrello(ArrayList<Prodotto> carrello) {
		this.carrello = carrello;
	}

	/**
	 * Verifica se l'utente è un amministratore.
	 * 
	 * @return True se l'utente è un amministratore, altrimenti False.
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * Imposta se l'utente è un amministratore o no.
	 * 
	 * @param isAdmin True se l'utente è un amministratore, altrimenti False.
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * Restituisce una rappresentazione in formato stringa dell'oggetto Utente.
	 * 
	 * @return Una stringa che rappresenta l'oggetto Utente.
	 */
	@Override
	public String toString() {
		return "Utente [username=" + username + ", email=" + email + ", password=" + password + ", nome=" + nome
				+ ", cognome=" + cognome + ", carrello=" + carrello + ", isAdmin=" + isAdmin + "]";
	}
}
