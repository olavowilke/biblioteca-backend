package br.com.biblioteca.domains.autor;

import br.com.biblioteca.domains.autor.dto.AutorCriarAtualizarDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "tb_autor")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Where(clause = "deleted_at IS NULL")
public class Autor {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private String nome;
    private String nacionalidade;
    private LocalDate dataNascimento;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Autor() {
        this.id = UUID.randomUUID();
    }

    public Autor(String nome, String nacionalidade, LocalDate dataNascimento) {
        this();
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.dataNascimento = dataNascimento;
    }

    public Autor(AutorCriarAtualizarDTO autorCriarAtualizarDTO) {
        this();
        this.nome = autorCriarAtualizarDTO.getNome();
        this.nacionalidade = autorCriarAtualizarDTO.getNacionalidade();
        this.dataNascimento = autorCriarAtualizarDTO.getDataNascimento();
    }

    public void atualizarPorId(AutorCriarAtualizarDTO autorCriarAtualizarDTO) {
        this.nome = autorCriarAtualizarDTO.getNome();
        this.nacionalidade = autorCriarAtualizarDTO.getNacionalidade();
        this.dataNascimento = autorCriarAtualizarDTO.getDataNascimento();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

}
