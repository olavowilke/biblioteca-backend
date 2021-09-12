package br.com.biblioteca.domains.estoque;

import br.com.biblioteca.domains.estoque.dto.EstoqueListaDTO;
import br.com.biblioteca.util.FilterPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estoque")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueService estoqueService;

    @GetMapping
    @PreAuthorize("hasAuthority('LIBRESTOQUE')")
    public Page<EstoqueListaDTO> findByPage(@RequestParam(value = "filter", defaultValue = "") String filter, FilterPageable filterPageable) {
        return estoqueService.findByPage(filter.toUpperCase(), filterPageable.listByPage());
    }

}
