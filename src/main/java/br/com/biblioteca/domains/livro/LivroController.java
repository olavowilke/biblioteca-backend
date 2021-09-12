package br.com.biblioteca.domains.livro;

import br.com.biblioteca.domains.livro.dto.*;
import br.com.biblioteca.util.FilterPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('LIBCLIVRO')")
    public void removeById(@PathVariable("id") UUID id) {
        livroService.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('LIBCLIVRO')")
    public ResponseEntity<Livro> criar(@Valid @RequestBody LivroCriarDTO livroCriarDTO, Authentication authentication) {
        Livro livroSalvo = livroService.criar(livroCriarDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(livroSalvo.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('LIBRLIVRO')")
    public LivroByIdDTO findById(@PathVariable("id") UUID id) {
        return livroService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('LIBCLIVRO')")
    public void updateLivro(@PathVariable("id") UUID id, @Valid @RequestBody LivroAtualizarDTO livroAtualizarDTO) {
        livroService.atualizar(livroAtualizarDTO, id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('LIBRLIVRO')")
    public Page<LivroListaDTO> findByPage(@RequestParam(value = "filter", defaultValue = "") String filter, FilterPageable filterPageable) {
        return livroService.findByPage(filter.toUpperCase(), filterPageable.listByPage());
    }

    @GetMapping("/isbn/{isbn}")
    public LivroByIsbnDTO findByIsbn(@PathVariable("isbn") String isbn) {
        return livroService.findByIsbn(isbn);
    }

}
