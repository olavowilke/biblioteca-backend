package br.com.biblioteca.domains.cliente;

import br.com.biblioteca.domains.cliente.dto.ClienteListaDTO;
import br.com.biblioteca.exception_handler.exception.CpfAlreadyUsedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
class ClienteRepositoryImpl implements ClienteRepository {

    private static final String CLIENTE_NAO_ENCONTRADO = "CLIENTE NÃO ENCONTRADO";
    private static final String CPF_EM_USO = "CPF EM USO";

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
        Optional<Cliente> clienteOptional = clienteRepositoryJpa.findByCpf(cpf);
        return clienteOptional.orElseThrow(() -> new EntityNotFoundException(CLIENTE_NAO_ENCONTRADO));
    }

    @Override
    public List<Cliente> saveAll(Collection<Cliente> clientes) {
        return clienteRepositoryJpa.saveAll(clientes);
    }

    @Override
    public void findByCpfParaValidar(String cpf) {
        Optional<Cliente> clienteOptional = clienteRepositoryJpa.findByCpf(cpf);
        if (clienteOptional.isPresent()) {
            throw new CpfAlreadyUsedException(CPF_EM_USO);
        }
    }

}
