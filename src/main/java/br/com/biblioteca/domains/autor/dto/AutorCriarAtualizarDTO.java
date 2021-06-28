package br.com.biblioteca.domains.autor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
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
    private LocalDate dataNascimento;

}
