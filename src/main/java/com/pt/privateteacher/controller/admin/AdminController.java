package com.pt.privateteacher.controller.admin;

import com.pt.privateteacher.model.Tutor;
import com.pt.privateteacher.model.Usuario;
import com.pt.privateteacher.repository.TutorRepository;
import com.pt.privateteacher.repository.UsuarioRepository;
import com.pt.privateteacher.service.FileSystemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/tutores")
public class AdminController {

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

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @GetMapping("")
    String index(Model model)
    {
        List<Tutor> tutores = tutorRepository.findAll();
        model.addAttribute("tutores", tutores); //agregar un atributo al model que se enviara a la VISTA
        return "admin/index"; //retorna a la VISTA index.html
    }

    //editar
    @GetMapping("/{id}/editar")
    String editar(@PathVariable Integer id, Model model)
    {
        Tutor tutor = tutorRepository.getById(id);
        model.addAttribute("tutor", tutor);
        model.addAttribute("tituloList", tituloList);
        return "admin/editar-tutor";
    }

    //editar
    //http:localhost:8080/tutores/editar  => editar.html   => ACTAULIZAR LOS DATOS DEL CURSO EN DB => POST
    @PostMapping("/{id}/editar") //buscar el tutor y lo envie como parametro a la vista para que se muestre en pantalla
    String actualizar(@PathVariable Integer id, Model model, @Validated Tutor tutor, BindingResult bindingResult, RedirectAttributes ra)
    {

        Tutor tutorFromDB = tutorRepository.getById(id);
        Usuario usuarioFromDB = usuarioRepository.getById(id);

        tutorFromDB.setDni(tutor.getDni());
        tutorFromDB.setEmail(tutor.getEmail());
        tutorFromDB.setNombres(tutor.getNombres());
        tutorFromDB.setApellidos(tutor.getApellidos());
        tutorFromDB.setNombreCompleto(tutor.getNombreCompleto());
        tutorFromDB.setPassword1(tutor.getPassword1());
        tutorFromDB.setPassword2(tutor.getPassword2());
        tutorFromDB.setFechanacimiento(tutor.getFechanacimiento());
        tutorFromDB.setCelular(tutor.getCelular());
        tutorFromDB.setTitulo(tutor.getTitulo());
        tutorFromDB.setCosto(tutor.getCosto());
        tutorFromDB.setDescripcion(tutor.getDescripcion());



        if(bindingResult.hasErrors())
        {
            model.addAttribute("tutor", tutor);
            return "admin/editar-tutor";
        }

        if (! tutor.getImagen().isEmpty())
        {
            String foto = fileSystemStorageService.store(tutor.getImagen());
            tutorFromDB.setFoto(foto);
        }

        if(! tutor.getEmail().equals(usuarioFromDB.getEmail())){
            tutorFromDB.getEmail();
        }

        tutorRepository.save(tutorFromDB);

        usuarioFromDB.setNombres(tutor.getNombres());
        usuarioFromDB.setApellidos(tutor.getApellidos());
        usuarioFromDB.setNombreCompleto(tutor.getNombreCompleto());
        usuarioFromDB.setEmail(tutor.getEmail());
        usuarioFromDB.setRol(Usuario.Rol.TUTOR);
        usuarioFromDB.setPassword(tutor.getPassword());

        usuarioRepository.save(usuarioFromDB);
        ra.addFlashAttribute("msgExito", "Tutor actualizado");

        return "redirect:/admin/tutores";

    }

    //eliminar
    //http:localhost:8080/cursos/{id}/eliminar  ELIMINAR CURSO DE LA BD => POST
    @PostMapping("/{id}/eliminar")
    String eliminar(@PathVariable Integer id, RedirectAttributes ra)
    {
        Tutor tutor = tutorRepository.getById(id);
        Usuario usuario = usuarioRepository.getById(id);
        tutorRepository.delete(tutor);
        usuarioRepository.delete(usuario);
        ra.addFlashAttribute("msgExito", "Tutor eliminado");
        return "redirect:/admin/tutores";
    }
}
