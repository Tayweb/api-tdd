package br.com.tayweb.api.resources;

import br.com.tayweb.api.domain.Usuario;
import br.com.tayweb.api.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioResource {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public Usuario findById(@PathVariable Long id){
        return usuarioService.findById(id);
    }
}
