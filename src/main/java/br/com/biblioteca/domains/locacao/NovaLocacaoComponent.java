package br.com.biblioteca.domains.locacao;

import br.com.biblioteca.domains.cliente.Cliente;
import br.com.biblioteca.domains.cliente.ClienteRepository;
import br.com.biblioteca.domains.estoque.Estoque;
import br.com.biblioteca.domains.estoque.EstoqueRepository;
import br.com.biblioteca.domains.livro.Livro;
import br.com.biblioteca.domains.livro.LivroRepository;
import br.com.biblioteca.domains.locacao.dto.LocacaoCriarDTO;
import br.com.biblioteca.domains.locacao.validator.LocacoesValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NovaLocacaoComponent {

    private final ClienteRepository clienteRepository;
    private final LivroRepository livroRepository;
    private final LocacaoRepository locacaoRepository;
    private final EstoqueRepository estoqueRepository;
    private final List<LocacoesValidator> locacoesValidator;

    public Locacao criar(LocacaoCriarDTO locacaoCriarDTO) {
        locacoesValidator.forEach(locacoesValidator1 -> locacoesValidator1.validar(locacaoCriarDTO));
        Cliente cliente = clienteRepository.findById(locacaoCriarDTO.getClienteId());
        List<Livro> livros = livroRepository.findAllById(locacaoCriarDTO.getLivroIds());
        Locacao locacao = new Locacao(locacaoCriarDTO, cliente, livros);
        List<Estoque> estoques = estoqueRepository.findByLivroIdInOrderByLivroNomeAsc(locacaoCriarDTO.getLivroIds());
        estoques.forEach(Estoque::retirar);
        estoqueRepository.saveAll(estoques);
        return locacaoRepository.save(locacao);
    }

}
