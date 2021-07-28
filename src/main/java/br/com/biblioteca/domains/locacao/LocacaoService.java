package br.com.biblioteca.domains.locacao;

import br.com.biblioteca.domains.estoque.Estoque;
import br.com.biblioteca.domains.estoque.EstoqueRepository;
import br.com.biblioteca.domains.livro.Livro;
import br.com.biblioteca.domains.locacao.dto.LocacaoAtualizarDTO;
import br.com.biblioteca.domains.locacao.dto.LocacaoCriarDTO;
import br.com.biblioteca.domains.locacao.dto.LocacaoListaDTO;
import br.com.biblioteca.util.disassembler.LivroDisassembler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class LocacaoService {

    private final LocacaoRepository locacaoRepository;
    private final EstoqueRepository estoqueRepository;
    private final NovaLocacaoComponent novaLocacaoComponent;

    public Locacao criar(LocacaoCriarDTO locacaoCriarDTO) {
        return novaLocacaoComponent.criar(locacaoCriarDTO);
    }

    public void atualizar(UUID locacaoId, LocacaoAtualizarDTO locacaoAtualizarDTO) {
        Locacao locacao = locacaoRepository.findById(locacaoId);
        locacao.fazerDevolucao(locacaoAtualizarDTO);
        devolverLivrosParaEstoque(locacao.getLivros());
    }

    private void devolverLivrosParaEstoque(List<Livro> livros) {
        List<UUID> livroIds = LivroDisassembler.mapToIds(livros);
        List<Estoque> estoques = estoqueRepository.findByLivroIdInOrderByLivroNomeAsc(livroIds);
        estoques.forEach(Estoque::devolver);
        estoqueRepository.saveAll(estoques);
    }

    public Page<LocacaoListaDTO> findByPage(String filter, Pageable pageable) {
        return locacaoRepository.findByPage(filter, pageable);
    }

}
