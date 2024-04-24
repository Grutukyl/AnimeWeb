package com.example.AnimeWeb.config;

import com.example.AnimeWeb.model.Usuario;
import com.example.AnimeWeb.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JpaUserDetailsService implements UserDetailsService {

    private final UsuarioRepository userRepo;

    public JpaUserDetailsService(UsuarioRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = userRepo.findUsuarioByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("Usuario não encontrado");
        }
        return new UsuarioDetals(user);
    }
}
