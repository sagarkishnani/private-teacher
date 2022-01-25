package com.pt.privateteacher.model;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
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

    @NotNull
    @Min(10000000)
    @Max(99999999)
    private Integer dni;

    @Enumerated(EnumType.STRING)
    private Rol rol;
    public enum Rol{
        TUTOR
    }

    @NotBlank
    private String nombres;

    @NotBlank
    private String apellidos;

    @Column(name = "nom_completo")
    private String nombreCompleto;

    @NotNull
    private Integer edad;

    @NotNull
    @Min(1000000)
    private Integer celular;

    @NotNull
    private Double costo;

    private String titulo;

    @NotBlank
    private String descripcion;

    @Column(name="calificacion")
    private Integer calificacion;

    @NotEmpty
    @Email
    private String email;

    private String password;

    @NotBlank
    @Transient
    private String password1;

    @NotBlank
    @Transient
    private String password2;

    @PrePersist
    @PreUpdate
    void asignarNombreCompleto()
    {
        nombreCompleto = nombres + " " + apellidos;
    }

    private String foto;

    @Transient
    private MultipartFile imagen;
}
