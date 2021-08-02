package br.com.biblioteca.domains.estoque;

import br.com.biblioteca.domains.estoque.dto.EstoqueListaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

interface EstoqueRepositoryJpa extends JpaRepository<Estoque, UUID> {

    List<Estoque> findByLivroIdIn(List<UUID> livroIds);

    @Query("SELECT estoque from Estoque estoque " +
            "JOIN estoque.livro livro ON livro.deletedAt IS NULL " +
            "WHERE UPPER(livro.titulo) LIKE %:filter%")
    Page<EstoqueListaDTO> findByPage(String filter, Pageable pageable);

    Estoque findByLivroId(UUID id);
}
