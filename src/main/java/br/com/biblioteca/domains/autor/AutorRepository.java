package br.com.biblioteca.domains.autor;

import br.com.biblioteca.domains.autor.dto.AutorListaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AutorRepository {

    Page<AutorListaDTO> findByPage(String filter, Pageable pageable);

    Autor save(Autor autor);

    Autor findById(UUID id);

}
