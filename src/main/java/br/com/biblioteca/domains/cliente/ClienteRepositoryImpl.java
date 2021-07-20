package br.com.biblioteca.domains.cliente;

import br.com.biblioteca.domains.cliente.dto.ClienteListaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
class ClienteRepositoryImpl implements ClienteRepository {

    private static final String CLIENTE_NAO_ENCONTRADO = "CLIENTE NÃO ENCONTRADO";

    private final ClienteRepositoryJpa clienteRepositoryJpa;

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepositoryJpa.save(cliente);
    }

    @Override
    public Cliente findById(UUID id) {
        Optional<Cliente> clienteOptional = clienteRepositoryJpa.findById(id);
        return clienteOptional.orElseThrow(() -> new EntityNotFoundException(CLIENTE_NAO_ENCONTRADO));
    }

    @Override
    public Page<ClienteListaDTO> findByPage(String filter, Pageable pageable) {
        return clienteRepositoryJpa.findByPage(filter, pageable);
    }

    @Override
    public Cliente findByCpf(String cpf) {
        return clienteRepositoryJpa.findByCpf(cpf);
    }

}
