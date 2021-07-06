package br.com.biblioteca.domains.genero_literario;

import br.com.biblioteca.domains.genero_literario.dto.GeneroLiterarioCriarAtualizarDTO;
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
@Table(name = "tb_genero_literario")
@Where(clause = "deleted_at IS NULL")
public class GeneroLiterario {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private String nome;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public GeneroLiterario() {
        this.id = UUID.randomUUID();
    }

    public GeneroLiterario(GeneroLiterarioCriarAtualizarDTO generoLiterarioCriarAtualizarDTO) {
        this();
        this.nome = generoLiterarioCriarAtualizarDTO.getNome();
    }

    public GeneroLiterario(String nome) {
        this();
        this.nome = nome;
    }

    public void update(GeneroLiterarioCriarAtualizarDTO generoLiterarioCriarAtualizarDTO) {
        this.nome = generoLiterarioCriarAtualizarDTO.getNome();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

}
