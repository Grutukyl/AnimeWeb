package com.example.AnimeWeb.Controler;

import com.example.AnimeWeb.model.Usuario;
import com.example.AnimeWeb.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;



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
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        if(!userRepo.existsByEmail(usuario.getEmail())) {
            System.out.println("Usuario registrado, new user.");
            userRepo.save(usuario);
            return "redirect:/login?cadastrado";
        }else{
            return "redirect:/login?cadastradoErro";
        }
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
            model.addAttribute("error", "Confira o usuario ou senha.");
        if (logout != null)
            model.addAttribute("message", "Você se desconectou com sucesso.");
        return "login";
    }

    @GetMapping(value = "/cadastrar")
    public String registrarr(Model model){
        return "registrarr";
    }


}
