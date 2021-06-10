package br.com.biblioteca.domains.autor;

import br.com.biblioteca.domains.autor.dto.AutorByIdDTO;
import br.com.biblioteca.domains.autor.dto.AutorCriarAtualizarDTO;
import br.com.biblioteca.domains.autor.dto.AutorListaDTO;
import br.com.biblioteca.util.FilterPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Autor> criar(@Valid @RequestBody AutorCriarAtualizarDTO autorCriarDTO) {
        Autor autorSalvo = autorService.criar(autorCriarDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorSalvo.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") UUID id, @Valid @RequestBody AutorCriarAtualizarDTO autorCriarAtualizarDTO) {
        autorService.update(id, autorCriarAtualizarDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerPorId(@PathVariable("id") UUID id) {
        autorService.deleteById(id);
    }

    @GetMapping
    public Page<AutorListaDTO> findByPage(@RequestParam(value = "filter", defaultValue = "") String filter, FilterPageable filterPageable) {
        return autorService.findByPage(filter.toUpperCase(), filterPageable.listByPage());
    }

    @GetMapping("/{id}")
    public AutorByIdDTO findById(@PathVariable("id") UUID id) {
        return autorService.findById(id);
    }

}
