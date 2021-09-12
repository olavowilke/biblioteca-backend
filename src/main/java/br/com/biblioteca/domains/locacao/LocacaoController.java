package br.com.biblioteca.domains.locacao;

import br.com.biblioteca.domains.locacao.dto.LocacaoAtualizarDTO;
import br.com.biblioteca.domains.locacao.dto.LocacaoCriarDTO;
import br.com.biblioteca.domains.locacao.dto.LocacaoListaDTO;
import br.com.biblioteca.util.FilterPageable;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/locacoes")
@RequiredArgsConstructor
public class LocacaoController {

    private final LocacaoService locacaoService;

    @ApiOperation("Cria uma Locação, à partir de um DTO")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('LIBCLOCACAO')")
    public ResponseEntity<Locacao> criar(@Valid @RequestBody LocacaoCriarDTO locacaoCriarDTO) {
        Locacao locacao = locacaoService.criar(locacaoCriarDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(locacao.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation("Atualiza Locação, no momento da devolução")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('LIBCLOCACAO')")
    public void atualizar(@PathVariable("id") UUID id,
                          @Valid @RequestBody LocacaoAtualizarDTO locacaoAtualizarDTO) {
        locacaoService.atualizar(id, locacaoAtualizarDTO);
    }

    @ApiOperation("Realiza busca paginada")
    @GetMapping
    @PreAuthorize("hasAuthority('LIBRLOCACAO')")
    public Page<LocacaoListaDTO> findByPage(@RequestParam(value = "filter", defaultValue = "") String filter, FilterPageable filterPageable) {
        return locacaoService.findByPage(filter.toUpperCase(), filterPageable.listByPage());
    }

}
