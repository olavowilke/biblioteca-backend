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
    private UUID editoraId;
    private UUID generoLiterarioId;
    private String isbn;
    private UUID autorId;

    public LivroByIdDTO(Livro livro) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.dataPublicacao = livro.getDataPublicacao();
        this.isbn = livro.getIsbn();
        this.editoraId = livro.getEditoraId();
        this.generoLiterarioId = livro.getGeneroLiterarioId();
        this.autorId = livro.getAutorId();
    }

}
