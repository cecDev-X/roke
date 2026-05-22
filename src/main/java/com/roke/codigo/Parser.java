package com.roke.codigo;

import java_cup.runtime.Symbol;

public class Parser {

    private Lexer lexer;
    private Symbol tokenActual;
    public TablaSimbolos tabla = new TablaSimbolos();

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    // --- MÉTODOS DE CONTROL ---
    private void avanzar() throws Exception {
        tokenActual = lexer.next_token();
    }

    private void comer(int tipoEsperado) throws Exception {
        if (tokenActual.sym == tipoEsperado) {
            avanzar();
        } else {
            System.err.println("Error Sintactico [Linea " + tokenActual.left + ", Columna " + tokenActual.right + "]: Se esperaba un componente diferente. Token encontrado: " + tokenActual.value);
            // Opcional: Aquí podrías hacer que avance hasta el próximo '$' para recuperar el error
            avanzar();
        }
    }

    // --- PUNTO DE ENTRADA (inicio ::= lista_declaraciones) ---
    public void parse() throws Exception {
        avanzar(); // Cargamos el primer token
        while (tokenActual.sym != sym.EOF) {
            declaracion();
        }
        System.out.println("Analisis finalizado.");
    }

    // --- MÉTODO AUXILIAR PARA VERIFICAR OPERADORES DE COMPARACIÓN ---
    private boolean esComparacion(int tipo) {
        return tipo == sym.IGUAL_A || tipo == sym.DIFERENTE ||
               tipo == sym.MAYOR_QUE || tipo == sym.MENOR_QUE ||
               tipo == sym.MAYOR_IGUAL || tipo == sym.MENOR_IGUAL;
    }

    // --- REGLAS DE DECLARACIÓN, REASIGNACIÓN, SALIDA Y CONTROL ---
    private void declaracion() throws Exception {
        // 0. SI (condicion) { ... } $
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
            comer(sym.FIN_SENTENCIA);
            return;
        }

        // 1. VER expresion FIN_SENTENCIA
        if (tokenActual.sym == sym.VER) {
            comer(sym.VER);
            Object e = expresion();
            comer(sym.FIN_SENTENCIA);

            if (e != null) {
                System.out.println("> " + e.toString());
            } else {
                System.err.println("Error: El valor a mostrar es nulo.");
            }
        }
        // 2. ID al inicio -> puede ser DECLARACIÓN NUEVA (id tipo -> expresion $)
        //    o REASIGNACIÓN (id -> expresion $)
        else if (tokenActual.sym == sym.ID) {
            String id = (String) tokenActual.value;
            comer(sym.ID);

            if (tokenActual.sym == sym.TIPO_NUMERIN || tokenActual.sym == sym.TIPO_DUVALIN || tokenActual.sym == sym.TIPO_TXT) {
                int tipo = tokenActual.sym;
                comer(tipo);
                comer(sym.ASIGNACION);

                Object v = expresion();
                comer(sym.FIN_SENTENCIA);

                if (tabla.buscar(id) != null) {
                    System.err.println("Error Semantico: La variable '" + id + "' ya ha sido declarada.");
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
                        System.err.println("Error de Tipo: El valor asignado no coincide con el tipo de la variable '" + id + "'.");
                    }
                }
            } else if (tokenActual.sym == sym.ASIGNACION) {
                comer(sym.ASIGNACION);

                Object v = expresion();
                comer(sym.FIN_SENTENCIA);

                Object variableExistente = tabla.buscar(id);
                if (variableExistente == null) {
                    System.err.println("Error Semantico: La variable '" + id + "' no ha sido definida.");
                } else {
                    if (variableExistente.getClass().isInstance(v)) {
                        tabla.insertar(id, v);
                        System.out.println("-> Roke: " + id + " actualizado a " + v.toString());
                    } else {
                        System.err.println("Error de Tipo: No puedes asignar " + v.getClass().getSimpleName() + " a " + id);
                    }
                }
            } else {
                System.err.println("Error Sintactico: Se esperaba un tipo o asignacion despues del identificador '" + id + "'.");
            }
        }
        // Si no es ninguna de las anteriores, hay basura en el código
        else {
            System.err.println("Error Sintactico: Instruccion no reconocida comenzando con " + tokenActual.value);
            avanzar(); // Consumimos el error para no ciclar infinitamente
        }
    }

    // --- LÓGICA DE EXPRESIONES (Precedencia) ---
    // Nivel 1: Sumas y Restas
    // Nivel 1: Sumas y Restas (Incluye Concatenación)
    private Object expresion() throws Exception {
        Object resultado = termino();

        while (tokenActual.sym == sym.MAS || tokenActual.sym == sym.MENOS) {
            int operador = tokenActual.sym;
            avanzar();
            Object derecha = termino();

            if (operador == sym.MAS) {
                // --- LÓGICA DE CONCATENACIÓN ---
                // Si cualquiera de los dos lados es un Txt, concatenamos
                if (resultado instanceof Txt || derecha instanceof Txt) {
                    String val1 = (resultado != null) ? resultado.toString() : "nulo";
                    String val2 = (derecha != null) ? derecha.toString() : "nulo";
                    resultado = new Txt(val1 + val2);
                } // --- LÓGICA DE SUMA NUMÉRICA ---
                else if (resultado instanceof Numerin && derecha instanceof Numerin) {
                    resultado = Operacion.sumar((Numerin) resultado, (Numerin) derecha);
                } else if (resultado instanceof Duvalin && derecha instanceof Duvalin) {
                    resultado = Operacion.sumar((Duvalin) resultado, (Duvalin) derecha);
                } else {
                    System.err.println("Error Semantico: Tipos incompatibles para la suma.");
                    return null;
                }
            } else if (operador == sym.MENOS) {
                // La resta no permite texto, solo números
                if (resultado instanceof Numerin && derecha instanceof Numerin) {
                    resultado = Operacion.restar((Numerin) resultado, (Numerin) derecha);
                } else if (resultado instanceof Duvalin && derecha instanceof Duvalin) {
                    resultado = Operacion.restar((Duvalin) resultado, (Duvalin) derecha);
                } else {
                    System.err.println("Error Semantico: No se puede restar texto.");
                    return null;
                }
            }
        }
        return resultado;
    }

    // Nivel 2: Multiplicaciones y Divisiones
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

    // Nivel 3: Valores base (IDs, Números, Textos)
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
            comer(sym.ID);

            resultado = tabla.buscar(id);
            if (resultado == null) {
                System.err.println("Error: Variable '" + id + "' no definida.");
            }
        } else {
            System.err.println("Error Sintactico: Se esperaba un valor o variable, se encontro " + tokenActual.value);
            avanzar();
        }

        return resultado;
    }

    // --- LÓGICA DE CONDICIONES (COMPARACIONES) ---
    private boolean condicion() throws Exception {
        Object izquierdo = expresion();

        if (!esComparacion(tokenActual.sym)) {
            System.err.println("Error Sintactico [Linea " + tokenActual.left + "]: Se esperaba un operador de comparacion (==, !=, >, <, >=, <=)");
            return false;
        }

        int operador = tokenActual.sym;
        avanzar();
        Object derecho = expresion();

        return evaluarComparacion(izquierdo, operador, derecho);
    }

    private boolean evaluarComparacion(Object izquierdo, int operador, Object derecho) {
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
            System.err.println("Error Semantico: No se puede comparar texto con >, <, >=, <=");
            return false;
        } else {
            System.err.println("Error Semantico: Tipos incompatibles para comparacion");
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

    // --- SALTO DE BLOQUE (Cuando la condición es falsa) ---
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
