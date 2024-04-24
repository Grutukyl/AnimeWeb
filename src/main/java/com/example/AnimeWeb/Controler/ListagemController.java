package com.example.AnimeWeb.Controler;

import com.example.AnimeWeb.Anilist.Midia;
import com.example.AnimeWeb.config.UsuarioDetals;
import com.example.AnimeWeb.model.Favorito;
import com.example.AnimeWeb.model.Filtros;
import com.example.AnimeWeb.model.Media;
import com.example.AnimeWeb.model.Usuario;
import com.example.AnimeWeb.repository.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class ListagemController {

    @Autowired
    private FavoritoRepository favoritoRepository;
    @GetMapping("/listagem")
    public String greetings(Model model
            , @RequestParam(value = "page", defaultValue = "1") int page
            , @RequestParam(value = "buscaNome", required = false) String buscaNome
            , @RequestParam(value = "type", required = false) String type
            , @RequestParam(value = "generos", required = false) List<String> generos
            ) throws URISyntaxException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Integer> favoritos = null;
        if (authentication != null && authentication.isAuthenticated()) {
            UsuarioDetals usuario = (UsuarioDetals) authentication.getPrincipal();
            Usuario user = usuario.getUser();
            favoritos = favoritoRepository.f(user.getId());
            System.out.println(favoritos);
        }
        Boolean isHentai = false;
        if(generos != null){
            isHentai = generos.contains("hentai");
        }

        Filtros filtros = new Filtros(buscaNome, type, page, generos);
        model.addAttribute("listagem", Midia.buscarAnimesFiltros(filtros.getPage()
                , filtros.getBuscaNome()
                , filtros.getType()
                , filtros.getGeneros()
                ,isHentai));
        model.addAttribute("filtros", filtros);
        model.addAttribute("favoritos", favoritos);

        return "listagem";
    }


    @GetMapping("/media/{id}")
    public String media(@PathVariable int id, Model model) throws URISyntaxException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Integer> favoritos = null;
        if (authentication != null && authentication.isAuthenticated()) {
            UsuarioDetals usuario = (UsuarioDetals) authentication.getPrincipal();
            Usuario user = usuario.getUser();
            favoritos = favoritoRepository.f(user.getId());
            System.out.println(favoritos);
        }

        Media media = Midia.dadosAnime(id);
        String generos = String.join(", ", media.getGenres());
        model.addAttribute("favoritos", favoritos);
        model.addAttribute("media", media);
        model.addAttribute("generos", generos);
        return "media";
    }

    @GetMapping("/listagemFavoritos")
    public String listagemFavoritos(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Favorito> favoritos = Collections.emptyList();
        List<Integer> favoritados = Collections.emptyList();
        if (authentication != null && authentication.isAuthenticated()) {
            UsuarioDetals usuario = (UsuarioDetals) authentication.getPrincipal();
            Usuario user = usuario.getUser();
            favoritados = favoritoRepository.f(user.getId());
            favoritos = favoritoRepository.findByUsuario(user);
            System.out.println(favoritos.get(0).getCoverImage());
            System.out.println(favoritos.size());
        }
        model.addAttribute("favoritados", favoritados);
        model.addAttribute("listaFavoritos", favoritos);
        return "listagemFavoritos";
    }
}
