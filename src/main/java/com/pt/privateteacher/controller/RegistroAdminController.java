package com.pt.privateteacher.controller;

import com.pt.privateteacher.model.Admin;
import com.pt.privateteacher.model.Student;
import com.pt.privateteacher.model.Usuario;
import com.pt.privateteacher.repository.AdminRepository;
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
@RequestMapping("/registro-admin")
public class RegistroAdminController {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping
    public String admin(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin/registro-admin";
    }

    @PostMapping
    public String crear(@Validated Admin admin, BindingResult bindingResult, RedirectAttributes ra, Model model) {
        Usuario usuario = new Usuario();

        if (bindingResult.hasErrors()) {
            model.addAttribute("admin", admin);
            return "admin/registro-admin";
        }

        String email = admin.getEmail();
        boolean adminYaExiste = adminRepository.existsByEmail(email);

        if(adminYaExiste)
        {
            bindingResult.rejectValue("email", "EmailAlreadyExists");
        }

        if (!admin.getPassword1().equals(admin.getPassword2())) {
            bindingResult.rejectValue("password1", "PasswordNotEquals");
        }

        admin.setPassword(passwordEncoder.encode(admin.getPassword1()));

        admin.setRol(Admin.Rol.ADMIN);
        adminRepository.save(admin);

        usuario.setNombres(admin.getNombres());
        usuario.setApellidos(admin.getApellidos());
        usuario.setNombreCompleto(admin.getNombreCompleto());
        usuario.setEmail(admin.getEmail());
        usuario.setRol(Usuario.Rol.ADMIN);
        usuario.setPassword(admin.getPassword());

        usuarioRepository.save(usuario);


        ra.addAttribute("registroExitoso", "");
        return "redirect:/login";
    }
}

