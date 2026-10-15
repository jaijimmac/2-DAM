package org.example.Entity;

import org.example.ENUMS.ValorComparacion;

public class ResultadoComparacion {

    private String nomFichero;

    private ValorComparacion valorComparacion;

    public ResultadoComparacion(String nomFichero, ValorComparacion valorComparacion){
        this.nomFichero = nomFichero;
        this.valorComparacion = valorComparacion;
    }

    public String getNomFichero() {
        return nomFichero;
    }

    public void setNomFichero(String nomFichero) {
        this.nomFichero = nomFichero;
    }

    public ValorComparacion getValorComparacion() {
        return valorComparacion;
    }

    public void setValorComparacion(ValorComparacion valorComparacion) {
        this.valorComparacion = valorComparacion;
    }
}
