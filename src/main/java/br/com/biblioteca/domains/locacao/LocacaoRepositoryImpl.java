package br.com.biblioteca.domains.locacao;

import br.com.biblioteca.domains.locacao.dto.LocacaoListaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class LocacaoRepositoryImpl implements LocacaoRepository {

    private static final String LOCACAO_NAO_ENCONTRADA = "LOCAÇÃO NÃO ENCONTRADA";
    private final LocacaoRepositoryJpa locacaoRepositoryJpa;

    @Override
    public Locacao save(Locacao locacao) {
        return locacaoRepositoryJpa.save(locacao);
    }

    @Override
    public Locacao findById(UUID id) {
        Optional<Locacao> historicoLocacao = locacaoRepositoryJpa.findById(id);
        return historicoLocacao.orElseThrow(() -> new EntityNotFoundException(LOCACAO_NAO_ENCONTRADA));
    }

    @Override
    public Page<LocacaoListaDTO> findByPage(String filter, Pageable pageable) {
        return locacaoRepositoryJpa.findByPage(filter, pageable);
    }

    @Override
    public List<Locacao> saveAll(Collection<Locacao> locacoes) {
        return locacaoRepositoryJpa.saveAll(locacoes);
    }

}
