package br.com.tayweb.api.services;

import br.com.tayweb.api.domain.Usuario;
import br.com.tayweb.api.domain.dto.UsuarioDTO;
import br.com.tayweb.api.repository.UsuarioRepository;
import br.com.tayweb.api.services.exceptions.DataIntegratyViolationException;
import br.com.tayweb.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
class UsuarioServiceTest {

    public static final long ID = 1L;
    public static final String NOME = "Augusto";
    public static final String EMAIL = "augusto@gmail.com";
    public static final String SENHA = "123";
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks // cria uma instância real da classe e injeta os mocks que são criados com a anotação @Mock
    private UsuarioService usuarioService;

    @Mock
    private ModelMapper mapper;

    private Usuario usuario;

    private UsuarioDTO usuarioDTO;

    private Optional<Usuario> optionalUsuario;


    @BeforeEach
    void setUp() {
        openMocks(this); // Inicia os Mocks da classe
        iniciarUsuario();
    }

    @Test
    void quandoBuscarTodosDeveraRetornarListaUsuario() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> response = usuarioService.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Usuario.class, response.get(0).getClass());
    }

    @Test
    void quandoBuscarPorIdDeveRetornarInstanciaUsuario() {
        when(usuarioRepository.findById(Mockito.anyLong())).thenReturn(optionalUsuario); // Faz o Mock das informações no repository para quando o service chamar ele, vim os dados mockado

        Usuario response = usuarioService.findById(ID);

        assertNotNull(response);
        assertEquals(Usuario.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void quandoBuscarIdInexistenteDeveraRetornarObjetoNaoEncontrado() {
        when(usuarioRepository.findById(Mockito.anyLong()))
                .thenThrow(new ObjectNotFoundException("Usuário não encontrado"));
        try {
            usuarioService.findById(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Usuário não encontrado", ex.getMessage());
        }
    }

    @Test
    void quandoSalvarUsuarioDeveraRetornarStatusSucesso() {
        when(usuarioRepository.save(any())).thenReturn(usuario);

        Usuario response = usuarioService.save(usuarioDTO);

        assertNotNull(response);
        assertEquals(Usuario.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(SENHA, response.getSenha());
    }

    @Test
    void quandoSalvarUsuarioComEmailJaCadastradoDeverarRetornarVioalaoIntegridade() {
        when(usuarioRepository.findByEmail(anyString())).thenReturn(optionalUsuario);

        try {
            optionalUsuario.get().setId(2L);
            usuarioService.save(usuarioDTO);
        } catch (Exception e) {
            assertEquals(DataIntegratyViolationException.class, e.getClass());
            assertEquals("Email já cadastrado", e.getMessage());
        }
    }

    @Test
    void quandoDeletarUsuarioDeveraRotornarStatusSucesso() {
        when(usuarioRepository.findById(anyLong())).thenReturn(optionalUsuario);
        doNothing().when(usuarioRepository).deleteById(anyLong());
        usuarioService.delete(ID);
        verify(usuarioRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void quandoDeletarUsuarioDeveraRotornarUsuarioNaoEncontrado() {
        when(usuarioRepository.findById(Mockito.anyLong()))
                .thenThrow(new ObjectNotFoundException("Usuário não encontrado"));
        try {
            usuarioService.delete(ID);
        }catch (Exception e) {
            assertEquals(ObjectNotFoundException.class, e.getClass());
            assertEquals("Usuário não encontrado", e.getMessage());
        }
    }

    private void iniciarUsuario() {
        usuario = new Usuario(ID, NOME, EMAIL, SENHA);
        usuarioDTO = new UsuarioDTO(ID, NOME, EMAIL, SENHA);
        optionalUsuario = Optional.of(new Usuario(ID, NOME, EMAIL, SENHA));
    }
}