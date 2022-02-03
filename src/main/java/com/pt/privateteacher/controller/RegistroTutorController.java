package com.pt.privateteacher.controller;

import com.pt.privateteacher.model.Tutor;
import com.pt.privateteacher.model.Usuario;
import com.pt.privateteacher.repository.TutorRepository;
import com.pt.privateteacher.repository.UsuarioRepository;
import com.pt.privateteacher.service.FileSystemStorageService;
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
@RequestMapping("/registro-profesor")
public class RegistroTutorController {
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private FileSystemStorageService fileSystemStorageService;
    @Autowired
    PasswordEncoder passwordEncoder;

    String[] words = {"extorsion", "robo", "agresion", "asesinato", "violacion", "amenaza"};

    public static boolean containsWords(String inputString, String[] items){
        boolean found = false;
        for (String item : items) {
            if(inputString.contains(item)) {
                found = true;
                break;
            }
        }
        return found;
    }

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
    public String crear(@Validated Tutor tutor, BindingResult bindingResult, RedirectAttributes ra, Model model) {

        Usuario usuario = new Usuario();

        if (bindingResult.hasErrors()) {
            model.addAttribute("tutor", tutor);
            model.addAttribute("tituloList", tituloList);
            return "registro-profesor";
        }

        String email = tutor.getEmail();
        boolean studentYaExiste = tutorRepository.existsByEmail(email);

        if(studentYaExiste)
        {
            bindingResult.rejectValue("email", "EmailAlreadyExists");
        }

        if(tutor.getImagen().isEmpty())
        {
            bindingResult.rejectValue("imagen", "MultipartNotEmpty");
        }

        if (!tutor.getPassword1().equals(tutor.getPassword2())) {
            bindingResult.rejectValue("password1", "PasswordNotEquals");
        }

        if (!tutor.getPassword2().equals(tutor.getPassword1())) {
            bindingResult.rejectValue("password1", "PasswordNotEquals");
        }

        String text = tutor.getDescripcion().toLowerCase();
        if (containsWords(text, words) == true){
            bindingResult.rejectValue("descripcion", "WordsNotAllowed");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("tutor", tutor);
            model.addAttribute("tituloList", tituloList);
            return "registro-profesor";
        }

        tutor.setPassword(passwordEncoder.encode(tutor.getPassword1()));

        if(!tutor.getImagen().isEmpty()) {
            String foto = fileSystemStorageService.store(tutor.getImagen());
            tutor.setFoto(foto);
        }

        tutor.setRol(Tutor.Rol.TUTOR);
        tutorRepository.save(tutor);

        usuario.setNombres(tutor.getNombres());
        usuario.setApellidos(tutor.getApellidos());
        usuario.setNombreCompleto(tutor.getNombreCompleto());
        usuario.setEmail(tutor.getEmail());
        usuario.setRol(Usuario.Rol.TUTOR);
        usuario.setPassword(tutor.getPassword());

        usuarioRepository.save(usuario);

        ra.addAttribute("registroExitoso", "");
        return "redirect:/login";
    }
}
