package br.com.tayweb.api.resources;

import br.com.tayweb.api.domain.Usuario;
import br.com.tayweb.api.domain.dto.UsuarioDTO;
import br.com.tayweb.api.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static br.com.tayweb.api.shared.ConstantsTest.EMAIL;
import static br.com.tayweb.api.shared.ConstantsTest.ID;
import static br.com.tayweb.api.shared.ConstantsTest.NOME;
import static br.com.tayweb.api.shared.ConstantsTest.SENHA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class UsuarioResourceTest {

    @InjectMocks
    UsuarioResource usuarioResource;

    @Mock
    UsuarioService usuarioService;

    @Mock
    ModelMapper modelMapper;

    private Usuario usuario;

    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        openMocks(this);
        iniciarUsuario();
    }

    @Test
    void deveRetornarListaUsuario() {
        when(usuarioService.findAll()).thenReturn(List.of(usuario));
        when(modelMapper.map(usuario, UsuarioDTO.class)).thenReturn(usuarioDTO);

        ResponseEntity<List<UsuarioDTO>> usuarioList = usuarioResource.findAll();
        assertNotNull(usuarioList);
    }

    @Test
    void quandoBuscarIdDeveraRetornarObjeto() {
        when(usuarioService.findById(ID)).thenReturn(usuario);
        when(modelMapper.map(usuario, UsuarioDTO.class)).thenReturn(usuarioDTO);

        ResponseEntity<UsuarioDTO> userDTO = usuarioResource.findById(usuario.getId());

        assertEquals(200, userDTO.getStatusCode().value());
        assertEquals(usuarioDTO, userDTO.getBody());
        assertEquals(ID, userDTO.getBody().getId());

    }

    @Test
    void quandoSalvarUsuarioDeveRetornarSucesso() {
        when(usuarioService.save(any())).thenReturn(usuario);

        ResponseEntity<UsuarioDTO> response = usuarioResource.save(usuarioDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void iniciarUsuario() {
        usuario = new Usuario(ID, NOME, EMAIL, SENHA);
        usuarioDTO = new UsuarioDTO(ID, NOME, EMAIL, SENHA);
    }
}