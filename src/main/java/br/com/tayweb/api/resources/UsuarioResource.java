package br.com.tayweb.api.resources;

import br.com.tayweb.api.domain.dto.UsuarioDTO;
import br.com.tayweb.api.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioResource {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping()
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        return ResponseEntity.ok()
                .body(usuarioService.findAll()
                        .stream().map(user -> mapper.map(user, UsuarioDTO.class)).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(mapper.map(usuarioService.findById(id), UsuarioDTO.class));
    }

    @PostMapping()
    public ResponseEntity<UsuarioDTO> save(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok().body(mapper.map(usuarioService.save(usuarioDTO), UsuarioDTO.class));
    }

    @PutMapping()
    public ResponseEntity<UsuarioDTO> update(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok().body(mapper.map(usuarioService.save(usuarioDTO), UsuarioDTO.class));
    }

    @DeleteMapping( value = "/{id}")
    public ResponseEntity<UsuarioDTO> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
