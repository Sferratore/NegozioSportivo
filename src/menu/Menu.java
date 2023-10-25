package menu;

import entities.Prodotto;
import entities.Utente;
import dao.*;

/**
 * Classe che rappresenta il menu di interazione con il negozio sportivo.
 */
public class Menu {
	private Boolean isStarted = true;
	private NegozioDao negozioDao;

	/**
	 * Costruttore della classe Menu che inizializza il dao del negozio.
	 */
	public Menu() {
		this.negozioDao = new NegozioDao();
	}

	/**
	 * Metodo per l'avvio del servizio.
	 */
	public void interagisci() {

		Integer startChoice = Utilities.userInput(Integer.class, "1 - Registrati"
				+ "\n2 - Loggati");

		switch (startChoice) {
		case 1:
			System.out.println("** Registrati! **");
			String username = Utilities.userInput(String.class, "Inserisci nuovo nome utente: ");
			String password = Utilities.userInput(String.class, "Inscerisci la nuova password: ");
			String email = Utilities.userInput(String.class, "Inscerisci la tua mail: ");
			String nome = Utilities.userInput(String.class, "Inscerisci il tuo nome: ");
			String cognome = Utilities.userInput(String.class, "Inscerisci il tuo cognome: ");
			Boolean isAdmin = false;

			Utente newUtente = new Utente(username, email, password, nome, cognome, isAdmin);

			Boolean b = negozioDao.registra(newUtente);
			if (b) {
				System.out.println("registrato con successo");
			} else {
				System.err.println("non sei riuscito a registrarti");
				interagisci();
			}
			break;
		case 2:
			System.out.println("** Menu login, loggati! **");
			String username1 = Utilities.userInput(String.class, "Inserisci nome utente: ");
			String password1 = Utilities.userInput(String.class, "Inscerisci la nuova password: ");

			Boolean b1 = negozioDao.login(username1, password1);
			if (b1) {
				System.out.println("loggato con successo");
			} else {
				System.err.println("non sei riuscito a loggare");
				interagisci();
			}
			break;
		default:
			interagisci();
			break;
		}

		Utente u = negozioDao.getUtenteLoggato();

		if (!u.isAdmin()) {
			while (isStarted) {
				Integer userChoice = Utilities.userInput(Integer.class, "Benvenuto User:" + "\n1 - Mostra tutti i prodotti"
						+ "\n2 - Aggiungi al carrello" + "\n3 - Rimuovi dal carrello" + "\n4 - Acquista" + "\n5 - Visualizza Carrello" + "\n6 - Ricerca Prodotto per nome" + "\n0 - Esci");

				switch (userChoice) {
				case 1:
					for (Prodotto p : negozioDao.getNegozio()) {
						System.out.println(p.toString());
					}
					break;
				case 2:
					Integer addId = Utilities.userInput(Integer.class, "Inserisci l'id del prodotto da acquistare");
					Integer addQt = Utilities.userInput(Integer.class, "Inserisci la quantità");
					Boolean b = negozioDao.aggiungiAlCarrello(addId, addQt);
					if (b) {
						System.out.println("aggiunto con successo");
					} else {
						System.err.println("non è stato possibile aggiungere");
					}
					break;
				case 3:
					Integer removeId = Utilities.userInput(Integer.class, "Inserisci l'id del prodotto da rimuovere");
					Boolean b1 = negozioDao.rimuoviDalCarrello(removeId);
					if (b1) {
						System.out.println("rimosso con successo");
					} else {
						System.err.println("non è stato possibile rimuovere");
					}
					break;
				case 4:
					Boolean b3 = negozioDao.acquista();
					if (b3) {
						System.out.println("acquistato con successo");
					} else {
						System.err.println("non è stato possibile acquistare");
					}
					break;
				case 5:
					System.out.println(u.getCarrello());
					break;
				case 6:
					boolean flag = false;
					String nome = Utilities.userInput(String.class, "Inserisci il nome del prodotto: ");
					for (Prodotto p : negozioDao.getNegozio()) {
						if(p.getNome().equals(nome)) {
							System.out.println(p.toString());
							flag = true;
						}
					}
					if(!flag) {
						System.out.println("Prodotto non trovato :-(");
					}
					break;
				case 0:
					System.out.println("** Arrivederci e grazie... **");
					isStarted = !isStarted;
					break;
				default:
					interagisci();
				}
			}

		} else if (u.isAdmin()) {
			while (isStarted) {
				Integer adminChoice = Utilities.userInput(Integer.class, "Benvenuto Admin:" + "\n1 - Aggiungi un Prodotto"
						+ "\n2 - Rimuovi un Prodotto" + "\n3 - Visualizza Prodotti" + "\n0 - Esci");

				switch (adminChoice) {
				case 1:
					String nome = Utilities.userInput(String.class, "Inserisci nome nuovo prodotto: ");
					String descrizione = Utilities.userInput(String.class, "Inserisci descrizione: ");
					Double prezzo = Utilities.userInput(Double.class, "Inserisci prezzo: ");
					Integer quantita = Utilities.userInput(Integer.class, "Inserisci quantità: ");

					Prodotto newP = new Prodotto(nome, descrizione, prezzo, quantita);
					Boolean b = negozioDao.aggiungiProdotto(newP);
					if (b) {
						System.out.println("aggiunto con successo");
					} else {
						System.err.println("non è stato possibile aggiungere");
					}
					break;
				case 2:
					Integer removeId = Utilities.userInput(Integer.class, "Inserisci l'id del prodotto da rimuovere");
					Boolean b1 = negozioDao.rimuoviProdotto(removeId);
					if (b1) {
						System.out.println("rimosso con successo");
					} else {
						System.err.println("non è stato possibile rimuovere");
					}
					break;
				case 3:
					System.out.println(negozioDao.getNegozio());
					break;
				case 0:
					System.out.println("** Arrivederci e grazie... **");
					isStarted = !isStarted;
					break;
				default:
					interagisci();
				}
			}
		}

	}

}
