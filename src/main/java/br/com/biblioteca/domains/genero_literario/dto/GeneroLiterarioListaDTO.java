package br.com.biblioteca.domains.genero_literario.dto;

import br.com.biblioteca.domains.genero_literario.GeneroLiterario;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class GeneroLiterarioListaDTO {

    private UUID id;
    private String nome;

    public GeneroLiterarioListaDTO(GeneroLiterario generoLiterario) {
        this.id = generoLiterario.getId();
        this.nome = generoLiterario.getNome();
    }

}
