public class Biblioteca {

    private Libro[] catalogoLibri;
    private Utente[] utentiRegistrati;
    private int numeroLibri;
    private int numeroUtenti;

    public Biblioteca(int maxLibri, int maxUtenti) {
        catalogoLibri = new Libro[maxLibri];
        utentiRegistrati = new Utente[maxUtenti];
        numeroLibri = 0;
        numeroUtenti = 0;
    }

    public void aggiungiLibro(Libro libro) {
        // se il numero di libri + minore della lunghezza del catalogo
        // aggiungi il libro al catalogo
        if (numeroLibri < catalogoLibri.length) {
            catalogoLibri[numeroLibri++] = libro;
        } else {
            System.out.println("Biblioteca piena,impossibile aggiungere altri libri");
        }
    }

    public void registraUtente(Utente utente) {
        if (numeroUtenti < utentiRegistrati.length) {
            utentiRegistrati[numeroUtenti++] = utente;
        } else {
            System.out.println("Numero massimo di utenti raggiungo..");
        }
    }

public void mostraCatalogo() {
    System.out.println("- Catalogo completo -");
    for(int i = 0; i < numeroLibri; ++i ) {
        System.out.println(catalogoLibri[i].getDettagli());
    }
}

public void prestitoLibro(String idUtente, String ISBN) {

        //controlla se l'utente è registrato quindi controlla se c'è un ID corrispondente
        //scorre l'array catalogo libri e se trova un libro con ISBN uguale a quello fornito
        //passa il libro all metodo prendereInPrestito

        for(int i = 0; i < utentiRegistrati.length; i++) {
            Utente utente = utentiRegistrati[i];
            if(utente != null && utente.getIDutente().equals(idUtente)){
                for(int j = 0; j < catalogoLibri.length; j++) {
                    Libro libro = catalogoLibri[j];
                    if(libro != null && libro.getISBN().equals(ISBN)) {
                        utente.prendereInPrestito(libro);
                        return ;
                    }
                }

            }
        }
    System.out.println("utente o libro non trovato");
}

public void restituzioneLibro(String idUtente,String ISBN) {

        //controlla se l'utente è registrato
    // cerca il libroo nel catalogo con un ISB fornito
    // se l'ISBN viene trovato chiama il metodo restituire libro

        for(int i = 0; i < utentiRegistrati.length; i++) {
        Utente utente = utentiRegistrati[i];
        if(utente != null && utente.getIDutente().equals(idUtente)){
            for(int j = 0; j < catalogoLibri.length; j++) {
                Libro libro = catalogoLibri[j];
                if(libro != null && libro.getISBN().equals(ISBN)) {
                    utente.restituireLibro(libro);
                    return ;
                }
            }

        }
    }
    System.out.println("Utente o libro non trovato");
}
}
