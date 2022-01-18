package com.pt.privateteacher.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Table(name = "estudiante")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idestudiante")
    private Integer idestudiante;

    @Column(name="dni")
    private Integer dni;

    @Enumerated(EnumType.STRING)
    private Rol rol;
    public enum Rol{
        ESTUDIANTE
    }

    @Column(name="nombres")
    private String nombres;

    @Column(name="apellidos")
    private String apellidos;

    @Column(name = "nom_completo")
    private String nombreCompleto;

    @Column(name="celular")
    private Integer celular;

    @Column(name = "edad")
    private Integer edad;

    @Column(name="nivel")
    private String nivel;

    @Column(name="interes")
    private String interes;

    @Email
    @Column(name="email")
    private String email;

    @Column(name="password")
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
        String nombreCompleto = nombres + " " + apellidos;
    }
}
