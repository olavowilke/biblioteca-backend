package br.com.biblioteca.domains.cliente.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ClienteCriarAtualizarDTO {

    @NotBlank
    @Size(max = 100)
    private String nome;

    @CPF
    @Size(min = 11, max = 11)
    private String cpf;

    @Valid
    private TelefoneDTO telefone;

    @Valid
    private EnderecoDTO endereco;

    @NotNull
    private LocalDate dataNascimento;

}
