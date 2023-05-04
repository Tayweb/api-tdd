package br.com.tayweb.api.services;

import br.com.tayweb.api.domain.Usuario;
import br.com.tayweb.api.repository.UsuarioRepository;
import br.com.tayweb.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public Usuario findById(Long id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
    }
}
