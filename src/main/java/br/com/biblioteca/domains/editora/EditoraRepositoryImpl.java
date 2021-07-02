package br.com.biblioteca.domains.editora;

import br.com.biblioteca.domains.editora.dto.EditoraListaDTO;
import br.com.biblioteca.util.DropdownDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
class EditoraRepositoryImpl implements EditoraRepository {

    private static final String EDITORA_NAO_ENCONTRADA = "EDITORA NÃO ENCONTRADA";

    private final EditoraRepositoryJpa editoraRepositoryJpa;

    @Override
    public Editora save(Editora editora) {
        return editoraRepositoryJpa.save(editora);
    }

    @Override
    public Editora findById(UUID id) {
        Optional<Editora> editoraOptional = editoraRepositoryJpa.findById(id);
        return editoraOptional.orElseThrow(() -> new EntityNotFoundException(EDITORA_NAO_ENCONTRADA));
    }

    @Override
    public Page<EditoraListaDTO> findByPage(String filter, Pageable pageable) {
        return editoraRepositoryJpa.findByPage(filter, pageable);
    }

    @Override
    public List<Editora> saveAll(Collection<Editora> editoras) {
        return editoraRepositoryJpa.saveAll(editoras);
    }

    @Override
    public List<DropdownDTO> findForDropdown() {
        return editoraRepositoryJpa.findForDropdown();
    }

}
