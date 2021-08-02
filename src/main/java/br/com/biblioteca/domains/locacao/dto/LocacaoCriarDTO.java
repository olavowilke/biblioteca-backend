package br.com.biblioteca.domains.locacao.dto;

import lombok.Getter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class LocacaoCriarDTO {

    @NotNull
    @PastOrPresent
    private LocalDateTime dataLocacao;

    @NotNull
    @Future
    private LocalDateTime dataPrevistaDevolucao;

    @NotNull
    private UUID clienteId;

    @NotEmpty
    private List<UUID> livroIds;

}
