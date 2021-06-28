package br.com.biblioteca.domains.cliente.dto;

import br.com.biblioteca.domains.cliente.Telefone;
import br.com.biblioteca.domains.cliente.TipoTelefone;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class TelefoneDTO {

    @NotBlank
    private String ddd;

    @NotBlank
    private String numero;

    @NotNull
    private TipoTelefone tipoTelefone;

    public TelefoneDTO(Telefone telefone) {
        this.ddd = telefone.getDdd();
        this.numero = telefone.getNumero();
        this.tipoTelefone = telefone.getTipo();
    }

}
