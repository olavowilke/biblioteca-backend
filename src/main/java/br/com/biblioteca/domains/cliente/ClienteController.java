package br.com.biblioteca.domains.cliente;

import br.com.biblioteca.domains.cliente.dto.*;
import br.com.biblioteca.util.FilterPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cliente> criar(@Valid @RequestBody ClienteCriarDTO clienteCriarDTO) {
        Cliente clienteSalvo = clienteService.criar(clienteCriarDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clienteSalvo.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") UUID id, @Valid @RequestBody ClienteAtualizarDTO clienteAtualizarDTO) {
        clienteService.update(id, clienteAtualizarDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerPorId(@PathVariable("id") UUID id) {
        clienteService.deleteById(id);
    }

    @GetMapping("/{id}")
    public ClienteByIdDTO findById(@PathVariable("id") UUID id) {
        return clienteService.findById(id);
    }

    @GetMapping("/cpf/{cpf}")
    public ClienteByCpfDTO findByCpf(@PathVariable("cpf") String cpf) {
        return clienteService.findByCpf(cpf);
    }

    @GetMapping
    public Page<ClienteListaDTO> findByPage(@RequestParam(value = "filter", defaultValue = "") String filter, FilterPageable filterPageable) {
        return clienteService.findByPage(filter.toUpperCase(), filterPageable.listByPage());
    }

}
