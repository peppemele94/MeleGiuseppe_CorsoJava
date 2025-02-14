public class Utente {

    private String nome;
    private String cognome;
    private String IDutente ;
    private Libro[] listaPrestiti ;
    private int prestiti;

    public Utente(String nome,String cognome,String IDutente){
        this.nome = nome ;
        this.cognome = cognome ;
        this.IDutente = IDutente;
        this.listaPrestiti = new Libro[3];
        this.prestiti = 0;
    }

    public void prendereInPrestito(Libro libro) {

        if(prestiti < 3 && libro.getDisponibile()) {
            libro.prestareLibro();
            //aggiungo il libro all'array e incremento i prestiti
            listaPrestiti[prestiti] = libro ;
            ++prestiti;
        } else {
            System.out.println("Libro gia preso o limite prestiti raggiunto");
        }

    }
    public void restituireLibro(Libro libro) {

        for(int i = 0; i < prestiti; ++i) {
            //se trovo il libro nell'array lo imposto come disponibile
     if(listaPrestiti[i] == libro) {
         libro.restituireLibro();
         //imposto l'ultima posizione a null decremento prestiti
         listaPrestiti[i] = listaPrestiti[prestiti - 1];
         listaPrestiti[prestiti -1] = null ;
         prestiti--;
         return;
        }
    }
        System.out.println("Libro non preso in prestito da questo utente");

    }


    public void mostraLibriInPrestito() {
        System.out.println("Libri presi in prestito per "
        + this.nome + " " + this.cognome);
        for(int i = 0; i < prestiti; ++i) {
            System.out.println(listaPrestiti[i].getDettagli());
        }
    }
public String getDettagli() {

        return "Utente " + this.nome + " " + this.cognome + " " + IDutente ;
}

public String getIDutente() {
        return this.IDutente;
}
}
