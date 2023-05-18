package br.com.tayweb.api.services;

import br.com.tayweb.api.domain.Usuario;
import br.com.tayweb.api.domain.dto.UsuarioDTO;
import br.com.tayweb.api.repository.UsuarioRepository;
import br.com.tayweb.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        MockitoAnnotations.openMocks(this); // Inicia os Mocks da classe
        iniciarUsuario();
    }

    @Test
    void whenfindAllThenReturnAnListOfUsers() { // Quando buscar todos retorne uma lista de usuário
        Mockito.when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> response = usuarioService.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Usuario.class, response.get(0).getClass());
    }

    @Test
    void whenfindByIdThenReturnAnUserInstance() { // Quando fizer a busca por id retorne uma instancia de usuário
        Mockito.when(usuarioRepository.findById(Mockito.anyLong())).thenReturn(optionalUsuario); // Faz o Mock das informações no repository para quando o service chamar ele, vim os dados mockado

        Usuario response = usuarioService.findById(ID);

        assertNotNull(response);
        assertEquals(Usuario.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {
        Mockito.when(usuarioRepository.findById(Mockito.anyLong()))
                .thenThrow(new ObjectNotFoundException("Usuário não encontrado"));
        try {
            usuarioService.findById(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Usuário não encontrado", ex.getMessage());
        }
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    private void iniciarUsuario() {
        usuario = new Usuario(ID, NOME, EMAIL, SENHA);
        usuarioDTO = new UsuarioDTO(ID, NOME, EMAIL, SENHA);
        optionalUsuario = Optional.of(new Usuario(ID, NOME, EMAIL, SENHA));
    }
}