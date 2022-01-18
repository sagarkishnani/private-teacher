package com.pt.privateteacher.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAlumno")
    private Integer idAlumno;

    @Size(max=8)
    @Column(name="dni")
    private Integer dni;

    @Enumerated(EnumType.STRING)
    private Student.Rol rol;
    public enum Rol{
        ALUMNO
    }

    @Column(name="nombres")
    private String nombres;

    @Column(name="apellidos")
    private String apellidos;

    @Size(max=11)
    @Column(name="celular")
    private Integer celular;

    @Column(name = "edad")
    private Integer edad;

    @Column(name="nivel")
    private String nivel;

    @Column(name="curso")
    private String curso;

    @Email
    @Column(name="email")
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
        String nombreCompleto = nombres + " " + apellidos;
    }
}
