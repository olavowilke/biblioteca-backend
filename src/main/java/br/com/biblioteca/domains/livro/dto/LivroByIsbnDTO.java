package br.com.biblioteca.domains.livro.dto;

import br.com.biblioteca.domains.livro.Livro;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class LivroByIsbnDTO {

    private UUID id;
    private String titulo;
    private String autorNome;
    private String isbn;

    public LivroByIsbnDTO(Livro livro) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.autorNome = livro.getAutorNome();
        this.isbn = livro.getIsbn();
    }

}
