package com.example.AnimeWeb.Controler;

import com.example.AnimeWeb.Anilist.Midia;
import com.example.AnimeWeb.model.Media;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import static com.example.AnimeWeb.Anilist.Midia.dataAnime;

@Controller
public class ListagemController {

    @GetMapping("/listagem")
    public String greetings(Model model, @RequestParam(value = "page",defaultValue = "1") int page) throws URISyntaxException, IOException {
        model.addAttribute("listagem", dataAnime(page));
        return "listagem";
    }

    @GetMapping("/media/{id}")
    public String media(@PathVariable int id, Model model) throws URISyntaxException, IOException {
        Media media = Midia.dadosAnime(id);
        String generos = String.join(", ", media.getGenres());
        model.addAttribute("media",media);
        model.addAttribute("generos",generos);
        System.out.println("PAGINA" + id);
        return "media";
    }


}
