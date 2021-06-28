package br.com.biblioteca.domains.cliente;

import br.com.biblioteca.domains.cliente.dto.TelefoneDTO;
import br.com.biblioteca.util.PostgreSQLEnumType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@Entity
@Table(name = "tb_telefone")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@Where(clause = "deleted_at IS NULL")
public class Telefone {

    @Id
    private UUID id;

    private String ddd;
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "tipo_telefone_info")
    @Type(type = "pgsql_enum")
    private TipoTelefone tipo;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Telefone() {
        this.id = UUID.randomUUID();
    }

    public Telefone(TelefoneDTO telefoneDTO) {
        this();
        this.ddd = telefoneDTO.getDdd();
        this.numero = telefoneDTO.getNumero();
        this.tipo = telefoneDTO.getTipoTelefone();
    }

    public String getTelefoneCompleto() {
        return ddd + numero;
    }

    public Telefone(String ddd, String numero, TipoTelefone tipo) {
        this.ddd = ddd;
        this.numero = numero;
        this.tipo = tipo;
    }

}
