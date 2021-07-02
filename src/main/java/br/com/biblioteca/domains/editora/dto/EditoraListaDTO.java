package br.com.biblioteca.domains.editora.dto;

import br.com.biblioteca.domains.editora.Editora;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class EditoraListaDTO {

    private UUID id;
    private String nome;

    public EditoraListaDTO(Editora editora) {
        this.id = editora.getId();
        this.nome = editora.getNome();
    }

}
