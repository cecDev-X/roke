package com.roke.codigo;

public class Numerin {
    private int valor;

    public Numerin(String valor) {
        this.valor = Integer.parseInt(valor);
    }

    public int getValor() { return valor; }
    @Override
    public String toString() { return String.valueOf(valor); }
}