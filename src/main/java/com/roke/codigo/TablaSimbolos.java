package com.roke.codigo;
import java.util.HashMap;

public class TablaSimbolos {
   
    private HashMap<String, Object> simbolos;

    public TablaSimbolos() {
        this.simbolos = new HashMap<>();
    }

    public void insertar(String nombre, Object valor) {
        simbolos.put(nombre, valor);
    }

    public Object buscar(String nombre) {
        return simbolos.get(nombre);
    }
}