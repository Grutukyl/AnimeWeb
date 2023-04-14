package com.example.AnimeWeb.Controler;

import com.example.AnimeWeb.model.Data;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
