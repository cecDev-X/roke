package com.roke.codigo;

import java.util.ArrayList;
import java.util.List;

public class ManejadorErrores {

    private static ManejadorErrores instancia;
    private final List<ErrorObj> errores;

    private ManejadorErrores() {
        errores = new ArrayList<>();
    }

    public static ManejadorErrores getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorErrores();
        }
        return instancia;
    }

    public static void reportar(TipoError tipo, int linea, int columna, String mensaje, String codigoDetectado) {
        ManejadorErrores m = getInstancia();
        ErrorObj error = new ErrorObj(tipo, linea, columna, mensaje, codigoDetectado);
        m.errores.add(error);
        System.err.println(formatear(error));
    }

    public static void reportar(TipoError tipo, int linea, int columna, String mensaje) {
        reportar(tipo, linea, columna, mensaje, "");
    }

    public void limpiar() {
        errores.clear();
    }

    public boolean hayErrores() {
        return !errores.isEmpty();
    }

    public int totalErrores() {
        return errores.size();
    }

    public List<ErrorObj> getErrores() {
        return new ArrayList<>(errores);
    }

    public static String formatear(ErrorObj error) {
        String tipo;
        switch (error.getTipo()) {
            case LEXICO:
                tipo = "LÉXICO";
                break;
            case SINTACTICO:
                tipo = "SINTÁCTICO";
                break;
            case SEMANTICO:
                tipo = "SEMÁNTICO";
                break;
            default:
                tipo = "DESCONOCIDO";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[ERROR ").append(tipo).append("]");
        sb.append(" Línea ").append(error.getLinea());
        if (error.getColumna() > 0) {
            sb.append(", Col ").append(error.getColumna());
        }
        sb.append(": ").append(error.getMensaje());
        if (!error.getCodigoDetectado().isEmpty()) {
            sb.append(" [").append(error.getCodigoDetectado()).append("]");
        }
        return sb.toString();
    }

    public String obtenerResumen() {
        if (!hayErrores()) {
            return "Compilación exitosa. 0 errores.";
        }
        int lexicos = 0, sintacticos = 0, semanticos = 0;
        for (ErrorObj e : errores) {
            switch (e.getTipo()) {
                case LEXICO:
                    lexicos++;
                    break;
                case SINTACTICO:
                    sintacticos++;
                    break;
                case SEMANTICO:
                    semanticos++;
                    break;
            }
        }
        return String.format(
            "Compilación finalizada con %d error(es):%n  - Léxicos: %d%n  - Sintácticos: %d%n  - Semánticos: %d",
            errores.size(), lexicos, sintacticos, semanticos
        );
    }

    public String obtenerTodos() {
        StringBuilder sb = new StringBuilder();
        for (ErrorObj e : errores) {
            sb.append(formatear(e)).append("\n");
        }
        sb.append("\n").append(obtenerResumen());
        return sb.toString();
    }

    public String obtenerTodosHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body style='font-family:sans-serif; font-size:13px; padding:4px;'>");
        for (ErrorObj e : errores) {
            String color;
            switch (e.getTipo()) {
                case LEXICO:
                    color = "#FF6B6B";
                    break;
                case SINTACTICO:
                    color = "#FFA94D";
                    break;
                case SEMANTICO:
                    color = "#FFD43B";
                    break;
                default:
                    color = "#FFFFFF";
            }
            sb.append("<p style='color:").append(color).append("; margin:2px 0;'>");
            sb.append(escapar(formatear(e)));
            sb.append("</p>");
        }
        sb.append("<hr style='border-color:#555;'>");
        sb.append("<p style='color:#AAAAAA; font-style:italic;'>");
        sb.append(escapar(obtenerResumen()));
        sb.append("</p></body></html>");
        return sb.toString();
    }

    private static String escapar(String s) {
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\n", "<br>");
    }
}
