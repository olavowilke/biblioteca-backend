package br.com.biblioteca.domains.locacao.validator;

import br.com.biblioteca.domains.estoque.Estoque;
import br.com.biblioteca.domains.estoque.EstoqueRepository;
import br.com.biblioteca.domains.livro.LivroRepository;
import br.com.biblioteca.domains.locacao.dto.LocacaoCriarDTO;
import br.com.biblioteca.exception_handler.exception.EstoqueNaoAtribuidoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class LivroAtribuidoEstoqueImpl implements LocacoesValidator {

    public static final String LIVRO_ESTOQUE = "UM OU MAIS LIVROS NÃO ESTÃO ATRIBUIDOS À UM ESTOQUE";

    private final LivroRepository livroRepository;
    private final EstoqueRepository estoqueRepository;

    @Override
    public void validar(LocacaoCriarDTO locacaoCriarDTO) {
        List<Estoque> estoques = estoqueRepository.findByLivroIdInOrderByLivroNomeAsc(locacaoCriarDTO.getLivroIds());
        if (estoques.isEmpty()) {
            throw new EstoqueNaoAtribuidoException(LIVRO_ESTOQUE);
        }
    }

}
