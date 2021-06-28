package br.com.biblioteca.domains.cliente;

import br.com.biblioteca.domains.cliente.dto.EnderecoDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "tb_endereco")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Endereco {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private String logradouro;
    private String numero;
    private String cep;
    private String cidade;
    private String estado;
    private String complemento;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Endereco() {
        this.id = UUID.randomUUID();
    }

    public Endereco(EnderecoDTO enderecoDTO) {
        this();
        this.logradouro = enderecoDTO.getLogradouro();
        this.numero = enderecoDTO.getNumero();
        this.cep = enderecoDTO.getCep();
        this.cidade = enderecoDTO.getCidade();
        this.estado = enderecoDTO.getEstado();
        this.complemento = enderecoDTO.getComplemento();
    }

    public Endereco(String logradouro, String numero, String cep, String cidade, String estado, String complemento) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.complemento = complemento;
    }

}
