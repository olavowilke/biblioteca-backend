package br.com.biblioteca.domains.livro;

import br.com.biblioteca.domains.autor.Autor;
import br.com.biblioteca.domains.autor.AutorRepository;
import br.com.biblioteca.domains.editora.Editora;
import br.com.biblioteca.domains.editora.EditoraRepository;
import br.com.biblioteca.domains.estoque.Estoque;
import br.com.biblioteca.domains.estoque.EstoqueRepository;
import br.com.biblioteca.domains.genero_literario.GeneroLiterario;
import br.com.biblioteca.domains.genero_literario.GeneroLiterarioRepository;
import br.com.biblioteca.domains.livro.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final EditoraRepository editoraRepository;
    private final GeneroLiterarioRepository generoLiterarioRepository;
    private final EstoqueRepository estoqueRepository;

    public void deleteById(UUID id) {
        Livro livro = livroRepository.findById(id);
        livro.delete();
        livroRepository.save(livro);
    }

    public Livro criar(LivroCriarDTO livroCriarDTO) {
        Autor autor = autorRepository.findById(livroCriarDTO.getAutorId());
        Editora editora = editoraRepository.findById(livroCriarDTO.getEditoraId());
        GeneroLiterario generoLiterario = generoLiterarioRepository.findById(livroCriarDTO.getGeneroLiterarioId());
        Livro livro = new Livro(livroCriarDTO, autor, editora, generoLiterario);
        livroRepository.save(livro);
        Estoque estoque = new Estoque(livro, livroCriarDTO.getQuantidadeDisponivel());
        estoqueRepository.save(estoque);
        return livro;
    }

    public LivroByIdDTO findById(UUID id) {
        Livro livro = livroRepository.findById(id);
        Estoque estoqueByLivroId = estoqueRepository.findByLivroId(id);
        BigDecimal qtdeDisponivel = estoqueByLivroId.getQuantidadeDisponivel();
        return new LivroByIdDTO(livro, qtdeDisponivel);
    }

    public void atualizar(LivroAtualizarDTO livroAtualizarDTO, UUID id) {
        Livro livro = livroRepository.findById(id);
        Autor autor = autorRepository.findById(livroAtualizarDTO.getAutorId());
        Editora editora = editoraRepository.findById(livroAtualizarDTO.getEditoraId());
        GeneroLiterario generoLiterario = generoLiterarioRepository.findById(livroAtualizarDTO.getGeneroLiterarioId());
        livro.atualizar(livroAtualizarDTO, autor, editora, generoLiterario);
        livroRepository.save(livro);
    }

    public Page<LivroListaDTO> findByPage(String filter, Pageable pageable) {
        return livroRepository.findByPage(filter, pageable);
    }

    public LivroByIsbnDTO findByIsbn(String isbn) {
        Livro livro = livroRepository.findByIsbn(isbn);
        return new LivroByIsbnDTO(livro);
    }

}
