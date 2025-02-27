package org.example;

import com.github.javafaker.Faker;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static DbConnection db;
    private static Faker faker = new Faker(new Locale("it-IT"));
    private static Random random = new Random();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            db = new DbConnection();

            // Popoliamo il database se necessario
           // popolaClienti(5);
           // popolaProdotti(10);

            // Mostra il menu interattivo
            menu();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void menu() throws SQLException {
        int scelta;
        do {
            System.out.println("===== MENU PRINCIPALE =====");
            System.out.println("1. Visualizza tutti i clienti");
            System.out.println("2. Aggiungi un nuovo cliente");
            System.out.println("3. Visualizza tutti i prodotti");
            System.out.println("4. Aggiungi un nuovo prodotto");
            System.out.println("5. Effettua un nuovo ordine");
            System.out.println("6. Modifica un ordine esistente");
            System.out.println("7. Visualizza lo storico degli ordini");
            System.out.println("8. Elimina un ordine");
            System.out.println("9. Esci");
            System.out.print("Scelta: ");

            scelta = scanner.nextInt();
            scanner.nextLine(); // Consuma il newline dopo il numero

            switch (scelta) {
                case 1 -> db.findAll().forEach(System.out::println);
                case 2 -> aggiungiCliente();
                case 3 -> db.findAllproduct().forEach(System.out::println);
                case 4 -> aggiungiProdotto();
                case 5 -> effettuaOrdine();
                case 6 -> modificaOrdine();
                case 7 -> db.visualizzaStoricoOrdini();
                case 8 -> eliminaOrdine();
                case 9 -> System.out.println("Uscita dal programma...");
                default -> System.out.println("Scelta non valida! Riprova.");
            }
        } while (scelta != 9);
    }

    public static void aggiungiCliente() throws SQLException {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Cognome: ");
        String cognome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefono: ");
        String telefono = scanner.nextLine();

        Cliente c = new Cliente(nome, cognome, email, telefono);
        db.inserisciCliente(c);
        System.out.println("Cliente aggiunto con successo!");
    }

    public static void aggiungiProdotto() throws SQLException {
        System.out.print("Nome prodotto: ");
        String nome = scanner.nextLine();
        System.out.print("Descrizione: ");
        String descrizione = scanner.nextLine();
        System.out.print("Prezzo: ");
        double prezzo = scanner.nextDouble();
        System.out.print("Quantità disponibile: ");
        int quantita = scanner.nextInt();
        scanner.nextLine(); // Consuma il newline

        Prodotto p = new Prodotto(nome, descrizione, prezzo, quantita);
        db.inserisciProdotto(p);
        System.out.println("Prodotto aggiunto con successo!");
    }

    public static void effettuaOrdine() throws SQLException {
        db.findAll().forEach(System.out::println);
        System.out.print("Inserisci l'ID del cliente che effettua l'ordine: ");
        int idCliente = scanner.nextInt();
        scanner.nextLine();

        int idOrdine = db.inserisciOrdine(idCliente);
        if (idOrdine == -1) {
            System.out.println("Errore nella creazione dell'ordine.");
            return;
        }

        boolean aggiungereProdotti = true;
        while (aggiungereProdotti) {
            db.findAllproduct().forEach(System.out::println);
            System.out.print("ID prodotto da aggiungere: ");
            int idProdotto = scanner.nextInt();
            System.out.print("Quantità: ");
            int quantita = scanner.nextInt();
            scanner.nextLine();

            db.inserisciDettaglioOrdine(idOrdine, idProdotto, quantita);

            System.out.print("Vuoi aggiungere un altro prodotto? (s/n): ");
            String risposta = scanner.nextLine();
            aggiungereProdotti = risposta.equalsIgnoreCase("s");
        }
        System.out.println("Ordine completato!");
    }

    public static void modificaOrdine() throws SQLException {
        System.out.print("Inserisci l'ID dell'ordine da modificare: ");
        int idOrdine = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Inserisci l'ID del prodotto da modificare: ");
        int idProdotto = scanner.nextInt();
        System.out.print("Nuova quantità: ");
        int nuovaQuantita = scanner.nextInt();
        scanner.nextLine();

        db.modificaDettaglioOrdine(idOrdine, idProdotto, nuovaQuantita);
        System.out.println("Ordine modificato con successo!");
    }

    public static void eliminaOrdine() throws SQLException {
        System.out.print("Inserisci l'ID dell'ordine da eliminare: ");
        int idOrdine = scanner.nextInt();
        scanner.nextLine();

        String sql = "DELETE FROM ordini WHERE id_ordine = ?";
        try (var ps = db.conn.prepareStatement(sql)) {
            ps.setInt(1, idOrdine);
            int righeAffette = ps.executeUpdate();
            if (righeAffette > 0) {
                System.out.println("Ordine eliminato con successo!");
            } else {
                System.out.println("Ordine non trovato.");
            }
        }
    }

    public static void popolaClienti(int numeroClienti) throws SQLException {
        List<Cliente> clientiEsistenti = db.findAll();
        if (clientiEsistenti.size() >= numeroClienti) return;

        for (int i = 0; i < numeroClienti; i++) {
            String nome = faker.name().firstName();
            String cognome = faker.name().lastName();
            String email = nome.toLowerCase() + "." + cognome.toLowerCase() + "@example.com";
            String telefono = faker.phoneNumber().cellPhone();

            Cliente c = new Cliente(nome, cognome, email, telefono);
            db.inserisciCliente(c);
        }
    }

    public static void popolaProdotti(int numeroProdotti) throws SQLException {
        List<Prodotto> prodottiEsistenti = db.findAllproduct();
        if (prodottiEsistenti.size() >= numeroProdotti) return;

        for (int i = 0; i < numeroProdotti; i++) {
            String nome = faker.commerce().productName();
            String descrizione = faker.lorem().sentence();
            double prezzo = Double.parseDouble(faker.commerce().price(10.0, 1000.0).replace(",", "."));
            int quantita = faker.number().numberBetween(5, 50);

            Prodotto p = new Prodotto(nome, descrizione, prezzo, quantita);
            db.inserisciProdotto(p);
        }
    }
}
