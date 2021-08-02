package br.com.biblioteca.domains.estoque;

import br.com.biblioteca.domains.livro.Livro;
import br.com.biblioteca.exception_handler.exception.LivroIndisponivelException;
import br.com.biblioteca.util.BigDecimalUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_estoque")
public class Estoque {

    public static final String ESTOQUE_INDISPONIVEL = "LIVRO INDISPONÍVEL NO ESTOQUE";

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Livro livro;

    private BigDecimal quantidadeDisponivel;

    public Estoque() {
        this.id = UUID.randomUUID();
    }

    public Estoque(Livro livro, BigDecimal quantidadeDisponivel) {
        this();
        this.livro = livro;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public void retirar() {
        if (BigDecimalUtil.isEqual(this.quantidadeDisponivel, BigDecimal.ZERO)) {
            throw new LivroIndisponivelException(ESTOQUE_INDISPONIVEL);
        }
        this.quantidadeDisponivel = this.quantidadeDisponivel.subtract(BigDecimal.ONE);
    }

    public void devolver() {
        this.quantidadeDisponivel = this.quantidadeDisponivel.add(BigDecimal.ONE);
    }

    public String getLivroTitulo() {
        return this.livro.getTitulo();
    }

}
