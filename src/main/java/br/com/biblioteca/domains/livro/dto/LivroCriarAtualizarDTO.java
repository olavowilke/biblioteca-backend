package br.com.biblioteca.domains.livro.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class LivroCriarAtualizarDTO {

    @NotBlank
    @Size(max = 50)
    private String titulo;

    @NotNull
    private LocalDate dataPublicacao;

    @NotBlank
    @Size(max = 50)
    private String editora;

    @NotBlank
    @Size(max = 50)
    private String generoLiterario;

    @NotNull
    private UUID autorId;

    @NotBlank
    private String isbn;

}
