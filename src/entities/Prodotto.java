package entities;

/**
 * Classe che rappresenta i prodotti di un negozio sportivo.
 */
public class Prodotto {
	
	private int id; // Chiave primaria (PK)
	private String nome;
	private String descrizione;
	private double prezzo;
	private int quantità;
	
	/**
	 * Costruttore vuoto per la classe Prodotto.
	 */
	public Prodotto() {

	}
	
	/**
	 * Costruttore per la classe Prodotto con parametri principali.
	 * 
	 * @param nome Il nome del prodotto.
	 * @param descrizione La descrizione del prodotto.
	 * @param prezzo Il prezzo del prodotto.
	 * @param quantità La quantità disponibile del prodotto.
	 */
	public Prodotto(String nome, String descrizione, double prezzo, int quantità) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.prezzo = prezzo;
		this.quantità = quantità;
	}
	
	/**
	 * Costruttore per la classe Prodotto con tutti i parametri.
	 * 
	 * @param id L'ID univoco del prodotto.
	 * @param nome Il nome del prodotto.
	 * @param descrizione La descrizione del prodotto.
	 * @param prezzo Il prezzo del prodotto.
	 * @param quantità La quantità disponibile del prodotto.
	 */
	public Prodotto(int id, String nome, String descrizione, double prezzo, int quantità) {
		this.id = id;
		this.nome = nome;
		this.descrizione = descrizione;
		this.prezzo = prezzo;
		this.quantità = quantità;
	}
	
	/**
	 * Restituisce l'ID del prodotto.
	 * 
	 * @return L'ID del prodotto.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Imposta l'ID del prodotto.
	 * 
	 * @param id L'ID del prodotto da impostare.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Restituisce il nome del prodotto.
	 * 
	 * @return Il nome del prodotto.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Imposta il nome del prodotto.
	 * 
	 * @param nome Il nome del prodotto da impostare.
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Restituisce la descrizione del prodotto.
	 * 
	 * @return La descrizione del prodotto.
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * Imposta la descrizione del prodotto.
	 * 
	 * @param descrizione La descrizione del prodotto da impostare.
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	/**
	 * Restituisce il prezzo del prodotto.
	 * 
	 * @return Il prezzo del prodotto.
	 */
	public double getPrezzo() {
		return prezzo;
	}

	/**
	 * Imposta il prezzo del prodotto.
	 * 
	 * @param prezzo Il prezzo del prodotto da impostare.
	 */
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	/**
	 * Restituisce la quantità disponibile del prodotto.
	 * 
	 * @return La quantità disponibile del prodotto.
	 */
	public int getQuantità() {
		return quantità;
	}

	/**
	 * Imposta la quantità disponibile del prodotto.
	 * 
	 * @param quantità La quantità disponibile del prodotto da impostare.
	 */
	public void setQuantità(int quantità) {
		this.quantità = quantità;
	}

	/**
	 * Restituisce una rappresentazione in formato stringa dell'oggetto Prodotto.
	 * 
	 * @return Una stringa che rappresenta l'oggetto Prodotto.
	 */
	@Override
	public String toString() {
		return "Prodotto [id=" + id + ", nome=" + nome + ", descrizione=" + descrizione + ", prezzo=" + prezzo
				+ ", quantità=" + quantità + "]";
	}
}
