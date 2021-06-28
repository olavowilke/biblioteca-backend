package br.com.biblioteca.domains.livro;

import br.com.biblioteca.domains.livro.dto.LivroListaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

interface LivroRepositoryJpa extends JpaRepository<Livro, UUID> {

    @Query("SELECT new br.com.biblioteca.domains.livro.dto.LivroListaDTO(livro, autor) FROM Livro livro " +
            "JOIN livro.autor autor ON autor.deletedAt IS NULL " +
            "WHERE UPPER(livro.titulo) LIKE %:filter% " +
            "OR UPPER(livro.editora) LIKE %:filter% " +
            "OR UPPER(livro.generoLiterario) LIKE %:filter% " +
            "OR UPPER(autor.nome) LIKE %:filter%")
    Page<LivroListaDTO> findByPage(String filter, Pageable pageable);

}
