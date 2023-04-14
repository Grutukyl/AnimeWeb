package com.example.AnimeWeb.repository;

import com.example.AnimeWeb.model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario,String> {
    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    Usuario findUsuarioByEmail(String email);
}
