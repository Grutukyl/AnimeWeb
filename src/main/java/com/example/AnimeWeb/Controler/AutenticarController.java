package com.example.AnimeWeb.Controler;

import com.example.AnimeWeb.model.Usuario;
import com.example.AnimeWeb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class AutenticarController {
    @Autowired
    private UsuarioRepository userRepo;

    @RequestMapping(value = "/registrar",method = RequestMethod.GET)
    public String Registrar(){
        return "registrarr";
    }

    @RequestMapping(value = "/registrar",method = RequestMethod.POST)
    public String Registrar(Usuario usuario){
        System.out.println("Usuario registrado, new user.");
        userRepo.save(usuario);
        return "redirect:/registrar";
    }

    @GetMapping("/usuarios")
    public ResponseEntity<Usuario> getUsuariosByName (@RequestParam("nome") String nome){
        return new ResponseEntity<Usuario>(userRepo.findUsuarioByEmail(nome), HttpStatus.OK);
    }

    @RequestMapping(value="/logout", method = RequestMethod.POST)
    public String logoutPage (WebRequest request, HttpServletResponse response) {
        return "/login?logout";
    }

    @GetMapping(value = "/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Confira seu usuario ou senha.");

        if (logout != null)
            model.addAttribute("message", "Você se desconectou com sucesso.");

        return "login";
    }


}
