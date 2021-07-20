package br.com.biblioteca.domains.cliente;

import br.com.biblioteca.domains.cliente.dto.ClienteAtualizarDTO;
import br.com.biblioteca.domains.cliente.dto.ClienteByIdDTO;
import br.com.biblioteca.domains.cliente.dto.ClienteCriarDTO;
import br.com.biblioteca.domains.cliente.dto.ClienteListaDTO;
import br.com.biblioteca.exception_handler.exception.CpfAlreadyUsedException;
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

    private static final String CPF_EM_USO = "CPF EM USO";

    public Cliente criar(ClienteCriarDTO clienteCriarDTO) {
        Cliente cliente = new Cliente(clienteCriarDTO);
        validarCpf(cliente.getCpf());
        return clienteRepository.save(cliente);
    }

    private void validarCpf(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf);
        if (cliente != null) {
            throw new CpfAlreadyUsedException(CPF_EM_USO);
        }
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

}
