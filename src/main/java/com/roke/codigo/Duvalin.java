package com.roke.codigo;

public class Duvalin {
    private double valor;
    // algo aquí
    public Duvalin(String valor) {
        this.valor = Double.parseDouble(valor);
    }
    //metodos get y toString
    public double getValor() { return valor; }
    @Override
    public String toString() { return String.valueOf(valor); }
}