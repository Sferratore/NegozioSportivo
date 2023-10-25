package entities;

/**s
 * Classe che rappresenta i prodotti di un negozio sportivo.
 */
public class Prodotto {
	
	private int id; // PK
	private String nome;
	private String descrizione;
	private double prezzo;
	private int quantità;
	
	// Costruttori
	public Prodotto() {

	}
	
	public Prodotto(String nome, String descrizione, double prezzo, int quantità) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.prezzo = prezzo;
		this.quantità = quantità;
	}
	
	public Prodotto(int id, String nome, String descrizione, double prezzo, int quantità) {
		this.id = id;
		this.nome = nome;
		this.descrizione = descrizione;
		this.prezzo = prezzo;
		this.quantità = quantità;
	}
	
	// Metodi

	
	// Getters & Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public int getQuantità() {
		return quantità;
	}

	public void setQuantità(int quantità) {
		this.quantità = quantità;
	}

	// toString
	@Override
	public String toString() {
		return "Prodotto [id=" + id + ", nome=" + nome + ", descrizione=" + descrizione + ", prezzo=" + prezzo
				+ ", quantità=" + quantità + "]";
	}
}
