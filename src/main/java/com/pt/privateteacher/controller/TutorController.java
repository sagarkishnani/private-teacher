package com.pt.privateteacher.controller;

import com.pt.privateteacher.model.Tutor;
import com.pt.privateteacher.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tutores")
public class TutorController {


    @Autowired
    private TutorRepository tutorRepository;

    @GetMapping("")
    String index(Model model)
    {
        List<Tutor> tutores = tutorRepository.findAll(); //Obtenemos el listado de los cursos desde la bd y se guarda en la variable tutores
        model.addAttribute("tutores", tutores); //agregar un atributo al model que se enviara a la VISTA
        return "tutores"; //retorna a la VISTA index.html
    }

    @GetMapping("/{idtutor}")
    String getTutor(@PathVariable Integer idtutor, Model model) //obtengo la informacion de 1 solo tutor
    {
        Tutor tutor = tutorRepository.getById(idtutor);
        model.addAttribute("tutor", tutor);

        return "detalles-tutor";
    }

}
