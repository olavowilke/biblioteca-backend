package br.com.biblioteca.domains.editora.dto;

import br.com.biblioteca.domains.editora.Editora;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class EditoraByIdDTO {

    private UUID id;
    private String nome;

    public EditoraByIdDTO(Editora editora) {
        this.id = editora.getId();
        this.nome = editora.getNome();
    }

}
