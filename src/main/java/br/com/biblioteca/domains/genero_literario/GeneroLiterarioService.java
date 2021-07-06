package br.com.biblioteca.domains.genero_literario;

import br.com.biblioteca.domains.genero_literario.dto.GeneroLiterarioByIdDTO;
import br.com.biblioteca.domains.genero_literario.dto.GeneroLiterarioCriarAtualizarDTO;
import br.com.biblioteca.domains.genero_literario.dto.GeneroLiterarioListaDTO;
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
public class GeneroLiterarioService {

    private final GeneroLiterarioRepository generoLiterarioRepository;

    public GeneroLiterario criar(GeneroLiterarioCriarAtualizarDTO generoLiterarioCriarDTO) {
        GeneroLiterario generoLiterario = new GeneroLiterario(generoLiterarioCriarDTO);
        return generoLiterarioRepository.save(generoLiterario);
    }

    public void update(UUID id, GeneroLiterarioCriarAtualizarDTO generoLiterarioAtualizarDTO) {
        GeneroLiterario generoLiterario = generoLiterarioRepository.findById(id);
        generoLiterario.update(generoLiterarioAtualizarDTO);
        generoLiterarioRepository.save(generoLiterario);
    }

    public GeneroLiterarioByIdDTO findById(UUID id) {
        GeneroLiterario generoLiterario = generoLiterarioRepository.findById(id);
        return new GeneroLiterarioByIdDTO(generoLiterario);
    }

    public Page<GeneroLiterarioListaDTO> findByPage(String filter, Pageable pageable) {
        return generoLiterarioRepository.findByPage(filter, pageable);
    }

    public void delete(UUID id) {
        GeneroLiterario generoLiterario = generoLiterarioRepository.findById(id);
        generoLiterario.delete();
        generoLiterarioRepository.save(generoLiterario);
    }

    public List<DropdownDTO> findForDropdown() {
        return generoLiterarioRepository.findForDropdown();
    }

}
