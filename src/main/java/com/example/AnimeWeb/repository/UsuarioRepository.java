package com.example.AnimeWeb.repository;

import com.example.AnimeWeb.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario,String> {
    List<Usuario> findByNomeContainingIgnoreCase(String nome);
    Usuario findUsuarioByEmail(String email);
    boolean existsByEmail(String email);


}
