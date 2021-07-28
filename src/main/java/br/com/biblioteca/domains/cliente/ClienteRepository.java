package br.com.biblioteca.domains.cliente;

import br.com.biblioteca.domains.cliente.dto.ClienteListaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ClienteRepository {

    Cliente findById(UUID id);

    Cliente save(Cliente cliente);

    Page<ClienteListaDTO> findByPage(String filter, Pageable pageable);

    Cliente findByCpf(String cpf);

    List<Cliente> saveAll(Collection<Cliente> clientes);

}
