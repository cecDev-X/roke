package com.roke.codigo;

import java.io.StringReader;

public class ManejadorCompilador {

    public static void ejecutar(String codigo, javax.swing.JTextPane terminal, javax.swing.JTextPane alertas) {
        try {
            terminal.setText("");
            alertas.setText("");

            Lexer lexer = new Lexer(new StringReader(codigo));

            Parser p = new Parser(lexer);

            p.parse();

            terminal.setText("--- Ejecución Finalizada con Éxito ---");

        } catch (Exception e) {
            alertas.setText("Error en la compilación: \n" + e.getMessage());
        }
    }
}
