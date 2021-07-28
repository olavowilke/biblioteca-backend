package br.com.biblioteca.domains.livro;

import br.com.biblioteca.domains.livro.dto.LivroListaDTO;
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
class LivroRepositoryImpl implements LivroRepository {

    private static final String LIVRO_NAO_ENCONTRADO = "LIVRO NÃO ENCONTRADO";

    private final LivroRepositoryJpa livroRepositoryJpa;

    @Override
    public Livro save(Livro livro) {
        return livroRepositoryJpa.save(livro);
    }

    @Override
    public Page<LivroListaDTO> findByPage(String filter, Pageable pageable) {
        return livroRepositoryJpa.findByPage(filter, pageable);
    }

    @Override
    public List<Livro> saveAll(Collection<Livro> livros) {
        return livroRepositoryJpa.saveAll(livros);
    }

    @Override
    public List<Livro> findAllById(List<UUID> livroId) {
        return livroRepositoryJpa.findAllById(livroId);
    }

    @Override
    public Livro findByIsbn(String isbn) {
        Optional<Livro> livroOptional = livroRepositoryJpa.findByIsbn(isbn);
        return livroOptional.orElseThrow(() -> new EntityNotFoundException(LIVRO_NAO_ENCONTRADO));
    }

    @Override
    public Livro findById(UUID id) {
        Optional<Livro> livroOptional = livroRepositoryJpa.findById(id);
        return livroOptional.orElseThrow(() -> new EntityNotFoundException(LIVRO_NAO_ENCONTRADO));
    }

}
