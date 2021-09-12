package br.com.biblioteca;

import br.com.biblioteca.domains.autor.Autor;
import br.com.biblioteca.domains.autor.AutorRepository;
import br.com.biblioteca.domains.editora.Editora;
import br.com.biblioteca.domains.editora.EditoraRepository;
import br.com.biblioteca.domains.estoque.Estoque;
import br.com.biblioteca.domains.estoque.EstoqueRepository;
import br.com.biblioteca.domains.genero_literario.GeneroLiterario;
import br.com.biblioteca.domains.genero_literario.GeneroLiterarioRepository;
import br.com.biblioteca.domains.livro.Livro;
import br.com.biblioteca.domains.livro.LivroRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;

public class EstoqueTest extends IntegrationTestConfiguration {

    @Autowired
    private GeneroLiterarioRepository generoLiterarioRepository;

    @Autowired
    private EditoraRepository editoraRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    private GeneroLiterario generoLiterario1;
    private Autor autor1;
    private Editora editora1;
    private Estoque estoque1, estoque2, estoque3, estoque4;
    private Livro livro1, livro2, livro3, livro4;
    private String editora1Id;
    private String autor1Id;
    private String generoLiterario1Id;

    @Before
    public void setUp() {
        super.setUp();
        RestAssured.basePath = "/estoque";
        prepararDados();
    }

    private void prepararDados() {
        this.generoLiterario1 = new GeneroLiterario("Romance");
        this.generoLiterario1Id = generoLiterario1.getId().toString();

        this.autor1 = new Autor("Lima Barreto", "Brasileiro", LocalDate.of(1881, 5, 13));
        this.autor1Id = autor1.getId().toString();

        this.editora1 = new Editora("Panini");
        this.editora1Id = editora1.getId().toString();

        this.livro1 = new Livro("O triste fim de Policarpo Quaresma", LocalDate.of(1915, 1, 1), editora1, generoLiterario1, "28172874989", autor1);
        this.livro2 = new Livro("A cruzada", LocalDate.of(2018, 1, 1), editora1, generoLiterario1, "28172874989", autor1);

        this.estoque1 = new Estoque(livro1, BigDecimal.TEN);
        this.estoque2 = new Estoque(livro2, BigDecimal.TEN);

        List<Livro> livros = List.of(livro1, livro2);
        List<Estoque> estoques = List.of(estoque1, estoque2);

        generoLiterarioRepository.save(generoLiterario1);
        editoraRepository.save(editora1);
        autorRepository.save(autor1);
        livroRepository.saveAll(livros);
        estoqueRepository.saveAll(estoques);
    }

    @Test
    public void findByPage_ParametroTitulo_Retornando200OK() {
        givenAuthenticated()
                .param("orderBy", "livro.titulo")
                .param("direction", "DESC")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("totalElements", is(2))
                .body("$", hasKey("content"))
                .body("content", everyItem(hasKey("id")))
                .body("content", everyItem(hasKey("livroTitulo")))
                .body("content", everyItem(hasKey("quantidadeDisponivel")))
                .body("content[0].size()", is(3))
                .body("content[0].id", is(estoque1.getId().toString()))
                .body("content[0].livroTitulo", is("O triste fim de Policarpo Quaresma"))
                .body("content[0].quantidadeDisponivel", is(10))
                .body("content[1].size()", is(3))
                .body("content[1].id", is(estoque2.getId().toString()))
                .body("content[1].livroTitulo", is("A cruzada"))
                .body("content[1].quantidadeDisponivel", is(10))
                .statusCode(HttpStatus.OK.value());
    }

}
