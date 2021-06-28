package br.com.biblioteca.domains.cliente.dto;

import br.com.biblioteca.domains.cliente.Cliente;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ClienteListaDTO {

    private UUID id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefoneCompleto;

    public ClienteListaDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.dataNascimento = cliente.getDataNascimento();
        this.telefoneCompleto = cliente.getTelefoneCompleto();
    }

}
