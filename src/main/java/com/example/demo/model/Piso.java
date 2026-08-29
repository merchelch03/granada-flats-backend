package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pisos")
public class Piso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String direccion;
    private String codigoInvitacion;

    private int numHabitaciones;
    private int numBanos;
    private int numComedores;
    private int numCocinas;

    @OneToMany(mappedBy = "piso", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("piso")
    private List<Estudiante> inquilinos = new ArrayList<>();

    // Constructores
    public Piso() {}

    public Piso(Long id, String direccion, String codigoInvitacion, List<Estudiante> inquilinos) {
        this.id = id;
        this.direccion = direccion;
        this.codigoInvitacion = codigoInvitacion;
        this.inquilinos = inquilinos;
    }

    // Getters
    public Long getId() { return id; }
    public String getDireccion() { return direccion; }
    public String getCodigoInvitacion() { return codigoInvitacion; }
    public List<Estudiante> getInquilinos() { return inquilinos; }
    public int getNumHabitaciones() { return numHabitaciones; }
    public int getNumBanos() { return numBanos; }
    public int getNumComedores() { return numComedores; }
    public int getNumCocinas() { return numCocinas; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setCodigoInvitacion(String codigoInvitacion) { this.codigoInvitacion = codigoInvitacion; }
    public void setInquilinos(List<Estudiante> inquilinos) { this.inquilinos = inquilinos; }
    public void setNumHabitaciones(int numHabitaciones) { this.numHabitaciones = numHabitaciones; }
    public void setNumBanos(int numBanos) { this.numBanos = numBanos; }
    public void setNumComedores(int numComedores) { this.numComedores = numComedores; }
    public void setNumCocinas(int numCocinas) { this.numCocinas = numCocinas; }
}
