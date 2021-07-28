package br.com.biblioteca.domains.locacao.dto;

import br.com.biblioteca.domains.locacao.Locacao;
import br.com.biblioteca.domains.locacao.Situacao;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class LocacaoListaDTO {

    private UUID id;
    private LocalDateTime dataLocacao;
    private LocalDateTime dataPrevistaDevolucao;
    private LocalDateTime dataDevolucao;
    private Situacao situacao;
    private String clienteNome;

    public LocacaoListaDTO(Locacao locacao) {
        this.id = locacao.getId();
        this.dataLocacao = locacao.getDataLocacao();
        this.dataPrevistaDevolucao = locacao.getDataPrevistaDevolucao();
        this.dataDevolucao = locacao.getDataDevolucao();
        this.situacao = locacao.getSituacao();
        this.clienteNome = locacao.getClienteNome();
    }

}
