package com.example.AnimeWeb.Controler;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ErrorController {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String retornarErro(Model modelo){
        modelo.addAttribute("erroMsg" , "ERRO NO SERVIDOR!");
        return "error";
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String retornarErroUNauthorized(Model modelo){
        modelo.addAttribute("erroMsg", "ACESSO NEGADO!");
        return "error";
    }

}
