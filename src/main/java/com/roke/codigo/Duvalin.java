package com.roke.codigo;

public class Duvalin {
    private double valor;

    public Duvalin(String valor) {
        this.valor = Double.parseDouble(valor);
    }

    public double getValor() { return valor; }
    @Override
    public String toString() { return String.valueOf(valor); }
}