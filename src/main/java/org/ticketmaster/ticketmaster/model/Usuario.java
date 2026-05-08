package org.ticketmaster.ticketmaster.model;

public class Usuario {
    private String username;
    private String correo;
    private String password;
    private String nombre;
    private String apellido;

    public Usuario() {
    }

    public Usuario(String username, String correo, String password, String nombre, String apellido) {
        this.username = username;
        this.correo = correo;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
}