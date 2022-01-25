package com.pt.privateteacher.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@Table(name = "administrador")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idadmin")
    private Integer idadmin;

    @Enumerated(EnumType.STRING)
    private Admin.Rol rol;

    public enum Rol{
        ADMIN
    }

    @NotBlank
    private String nombres;

    @NotBlank
    private String apellidos;

    @Column(name = "nom_completo")
    private String nombreCompleto;

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
