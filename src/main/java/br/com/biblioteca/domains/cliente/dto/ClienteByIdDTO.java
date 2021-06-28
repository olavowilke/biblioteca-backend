package br.com.biblioteca.domains.cliente.dto;

import br.com.biblioteca.domains.cliente.Cliente;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ClienteByIdDTO {

    private UUID id;
    private String nome;
    private String cpf;
    private TelefoneDTO telefone;
    private EnderecoDTO endereco;
    private LocalDate dataNascimento;

    public ClienteByIdDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.telefone = new TelefoneDTO(cliente.getTelefone());
        this.dataNascimento = cliente.getDataNascimento();
        this.endereco = new EnderecoDTO(cliente.getEndereco());
    }

}
