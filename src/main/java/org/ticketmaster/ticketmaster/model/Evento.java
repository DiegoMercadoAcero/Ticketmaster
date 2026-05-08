package org.ticketmaster.ticketmaster.model;

public class Evento {
    private int idEvento;
    private String nombre;
    private String tipoEvento;
    private String ubicacion;
    private int capacidad;
    private double precio;
    private String horarios;

    public Evento(int idEvento, String nombre, String tipoEvento, String ubicacion, int capacidad, double precio, String horarios) {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.tipoEvento = tipoEvento;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
        this.precio = precio;
        this.horarios = horarios;
    }

    // Getters corregidos
    public int getIdEvento() { return idEvento; }
    public String getNombre() { return nombre; }
    public String getTipoEvento() { return tipoEvento; }
    public String getUbicacion() { return ubicacion; }
    public int getCapacidad() { return capacidad; }
    public double getPrecio() { return precio; } // Corregido: devuelve double
    public String getHorarios() { return horarios; }
}