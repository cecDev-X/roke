package com.roke.codigo;

public class ErrorObj {

    private final TipoError tipo;
    private final int linea;
    private final int columna;
    private final String mensaje;
    private final String codigoDetectado;

    public ErrorObj(TipoError tipo, int linea, int columna, String mensaje, String codigoDetectado) {
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
        this.mensaje = mensaje;
        this.codigoDetectado = codigoDetectado;
    }

    public TipoError getTipo() {
        return tipo;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getCodigoDetectado() {
        return codigoDetectado;
    }
}
