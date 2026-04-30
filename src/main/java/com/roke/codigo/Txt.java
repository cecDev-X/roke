package com.roke.codigo;

public class Txt {
    private String valor;

    public Txt(String valor) {
         this.valor = valor.replace("\"", "");
    }
    //metodos get y Tostring 
    public String getValor() { return valor; }
    @Override
    public String toString() { return valor; }
}