package com.example.AnimeWeb.Controler;

import com.example.AnimeWeb.model.Data;
import com.example.AnimeWeb.model.Media;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import static com.example.AnimeWeb.util.Util.callGraphQLService;

@Controller
public class DemoController {

    @GetMapping("/ggg")
    public String greetings(Model model, @RequestParam(value = "page",defaultValue = "1") int page) throws URISyntaxException, IOException {
        model.addAttribute("bomdia", dataAnime(page));
        return "ggg";
    }

    @GetMapping("/media/{id}")
    public String media(@PathVariable int id, Model model) throws URISyntaxException, IOException {

        Media media = mediaAnime(id);
        String generos = String.join(", ", media.getGenres());
        model.addAttribute("media",media);
        model.addAttribute("generos",generos);
        System.out.println("PAGINA" + id);

        return "media";
    }




    public Media mediaAnime(int id) throws  URISyntaxException, IOException{
        String query = ("query {\n" +
                "  Media (id: "+id+") { \n" +
                "    id\n" +
                "    season\n" +
                "    description\n" +
                "    genres\n" +
                "    title {\n" +
                "      romaji\n" +
                "    }\n" +
                "    coverImage {\n" +
                "      extraLarge\n" +
                "      large\n" +
                "    }\n" +
                "  }\n" +
                "}\n");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        objectMapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        HttpResponse response = callGraphQLService("https://graphql.anilist.co/", query);
        String json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

        JsonNode root = new ObjectMapper().readTree(json);
        JsonNode mediaNode = root.at("/data/Media");
        Media parsedResponse = new ObjectMapper().treeToValue(mediaNode, Media.class);
        return parsedResponse;

    }

    public Data dataAnime(int page) throws URISyntaxException, IOException {
        String query = ("query {\n" +
                        "  Page (page: " +page+ ", perPage:25) {\n" +
                        "    pageInfo {\n" +
                        "      total\n" +
                        "      currentPage\n" +
                        "      lastPage\n" +
                        "      hasNextPage\n" +
                        "      perPage\n" +
                        "    }\n" +
                        "  media (type: ANIME) { # Insert our variables into the query arguments (id) (type: ANIME is hard-coded in the query)\n" +
                        "                    id\n" +
                        "                    season\n" +
                        "                    title{\n" +
                        "                        romaji\n" +
                        "                    }\n" +
                        "                    coverImage{\n" +
                        "                        extraLarge\n" +
                        "                        large\n" +
                        "                  }\n" +
                        "                  }\n" +
                        "  }\n" +
                        "}\n");


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        objectMapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        HttpResponse response = callGraphQLService("https://graphql.anilist.co/", query);
        String json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

        Data parsedResponse = objectMapper.readValue(json, Data.class);
        return parsedResponse;
    }
}
