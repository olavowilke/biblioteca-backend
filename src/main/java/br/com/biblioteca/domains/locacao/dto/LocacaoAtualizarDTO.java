package br.com.biblioteca.domains.locacao.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class LocacaoAtualizarDTO {

    @NotNull
    private LocalDateTime dataDevolucao;

    public boolean isEmAtraso(LocalDateTime dataPrevistaDevolucao) {
        return this.dataDevolucao.isAfter(dataPrevistaDevolucao);
    }

}
