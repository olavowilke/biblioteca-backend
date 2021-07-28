package br.com.biblioteca.domains.locacao;

import br.com.biblioteca.domains.locacao.dto.LocacaoListaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface LocacaoRepository {

    Locacao save(Locacao locacao);

    Locacao findById(UUID id);

    Page<LocacaoListaDTO> findByPage(String filter, Pageable pageable);

    List<Locacao> saveAll(Collection<Locacao> locacoes);

}
