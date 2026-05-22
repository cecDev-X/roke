package com.roke.codigo;

import java_cup.runtime.Symbol;

public class Parser {

    private Lexer lexer;
    private Symbol tokenActual;
    public TablaSimbolos tabla = new TablaSimbolos();

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    private void avanzar() throws Exception {
        tokenActual = lexer.next_token();
    }

    private void comer(int tipoEsperado) throws Exception {
        if (tokenActual.sym == tipoEsperado) {
            avanzar();
        } else {
            ManejadorErrores.reportar(TipoError.SINTACTICO, tokenActual.left, tokenActual.right,
                "Se esperaba un componente diferente. Token encontrado: " + tokenActual.value,
                tokenActual.value != null ? tokenActual.value.toString() : "");
            avanzar();
        }
    }

    public void parse() throws Exception {
        avanzar();
        while (tokenActual.sym != sym.EOF) {
            declaracion();
        }
        System.out.println("Analisis finalizado.");
    }

    private boolean esComparacion(int tipo) {
        return tipo == sym.IGUAL_A || tipo == sym.DIFERENTE ||
               tipo == sym.MAYOR_QUE || tipo == sym.MENOR_QUE ||
               tipo == sym.MAYOR_IGUAL || tipo == sym.MENOR_IGUAL;
    }

    private void declaracion() throws Exception {
        if (tokenActual.sym == sym.SI) {
            comer(sym.SI);
            comer(sym.PARENTESIS_A);

            boolean condicionResultado = condicion();

            comer(sym.PARENTESIS_C);
            comer(sym.LLAVE_A);

            if (condicionResultado) {
                tabla.entrarBloque();
                while (tokenActual.sym != sym.LLAVE_C && tokenActual.sym != sym.EOF) {
                    declaracion();
                }
                tabla.salirBloque();
            } else {
                saltarHastaLLaveC();
            }

            comer(sym.LLAVE_C);
            return;
        }

        if (tokenActual.sym == sym.VER) {
            comer(sym.VER);
            Object e = expresion();
            comer(sym.FIN_SENTENCIA);

            if (e != null) {
                System.out.println("> " + e.toString());
            } else {
                ManejadorErrores.reportar(TipoError.SEMANTICO, 0, 0, "El valor a mostrar es nulo.");
            }
        }
        else if (tokenActual.sym == sym.ID) {
            String id = (String) tokenActual.value;
            int idLinea = tokenActual.left;
            int idColumna = tokenActual.right;
            comer(sym.ID);

            if (tokenActual.sym == sym.TIPO_NUMERIN || tokenActual.sym == sym.TIPO_DUVALIN || tokenActual.sym == sym.TIPO_TXT) {
                int tipo = tokenActual.sym;
                comer(tipo);
                comer(sym.ASIGNACION);

                Object v = expresion();
                comer(sym.FIN_SENTENCIA);

                if (tabla.buscar(id) != null) {
                    ManejadorErrores.reportar(TipoError.SEMANTICO, idLinea, idColumna,
                        "La variable '" + id + "' ya ha sido declarada.", id);
                } else {
                    if (tipo == sym.TIPO_NUMERIN && v instanceof Numerin) {
                        tabla.insertar(id, v);
                        System.out.println("-> Roke: " + id + " guardado con valor " + v.toString());
                    } else if (tipo == sym.TIPO_DUVALIN && v instanceof Duvalin) {
                        tabla.insertar(id, v);
                        System.out.println("-> Roke: " + id + " guardado con valor " + v.toString());
                    } else if (tipo == sym.TIPO_TXT && v instanceof Txt) {
                        tabla.insertar(id, v);
                        System.out.println("-> Roke: " + id + " guardado con valor " + v.toString());
                    } else {
                        ManejadorErrores.reportar(TipoError.SEMANTICO, idLinea, idColumna,
                            "El valor asignado no coincide con el tipo de la variable '" + id + "'.", id);
                    }
                }
            } else if (tokenActual.sym == sym.ASIGNACION) {
                comer(sym.ASIGNACION);

                Object v = expresion();
                comer(sym.FIN_SENTENCIA);

                Object variableExistente = tabla.buscar(id);
                if (variableExistente == null) {
                    ManejadorErrores.reportar(TipoError.SEMANTICO, idLinea, idColumna,
                        "La variable '" + id + "' no ha sido definida.", id);
                } else {
                    if (variableExistente.getClass().isInstance(v)) {
                        tabla.insertar(id, v);
                        System.out.println("-> Roke: " + id + " actualizado a " + v.toString());
                    } else {
                        ManejadorErrores.reportar(TipoError.SEMANTICO, idLinea, idColumna,
                            "No puedes asignar " + v.getClass().getSimpleName() + " a " + id, id);
                    }
                }
            } else {
                ManejadorErrores.reportar(TipoError.SINTACTICO, idLinea, idColumna,
                    "Se esperaba un tipo o asignacion despues del identificador '" + id + "'.", id);
            }
        }
        else {
            ManejadorErrores.reportar(TipoError.SINTACTICO, tokenActual.left, tokenActual.right,
                "Instruccion no reconocida comenzando con " + tokenActual.value,
                tokenActual.value != null ? tokenActual.value.toString() : "");
            avanzar();
        }
    }

