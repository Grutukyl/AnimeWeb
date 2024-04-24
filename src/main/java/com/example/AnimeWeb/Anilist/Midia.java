package com.example.AnimeWeb.Anilist;

import com.example.AnimeWeb.model.Data;
import com.example.AnimeWeb.model.Media;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.example.AnimeWeb.util.Util.callGraphQLService;

public class Midia {


    private static final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.WRAP_ROOT_VALUE)
            .enable(DeserializationFeature.UNWRAP_ROOT_VALUE)
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);



    public static Data buscarAnimesFiltros(int page, String nome, String tipo, List<String> generos, Boolean hentai) throws URISyntaxException, IOException {
        String query = queryBuilder(page,nome, tipo, generos, hentai);

        HttpResponse response = callGraphQLService("https://graphql.anilist.co/", query);
        String json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        System.out.println(query);
        Data parsedResponse = objectMapper.readValue(json, Data.class);
        return parsedResponse;
    }

    public static Data buscarAnimes(int page, String nome) throws URISyntaxException, IOException {
        String query = ("query {\n" +
                "  Page(page: "+page+", perPage: 25) {\n" +
                "    pageInfo {\n" +
                "      total\n" +
                "      currentPage\n" +
                "      lastPage\n" +
                "      hasNextPage\n" +
                "      perPage\n" +
                "    }\n" +
                "    media(search: \""+nome+"\", type: ANIME) {\n" +
                "      id\n" +
                "      season\n" +
                "      title {\n" +
                "        romaji\n" +
                "      }\n" +
                "      coverImage {\n" +
                "        extraLarge\n" +
                "        large\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        objectMapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        HttpResponse response = callGraphQLService("https://graphql.anilist.co/", query);
        String json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

        Data parsedResponse = objectMapper.readValue(json, Data.class);
        return parsedResponse;
    }


    public static Media dadosAnime(int id) throws URISyntaxException, IOException {
        String query = ("query {\n" +
                "  Media (id: "+id+") { \n" +
                "    id\n" +
                "    season\n" +
                "    seasonYear \n" +
                "    description\n" +
                "    averageScore\n" +
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
    public static Data dataAnime(int page) throws URISyntaxException, IOException {
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
        System.out.println(query);
        Data parsedResponse = objectMapper.readValue(json, Data.class);
        return parsedResponse;
    }

    public static String queryBuilder(int page, String nome, String tipo, List<String> generos, Boolean hentai) {
        String genresString = String.join("\", \"", generos);
        genresString = "[\"" + genresString + "\"]";
        String query = ("query {\n" +
                "  Page(page: " + page + ", perPage: 25) {\n" +
                "    pageInfo {\n" +
                "      total\n" +
                "      currentPage\n" +
                "      lastPage\n" +
                "      hasNextPage\n" +
                "      perPage\n" +
                "    }\n" +
                "    media(" + (nome.isBlank() ? "" : ("search: \"" + nome + "\"")) +
                "    "+ (tipo.isBlank() ?  "" : ", type: " +tipo.toUpperCase()) +
                "    "+ (generos.isEmpty() ?  "" : ", genre_in: " +genresString) +
                "    " + (hentai ? "" : ",genre_not_in: \"hentai\"") + ") {\n" +
                "      id\n" +
                "      season\n" +
                "      title {\n" +
                "        romaji\n" +
                "      }\n" +
                "      coverImage {\n" +
                "        extraLarge\n" +
                "        large\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
        return query;
    }
}
