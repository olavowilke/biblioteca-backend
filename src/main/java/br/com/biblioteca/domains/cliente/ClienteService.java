package br.com.biblioteca.domains.cliente;

import br.com.biblioteca.domains.cliente.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente criar(ClienteCriarDTO clienteCriarDTO) {
        Cliente cliente = new Cliente(clienteCriarDTO);
        validarCpf(cliente.getCpf());
        return clienteRepository.save(cliente);
    }

    private void validarCpf(String cpf) {
        clienteRepository.findByCpfParaValidar(cpf);
    }

    public void update(UUID id, ClienteAtualizarDTO clienteAtualizarDTO) {
        Cliente cliente = clienteRepository.findById(id);
        cliente.update(clienteAtualizarDTO);
        clienteRepository.save(cliente);
    }

    public Page<ClienteListaDTO> findByPage(String filter, Pageable pageable) {
        return clienteRepository.findByPage(filter, pageable);
    }

    public ClienteByIdDTO findById(UUID id) {
        Cliente cliente = clienteRepository.findById(id);
        return new ClienteByIdDTO(cliente);
    }

    public void deleteById(UUID id) {
        Cliente cliente = clienteRepository.findById(id);
        cliente.delete();
        clienteRepository.save(cliente);
    }

    public ClienteByCpfDTO findByCpf(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf);
        return new ClienteByCpfDTO(cliente);
    }

}
