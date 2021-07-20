package br.com.biblioteca.domains.genero_literario;

import br.com.biblioteca.domains.genero_literario.dto.GeneroLiterarioListaDTO;
import br.com.biblioteca.util.DropdownDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

interface GeneroLiterarioRepositoryJpa extends JpaRepository<GeneroLiterario, UUID> {

    @Query("SELECT generoLiterario FROM GeneroLiterario generoLiterario " +
            "WHERE UPPER(generoLiterario.nome) LIKE %:filter%")
    Page<GeneroLiterarioListaDTO> findByPage(String filter, Pageable pageable);

    @Query("SELECT generoLiterario FROM GeneroLiterario generoLiterario")
    List<DropdownDTO> findForDropdown();

    GeneroLiterario findByNome(String nome);

    GeneroLiterario findByNomeAndIdNot(String nome, UUID id);

}
