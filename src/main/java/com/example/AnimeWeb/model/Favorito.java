package com.example.AnimeWeb.model;

import com.example.AnimeWeb.Records.FavoritoDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Getter
public class Favorito{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int idMedia;
    private String coverImage;
    private String title;
    @ManyToOne
    private Usuario usuario;
    public Favorito(FavoritoDTO favoritoDTO, Usuario usuario){
        this.idMedia = favoritoDTO.idMedia();
        this.coverImage = favoritoDTO.coverImage();
        this.title = favoritoDTO.title();
        this.usuario = usuario;
    }

    public boolean equalsByIdMedia(int idMedia){
        return this.idMedia == idMedia;
    }
}
