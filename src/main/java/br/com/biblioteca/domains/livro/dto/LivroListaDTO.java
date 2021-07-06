package br.com.biblioteca.domains.livro.dto;

import br.com.biblioteca.domains.autor.Autor;
import br.com.biblioteca.domains.editora.Editora;
import br.com.biblioteca.domains.genero_literario.GeneroLiterario;
import br.com.biblioteca.domains.livro.Livro;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class LivroListaDTO {

    private UUID id;
    private String titulo;
    private LocalDate dataPublicacao;
    private String editoraNome;
    private String generoLiterarioNome;
    private String autorNome;
    private String isbn;

    public LivroListaDTO(Livro livro, Autor autor, Editora editora, GeneroLiterario generoLiterario) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.dataPublicacao = livro.getDataPublicacao();
        this.editoraNome = editora.getNome();
        this.generoLiterarioNome = generoLiterario.getNome();
        this.isbn = livro.getIsbn();
        this.autorNome = autor.getNome();
    }

}
