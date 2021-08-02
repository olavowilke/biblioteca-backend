package br.com.biblioteca.domains.locacao.dto;

import lombok.Getter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class LocacaoAtualizarDTO {

    @NotNull
    @Future
    private LocalDateTime dataDevolucao;

    public boolean isEmAtraso(LocalDateTime dataPrevistaDevolucao) {
        return this.dataDevolucao.isAfter(dataPrevistaDevolucao);
    }

}
