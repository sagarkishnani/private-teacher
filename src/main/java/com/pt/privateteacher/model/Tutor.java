package com.pt.privateteacher.model;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;


@Entity
@Data
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtutor")
    private Integer idtutor;

    @Size(max=8)
    @Column(name="dni")
    private Integer dni;

    @Enumerated(EnumType.STRING)
    private Rol rol;
    public enum Rol{
        TUTOR
    }

    @Column(name="nombres")
    private String nombres;

    @Column(name="apellidos")
    private String apellidos;

    @Column(name = "nom_completo")
    private String nombreCompleto;

    @Size(max=11)
    @Column(name="celular")
    private Integer celular;

    @Column(name="costo")
    private Double costo;

    @Column(name="titulo")
    private String titulo;

    @Column(name="descripcion")
    private String descripcion;

    @Column(name="calificacion")
    private Integer calificacion;

    @Email
    @Column(name="email")
    private String email;

    private String password;

    @PrePersist
    @PreUpdate
    void asignarNombreCompleto()
    {
        String nombreCompleto = nombres + " " + apellidos;
    }

    @Column(name="foto")
    private String foto;

}
