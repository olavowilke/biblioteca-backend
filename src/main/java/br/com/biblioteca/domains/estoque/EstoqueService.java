package br.com.biblioteca.domains.estoque;

import br.com.biblioteca.domains.estoque.dto.EstoqueListaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;

    public Page<EstoqueListaDTO> findByPage(String filter, Pageable pageable) {
        return estoqueRepository.findByPage(filter, pageable);
    }

}
