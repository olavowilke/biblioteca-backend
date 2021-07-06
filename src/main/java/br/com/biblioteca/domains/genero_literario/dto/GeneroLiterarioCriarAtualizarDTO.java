package br.com.biblioteca.domains.genero_literario.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class GeneroLiterarioCriarAtualizarDTO {

    @NotBlank
    @Size(max = 50)
    private String nome;

}
