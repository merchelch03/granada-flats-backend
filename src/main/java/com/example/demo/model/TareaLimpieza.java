package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tareas_limpieza")
public class TareaLimpieza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private String zona;
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private EstadoTarea estado;

    @ManyToOne
    @JoinColumn(name = "piso_id", nullable = false)
    private Piso piso;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @Transient
    private Long estudianteId;

    // Constructores
    public TareaLimpieza() {
    }

    public TareaLimpieza(Long id, String nombre, String descripcion, String zona, LocalDate fecha, EstadoTarea estado, Piso piso, Estudiante estudiante) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.zona = zona;
        this.fecha = fecha;
        this.estado = estado;
        this.piso = piso;
        this.estudiante = estudiante;
    }

    // Getters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getZona() { return zona; }
    public LocalDate getFecha() { return fecha; }
    public EstadoTarea getEstado() { return estado; }
    public Piso getPiso() { return piso; }
    public Estudiante getEstudiante() { return estudiante; }
    public Long getEstudianteId() { return estudianteId; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setZona(String zona) { this.zona = zona; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setEstado(EstadoTarea estado) { this.estado = estado; }
    public void setPiso(Piso piso) { this.piso = piso; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }
    public void setEstudianteId(Long estudianteId) { this.estudianteId = estudianteId; }
}
