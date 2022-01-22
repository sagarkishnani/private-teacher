package com.pt.privateteacher.controller;

import com.pt.privateteacher.model.Tutor;
import com.pt.privateteacher.repository.TutorRepository;
import com.pt.privateteacher.service.FileSystemStorageService;
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
@RequestMapping("/registro-profesor")
public class RegistroTutorController {
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    static List<String> tituloList = null;
    static {
        tituloList = new ArrayList<>();
        tituloList.add("English for Business");
        tituloList.add("English for Law");
        tituloList.add("English for Engineering");
        tituloList.add("English for Architecture");
        tituloList.add("English for Medicine");
        tituloList.add("Exam Preparation");
    }

    @GetMapping
    public String tutor(Model model) {
        model.addAttribute("tutor", new Tutor());
        model.addAttribute("tituloList", tituloList);
        return "registro-profesor";
    }

    @PostMapping
    public String crear(Tutor tutor, BindingResult bindingResult, RedirectAttributes ra, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tutor", tutor);
            return "registro-profesor";
        }
/*
        String email = estudiante.getEmail();
        boolean studentYaExiste = studentRepository.existsByEmail(email);

        if(studentYaExiste)
        {
            bindingResult.rejectValue("email", "EmailAlreadyExists");
        }
*/
            /*//validar la igualdad de contraseñas
            if (!tutor.getPassword1().equals(tutor.getPassword2())) {
                bindingResult.rejectValue("password1", "PasswordNotEquals");
            }

            tutor.setPassword(tutor.getPassword1());
*/
        if(!tutor.getImagen().isEmpty()) {
            String foto = fileSystemStorageService.store(tutor.getImagen());
            tutor.setFoto(foto);
        }

        tutor.setRol(Tutor.Rol.TUTOR);
        tutorRepository.save(tutor);

        ra.addAttribute("registroExitoso", "Registro exitoso");
        return "login";
    }
}
