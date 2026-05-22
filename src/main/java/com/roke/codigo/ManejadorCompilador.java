package com.roke.codigo;

import java.io.StringReader;

public class ManejadorCompilador {

    public static void ejecutar(String codigo, javax.swing.JTextPane terminal, javax.swing.JTextPane alertas) {
        ManejadorErrores manejador = ManejadorErrores.getInstancia();
        manejador.limpiar();

        try {
            terminal.setText("");
            alertas.setText("");

            Lexer lexer = new Lexer(new StringReader(codigo));
            Parser p = new Parser(lexer);
            p.parse();

            if (manejador.hayErrores()) {
                alertas.setContentType("text/html");
                alertas.setText(manejador.obtenerTodosHTML());
                terminal.setText("rokev1.0 \n");
            } else {
                terminal.setText("rokev1.0 \n");
            }

        } catch (Exception e) {
            alertas.setText("Error en la compilación: \n" + e.getMessage());
        }
    }
}
