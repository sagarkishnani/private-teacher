package com.pt.privateteacher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/contacto")
    public String contacto() {
        return "contacto";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/registrar")
    public String registrar() {
        return "registro-alumno";
    }
}
