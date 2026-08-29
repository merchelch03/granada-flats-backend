package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "estudiantes")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Email(message = "Debe ser un correo electrónico válido")
    @Column(unique = true)
    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name = "piso_id")
    @JsonIgnoreProperties("inquilinos")
    private Piso piso;

    // Constructores
    public Estudiante() {}

    public Estudiante(Long id, String nombre, String email, String password, Piso piso) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.piso = piso;
    }

    // Getters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Piso getPiso() { return piso; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setPiso(Piso piso) { this.piso = piso; }
}
