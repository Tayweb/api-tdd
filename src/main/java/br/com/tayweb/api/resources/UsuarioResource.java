package br.com.tayweb.api.resources;

import br.com.tayweb.api.domain.dto.UsuarioDTO;
import br.com.tayweb.api.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioResource {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(mapper.map(usuarioService.findById(id), UsuarioDTO.class));
    }
}
