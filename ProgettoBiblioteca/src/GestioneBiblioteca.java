import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestioneBiblioteca {

    public static void main(String[] args) {
Utente [] utenti = new Utente[3] ;
Biblioteca biblioteca = new Biblioteca(5,5);
        Scanner scanner = new Scanner(System.in);
        // aggiungo i libri ad una lista

        Libro libro1 = new Libro("Harry Potter e la pietra filosofale" ,"JK.Rowling","7845");
        Libro libro2 = new Libro("Il Signore degli Anelli","Tolkien","9685");
        Libro libro3 = new Libro("Percy Jackson","Rick Riordan","6325");
        Libro libro4 = new Libro("50 Sfumature","E.L James","2314");
        Libro libro5 = new Libro("Scheletri","Zero Calcare","5463");

        Utente utente1 =  new Utente("Giuseppe","Mele","1");
        Utente utente2 = new  Utente("Nicola","Lavolpe","2");
        Utente utente3 = new Utente("Stefano","Giliberti","3");

        biblioteca.aggiungiLibro(libro1);
        biblioteca.aggiungiLibro(libro2);
        biblioteca.aggiungiLibro(libro3);
        biblioteca.aggiungiLibro(libro4);
        biblioteca.aggiungiLibro(libro5);
        utenti[0] = utente1;
        utenti[1] = utente2;
        utenti[2] = utente3;

        biblioteca.registraUtente(utente1);
        biblioteca.registraUtente(utente2);
        biblioteca.registraUtente(utente3);

        while(true) {
            System.out.println("---- Menu Biblioteca ---");
            System.out.println("-----------------------------");
            System.out.println("|   1 : Catalogo Libri      |");
            System.out.println("|   2 : Mostra utenti       |");
           System.out.println("|   3 : Prestare un libro   |");
           System.out.println("|   4 : Restituire un libro |");
            System.out.println("|   5 : Libri in prestito   |");
            System.out.println("|   0 : Esci                |");
            System.out.println("-----------------------------");
            System.out.println("Inserisci la scelta :");

            int scelta = Integer.parseInt(scanner.nextLine());


            switch (scelta) {

                case 1:
                    System.out.println("Catalogo");
                    biblioteca.mostraCatalogo();
                    break ;
                case 2:
                    System.out.println(utente1.getDettagli());
                    System.out.println(utente2.getDettagli());
                    System.out.println(utente3.getDettagli());
                    break ;
                case 3:
                    System.out.println("Inserisci l'ID utente");
                    String id = scanner.nextLine();
                    System.out.println("Inserisci l'ISBN");
                    String isbn = scanner.nextLine();
                    biblioteca.prestitoLibro(id,isbn);
                    break ;

                case 4:
                    System.out.println("Inserisci l'ID utente");
                    String id_restituzione = scanner.nextLine();
                    System.out.println("Inserisci l'ISBN");
                    String isbn_restituzione = scanner.nextLine();
                    biblioteca.restituzioneLibro(id_restituzione,isbn_restituzione);
                    break ;
                case 5:
                    System.out.println("Libri in prestito suddivisi per utente");
                    for(int i = 0; i < utenti.length; i++ ) {
                        System.out.println(utenti[i].getDettagli());
                        utenti[i].mostraLibriInPrestito();
                        System.out.println("--------------");
                    }
                    break ;
                case 0:
                    System.out.println("uscita");
                    return ;
                default:
                    System.out.println("Scelta non valida, riprova");

            }
        }

    }
}
