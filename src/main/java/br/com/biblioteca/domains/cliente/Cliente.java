package br.com.biblioteca.domains.cliente;

import br.com.biblioteca.domains.cliente.dto.ClienteCriarAtualizarDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_cliente")
@Where(clause = "deleted_at IS NULL")
public class Cliente {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private String nome;
    private String cpf;
    private LocalDate dataNascimento;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Telefone telefone;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Endereco endereco;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Cliente() {
        this.id = UUID.randomUUID();
    }

    public Cliente(ClienteCriarAtualizarDTO clienteCriarAtualizarDTO) {
        this();
        this.nome = clienteCriarAtualizarDTO.getNome();
        this.cpf = clienteCriarAtualizarDTO.getCpf();
        this.telefone = new Telefone(clienteCriarAtualizarDTO.getTelefone());
        this.endereco = new Endereco(clienteCriarAtualizarDTO.getEndereco());
        this.dataNascimento = clienteCriarAtualizarDTO.getDataNascimento();
    }

    public Cliente(String nome, String cpf, LocalDate dataNascimento, Telefone telefone, Endereco endereco) {
        this();
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public void update(ClienteCriarAtualizarDTO clienteCriarAtualizarDTO) {
        this.nome = clienteCriarAtualizarDTO.getNome();
        this.cpf = clienteCriarAtualizarDTO.getCpf();
        this.dataNascimento = clienteCriarAtualizarDTO.getDataNascimento();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public String getTelefoneCompleto() {
        return telefone.getTelefoneCompleto();
    }

}
