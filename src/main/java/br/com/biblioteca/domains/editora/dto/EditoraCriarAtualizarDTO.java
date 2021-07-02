package br.com.biblioteca.domains.editora.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class EditoraCriarAtualizarDTO {

    @NotBlank
    @Size(max = 50)
    private String nome;

}
