package br.com.biblioteca.domains.livro;

import br.com.biblioteca.domains.autor.Autor;
import br.com.biblioteca.domains.autor.AutorRepository;
import br.com.biblioteca.domains.livro.dto.LivroByIdDTO;
import br.com.biblioteca.domains.livro.dto.LivroCriarAtualizarDTO;
import br.com.biblioteca.domains.livro.dto.LivroListaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public void deleteById(UUID id) {
        Livro livro = livroRepository.findById(id);
        livro.delete();
        livroRepository.save(livro);
    }

    public Livro criar(LivroCriarAtualizarDTO livroCriarDTO) {
        UUID autorId = livroCriarDTO.getAutorId();
        Autor autor = autorRepository.findById(autorId);
        Livro livro = new Livro(livroCriarDTO, autor);
        return livroRepository.save(livro);
    }

    public LivroByIdDTO findById(UUID id) {
        Livro livro = livroRepository.findById(id);
        return new LivroByIdDTO(livro);
    }

    public void atualizar(LivroCriarAtualizarDTO livroAtualizarDTO, UUID id) {
        Livro livro = livroRepository.findById(id);
        livro.atualizar(livroAtualizarDTO);
        livroRepository.save(livro);
    }

    public Page<LivroListaDTO> findByPage(String filter, Pageable pageable) {
        return livroRepository.findByPage(filter, pageable);
    }

}
