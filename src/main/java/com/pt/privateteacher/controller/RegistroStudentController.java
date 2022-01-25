package com.pt.privateteacher.controller;

import com.pt.privateteacher.model.Student;
import com.pt.privateteacher.model.Usuario;
import com.pt.privateteacher.repository.StudentRepository;
import com.pt.privateteacher.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/registro-alumno")
public class RegistroStudentController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    static List<String> nivelList = null;
    static {
        nivelList = new ArrayList<>();
        nivelList.add("Junior");
        nivelList.add("Basico");
        nivelList.add("Intermedio");
        nivelList.add("Avanzado");
    }

    static List<String> interesList = null;
    static {
        interesList = new ArrayList<>();
        interesList.add("English for Business");
        interesList.add("English for Law");
        interesList.add("English for Engineering");
        interesList.add("English for Architecture");
        interesList.add("English for Medicine");
        interesList.add("Exam Preparation");
    }

        @GetMapping
        public String alumno(Model model) {
            model.addAttribute("student", new Student());
            model.addAttribute("nivelList", nivelList);
            model.addAttribute("interesList", interesList);
            return "registro-alumno";
        }

        @PostMapping
        public String crear(@Validated Student student, BindingResult bindingResult, RedirectAttributes ra, Model model) {
        Usuario usuario = new Usuario();

        if (bindingResult.hasErrors()) {
            model.addAttribute("student", student);
            model.addAttribute("nivelList", nivelList);
            model.addAttribute("interesList", interesList);
            return "registro-alumno";
        }

        String email = student.getEmail();
        boolean studentYaExiste = studentRepository.existsByEmail(email);

        if(studentYaExiste)
        {
            bindingResult.rejectValue("email", "EmailAlreadyExists");
        }

        if (!student.getPassword1().equals(student.getPassword2())) {
            bindingResult.rejectValue("password1", "PasswordNotEquals");
        }

        student.setPassword(passwordEncoder.encode(student.getPassword1()));

        student.setRol(Student.Rol.ESTUDIANTE);
        studentRepository.save(student);

        usuario.setNombres(student.getNombres());
        usuario.setApellidos(student.getApellidos());
        usuario.setNombreCompleto(student.getNombreCompleto());
        usuario.setEmail(student.getEmail());
        usuario.setRol(Usuario.Rol.ESTUDIANTE);
        usuario.setPassword(student.getPassword());

        usuarioRepository.save(usuario);


        ra.addAttribute("registroExitoso", "");
        return "redirect:/login";
    }
}

