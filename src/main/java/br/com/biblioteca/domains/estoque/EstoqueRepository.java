package br.com.biblioteca.domains.estoque;

import br.com.biblioteca.domains.estoque.dto.EstoqueListaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface EstoqueRepository {

    List<Estoque> findByLivroIdInOrderByLivroNomeAsc(List<UUID> livroIds);

    List<Estoque> saveAll(List<Estoque> estoques);

    Estoque save(Estoque estoque);

    Page<EstoqueListaDTO> findByPage(String filter, Pageable pageable);

}
