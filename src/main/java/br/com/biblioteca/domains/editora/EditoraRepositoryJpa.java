package br.com.biblioteca.domains.editora;

import br.com.biblioteca.domains.editora.dto.EditoraListaDTO;
import br.com.biblioteca.util.DropdownDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

interface EditoraRepositoryJpa extends JpaRepository<Editora, UUID> {

    @Query("SELECT editora FROM Editora editora " +
            "WHERE UPPER(editora.nome) LIKE %:filter%")
    Page<EditoraListaDTO> findByPage(String filter, Pageable pageable);

    @Query("SELECT editora FROM Editora editora ")
    List<DropdownDTO> findForDropdown();

}
