package org.example;

import java.sql.Timestamp;

public class Ordine {

    private int idOrdine;
    private int idCliente ;
    private Timestamp dataOrdine;

    public Ordine(int idOrdine, int idCliente, Timestamp dataOrdine) {
        this.idOrdine = idOrdine;
        this.idCliente = idCliente;
        this.dataOrdine = dataOrdine;
    }

    public Ordine(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Timestamp getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(Timestamp dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    @Override
    public String toString() {
        return "Ordine{" +
                "idOrdine=" + idOrdine +
                ", idCliente=" + idCliente +
                ", dataOrdine=" + dataOrdine +
                '}';
    }
}
