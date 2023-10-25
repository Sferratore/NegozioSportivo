package menu;

import entities.Prodotto;
import entities.Utente;

public class Menu {
	private Boolean isStarted = true;
	private NegozioDao negozioDao;

	public Menu() {
		this.negozioDao = new NegozioDao();
	}

	// metodo per l'avvio del servizio
	public void interagisci() {
		Utente u = negozioDao.getUtenteLoggato();
		
		if(!u) {
			System.out.println("** Nessun utente loggato, registrati! **");
			String username = Utilities.userInput(String.class, "Inserisci nuovo nome utente: ");
			String password = Utilities.userInput(String.class, "Inscerisci la nuova password");
			Boolean b = negozioDao.login(username, password);
			if(b)){
				System.out.println("loggato con successo");
			} else {
				System.err.println("non sei riuscito a loggarti");
				interagisci();
			}
		}
		
		if(!u.isAdmin()) {
			while (isStarted) {
				Integer userChoice = Utilities.userInput(Integer.class, "Benvenuto User:" + "\n1 - Mostra tutti i prodotti"
						+ "\n2 - Aggiungi al carrello" + "\n3 - Rimuovi dal carrello" + "\n4 - Acquista" + "\n5 - Esci");
				
				switch (userChoice) {
				case 1:
					for(Prodotto p : negozioDao.getNegozio()) {
						System.out.println(p.toString());
					}
					break;
				case 2:
					Integer addId = Utilities.userInput(Integer.class, "Inserisci l'id del prodotto da acquistare");
					Boolean b = negozioDao.aggiungiAlCarrello(addId);
					if(b) {
						System.out.println("aggiunto con successo");
					} else {
						System.err.println("non è stato possibile aggiungere");
					}
					break;
				case 3:
					Integer removeId = Utilities.userInput(Integer.class, "Inserisci l'id del prodotto da rimuovere");
					Boolean b1 = negozioDao.rimuoviDalCarrello(removeId);
					if(b1) {
						System.out.println("rimosso con successo");
					} else {
						System.err.println("non è stato possibile rimuovere");
					}
					break;
				case 4:
					Boolean b3 = negozioDao.acquista();
					if(b3) {
						System.out.println("acquistato con successo");
					} else {
						System.err.println("non è stato possibile acquistare");
					}
					break;
				case 5:
					System.out.println("** Arrivederci e grazie... **");
					isStarted = !isStarted;
					break;
				default:
					interagisci();
				}
			}
			
		} else if (u.isAdmin()) {
			Integer adminChoice = Utilities.userInput(Integer.class, "Benvenuto Admin:" + "\n1 - Aggiungi un Prodotto"
					+ "\n2 - Rimuovi un Prodotto" + "\n3 - Esci");
			
			switch (adminChoice) {
			case 1:
				String nome = Utilities.userInput(String.class, "Inserisci nome nuovo prodotto: ");
				String descrizione = Utilities.userInput(String.class, "Inserisci nome nuovo prodotto: ");
				Double prezzo = Utilities.userInput(Double.class, "Inserisci nome nuovo prodotto: ");
				Integer quantita = Utilities.userInput(Integer.class, "Inserisci nome nuovo prodotto: ");
				
				Prodotto newP = new Prodotto(nome, descrizione, prezzo, quantita);
				Boolean b = negozioDao.aggiungiProdotto(newP);
				if(b) {
					System.out.println("aggiunto con successo");
				} else {
					System.err.println("non è stato possibile aggiungere");
				}
				break;
			case 2:
				Integer removeId = Utilities.userInput(Integer.class, "Inserisci l'id del prodotto da rimuovere");
				Boolean b1 = negozioDao.rimuoviProdotto(removeId);
				if(b1) {
					System.out.println("rimosso con successo");
				} else {
					System.err.println("non è stato possibile rimuovere");
				}
				break;
			case 3:
				System.out.println("** Arrivederci e grazie... **");
				isStarted = !isStarted;
				break;
			default:
				interagisci();
			}
		}
		
	}
}
