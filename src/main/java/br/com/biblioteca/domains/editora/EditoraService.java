package br.com.biblioteca.domains.editora;

import br.com.biblioteca.domains.editora.dto.EditoraByIdDTO;
import br.com.biblioteca.domains.editora.dto.EditoraCriarAtualizarDTO;
import br.com.biblioteca.domains.editora.dto.EditoraListaDTO;
import br.com.biblioteca.util.DropdownDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class EditoraService {

    private final EditoraRepository editoraRepository;

    public Editora criar(EditoraCriarAtualizarDTO editoraCriarDTO) {
        Editora editora = new Editora(editoraCriarDTO);
        return editoraRepository.save(editora);
    }

    public void update(UUID id, EditoraCriarAtualizarDTO editoraAtualizarDTO) {
        Editora editora = editoraRepository.findById(id);
        editora.update(editoraAtualizarDTO);
        editoraRepository.save(editora);
    }

    public EditoraByIdDTO findById(UUID id) {
        Editora editora = editoraRepository.findById(id);
        return new EditoraByIdDTO(editora);
    }

    public void deleteById(UUID id) {
        Editora editora = editoraRepository.findById(id);
        editora.delete();
        editoraRepository.save(editora);
    }

    public Page<EditoraListaDTO> findByPage(String filter, Pageable pageable) {
        return editoraRepository.findByPage(filter, pageable);
    }

    public List<DropdownDTO> findForDropdown() {
        return editoraRepository.findForDropdown();
    }

}
