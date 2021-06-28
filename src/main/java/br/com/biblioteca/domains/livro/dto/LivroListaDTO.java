package br.com.biblioteca.domains.livro.dto;

import br.com.biblioteca.domains.autor.Autor;
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
    private String editora;
    private String generoLiterario;
    private String autorNome;
    private String isbn;

    public LivroListaDTO(Livro livro, Autor autor) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.dataPublicacao = livro.getDataPublicacao();
        this.editora = livro.getEditora();
        this.generoLiterario = livro.getGeneroLiterario();
        this.isbn = livro.getIsbn();
        this.autorNome = autor.getNome();
    }

}
