package com.ap.fq.Security.Dto;

import java.util.HashSet;
import java.util.Set;

public class NuevoUsuario
{
    private String nombre;
    private String nombreUsuario;
    private String email;
    private String password;
    private Set<String> roles;
    
    public NuevoUsuario() {
        this.roles = new HashSet<String>();
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombreUsuario() {
        return this.nombreUsuario;
    }
    
    public void setNombreUsuario(final String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(final String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    public Set<String> getRoles() {
        return this.roles;
    }
    
    public void setRoles(final Set<String> roles) {
        this.roles = roles;
    }
}