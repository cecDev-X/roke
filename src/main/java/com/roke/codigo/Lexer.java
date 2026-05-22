package com.roke.codigo;

import java_cup.runtime.Symbol;
import java.io.Reader;

public class Lexer implements java_cup.runtime.Scanner {

    private String codigo;
    private int pos = 0;
    private int linea = 1;
    private int columna = 1;

    public Lexer(Reader reader) {
        StringBuilder sb = new StringBuilder();
        try {
            int ch;
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
        } catch (Exception e) {
            ManejadorErrores.reportar(TipoError.LEXICO, 0, 0, "Error al leer el archivo fuente.");
        }
        this.codigo = sb.toString();
    }

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

            if (actual == '#') {
                while (pos < codigo.length() && codigo.charAt(pos) != '\n') {
                    pos++;
                    columna++;
                }
                continue;
            }

            if (actual == '-') {
                if (pos + 1 < codigo.length() && codigo.charAt(pos + 1) == '>') {
                    pos += 2;
                    columna += 2;
                    return symbol(sym.ASIGNACION, "->");
                }
                pos++;
                columna++;
                return symbol(sym.MENOS, "-");
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

            if (actual == '(') {
                pos++; columna++;
                return symbol(sym.PARENTESIS_A, "(");
            }
            if (actual == ')') {
                pos++; columna++;
                return symbol(sym.PARENTESIS_C, ")");
            }
            if (actual == '{') {
                pos++; columna++;
                return symbol(sym.LLAVE_A, "{");
            }
            if (actual == '}') {
                pos++; columna++;
                return symbol(sym.LLAVE_C, "}");
            }
            if (actual == '=') {
                if (pos + 1 < codigo.length() && codigo.charAt(pos + 1) == '=') {
                    pos += 2; columna += 2;
                    return symbol(sym.IGUAL_A, "==");
                }
                ManejadorErrores.reportar(TipoError.LEXICO, linea, columna,
                    "Caracter '=' no reconocido. ¿Quisiste decir '=='?", "=");
                pos++;
                columna++;
                continue;
            }
            if (actual == '!') {
                if (pos + 1 < codigo.length() && codigo.charAt(pos + 1) == '=') {
                    pos += 2; columna += 2;
                    return symbol(sym.DIFERENTE, "!=");
                }
                ManejadorErrores.reportar(TipoError.LEXICO, linea, columna,
                    "Caracter '!' no reconocido.", "!");
                pos++;
                columna++;
                continue;
            }
            if (actual == '>') {
                if (pos + 1 < codigo.length() && codigo.charAt(pos + 1) == '=') {
                    pos += 2; columna += 2;
                    return symbol(sym.MAYOR_IGUAL, ">=");
                }
                pos++; columna++;
                return symbol(sym.MAYOR_QUE, ">");
            }
            if (actual == '<') {
                if (pos + 1 < codigo.length() && codigo.charAt(pos + 1) == '=') {
                    pos += 2; columna += 2;
                    return symbol(sym.MENOR_IGUAL, "<=");
                }
                pos++; columna++;
                return symbol(sym.MENOR_QUE, "<");
            }

            if (actual == '"') {
                StringBuilder texto = new StringBuilder();
                pos++;
                columna++;

                while (pos < codigo.length() && codigo.charAt(pos) != '"') {
                    texto.append(codigo.charAt(pos));
                    pos++;
                    columna++;
                }

                pos++;
                columna++;
                return symbol(sym.VALOR_TXT, texto.toString());
            }

            if (Character.isDigit(actual)) {
                StringBuilder numStr = new StringBuilder();
                boolean esDecimal = false;
                int inicioColumna = columna;

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
                    String[] partes = numero.split("\\.");
                    if (partes.length != 2 || partes[1].isEmpty()) {
                        ManejadorErrores.reportar(TipoError.SEMANTICO, linea, inicioColumna,
                            "Número decimal '" + numero + "' mal formado. Se espera al menos un dígito después del punto.", numero);
                        return symbol(sym.VALOR_DECIMAL, numero);
                    }
                    String parteEntera = partes[0];
                    String parteDecimal = partes[1];

                    if (parteEntera.length() > 10 || parteDecimal.length() > 10) {
                        ManejadorErrores.reportar(TipoError.LEXICO, linea, inicioColumna,
                            "El duvalin '" + numero + "' excede el límite (max 10 enteros y 10 decimales).", numero);
                    }
                    return symbol(sym.VALOR_DECIMAL, numero);
                } else {
                    if (numero.length() > 10) {
                        ManejadorErrores.reportar(TipoError.LEXICO, linea, inicioColumna,
                            "El numerin '" + numero + "' excede los 10 dígitos.", numero);
                    }
                    return symbol(sym.VALOR_ENTERO, numero);
                }
            }

            if (Character.isLetter(actual) || actual == '_') {
                StringBuilder palabraStr = new StringBuilder();

                while (pos < codigo.length() && (Character.isLetterOrDigit(codigo.charAt(pos)) || codigo.charAt(pos) == '_')) {
                    palabraStr.append(codigo.charAt(pos));
                    pos++;
                    columna++;
                }

                String palabra = palabraStr.toString();

                switch (palabra) {
                    case "numerin":
                        return symbol(sym.TIPO_NUMERIN, palabra);
                    case "duvalin":
                        return symbol(sym.TIPO_DUVALIN, palabra);
                    case "txt":
                        return symbol(sym.TIPO_TXT, palabra);
                    case "ver":
                        return symbol(sym.VER, palabra);
                    case "si":
                        return symbol(sym.SI, palabra);
                    default:
                        return symbol(sym.ID, palabra);
                }
            }

            ManejadorErrores.reportar(TipoError.LEXICO, linea, columna,
                "Caracter no reconocido '" + actual + "'.", String.valueOf(actual));
            pos++;
            columna++;
        }

        return symbol(sym.EOF);
    }
}
