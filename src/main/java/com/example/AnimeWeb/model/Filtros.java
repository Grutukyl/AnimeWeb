package com.example.AnimeWeb.model;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Filtros {

    private String buscaNome;
    private String type;
    private int page;
    private List<String> generos;

    public Filtros(String buscaNome, String type, int page, List<String> generos) {
        if(buscaNome == null){
            buscaNome = "";
        }
        if(type == null){
            type = "ANIME";
        }
        if(generos == null){
            generos = new ArrayList<String>();
        }
        if(page < 1){
            page = 1;
        }
        this.buscaNome = buscaNome;
        this.type = type;
        this.page = page;
        this.generos = generos;
    }

    @Override
    public String toString() {
        return "Filtros{" +
                "buscaNome='" + buscaNome + '\'' +
                ", type='" + type + '\'' +
                ", page=" + page +
                ", generos=" + generos +
                '}';
    }
}
