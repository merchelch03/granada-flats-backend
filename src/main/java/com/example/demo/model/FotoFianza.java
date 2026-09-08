package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "fianza_evidencias")
public class FotoFianza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "piso_id", nullable = false)
    private Piso piso;

    @ManyToOne
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    @Lob
    @Column(columnDefinition = "LONGBLOB") // Para MySQL/H2/Postgres ajuste según motor
    private byte[] datosImagen;

    private String urlImagen;
    private String estancia;
    private String descripcion;

    @Column(nullable = false, length = 64)
    private String codigoHashSha256;

    @Column(nullable = false)
    private LocalDateTime timestampServidor;

    // Constructores
    public FotoFianza() {
    }

    public FotoFianza(Long id, Piso piso, Estudiante estudiante, byte[] datosImagen, String urlImagen, String estancia, String descripcion, String codigoHashSha256, LocalDateTime timestampServidor) {
        this.id = id;
        this.piso = piso;
        this.estudiante = estudiante;
        this.datosImagen = datosImagen;
        this.urlImagen = urlImagen;
        this.estancia = estancia;
        this.descripcion = descripcion;
        this.codigoHashSha256 = codigoHashSha256;
        this.timestampServidor = timestampServidor;
    }

    // Getters
    public Long getId() { return id; }
    public Piso getPiso() { return piso; }
    public Estudiante getEstudiante() { return estudiante; }
    public byte[] getDatosImagen() { return datosImagen; }
    public String getUrlImagen() { return urlImagen; }
    public String getEstancia() { return estancia; }
    public String getDescripcion() { return descripcion; }
    public String getCodigoHashSha256() { return codigoHashSha256; }
    public LocalDateTime getTimestampServidor() { return timestampServidor; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setPiso(Piso piso) { this.piso = piso; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }
    public void setDatosImagen(byte[] datosImagen) { this.datosImagen = datosImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }
    public void setEstancia(String estancia) { this.estancia = estancia; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setCodigoHashSha256(String codigoHashSha256) { this.codigoHashSha256 = codigoHashSha256; }
    public void setTimestampServidor(LocalDateTime timestampServidor) { this.timestampServidor = timestampServidor; }
}
