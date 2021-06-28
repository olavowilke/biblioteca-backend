package br.com.biblioteca.domains.autor;

import br.com.biblioteca.domains.autor.dto.AutorListaDTO;
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
class AutorRepositoryImpl implements AutorRepository {

    private static final String AUTOR_NAO_ENCONTRADO = "AUTOR NÃO ENCONTRADO";

    private final AutorRepositoryJpa autorRepositoryJpa;

    @Override
    public Autor save(Autor autor) {
        return autorRepositoryJpa.save(autor);
    }

    @Override
    public Autor findById(UUID id) {
        Optional<Autor> autorOptional = autorRepositoryJpa.findById(id);
        return autorOptional.orElseThrow(() -> new EntityNotFoundException(AUTOR_NAO_ENCONTRADO));
    }

    @Override
    public List<Autor> saveAll(Collection<Autor> autores) {
        return autorRepositoryJpa.saveAll(autores);
    }

    @Override
    public Page<AutorListaDTO> findByPage(String filter, Pageable pageable) {
        return autorRepositoryJpa.findByPage(filter, pageable);
    }

}
