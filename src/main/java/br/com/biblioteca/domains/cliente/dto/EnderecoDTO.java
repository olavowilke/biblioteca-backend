package br.com.biblioteca.domains.cliente.dto;


import br.com.biblioteca.domains.cliente.Endereco;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@Validated
public class EnderecoDTO {

    @NotBlank
    private String logradouro;

    @NotBlank
    private String numero;

    @NotBlank
    private String cep;

    @NotBlank
    private String cidade;

    @Size(max = 2)
    @NotBlank
    private String estado;

    @Size(max = 200)
    private String complemento;

    public EnderecoDTO(Endereco endereco) {
        this.logradouro = endereco.getLogradouro();
        this.numero = endereco.getNumero();
        this.cep = endereco.getCep();
        this.cidade = endereco.getCidade();
        this.estado = endereco.getEstado();
        this.complemento = endereco.getComplemento();
    }

}