    private Object expresion() throws Exception {
        Object resultado = termino();

        while (tokenActual.sym == sym.MAS || tokenActual.sym == sym.MENOS) {
            int operador = tokenActual.sym;
            avanzar();
            Object derecha = termino();

            if (operador == sym.MAS) {
                if (resultado instanceof Txt || derecha instanceof Txt) {
                    String val1 = (resultado != null) ? resultado.toString() : "nulo";
                    String val2 = (derecha != null) ? derecha.toString() : "nulo";
                    resultado = new Txt(val1 + val2);
                } else if (resultado instanceof Numerin && derecha instanceof Numerin) {
                    resultado = Operacion.sumar((Numerin) resultado, (Numerin) derecha);
                } else if (resultado instanceof Duvalin && derecha instanceof Duvalin) {
                    resultado = Operacion.sumar((Duvalin) resultado, (Duvalin) derecha);
                } else {
                    ManejadorErrores.reportar(TipoError.SEMANTICO, tokenActual.left, tokenActual.right,
                        "Tipos incompatibles para la suma.");
                    return null;
                }
            } else if (operador == sym.MENOS) {
                if (resultado instanceof Numerin && derecha instanceof Numerin) {
                    resultado = Operacion.restar((Numerin) resultado, (Numerin) derecha);
                } else if (resultado instanceof Duvalin && derecha instanceof Duvalin) {
                    resultado = Operacion.restar((Duvalin) resultado, (Duvalin) derecha);
                } else {
                    ManejadorErrores.reportar(TipoError.SEMANTICO, tokenActual.left, tokenActual.right,
                        "No se puede restar texto.");
                    return null;
                }
            }
        }
        return resultado;
    }

    private Object termino() throws Exception {
        Object resultado = factor();

        while (tokenActual.sym == sym.MULT || tokenActual.sym == sym.DIV) {
            int operador = tokenActual.sym;
            avanzar();
            Object derecha = factor();

            if (operador == sym.MULT) {
                if (resultado instanceof Numerin && derecha instanceof Numerin) {
                    resultado = Operacion.multiplicar((Numerin) resultado, (Numerin) derecha);
                } else if (resultado instanceof Duvalin && derecha instanceof Duvalin) {
                    resultado = Operacion.multiplicar((Duvalin) resultado, (Duvalin) derecha);
                }
            } else if (operador == sym.DIV) {
                if (resultado instanceof Numerin && derecha instanceof Numerin) {
                    resultado = Operacion.dividir((Numerin) resultado, (Numerin) derecha);
                } else if (resultado instanceof Duvalin && derecha instanceof Duvalin) {
                    resultado = Operacion.dividir((Duvalin) resultado, (Duvalin) derecha);
                }
            }
        }
        return resultado;
    }

