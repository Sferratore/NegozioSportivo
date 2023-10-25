CREATE DATABASE NegozioSportivo;
USE NegozioSportivo;
-- Tabella Utente
CREATE TABLE Utente (
    username VARCHAR(255) PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    cognome VARCHAR(255) NOT NULL,
    isAdmin BOOLEAN NOT NULL
);

-- Tabella Prodotto
CREATE TABLE Prodotto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descrizione TEXT,
    prezzo DOUBLE NOT NULL,
    quantità INT NOT NULL
);

-- Tabella Acquisti (per collegare Utente e Prodotto acquistato)
CREATE TABLE Acquisti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    prodotto_id INT,
    quantita_acquistata INT NOT NULL,
    FOREIGN KEY (username) REFERENCES Utente(username),
    FOREIGN KEY (prodotto_id) REFERENCES Prodotto(id)
);

-- Tabella Carrello (per gestire i prodotti nel carrello degli utenti)
CREATE TABLE Carrello (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    prodotto_id INT,
    quantita_carrello INT NOT NULL,
    FOREIGN KEY (username) REFERENCES Utente(username),
    FOREIGN KEY (prodotto_id) REFERENCES Prodotto(id)
);


INSERT INTO Prodotto (nome, descrizione, prezzo, quantità)
VALUES
    ('Prodotto1', 'Descrizione del prodotto 1', 19.99, 50),
    ('Prodotto2', 'Descrizione del prodotto 2', 29.99, 30);
COMMIT;


SELECT * FROM Prodotto