package com.example.AnimeWeb.Controler;


import com.example.AnimeWeb.config.UsuarioDetals;
import com.example.AnimeWeb.model.Favorito;
import com.example.AnimeWeb.Records.FavoritoDTO;
import com.example.AnimeWeb.model.Usuario;
import com.example.AnimeWeb.repository.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FavoritosController {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @PostMapping("/favoritos")
    public ResponseEntity favoritos(@RequestBody FavoritoDTO favoritoDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(favoritoDTO.idMedia());
        if (authentication != null && authentication.isAuthenticated()) {
            UsuarioDetals usuario = (UsuarioDetals) authentication.getPrincipal();
            Usuario user = usuario.getUser();
            if (checkIfFavoritoExists(favoritoDTO)) {
                favoritoRepository.deleteByUsuarioAndIdMedia(user.getId(), favoritoDTO.idMedia());
                return ResponseEntity.ok().build();
            }
            Favorito favorito = new Favorito(favoritoDTO, user);
            favoritoRepository.save(favorito);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    private boolean checkIfFavoritoExists(FavoritoDTO favoritoDTO) {
        return favoritoRepository.existsByIdMedia(favoritoDTO.idMedia());
    }

}
