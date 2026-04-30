package com.roke.codigo;

import java_cup.runtime.Symbol;
import java.io.Reader;

public class Lexer implements java_cup.runtime.Scanner {

    private String codigo;
    private int pos = 0;
    private int linea = 1;
    private int columna = 1;

    // Constructor que CUP usa por defecto (recibe un Reader)
    public Lexer(Reader reader) {
        StringBuilder sb = new StringBuilder();
        try {
            int ch;
            // Leemos todo el archivo y lo guardamos en un String para manipularlo más fácil
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
        } catch (Exception e) {
            System.err.println("Error al leer el archivo fuente.");
        }
        this.codigo = sb.toString();
    }

    // Métodos de ayuda para crear tokens (compatibilidad con CUP)
    private Symbol symbol(int type) {
        return new Symbol(type, linea, columna);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, linea, columna, value);
    }

    @Override
    public Symbol next_token() throws Exception {
        while (pos < codigo.length()) {
            char actual = codigo.charAt(pos);

            // 1. IGNORAR ESPACIOS Y SALTOS DE LÍNEA
            if (Character.isWhitespace(actual)) {
                if (actual == '\n') {
                    linea++;
                    columna = 1;
                } else {
                    columna++;
                }
                pos++;
                continue;
            }

            // 2. COMENTARIOS (Ignorar todo hasta el final de la línea)
            if (actual == '#') {
                while (pos < codigo.length() && codigo.charAt(pos) != '\n') {
                    pos++;
                    columna++;
                }
                continue;
            }

            // 3. OPERADORES Y SÍMBOLOS ESPECIALES
            if (actual == '-') {
                if (pos + 1 < codigo.length() && codigo.charAt(pos + 1) == '>') {
                    pos += 2;
                    columna += 2;
                    return symbol(sym.ASIGNACION, "->"); // <-- Agregamos el lexema
                }
                pos++;
                columna++;
                return symbol(sym.MENOS, "-"); // <-- Agregamos el lexema
            }
            if (actual == '+') {
                pos++;
                columna++;
                return symbol(sym.MAS, "+");
            }
            if (actual == '*') {
                pos++;
                columna++;
                return symbol(sym.MULT, "*");
            }
            if (actual == '/') {
                pos++;
                columna++;
                return symbol(sym.DIV, "/");
            }
            if (actual == '$') {
                pos++;
                columna++;
                return symbol(sym.FIN_SENTENCIA, "$");
            }

            // 4. STRINGS (Textos entre comillas)
            if (actual == '"') {
                StringBuilder texto = new StringBuilder();
                pos++;
                columna++; // Saltamos la primera comilla

                while (pos < codigo.length() && codigo.charAt(pos) != '"') {
                    texto.append(codigo.charAt(pos));
                    pos++;
                    columna++;
                }

                pos++;
                columna++; // Saltamos la última comilla
                return symbol(sym.VALOR_TXT, texto.toString());
            }

            // 5. NÚMEROS (Enteros y Decimales con tus propios límites lógicos)
            // 5. NÚMEROS (Enteros y Decimales)
            if (Character.isDigit(actual)) {
                StringBuilder numStr = new StringBuilder();
                boolean esDecimal = false;

                while (pos < codigo.length() && (Character.isDigit(codigo.charAt(pos)) || codigo.charAt(pos) == '.')) {
                    if (codigo.charAt(pos) == '.') {
                        esDecimal = true;
                    }
                    numStr.append(codigo.charAt(pos));
                    pos++;
                    columna++;
                }

                String numero = numStr.toString();

                if (esDecimal) {
                    // Nueva validación: 10 dígitos izquierda y 10 derecha para duvalin
                    String[] partes = numero.split("\\.");
                    String parteEntera = partes[0];
                    String parteDecimal = (partes.length > 1) ? partes[1] : "";

                    if (parteEntera.length() > 10 || parteDecimal.length() > 10) {
                        System.err.println("Error Lexico [Linea " + linea + "]: El duvalin '" + numero + "' excede el limite (max 10 enteros y 10 decimales).");
                    }
                    return symbol(sym.VALOR_DECIMAL, numero);
                } else {
                    // Límite para numerin (enteros)
                    if (numero.length() > 10) {
                        System.err.println("Error Lexico [Linea " + linea + "]: El numerin '" + numero + "' excede los 10 dígitos.");
                    }
                    return symbol(sym.VALOR_ENTERO, numero);
                }
            }

            // 6. PALABRAS RESERVADAS E IDENTIFICADORES
            if (Character.isLetter(actual) || actual == '_') {
                StringBuilder palabraStr = new StringBuilder();

                while (pos < codigo.length() && (Character.isLetterOrDigit(codigo.charAt(pos)) || codigo.charAt(pos) == '_')) {
                    palabraStr.append(codigo.charAt(pos));
                    pos++;
                    columna++;
                }

                String palabra = palabraStr.toString();

                // Usamos un switch de Java normal en lugar de un autómata complejo
                switch (palabra) {
                    case "numerin":
                        return symbol(sym.TIPO_NUMERIN, palabra);
                    case "duvalin":
                        return symbol(sym.TIPO_DUVALIN, palabra);
                    case "txt":
                        return symbol(sym.TIPO_TXT, palabra);
                    case "ver":
                        return symbol(sym.VER, palabra);
                    default:
                        return symbol(sym.ID, palabra);
                }
            }

            // 7. MANEJO DE CARACTERES DESCONOCIDOS (Error léxico)
            System.err.println("Error Lexico: Caracter no reconocido '" + actual + "' en linea " + linea);
            pos++;
            columna++;
        }

        // Cuando el ciclo while termina, llegamos al fin del archivo
        return symbol(sym.EOF);
    }
}
