package br.com.biblioteca.util;

import br.com.biblioteca.domains.autor.Autor;
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

}
