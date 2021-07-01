package br.com.biblioteca.domains.autor;

import br.com.biblioteca.domains.autor.dto.AutorListaDTO;
import br.com.biblioteca.util.DropdownDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

interface AutorRepositoryJpa extends JpaRepository<Autor, UUID> {

    @Query("SELECT autor from Autor autor " +
            "WHERE UPPER(autor.nome) LIKE %:filter% " +
            "OR UPPER(autor.nacionalidade) LIKE %:filter%")
    Page<AutorListaDTO> findByPage(String filter, Pageable pageable);

    @Query("SELECT autor from Autor autor ")
    List<DropdownDTO> findForDropdown();

}
