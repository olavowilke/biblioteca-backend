package br.com.biblioteca.domains.autor;

import br.com.biblioteca.domains.autor.dto.AutorByIdDTO;
import br.com.biblioteca.domains.autor.dto.AutorCriarAtualizarDTO;
import br.com.biblioteca.domains.autor.dto.AutorListaDTO;
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
public class AutorService {

    private final AutorRepository autorRepository;

    public Autor criar(AutorCriarAtualizarDTO autorCriarAtualizarDTO) {
        Autor autor = new Autor(autorCriarAtualizarDTO);
        return autorRepository.save(autor);
    }

    public void update(UUID id, AutorCriarAtualizarDTO autorCriarAtualizarDTO) {
        Autor autor = autorRepository.findById(id);
        autor.atualizarPorId(autorCriarAtualizarDTO);
        autorRepository.save(autor);
    }

    public Page<AutorListaDTO> findByPage(String filter, Pageable pageable) {
        return autorRepository.findByPage(filter, pageable);
    }

    public AutorByIdDTO findById(UUID id) {
        Autor autor = autorRepository.findById(id);
        return new AutorByIdDTO(autor);
    }

    public void deleteById(UUID id) {
        Autor autor = autorRepository.findById(id);
        autor.delete();
        autorRepository.save(autor);
    }

    public List<DropdownDTO> findForDropdown() {
        return autorRepository.findForDropdown();
    }

}
