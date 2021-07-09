package br.com.biblioteca.domains.cliente.dto;

import br.com.biblioteca.domains.cliente.Telefone;
import br.com.biblioteca.domains.cliente.TipoTelefone;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class TelefoneDTO {

    @NotBlank
    @Size(max = 2)
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
