package org.example;

public class DettaglioOrdine {

    private int idDettagliOrdine;
    private int idOrdine;
    private int idProdotto;
    private int quantita;
    private double prezzoUnitario ;

    public DettaglioOrdine(int idDettagliOrdine, int idOrdine, int idProdotto, int quantita, double prezzoUnitario) {
        this.idDettagliOrdine = idDettagliOrdine;
        this.idOrdine = idOrdine;
        this.idProdotto = idProdotto;
        this.quantita = quantita;
        this.prezzoUnitario = prezzoUnitario;
    }

    public DettaglioOrdine(int idOrdine, int idProdotto, int quantita, double prezzoUnitario) {
        this.idOrdine = idOrdine;
        this.idProdotto = idProdotto;
        this.quantita = quantita;
        this.prezzoUnitario = prezzoUnitario;
    }


    public int getIdDettagliOrdine() {
        return idDettagliOrdine;
    }

    public void setIdDettagliOrdine(int idDettagliOrdine) {
        this.idDettagliOrdine = idDettagliOrdine;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public double getPrezzoUnitario() {
        return prezzoUnitario;
    }

    public void setPrezzoUnitario(double prezzoUnitario) {
        this.prezzoUnitario = prezzoUnitario;
    }

    @Override
    public String toString() {
        return "DettaglioOrdine{" +
                "idDettagliOrdine=" + idDettagliOrdine +
                ", idOrdine=" + idOrdine +
                ", idProdotto=" + idProdotto +
                ", quantita=" + quantita +
                ", prezzoUnitario=" + prezzoUnitario +
                '}';
    }
}
