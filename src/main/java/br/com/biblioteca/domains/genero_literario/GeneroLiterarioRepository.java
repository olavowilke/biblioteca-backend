package br.com.biblioteca.domains.genero_literario;

import br.com.biblioteca.domains.genero_literario.dto.GeneroLiterarioListaDTO;
import br.com.biblioteca.util.DropdownDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface GeneroLiterarioRepository {

    GeneroLiterario save(GeneroLiterario generoLiterario);

    GeneroLiterario findById(UUID id);

    Page<GeneroLiterarioListaDTO> findByPage(String filter, Pageable pageable);

    List<GeneroLiterario> saveAll(Collection<GeneroLiterario> generosLiterarios);

    List<DropdownDTO> findForDropdown();

}
