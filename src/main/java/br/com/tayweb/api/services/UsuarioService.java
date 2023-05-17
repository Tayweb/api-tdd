package br.com.tayweb.api.services;

import br.com.tayweb.api.domain.Usuario;
import br.com.tayweb.api.domain.dto.UsuarioDTO;
import br.com.tayweb.api.repository.UsuarioRepository;
import br.com.tayweb.api.services.exceptions.DataIntegratyViolationException;
import br.com.tayweb.api.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper mapper;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
    }

    public Usuario save(UsuarioDTO usuarioDTO) {
        findByEmail(usuarioDTO);
        return usuarioRepository.save(mapper.map(usuarioDTO, Usuario.class));
    }

    public void delete(Long id) {
        findById(id);
        usuarioRepository.deleteById(id);
    }

    private void findByEmail(UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(usuarioDTO.getEmail());
        if (usuario.isPresent() && !usuario.get().getId().equals(usuarioDTO.getId())) {
            throw new DataIntegratyViolationException("Email já cadastrado");
        }
    }
}
