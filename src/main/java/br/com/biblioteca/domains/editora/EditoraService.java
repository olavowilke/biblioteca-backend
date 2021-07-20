package br.com.biblioteca.domains.editora;

import br.com.biblioteca.domains.editora.dto.EditoraByIdDTO;
import br.com.biblioteca.domains.editora.dto.EditoraCriarAtualizarDTO;
import br.com.biblioteca.domains.editora.dto.EditoraListaDTO;
import br.com.biblioteca.exception_handler.exception.NomeAlreadyUsedException;
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

    private static final String NOME_EM_USO = "NOME EM USO";

    public Editora criar(EditoraCriarAtualizarDTO editoraCriarDTO) {
        Editora editora = new Editora(editoraCriarDTO);
        validarNomeCriar(editora.getNome());
        return editoraRepository.save(editora);
    }

    private void validarNomeCriar(String nome) {
        Editora editora = editoraRepository.findByNome(nome);
        validarNomeExistente(editora);
    }

    public void atualizar(UUID id, EditoraCriarAtualizarDTO editoraAtualizarDTO) {
        Editora editora = editoraRepository.findById(id);
        validarNomeAtualizar(id, editoraAtualizarDTO.getNome());
        editora.update(editoraAtualizarDTO);
        editoraRepository.save(editora);
    }

    private void validarNomeAtualizar(UUID id, String nome) {
        Editora editora = editoraRepository.findByNomeAndIdNot(nome, id);
        validarNomeExistente(editora);
    }

    private void validarNomeExistente(Editora editora) {
        if (editora != null) {
            throw new NomeAlreadyUsedException(NOME_EM_USO);
        }
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
