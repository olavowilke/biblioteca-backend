package br.com.biblioteca.domains.locacao;

import br.com.biblioteca.domains.cliente.Cliente;
import br.com.biblioteca.domains.livro.Livro;
import br.com.biblioteca.domains.locacao.dto.LocacaoAtualizarDTO;
import br.com.biblioteca.domains.locacao.dto.LocacaoCriarDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_locacao")
public class Locacao {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private LocalDateTime dataLocacao;
    private LocalDateTime dataPrevistaDevolucao;
    private LocalDateTime dataDevolucao;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "situacao_info")
    @Type(type = "pgsql_enum")
    private Situacao situacao;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tb_locacao_livro", joinColumns = @JoinColumn(name = "locacao_id"),
            inverseJoinColumns = @JoinColumn(name = "livro_id"))
    private List<Livro> livros;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Locacao() {
        this.id = UUID.randomUUID();
    }

    public Locacao(LocacaoCriarDTO locacaoCriarDTO, Cliente cliente, List<Livro> livros) {
        this();
        this.dataLocacao = locacaoCriarDTO.getDataLocacao();
        this.dataPrevistaDevolucao = locacaoCriarDTO.getDataPrevistaDevolucao();
        this.situacao = Situacao.EM_ANDAMENTO;
        this.cliente = cliente;
        this.livros = livros;
    }

    public Locacao(LocalDateTime dataLocacao, LocalDateTime dataPrevistaDevolucao, LocalDateTime dataDevolucao, Cliente cliente, List<Livro> livros) {
        this();
        this.dataLocacao = dataLocacao;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
        this.dataDevolucao = dataDevolucao;
        this.situacao = Situacao.EM_ANDAMENTO;
        this.cliente = cliente;
        this.livros = livros;
    }

    public void fazerDevolucao(LocacaoAtualizarDTO locacaoAtualizarDTO) {
        this.dataDevolucao = locacaoAtualizarDTO.getDataDevolucao();
        this.situacao = locacaoAtualizarDTO.isEmAtraso(this.dataPrevistaDevolucao) ?
                Situacao.DEVOLVIDO_COM_ATRASO : Situacao.DEVOLVIDO;
    }

    public String getClienteNome() {
        return this.cliente.getNome();
    }

}
