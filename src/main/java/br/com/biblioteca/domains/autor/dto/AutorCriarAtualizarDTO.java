package br.com.biblioteca.domains.autor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AutorCriarAtualizarDTO {

    @NotBlank
    @Size(max = 50)
    private String nome;

    @NotBlank
    @Size(max = 50)
    private String nacionalidade;

    @NotNull
    @Past
    private LocalDate dataNascimento;

}
