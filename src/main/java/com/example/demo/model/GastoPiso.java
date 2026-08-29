package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "gastos_piso")
public class GastoPiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "piso_id", nullable = false)
    private Piso piso;

    @ManyToOne
    @JoinColumn(name = "pagador_id", nullable = false)
    private Estudiante pagador;

    @Enumerated(EnumType.STRING)
    private TipoGasto tipoGasto;

    private String concepto;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal importeTotal;

    private LocalDate fecha;
    private String comprobanteUrl;

    public enum TipoGasto { SUMINISTRO_OFICIAL, COMPRA_COMUN }

    // Constructores
    public GastoPiso() {}

    public GastoPiso(Long id, Piso piso, Estudiante pagador, TipoGasto tipoGasto, String concepto, BigDecimal importeTotal, LocalDate fecha, String comprobanteUrl) {
        this.id = id;
        this.piso = piso;
        this.pagador = pagador;
        this.tipoGasto = tipoGasto;
        this.concepto = concepto;
        this.importeTotal = importeTotal;
        this.fecha = fecha;
        this.comprobanteUrl = comprobanteUrl;
    }

    // Getters
    public Long getId() { return id; }
    public Piso getPiso() { return piso; }
    public Estudiante getPagador() { return pagador; }
    public TipoGasto getTipoGasto() { return tipoGasto; }
    public String getConcepto() { return concepto; }
    public BigDecimal getImporteTotal() { return importeTotal; }
    public LocalDate getFecha() { return fecha; }
    public String getComprobanteUrl() { return comprobanteUrl; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setPiso(Piso piso) { this.piso = piso; }
    public void setPagador(Estudiante pagador) { this.pagador = pagador; }
    public void setTipoGasto(TipoGasto tipoGasto) { this.tipoGasto = tipoGasto; }
    public void setConcepto(String concepto) { this.concepto = concepto; }
    public void setImporteTotal(BigDecimal importeTotal) { this.importeTotal = importeTotal; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setComprobanteUrl(String comprobanteUrl) { this.comprobanteUrl = comprobanteUrl; }
}