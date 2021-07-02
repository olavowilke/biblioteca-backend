package br.com.biblioteca.domains.editora;

import br.com.biblioteca.domains.editora.dto.EditoraCriarAtualizarDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_editora")
@Where(clause = "deleted_at IS NULL")
public class Editora {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private String nome;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Editora() {
        this.id = UUID.randomUUID();
    }

    public Editora(EditoraCriarAtualizarDTO editoraCriarAtualizarDTO) {
        this();
        this.nome = editoraCriarAtualizarDTO.getNome();
    }

    public Editora(String nome) {
        this();
        this.nome = nome;
    }

    public void update(EditoraCriarAtualizarDTO editoraAtualizarDTO) {
        this.nome = editoraAtualizarDTO.getNome();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

}
