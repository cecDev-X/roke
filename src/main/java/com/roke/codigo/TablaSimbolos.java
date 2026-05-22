package com.roke.codigo;
import java.util.HashMap;
import java.util.Stack;

public class TablaSimbolos {

    private Stack<HashMap<String, Object>> pila;

    public TablaSimbolos() {
        pila = new Stack<>();
        pila.push(new HashMap<>());
    }

    public void entrarBloque() {
        pila.push(new HashMap<>());
    }

    public void salirBloque() {
        if (pila.size() > 1) {
            pila.pop();
        }
    }

    public void insertar(String nombre, Object valor) {
        pila.peek().put(nombre, valor);
    }

    public Object buscar(String nombre) {
        for (int i = pila.size() - 1; i >= 0; i--) {
            if (pila.get(i).containsKey(nombre)) {
                return pila.get(i).get(nombre);
            }
        }
        return null;
    }
}