    private Object factor() throws Exception {
        Object resultado = null;

        if (tokenActual.sym == sym.VALOR_ENTERO) {
            resultado = new Numerin((String) tokenActual.value);
            comer(sym.VALOR_ENTERO);
        } else if (tokenActual.sym == sym.VALOR_DECIMAL) {
            resultado = new Duvalin((String) tokenActual.value);
            comer(sym.VALOR_DECIMAL);
        } else if (tokenActual.sym == sym.VALOR_TXT) {
            resultado = new Txt((String) tokenActual.value);
            comer(sym.VALOR_TXT);
        } else if (tokenActual.sym == sym.ID) {
            String id = (String) tokenActual.value;
            int idLinea = tokenActual.left;
            int idColumna = tokenActual.right;
            comer(sym.ID);

            resultado = tabla.buscar(id);
            if (resultado == null) {
                ManejadorErrores.reportar(TipoError.SEMANTICO, idLinea, idColumna,
                    "Variable '" + id + "' no definida.", id);
            }
        } else {
            ManejadorErrores.reportar(TipoError.SINTACTICO, tokenActual.left, tokenActual.right,
                "Se esperaba un valor o variable, se encontro " + tokenActual.value,
                tokenActual.value != null ? tokenActual.value.toString() : "");
            avanzar();
        }

        return resultado;
    }

    private boolean condicion() throws Exception {
        Object izquierdo = expresion();

        if (!esComparacion(tokenActual.sym)) {
            ManejadorErrores.reportar(TipoError.SINTACTICO, tokenActual.left, tokenActual.right,
                "Se esperaba un operador de comparacion (==, !=, >, <, >=, <=)");
            return false;
        }

        int operador = tokenActual.sym;
        int opLinea = tokenActual.left;
        int opColumna = tokenActual.right;
        avanzar();
        Object derecho = expresion();

        return evaluarComparacion(izquierdo, operador, derecho, opLinea, opColumna);
    }

    private boolean evaluarComparacion(Object izquierdo, int operador, Object derecho, int linea, int columna) {
        if (izquierdo instanceof Numerin && derecho instanceof Numerin) {
            int a = ((Numerin) izquierdo).getValor();
            int b = ((Numerin) derecho).getValor();
            return aplicarComparacion((double) a, (double) b, operador);
        } else if (izquierdo instanceof Duvalin && derecho instanceof Duvalin) {
            double a = ((Duvalin) izquierdo).getValor();
            double b = ((Duvalin) derecho).getValor();
            return aplicarComparacion(a, b, operador);
        } else if (izquierdo instanceof Txt && derecho instanceof Txt) {
            String a = ((Txt) izquierdo).getValor();
            String b = ((Txt) derecho).getValor();
            if (operador == sym.IGUAL_A) return a.equals(b);
            if (operador == sym.DIFERENTE) return !a.equals(b);
            ManejadorErrores.reportar(TipoError.SEMANTICO, linea, columna,
                "No se puede comparar texto con >, <, >=, <=");
            return false;
        } else {
            ManejadorErrores.reportar(TipoError.SEMANTICO, linea, columna,
                "Tipos incompatibles para comparacion");
            return false;
        }
    }

    private boolean aplicarComparacion(double a, double b, int operador) {
        switch (operador) {
            case sym.IGUAL_A:    return a == b;
            case sym.DIFERENTE:   return a != b;
            case sym.MAYOR_QUE:   return a > b;
            case sym.MENOR_QUE:   return a < b;
            case sym.MAYOR_IGUAL: return a >= b;
            case sym.MENOR_IGUAL: return a <= b;
            default: return false;
        }
    }

    private void saltarHastaLLaveC() throws Exception {
        int profundidad = 1;
        while (profundidad > 0 && tokenActual.sym != sym.EOF) {
            if (tokenActual.sym == sym.LLAVE_A) {
                profundidad++;
                avanzar();
            } else if (tokenActual.sym == sym.LLAVE_C) {
                profundidad--;
                if (profundidad > 0) {
                    avanzar();
                }
            } else {
                avanzar();
            }
        }
    }
}
