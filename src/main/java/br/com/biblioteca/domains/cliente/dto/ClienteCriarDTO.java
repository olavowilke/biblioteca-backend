package br.com.biblioteca.domains.cliente.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ClienteCriarDTO {

    @NotBlank
    @Size(max = 100)
    private String nome;

    @CPF
    private String cpf;

    @Valid
    private TelefoneDTO telefone;

    @Valid
    private EnderecoDTO endereco;

    @NotNull
    @Past
    private LocalDate dataNascimento;

}
