package br.com.biblioteca.domains.estoque;

import br.com.biblioteca.domains.estoque.dto.EstoqueListaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
class EstoqueRepositoryImpl implements EstoqueRepository {

    private final EstoqueRepositoryJpa estoqueRepositoryJpa;

    @Override
    public List<Estoque> findByLivroIdInOrderByLivroNomeAsc(List<UUID> livroIds) {
        return estoqueRepositoryJpa.findByLivroIdIn(livroIds);
    }

    @Override
    public List<Estoque> saveAll(List<Estoque> estoques) {
        return estoqueRepositoryJpa.saveAll(estoques);
    }

    @Override
    public Estoque save(Estoque estoque) {
        return estoqueRepositoryJpa.save(estoque);
    }

    @Override
    public Page<EstoqueListaDTO> findByPage(String filter, Pageable pageable) {
        return estoqueRepositoryJpa.findByPage(filter, pageable);
    }

    @Override
    public Estoque findByLivroId(UUID id) {
        return estoqueRepositoryJpa.findByLivroId(id);
    }

}
