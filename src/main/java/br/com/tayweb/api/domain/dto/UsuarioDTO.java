package br.com.tayweb.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

        private Long id;
        private String nome;
        private String email;
}
