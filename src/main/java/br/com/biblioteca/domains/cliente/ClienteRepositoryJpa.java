package br.com.biblioteca.domains.cliente;

import br.com.biblioteca.domains.cliente.dto.ClienteListaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

interface ClienteRepositoryJpa extends JpaRepository<Cliente, UUID> {

    Cliente findByCpf(String cpf);

    @Query("SELECT cliente from Cliente cliente " +
            "WHERE UPPER(cliente.nome) LIKE %:filter% " +
            "OR cliente.cpf LIKE %:filter%")
    Page<ClienteListaDTO> findByPage(String filter, Pageable pageable);

}
