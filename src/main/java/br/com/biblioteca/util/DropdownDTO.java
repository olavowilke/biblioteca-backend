package br.com.biblioteca.util;

import br.com.biblioteca.domains.autor.Autor;
import br.com.biblioteca.domains.editora.Editora;
import br.com.biblioteca.domains.genero_literario.GeneroLiterario;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DropdownDTO {

    private UUID id;
    private String nome;

    public DropdownDTO(Autor autor) {
        this.id = autor.getId();
        this.nome = autor.getNome();
    }

    public DropdownDTO(Editora editora) {
        this.id = editora.getId();
        this.nome = editora.getNome();
    }

    public DropdownDTO(GeneroLiterario generoLiterario) {
        this.id = generoLiterario.getId();
        this.nome = generoLiterario.getNome();
    }

}
