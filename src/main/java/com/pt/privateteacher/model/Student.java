package com.pt.privateteacher.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@Table(name = "estudiante")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idestudiante")
    private Integer idestudiante;

    @NotNull
    @Min(10000000)
    @Max(99999999)
    private Integer dni;

    @Enumerated(EnumType.STRING)
    private Rol rol;
    public enum Rol{
        ESTUDIANTE
    }

    @NotBlank
    private String nombres;

    @NotBlank
    private String apellidos;

    @Column(name = "nom_completo")
    private String nombreCompleto;

    @NotNull
    @Min(1000000)
    private Integer celular;

    @NotNull
    private Integer edad;

    private String nivel;

    private String interes;

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
    @Column(name = "nom_completo")
    void asignarNombreCompleto()
    {
        nombreCompleto = nombres + " " + apellidos;
    }
}
