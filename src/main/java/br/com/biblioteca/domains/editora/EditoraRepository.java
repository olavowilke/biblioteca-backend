package br.com.biblioteca.domains.editora;

import br.com.biblioteca.domains.editora.dto.EditoraListaDTO;
import br.com.biblioteca.util.DropdownDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface EditoraRepository {

    Editora save(Editora editora);

    Editora findById(UUID id);

    Page<EditoraListaDTO> findByPage(String filter, Pageable pageable);

    List<Editora> saveAll(Collection<Editora> editoras);

    List<DropdownDTO> findForDropdown();

}
