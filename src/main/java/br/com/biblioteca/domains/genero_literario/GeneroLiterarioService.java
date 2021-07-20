package br.com.biblioteca.domains.genero_literario;

import br.com.biblioteca.domains.genero_literario.dto.GeneroLiterarioByIdDTO;
import br.com.biblioteca.domains.genero_literario.dto.GeneroLiterarioCriarAtualizarDTO;
import br.com.biblioteca.domains.genero_literario.dto.GeneroLiterarioListaDTO;
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
public class GeneroLiterarioService {

    private final GeneroLiterarioRepository generoLiterarioRepository;

    private static final String NOME_EM_USO = "NOME EM USO";

    public GeneroLiterario criar(GeneroLiterarioCriarAtualizarDTO generoLiterarioCriarDTO) {
        GeneroLiterario generoLiterario = new GeneroLiterario(generoLiterarioCriarDTO);
        validarNomeCriar(generoLiterario.getNome());
        return generoLiterarioRepository.save(generoLiterario);
    }

    private void validarNomeCriar(String nome) {
        GeneroLiterario generoLiterario = generoLiterarioRepository.findByNome(nome);
        if (generoLiterario != null) {
            throw new NomeAlreadyUsedException(NOME_EM_USO);
        }
    }

    public void atualizar(UUID id, GeneroLiterarioCriarAtualizarDTO generoLiterarioAtualizarDTO) {
        GeneroLiterario generoLiterario = generoLiterarioRepository.findById(id);
        validarNomeAtualizar(id, generoLiterarioAtualizarDTO.getNome());
        generoLiterario.update(generoLiterarioAtualizarDTO);
        generoLiterarioRepository.save(generoLiterario);
    }

    private void validarNomeAtualizar(UUID id, String nome) {
        GeneroLiterario generoLiterario = generoLiterarioRepository.findByNomeAndIdNot(nome, id);
        if (generoLiterario != null) {
            throw new NomeAlreadyUsedException(NOME_EM_USO);
        }
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
