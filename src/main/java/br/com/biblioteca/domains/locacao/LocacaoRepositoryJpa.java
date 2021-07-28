package br.com.biblioteca.domains.locacao;

import br.com.biblioteca.domains.locacao.dto.LocacaoListaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

interface LocacaoRepositoryJpa extends JpaRepository<Locacao, UUID> {

    @Query("SELECT locacao from Locacao locacao " +
            "JOIN locacao.cliente cliente ON cliente.deletedAt IS NULL")
    Page<LocacaoListaDTO> findByPage(String filter, Pageable pageable);

}
