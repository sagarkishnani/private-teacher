package com.pt.privateteacher.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Integer id;

    private String nombres;

    private String apellidos;

    @Column(name = "nom_completo")
    private String nombreCompleto;

    private String email;

    @Enumerated(EnumType.STRING)
    private Usuario.Rol rol;
    public enum Rol{
        ADMIN,
        TUTOR,
        ESTUDIANTE
    }

    @NotBlank
    @Transient
    private String password1;

    @NotBlank
    @Transient
    private String password2;

    private String password;
}
