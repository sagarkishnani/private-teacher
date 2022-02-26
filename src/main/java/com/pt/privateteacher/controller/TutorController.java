package com.pt.privateteacher.controller;

import com.pt.privateteacher.model.Inscripcion;
import com.pt.privateteacher.model.Tutor;
import com.pt.privateteacher.model.Usuario;
import com.pt.privateteacher.model.checkbox.Titulo;
import com.pt.privateteacher.model.dropdownlist.Orden;
import com.pt.privateteacher.repository.InscripcionRepository;
import com.pt.privateteacher.repository.StudentRepository;
import com.pt.privateteacher.repository.TutorRepository;
import com.pt.privateteacher.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/tutores")
public class TutorController {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    List<Titulo> tituloList = Arrays.asList(
            new Titulo(1, "English for Business"),
            new Titulo(2, "English for Law"),
            new Titulo(3, "English for Engineering"),
            new Titulo(4, "English for Architecture"),
            new Titulo(5, "English for Medicine"),
            new Titulo(6, "Exam Preparation")
    );

    List<Orden> ordenList = Arrays.asList(
            new Orden("Recomendados"),
            new Orden("Mayor Costo"),
            new Orden("Menor Costo")
    );

    @GetMapping("")
    String index(Orden orden, Titulo titulo, Model model)
    {
        List<Tutor> tutores = tutorRepository.findAll(); //Obtenemos el listado de los cursos desde la bd y se guarda en la variable tutores

        Long count = tutores.stream().count();

        model.addAttribute("tutores", tutores); //agregar un atributo al model que se enviara a la VISTA
        model.addAttribute("tituloList", tituloList);
        model.addAttribute("ordenList", ordenList);
        model.addAttribute("orden", orden);
        model.addAttribute("titulo", titulo);
        model.addAttribute("count", count);
        return "tutores"; //retorna a la VISTA index.html
    }

    @GetMapping("/{idtutor}")
    String getTutor(@PathVariable Integer idtutor, Model model) //obtengo la informacion de 1 solo tutor
    {
        Tutor tutor = tutorRepository.getById(idtutor);
        model.addAttribute("tutor", tutor);

        return "detalles-tutor";
    }

    @GetMapping("/mis-cursos")
    String misCursos(Principal principal, Model model)
    {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).get();
        List<Inscripcion> inscripciones = inscripcionRepository.findByUsuario(usuario);
        model.addAttribute("inscripciones", inscripciones);
        return "tutor/mis-cursos";
    }

    @GetMapping("/detalle-curso")
    String cursosDetalle(Principal principal, Model model)
    {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).get();
        List<Inscripcion> inscripciones = inscripcionRepository.findByUsuario(usuario);
        model.addAttribute("inscripciones", inscripciones);
        return "tutor/detalle-curso";
    }

    @PostMapping("")
    String postTitulo(Orden orden, Titulo titulo, Model model)
    {
        List<Tutor> tutores;
        String s = titulo.getNameTitulo();
        if(titulo.getNameTitulo() == null){
            s = "English for Business,English for Law,English for Engineering,English for Architecture,English for Medicine,Exam Preparation";
        }
        List<String> myList = new ArrayList<>(Arrays.asList(s.split(",")));

        if (orden.getNameOrden().equals("Recomendados")) {
            tutores = tutorRepository.findByTituloInRecom(myList);
        }
        else if (orden.getNameOrden().equals("Mayor Costo")) {
            tutores = tutorRepository.findByTituloInDesc(myList);
        }
        else{
            tutores = tutorRepository.findByTituloInAsc(myList);
        }
        Long count = tutores.stream().count();

        model.addAttribute("tutores", tutores);
        model.addAttribute("tituloList", tituloList);
        model.addAttribute("ordenList", ordenList);
        model.addAttribute("orden", orden);
        model.addAttribute("titulo", titulo);
        model.addAttribute("count", count);

        System.out.println(orden.getNameOrden());
        System.out.println(titulo.getNameTitulo());
        return "tutores-filtro"; //retorna a la VISTA index.html
    }
}
