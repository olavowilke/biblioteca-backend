package br.com.biblioteca.domains.genero_literario;

import br.com.biblioteca.domains.genero_literario.dto.GeneroLiterarioByIdDTO;
import br.com.biblioteca.domains.genero_literario.dto.GeneroLiterarioCriarAtualizarDTO;
import br.com.biblioteca.domains.genero_literario.dto.GeneroLiterarioListaDTO;
import br.com.biblioteca.util.DropdownDTO;
import br.com.biblioteca.util.FilterPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/generos-literarios")
@RequiredArgsConstructor
public class GeneroLiterarioController {

    private final GeneroLiterarioService generoLiterarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GeneroLiterario> criar(
            @Valid @RequestBody GeneroLiterarioCriarAtualizarDTO generoLiterarioCriarDTO) {
        GeneroLiterario generoLiterarioSalvo = generoLiterarioService.criar(generoLiterarioCriarDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(generoLiterarioSalvo.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            @PathVariable("id") UUID id,
            @Valid @RequestBody GeneroLiterarioCriarAtualizarDTO generoLiterarioAtualizarDTO) {
        generoLiterarioService.update(id, generoLiterarioAtualizarDTO);
    }

    @GetMapping("/{id}")
    public GeneroLiterarioByIdDTO findById(@PathVariable("id") UUID id) {
        return generoLiterarioService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") UUID id) {
        generoLiterarioService.delete(id);
    }

    @GetMapping
    public Page<GeneroLiterarioListaDTO> findByPage(@RequestParam(value = "filter", defaultValue = "")
                                                            String filter, FilterPageable filterPageable) {
        return generoLiterarioService.findByPage(filter.toUpperCase(), filterPageable.listByPage());
    }

    @GetMapping("/dropdown")
    public List<DropdownDTO> findForDropdown() {
        return generoLiterarioService.findForDropdown();
    }

}
