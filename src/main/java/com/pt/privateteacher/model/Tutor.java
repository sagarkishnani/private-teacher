package com.pt.privateteacher.model;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;
import java.time.LocalDateTime;


@Entity
@Data
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtutor")
    private Integer idtutor;

    @NotNull
    @Size(min = 8, max = 8, message = "Este campo debe tener 8 digitos")
    @Pattern(regexp = "^[0-9]*")
    private String dni;

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
    private String fechanacimiento;

    @NotNull
    @Size(min = 7, max = 11, message = "Este campo debe tener entre 7 y 11 digitos")
    @Pattern(regexp = "^[0-9]*")
    private String celular;

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
    @Size(min = 8, message = "La contraseña debe contener al menos 8 caracteres")
    @Pattern.List({
            @Pattern(regexp = "(?=.*[0-9]).+", message = "La contraseña debe tener un digito."),
            @Pattern(regexp = "(?=.*[a-z]).+", message = "La contraseña debe tener una minuscula."),
            @Pattern(regexp = "(?=.*[A-Z]).+", message = "La contraseña debe tener una mayuscula."),
            @Pattern(regexp = "(?=.*[!@#$%^&*+=?_()/\"\\.,<>~`;:]).+", message ="La contraseña debe tener un caracter especial."),
            @Pattern(regexp = "(?=\\S+$).+", message = "La contraseña no debe tener espacios.")
    })
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
