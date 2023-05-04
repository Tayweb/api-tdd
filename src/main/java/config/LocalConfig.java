package config;

import br.com.tayweb.api.domain.Usuario;
import br.com.tayweb.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    public void startDB(){
        Usuario usuario = new Usuario(null, "Tainá", "tainafontes058@gmail.com","admin");
        Usuario usuario2 = new Usuario(null, "Raquel", "raquel@gmail.com","123");

        usuarioRepository.saveAll(List.of(usuario,usuario2));

    }
}
