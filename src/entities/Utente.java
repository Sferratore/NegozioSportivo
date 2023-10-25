package entities;

import java.util.ArrayList;

/**
 * Classe che rappresenta l'utente di un negozio sportivo.
 */
public class Utente {
	
	private String username; // PK
	private String email;
	private String password;
	private String nome;
	private String cognome;
	private ArrayList<Prodotto> carrello = new ArrayList<>();
	private boolean isAdmin;
	
	// Costruttori
	public Utente() {
		
	}
	
	public Utente(String username, String email, String password, String nome, String cognome,
			boolean isAdmin) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.isAdmin = isAdmin;
	}
	
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

	// Metodi
	
	
	// Getters & Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public ArrayList<Prodotto> getCarrello() {
		return carrello;
	}

	public void setCarrello(ArrayList<Prodotto> carrello) {
		this.carrello = carrello;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	// toString
	@Override
	public String toString() {
		return "Utente [username=" + username + ", email=" + email + ", password=" + password + ", nome=" + nome
				+ ", cognome=" + cognome + ", carrello=" + carrello + ", isAdmin=" + isAdmin + "]";
	}
	
	
	
}
