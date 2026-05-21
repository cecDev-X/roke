package com.roke.codigo;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java_cup.runtime.Symbol;

public class AnalizadorEstructural {

    public static class LineaGramatica {
        public final String codigoOriginal;
        public final String estructura;

        public LineaGramatica(String codigoOriginal, String estructura) {
            this.codigoOriginal = codigoOriginal;
            this.estructura = estructura;
        }
    }

    public static List<LineaGramatica> analizar(String codigoFuente) {
        List<LineaGramatica> resultado = new ArrayList<>();
        String[] lineas = codigoFuente.split("\n");

        for (String linea : lineas) {
            String trimmed = linea.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                continue;
            }
            String estructura = analizarLinea(trimmed);
            if (estructura != null) {
                resultado.add(new LineaGramatica(trimmed, estructura));
            }
        }
        return resultado;
    }

    private static String analizarLinea(String linea) {
        StringBuilder estructura = new StringBuilder();
        try {
            Lexer lexer = new Lexer(new StringReader(linea));
            while (true) {
                Symbol token = lexer.next_token();
                if (token.sym == sym.EOF) {
                    break;
                }
                if (estructura.length() > 0) {
                    estructura.append(" ");
                }
                estructura.append(nombreGramatical(token.sym));
            }
        } catch (Exception e) {
            return "<error_lexico>";
        }
        return estructura.toString();
    }

    public static String nombreGramatical(int symID) {
        switch (symID) {
            case sym.TIPO_NUMERIN:
                return "<tipo_numerin>";
            case sym.TIPO_DUVALIN:
                return "<tipo_duvalin>";
            case sym.TIPO_TXT:
                return "<tipo_txt>";
            case sym.ASIGNACION:
                return "<asignacion>";
            case sym.FIN_SENTENCIA:
                return "<fin_sentencia>";
            case sym.VER:
                return "<instruccion_salida>";
            case sym.MAS:
                return "<operador_suma>";
            case sym.MENOS:
                return "<operador_resta>";
            case sym.MULT:
                return "<operador_multiplicacion>";
            case sym.DIV:
                return "<operador_division>";
            case sym.VALOR_ENTERO:
                return "<numero_entero>";
            case sym.VALOR_DECIMAL:
                return "<numero_decimal>";
            case sym.VALOR_TXT:
                return "<cadena_texto>";
            case sym.ID:
                return "<identificador>";
            default:
                return "<desconocido>";
        }
    }
}
