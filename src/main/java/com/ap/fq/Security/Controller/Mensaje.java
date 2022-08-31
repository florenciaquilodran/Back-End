package com.ap.fq.Security.Controller;

public class Mensaje
{
    private String mensaje;
    
    public Mensaje() {
    }
    
    public Mensaje(final String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String getMensaje() {
        return this.mensaje;
    }
    
    public void setMensaje(final String mensaje) {
        this.mensaje = mensaje;
    }
}