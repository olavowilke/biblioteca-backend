package br.com.biblioteca.domains.estoque.dto;

import br.com.biblioteca.domains.estoque.Estoque;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class EstoqueListaDTO {

    private UUID id;
    private String livroTitulo;
    private BigDecimal quantidadeDisponivel;

    public EstoqueListaDTO(Estoque estoque) {
        this.id = estoque.getId();
        this.livroTitulo = estoque.getLivroTitulo();
        this.quantidadeDisponivel = estoque.getQuantidadeDisponivel();
    }

}
