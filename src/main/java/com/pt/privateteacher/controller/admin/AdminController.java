package com.pt.privateteacher.controller.admin;

import com.pt.privateteacher.model.Tutor;
import com.pt.privateteacher.repository.TutorRepository;
import com.pt.privateteacher.service.FileSystemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/tutores")
public class AdminController {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @GetMapping("")
    String index(Model model)
    {
        List<Tutor> tutores = tutorRepository.findAll();
        model.addAttribute("tutores", tutores); //agregar un atributo al model que se enviara a la VISTA
        return "admin/index"; //retorna a la VISTA index.html
    }
}
