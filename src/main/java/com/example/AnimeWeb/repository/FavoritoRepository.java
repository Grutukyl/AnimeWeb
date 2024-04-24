package com.example.AnimeWeb.repository;

import com.example.AnimeWeb.model.Favorito;
import com.example.AnimeWeb.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritoRepository  extends CrudRepository<Favorito,Long> {

    boolean existsByIdMedia(int idMedia);

    List<Favorito> findByUsuario(Usuario usuario);

    @Query(value = "SELECT favorito.id_media FROM favorito  WHERE usuario_id = :id", nativeQuery = true)
    List<Integer> f(long id);

    @Query(value = "delete from favorito where usuario_id = :id and id_media = :idMedia", nativeQuery = true)
    void deleteByUsuarioAndIdMedia(long id, int idMedia);

}
