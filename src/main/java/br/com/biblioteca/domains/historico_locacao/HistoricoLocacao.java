package br.com.biblioteca.domains.historico_locacao;

import br.com.biblioteca.domains.cliente.Cliente;
import br.com.biblioteca.domains.livro.Livro;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_historico_locacao")
@Where(clause = "deleted_at IS NULL")
public class HistoricoLocacao {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private LocalDate dataLocacao;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    private Livro livro;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

}
