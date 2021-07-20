package br.com.biblioteca.domains.editora;

import br.com.biblioteca.domains.editora.dto.EditoraByIdDTO;
import br.com.biblioteca.domains.editora.dto.EditoraCriarAtualizarDTO;
import br.com.biblioteca.domains.editora.dto.EditoraListaDTO;
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
@RequestMapping("/editoras")
@RequiredArgsConstructor
public class EditoraController {

    private final EditoraService editoraService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Editora> criar(@Valid @RequestBody EditoraCriarAtualizarDTO editoraCriarDTO) {
        Editora editoraSalva = editoraService.criar(editoraCriarDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(editoraSalva.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") UUID id, @Valid @RequestBody EditoraCriarAtualizarDTO editoraAtualizarDTO) {
        editoraService.atualizar(id, editoraAtualizarDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") UUID id) {
        editoraService.deleteById(id);
    }

    @GetMapping("/{id}")
    public EditoraByIdDTO findById(@PathVariable("id") UUID id) {
        return editoraService.findById(id);
    }

    @GetMapping
    public Page<EditoraListaDTO> findByPage(@RequestParam(value = "filter", defaultValue = "") String filter, FilterPageable filterPageable) {
        return editoraService.findByPage(filter.toUpperCase(), filterPageable.listByPage());
    }

    @GetMapping("/dropdown")
    public List<DropdownDTO> findForDropdown() {
        return editoraService.findForDropdown();
    }

}
