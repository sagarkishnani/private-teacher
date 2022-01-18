package com.pt.privateteacher.controller;

import com.pt.privateteacher.model.Student;
import com.pt.privateteacher.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/registrar")
public class RegistroController {
    @Autowired
    private StudentRepository studentRepository;

    //@Autowired PasswordEncoder passwordEncoder

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
    public String index(Model model)
    {
        model.addAttribute("student", new Student());
        model.addAttribute("nivelList", nivelList);
        model.addAttribute("interesList", interesList);
        return "registro-alumno";
    }

    @PostMapping
    public String crear(Student student, BindingResult bindingResult, RedirectAttributes ra, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("student", student);
            return "registro-alumno";
        }
/*
        String email = estudiante.getEmail();
        boolean studentYaExiste = studentRepository.existsByEmail(email);

        if(studentYaExiste)
        {
            bindingResult.rejectValue("email", "EmailAlreadyExists");
        }
*/
        //validar la igualdad de contraseñas
        if(!student.getPassword1().equals(student.getPassword2()))
        {
            bindingResult.rejectValue("password1", "PasswordNotEquals");
        }

        student.setPassword(student.getPassword1());

        student.setRol(Student.Rol.ESTUDIANTE);
        studentRepository.save(student);

        ra.addAttribute("registroExitoso", "Registro exitoso");
        return "login";
    }
}
