package com.pt.privateteacher.controller;

import com.pt.privateteacher.model.Inscripcion;
import com.pt.privateteacher.model.Student;
import com.pt.privateteacher.model.Tutor;
import com.pt.privateteacher.model.Usuario;
import com.pt.privateteacher.repository.InscripcionRepository;
import com.pt.privateteacher.repository.StudentRepository;
import com.pt.privateteacher.repository.TutorRepository;
import com.pt.privateteacher.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    //http://localhost:8080/usuario/12/inscribir
    @GetMapping("/usuario/inscribir/{idtutor}")
    String inscribir(@PathVariable Integer idtutor,
                     Principal principal, RedirectAttributes ra)
    {
            Usuario usuario = usuarioRepository.findByEmail(principal.getName()).get();
            Student student = studentRepository.findByEmail(principal.getName()).get();
            Tutor tutor = tutorRepository.getById(idtutor);

            Inscripcion inscripcion = new Inscripcion();
            inscripcion.setUsuario(usuario);
            inscripcion.setTutor(tutor);
            inscripcion.setStudent(student);
            inscripcion.setFechaInscripcion(LocalDateTime.now());
            ra.addFlashAttribute("mensaje", "Se registró correctamente al curso de " + tutor.getTitulo() + " con el tutor " + tutor.getNombreCompleto() + ". Su clase estará disponible en Mis cursos");

            inscripcionRepository.save(inscripcion);

            return "redirect:/tutores";
    }

    @GetMapping("/mis-cursos")
    String misCursos(Principal principal, Model model)
    {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).get();
        List<Inscripcion> inscripciones = inscripcionRepository.findByUsuario(usuario);
        model.addAttribute("inscripciones", inscripciones);
        return "mis-cursos";
    }
}
