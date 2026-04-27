package com.roke.codigo;

public class Operacion {
    public static Numerin sumar(Numerin a, Numerin b) {
        int resultado = a.getValor() + b.getValor();
        return new Numerin(String.valueOf(resultado));
    }
    
    public static Numerin restar(Numerin a, Numerin b) {
        int resultado = a.getValor() - b.getValor();
        return new Numerin(String.valueOf(resultado));
    }
    
    public static Numerin multiplicar(Numerin a, Numerin b) {
        int resultado = a.getValor() * b.getValor();
        return new Numerin(String.valueOf(resultado));
    }
    
    public static Numerin dividir(Numerin a, Numerin b) {
        int resultado = a.getValor() / b.getValor();
        return new Numerin(String.valueOf(resultado));
    }
    
    
    public static Duvalin sumar(Duvalin a, Duvalin b) {
        double resultado = a.getValor() + b.getValor();
        return new Duvalin(String.valueOf(resultado));
    }
    
    public static Duvalin restar(Duvalin a, Duvalin b) {
        double resultado = a.getValor() - b.getValor();
        return new Duvalin(String.valueOf(resultado));
    }
    
    public static Duvalin multiplicar(Duvalin a, Duvalin b) {
        double resultado = a.getValor() * b.getValor();
        return new Duvalin(String.valueOf(resultado));
    }
    
    public static Duvalin dividir(Duvalin a, Duvalin b) {
        double resultado = a.getValor() / b.getValor();
        return new Duvalin(String.valueOf(resultado));
    }
}