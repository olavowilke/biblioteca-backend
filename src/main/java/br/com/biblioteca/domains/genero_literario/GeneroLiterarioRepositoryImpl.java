package br.com.biblioteca.domains.genero_literario;

import br.com.biblioteca.domains.genero_literario.dto.GeneroLiterarioListaDTO;
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
class GeneroLiterarioRepositoryImpl implements GeneroLiterarioRepository {

    private static final String GENERO_NAO_ENCONTRADO = "GÊNERO NÃO ENCONTRADO";

    private final GeneroLiterarioRepositoryJpa generoLiterarioRepositoryJpa;

    @Override
    public GeneroLiterario save(GeneroLiterario generoLiterario) {
        return generoLiterarioRepositoryJpa.save(generoLiterario);
    }

    @Override
    public GeneroLiterario findById(UUID id) {
        Optional<GeneroLiterario> generoLiterarioOptional = generoLiterarioRepositoryJpa.findById(id);
        return generoLiterarioOptional.orElseThrow(() -> new EntityNotFoundException(GENERO_NAO_ENCONTRADO));
    }

    @Override
    public Page<GeneroLiterarioListaDTO> findByPage(String filter, Pageable pageable) {
        return generoLiterarioRepositoryJpa.findByPage(filter, pageable);
    }

    @Override
    public List<GeneroLiterario> saveAll(Collection<GeneroLiterario> generosLiterarios) {
        return generoLiterarioRepositoryJpa.saveAll(generosLiterarios);
    }

    @Override
    public List<DropdownDTO> findForDropdown() {
        return generoLiterarioRepositoryJpa.findForDropdown();
    }

}
