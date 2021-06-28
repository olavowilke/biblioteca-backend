package br.com.biblioteca.domains.livro;

import br.com.biblioteca.domains.livro.dto.LivroListaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface LivroRepository {

    Livro findById(UUID id);

    Livro save(Livro livro);

    Page<LivroListaDTO> findByPage(String filter, Pageable pageable);

    List<Livro> saveAll(Collection<Livro> livros);

}
