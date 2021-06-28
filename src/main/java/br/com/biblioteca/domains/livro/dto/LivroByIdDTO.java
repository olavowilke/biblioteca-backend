package br.com.biblioteca.domains.livro.dto;

import br.com.biblioteca.domains.livro.Livro;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class LivroByIdDTO {

    private UUID id;
    private String titulo;
    private LocalDate dataPublicacao;
    private String editora;
    private String generoLiterario;
    private String isbn;

    public LivroByIdDTO(Livro livro) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.dataPublicacao = livro.getDataPublicacao();
        this.editora = livro.getEditora();
        this.generoLiterario = livro.getGeneroLiterario();
        this.isbn = livro.getIsbn();
    }

}
