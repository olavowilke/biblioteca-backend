package br.com.biblioteca.domains.cliente.dto;

import br.com.biblioteca.domains.cliente.Cliente;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ClienteByCpfDTO {

    private UUID id;
    private String cpf;
    private String nome;
    private LocalDate dataNascimento;

    public ClienteByCpfDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.cpf = cliente.getCpf();
        this.nome = cliente.getNome();
        this.dataNascimento = cliente.getDataNascimento();
    }

}
