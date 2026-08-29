package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "deudas_inquilinos")
public class DeudaInquilino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gasto_id", nullable = false)
    private GastoPiso gastoPiso;

    @ManyToOne
    @JoinColumn(name = "deudor_id", nullable = false)
    private Estudiante deudor;

    @ManyToOne
    @JoinColumn(name = "acreedor_id", nullable = false)
    private Estudiante acreedor;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cuotaIndividual;

    @Enumerated(EnumType.STRING)
    private EstadoDeuda estado;

    // Constructores
    public DeudaInquilino() {
    }

    public DeudaInquilino(Long id, GastoPiso gastoPiso, Estudiante deudor, Estudiante acreedor, BigDecimal cuotaIndividual, EstadoDeuda estado) {
        this.id = id;
        this.gastoPiso = gastoPiso;
        this.deudor = deudor;
        this.acreedor = acreedor;
        this.cuotaIndividual = cuotaIndividual;
        this.estado = estado;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public GastoPiso getGastoPiso() {
        return gastoPiso;
    }

    public Estudiante getDeudor() {
        return deudor;
    }

    public Estudiante getAcreedor() {
        return acreedor;
    }

    public BigDecimal getCuotaIndividual() {
        return cuotaIndividual;
    }

    public EstadoDeuda getEstado() {
        return estado;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setGastoPiso(GastoPiso gastoPiso) {
        this.gastoPiso = gastoPiso;
    }

    public void setDeudor(Estudiante deudor) {
        this.deudor = deudor;
    }

    public void setAcreedor(Estudiante acreedor) {
        this.acreedor = acreedor;
    }

    public void setCuotaIndividual(BigDecimal cuotaIndividual) {
        this.cuotaIndividual = cuotaIndividual;
    }

    public void setEstado(EstadoDeuda estado) {
        this.estado = estado;
    }
}