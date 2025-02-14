public class Libro {

    private String titolo ;
    private String autore ;
    private String ISBN ;
    private boolean disponibile ;

    public Libro(String titolo,String autore,String ISBN) {

        this.titolo = titolo ;
        this.autore = autore ;
        this.ISBN = ISBN;
        this.disponibile = true ;

    }


    public String getTitolo() {
        return this.titolo;
    }
    public String getAutore() {
        return this.autore;
    }
    public String getISBN() {return this.ISBN ;}
    public boolean getDisponibile(){ return this.disponibile;}
    public String getDettagli () {

        return "Titolo : " + this.titolo + " Autore : " + this.autore +
                " ISBN : " + this.ISBN + " Disponibile :" + this.disponibile;
    }

    public void prestareLibro() {
    //imposto disponibile uguale false se disponibile
        if(disponibile ) {
            this.disponibile = false ;
            System.out.println("Libro :" + this.titolo + " preso");
        }else {
            System.out.println("Libro :" + this.titolo + "non disponibile");
        }
    }

    public void restituireLibro() {
        // se il libro
        if(!disponibile) {
            disponibile = true ;
            System.out.println("Libro:" + this.titolo + " restituito");
        }
        else {
            System.out.println("Il libro :" + this.titolo + " non era in prestito");
        }
    }


}
