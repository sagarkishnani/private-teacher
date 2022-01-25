package com.pt.privateteacher.controller;

import com.pt.privateteacher.model.Usuario;
import com.pt.privateteacher.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    String misCursos(Principal principal, Model model)
    {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).get();
        return "mis-cursos";
    }
}
