package br.com.biblioteca.domains.locacao.validator;

import br.com.biblioteca.domains.estoque.Estoque;
import br.com.biblioteca.domains.estoque.EstoqueRepository;
import br.com.biblioteca.domains.livro.LivroRepository;
import br.com.biblioteca.domains.locacao.dto.LocacaoCriarDTO;
import br.com.biblioteca.exception_handler.exception.LivroIndisponivelException;
import br.com.biblioteca.util.BigDecimalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
class LivroDisponivelEstoqueImpl implements LocacoesValidator {

    public static final String ESTOQUE = "LIVRO INDISPONÍVEL NO ESTOQUE";

    private final LivroRepository livroRepository;
    private final EstoqueRepository estoqueRepository;

    @Override
    public void validar(LocacaoCriarDTO locacaoCriarDTO) {
        List<Estoque> estoques = estoqueRepository.findByLivroIdInOrderByLivroNomeAsc(locacaoCriarDTO.getLivroIds());
        boolean isAnyIndisponivel = estoques.stream().anyMatch(estoque -> BigDecimalUtil.isEqual(estoque.getQuantidadeDisponivel(), BigDecimal.ZERO));
        if (isAnyIndisponivel) {
            throw new LivroIndisponivelException(ESTOQUE);
        }
    }

}
