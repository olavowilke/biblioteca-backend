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
import br.com.biblioteca.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;


public class LivroTest extends IntegrationTestConfiguration {

    private String criarLivroJson;
    private String atualizarLivroJson;
    private Livro livro1, livro2;
    private Autor autor1, autor2;
    private Editora editora1, editora2;
    private GeneroLiterario generoLiterario1, generoLiterario2;
    private String livro1Id, livro2Id;
    private String autor1Id, autor2Id;
    private String editora1Id, editora2Id;
    private String generoLiterario1Id, generoLiterario2Id;
    private Estoque estoque1, estoque2;

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private GeneroLiterarioRepository generoLiterarioRepository;
    @Autowired
    private EditoraRepository editoraRepository;
    @Autowired
    private EstoqueRepository estoqueRepository;

    @Before
    public void setUp() {
        super.setUp();
        RestAssured.basePath = "/livros";
        criarLivroJson = ResourceUtils.getContentFromResource("/json/criar-livro.json");
        atualizarLivroJson = ResourceUtils.getContentFromResource("/json/atualizar-livro.json");
        prepararDados();
    }

    private void prepararDados() {
        this.generoLiterario1 = new GeneroLiterario("Romance");
        this.generoLiterario2 = new GeneroLiterario("Aventura");
        this.generoLiterario1Id = generoLiterario1.getId().toString();
        this.generoLiterario2Id = generoLiterario2.getId().toString();

        this.autor1 = new Autor("Lima Barreto", "Brasileiro", LocalDate.of(1881, 5, 13));
        this.autor2 = new Autor("Olavo Wilke", "Brasileiro", LocalDate.of(1996, 8, 1));
        this.autor1Id = autor1.getId().toString();
        this.autor2Id = autor2.getId().toString();

        this.editora1 = new Editora("Panini");
        this.editora2 = new Editora("Saraiva");
        this.editora1Id = editora1.getId().toString();
        this.editora2Id = editora2.getId().toString();

        this.livro1 = new Livro("O triste fim de Policarpo Quaresma", LocalDate.of(1915, 1, 1), editora1, generoLiterario1, "123456789111", autor1);
        this.livro2 = new Livro("O triste fim da minha ereção", LocalDate.of(2018, 1, 1), editora2, generoLiterario2, "123456789112", autor2);
        this.livro1Id = livro1.getId().toString();
        this.livro2Id = livro2.getId().toString();

        this.estoque1 = new Estoque(livro1, BigDecimal.TEN);
        this.estoque2 = new Estoque(livro2, BigDecimal.TEN);

        List<Autor> autores = List.of(autor1, autor2);
        List<Editora> editoras = List.of(editora1, editora2);
        List<Livro> livros = List.of(livro1, livro2);
        List<GeneroLiterario> generosLiterarios = List.of(generoLiterario1, generoLiterario2);
        List<Estoque> estoques = List.of(estoque1, estoque2);

        autorRepository.saveAll(autores);
        editoraRepository.saveAll(editoras);
        generoLiterarioRepository.saveAll(generosLiterarios);
        livroRepository.saveAll(livros);
        estoqueRepository.saveAll(estoques);
    }

    @Test
    public void findById_Retornando200OK() {
        givenAuthenticated()
                .pathParam("livroId", livro2Id)
                .when()
                .get("/{livroId}")
                .then()
                .body("size()", is(8))
                .body("$", hasKey("id"))
                .body("$", hasKey("titulo"))
                .body("$", hasKey("dataPublicacao"))
                .body("$", hasKey("isbn"))
                .body("$", hasKey("generoLiterarioId"))
                .body("$", hasKey("autorId"))
                .body("$", hasKey("editoraId"))
                .body("$", hasKey("quantidadeDisponivel"))
                .body("id", is(livro2Id))
                .body("titulo", is("O triste fim da minha ereção"))
                .body("dataPublicacao", is("2018-01-01"))
                .body("isbn", is("123456789112"))
                .body("generoLiterarioId", is(generoLiterario2Id))
                .body("autorId", is(autor2Id))
                .body("editoraId", is(editora2Id))
                .body("quantidadeDisponivel", is(10))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findById_Retornando404NotFound() {
        givenAuthenticated()
                .pathParam("livroId", "8888b6f9-dd09-4d3b-bbd5-e58e1396f641")
                .when()
                .get("/{livroId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void findByIsbn_Retornando200OK() {
        givenAuthenticated()
                .pathParam("isbn", livro2.getIsbn())
                .when()
                .get("/isbn/{isbn}")
                .then()
                .body("size()", is(4))
                .body("$", hasKey("id"))
                .body("$", hasKey("titulo"))
                .body("$", hasKey("autorNome"))
                .body("$", hasKey("isbn"))
                .body("id", is(livro2Id))
                .body("titulo", is("O triste fim da minha ereção"))
                .body("autorNome", is("Olavo Wilke"))
                .body("isbn", is("123456789112"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findByIsbn_Retornando404NOTFOUND_QuandoIsbnInvalido() {
        givenAuthenticated()
                .pathParam("isbn", "1234")
                .when()
                .get("/isbn/{isbn}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void cadastrarLivro_Retornando201Created() {
        String payload = criarLivroJson
                .replace("{{autorId}}", autor2Id)
                .replace("{{editoraId}}", editora2Id)
                .replace("{{generoLiterarioId}}", generoLiterario2Id)
                .replace("{{titulo}}", "O triste fim da minha ereção")
                .replace("{{dataPublicacao}}", "2018-01-01")
                .replace("{{isbn}}", "123456789111")
                .replace("{{quantidadeDisponivel}}", "10");

        Response response = givenAuthenticated()
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .post();
        response.then()
                .statusCode(HttpStatus.CREATED.value());

        String id = getIdHeaderLocation(response);

        givenAuthenticated()
                .pathParam("livroId", id)
                .when()
                .get("/{livroId}")
                .then()
                .body("size()", is(8))
                .body("$", hasKey("id"))
                .body("$", hasKey("titulo"))
                .body("$", hasKey("dataPublicacao"))
                .body("$", hasKey("isbn"))
                .body("$", hasKey("generoLiterarioId"))
                .body("$", hasKey("autorId"))
                .body("$", hasKey("editoraId"))
                .body("$", hasKey("quantidadeDisponivel"))
                .body("id", is(id))
                .body("titulo", is("O triste fim da minha ereção"))
                .body("dataPublicacao", is("2018-01-01"))
                .body("isbn", is("123456789111"))
                .body("generoLiterarioId", is(generoLiterario2Id))
                .body("autorId", is(autor2Id))
                .body("editoraId", is(editora2Id))
                .body("quantidadeDisponivel", is(10))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deletarLivro_Retornando204NoContent() {
        givenAuthenticated()
                .pathParam("livroId", livro2Id)
                .when()
                .delete("/{livroId}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void atualizarLivro_Retornando204NoContent() {
        String payload = atualizarLivroJson
                .replace("{{autorId}}", autor2Id)
                .replace("{{editoraId}}", editora2Id)
                .replace("{{generoLiterarioId}}", generoLiterario2Id)
                .replace("{{titulo}}", "O triste fim da minha ereção")
                .replace("{{dataPublicacao}}", "2018-01-01")
                .replace("{{isbn}}", "123456789123");

        givenAuthenticated()
                .pathParam("livroId", livro2Id)
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{livroId}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        givenAuthenticated()
                .pathParam("livroId", livro2Id)
                .when()
                .get("/{livroId}")
                .then()
                .body("size()", is(8))
                .body("$", hasKey("id"))
                .body("$", hasKey("titulo"))
                .body("$", hasKey("dataPublicacao"))
                .body("$", hasKey("isbn"))
                .body("$", hasKey("generoLiterarioId"))
                .body("$", hasKey("autorId"))
                .body("$", hasKey("editoraId"))
                .body("$", hasKey("quantidadeDisponivel"))
                .body("id", is(livro2Id))
                .body("titulo", is("O triste fim da minha ereção"))
                .body("dataPublicacao", is("2018-01-01"))
                .body("isbn", is("123456789123"))
                .body("generoLiterarioId", is(generoLiterario2Id))
                .body("autorId", is(autor2Id))
                .body("editoraId", is(editora2Id))
                .body("quantidadeDisponivel", is(10))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void atualizarLivro_CampoVazio_400BadRequest() {
        String payload = atualizarLivroJson
                .replace("{{autorId}}", autor2Id)
                .replace("{{editoraId}}", editora1Id)
                .replace("{{titulo}}", "Dom casmurro")
                .replace("{{dataPublicacao}}", "")
                .replace("{{generoLiterarioId}}", generoLiterario2Id)
                .replace("{{isbn}", "312312312312321");

        givenAuthenticated()
                .pathParam("livroId", livro2Id)
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{livroId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void findByPage_QuandoParametroTitulo_Retornando200OK() {
        givenAuthenticated()
                .param("orderBy", "titulo")
                .param("direction", "ASC")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("totalElements", is(2))
                .body("$", hasKey("content"))
                .body("content", everyItem(hasKey("id")))
                .body("content", everyItem(hasKey("titulo")))
                .body("content", everyItem(hasKey("dataPublicacao")))
                .body("content", everyItem(hasKey("generoLiterarioNome")))
                .body("content", everyItem(hasKey("isbn")))
                .body("content", everyItem(hasKey("autorNome")))
                .body("content", everyItem(hasKey("editoraNome")))
                .body("content[0].size()", is(7))
                .body("content[0].id", is(livro2Id))
                .body("content[0].titulo", is("O triste fim da minha ereção"))
                .body("content[0].dataPublicacao", is("2018-01-01"))
                .body("content[0].isbn", is("123456789112"))
                .body("content[0].generoLiterarioNome", is("Aventura"))
                .body("content[0].autorNome", is("Olavo Wilke"))
                .body("content[0].editoraNome", is("Saraiva"))
                .body("content[1].size()", is(7))
                .body("content[1].id", is(livro1Id))
                .body("content[1].titulo", is("O triste fim de Policarpo Quaresma"))
                .body("content[1].dataPublicacao", is("1915-01-01"))
                .body("content[1].isbn", is("123456789111"))
                .body("content[1].generoLiterarioNome", is("Romance"))
                .body("content[1].autorNome", is("Lima Barreto"))
                .body("content[1].editoraNome", is("Panini"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findByPage_QuandoParametroEditora_Retornando200OK() {
        givenAuthenticated()
                .param("orderBy", "editora.nome")
                .param("direction", "ASC")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("totalElements", is(2))
                .body("$", hasKey("content"))
                .body("content", everyItem(hasKey("id")))
                .body("content", everyItem(hasKey("titulo")))
                .body("content", everyItem(hasKey("dataPublicacao")))
                .body("content", everyItem(hasKey("generoLiterarioNome")))
                .body("content", everyItem(hasKey("isbn")))
                .body("content", everyItem(hasKey("autorNome")))
                .body("content", everyItem(hasKey("editoraNome")))
                .body("content[0].size()", is(7))
                .body("content[0].id", is(livro1Id))
                .body("content[0].titulo", is("O triste fim de Policarpo Quaresma"))
                .body("content[0].dataPublicacao", is("1915-01-01"))
                .body("content[0].isbn", is("123456789111"))
                .body("content[0].generoLiterarioNome", is("Romance"))
                .body("content[0].autorNome", is("Lima Barreto"))
                .body("content[0].editoraNome", is("Panini"))
                .body("content[1].size()", is(7))
                .body("content[1].id", is(livro2Id))
                .body("content[1].titulo", is("O triste fim da minha ereção"))
                .body("content[1].dataPublicacao", is("2018-01-01"))
                .body("content[1].generoLiterarioNome", is("Aventura"))
                .body("content[1].isbn", is("123456789112"))
                .body("content[1].autorNome", is("Olavo Wilke"))
                .body("content[1].editoraNome", is("Saraiva"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findByPage_QuandoParametroGeneroLiterario_Retornando200OK() {
        givenAuthenticated()
                .param("orderBy", "generoLiterario.nome")
                .param("direction", "ASC")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("totalElements", is(2))
                .body("$", hasKey("content"))
                .body("content", everyItem(hasKey("id")))
                .body("content", everyItem(hasKey("titulo")))
                .body("content", everyItem(hasKey("dataPublicacao")))
                .body("content", everyItem(hasKey("generoLiterarioNome")))
                .body("content", everyItem(hasKey("isbn")))
                .body("content", everyItem(hasKey("autorNome")))
                .body("content", everyItem(hasKey("editoraNome")))
                .body("content[0].size()", is(7))
                .body("content[0].id", is(livro2Id))
                .body("content[0].titulo", is("O triste fim da minha ereção"))
                .body("content[0].dataPublicacao", is("2018-01-01"))
                .body("content[0].generoLiterarioNome", is("Aventura"))
                .body("content[0].isbn", is("123456789112"))
                .body("content[0].autorNome", is("Olavo Wilke"))
                .body("content[0].editoraNome", is("Saraiva"))
                .body("content[1].size()", is(7))
                .body("content[1].id", is(livro1Id))
                .body("content[1].titulo", is("O triste fim de Policarpo Quaresma"))
                .body("content[1].dataPublicacao", is("1915-01-01"))
                .body("content[1].generoLiterarioNome", is("Romance"))
                .body("content[1].isbn", is("123456789111"))
                .body("content[1].autorNome", is("Lima Barreto"))
                .body("content[1].editoraNome", is("Panini"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findByPage_QuandoParametroDataPublicacao_Retornando200OK() {
        givenAuthenticated()
                .param("orderBy", "dataPublicacao")
                .param("direction", "ASC")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("totalElements", is(2))
                .body("$", hasKey("content"))
                .body("content", everyItem(hasKey("id")))
                .body("content", everyItem(hasKey("titulo")))
                .body("content", everyItem(hasKey("dataPublicacao")))
                .body("content", everyItem(hasKey("generoLiterarioNome")))
                .body("content", everyItem(hasKey("isbn")))
                .body("content", everyItem(hasKey("autorNome")))
                .body("content", everyItem(hasKey("editoraNome")))
                .body("content[0].size()", is(7))
                .body("content[0].id", is(livro1Id))
                .body("content[0].titulo", is("O triste fim de Policarpo Quaresma"))
                .body("content[0].dataPublicacao", is("1915-01-01"))
                .body("content[0].generoLiterarioNome", is("Romance"))
                .body("content[0].isbn", is("123456789111"))
                .body("content[0].autorNome", is("Lima Barreto"))
                .body("content[0].editoraNome", is("Panini"))
                .body("content[1].size()", is(7))
                .body("content[1].id", is(livro2Id))
                .body("content[1].titulo", is("O triste fim da minha ereção"))
                .body("content[1].dataPublicacao", is("2018-01-01"))
                .body("content[1].generoLiterarioNome", is("Aventura"))
                .body("content[1].isbn", is("123456789112"))
                .body("content[1].autorNome", is("Olavo Wilke"))
                .body("content[1].editoraNome", is("Saraiva"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findByPage_QuandoParametroAutorNome_Retornando200OK() {
        givenAuthenticated()
                .param("orderBy", "autor.nome")
                .param("direction", "ASC")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("totalElements", is(2))
                .body("$", hasKey("content"))
                .body("content", everyItem(hasKey("id")))
                .body("content", everyItem(hasKey("titulo")))
                .body("content", everyItem(hasKey("dataPublicacao")))
                .body("content", everyItem(hasKey("generoLiterarioNome")))
                .body("content", everyItem(hasKey("isbn")))
                .body("content", everyItem(hasKey("autorNome")))
                .body("content", everyItem(hasKey("editoraNome")))
                .body("content[0].size()", is(7))
                .body("content[0].id", is(livro1Id))
                .body("content[0].titulo", is("O triste fim de Policarpo Quaresma"))
                .body("content[0].dataPublicacao", is("1915-01-01"))
                .body("content[0].generoLiterarioNome", is("Romance"))
                .body("content[0].isbn", is("123456789111"))
                .body("content[0].editoraNome", is("Panini"))
                .body("content[0].autorNome", is("Lima Barreto"))
                .body("content[1].size()", is(7))
                .body("content[1].id", is(livro2Id))
                .body("content[1].titulo", is("O triste fim da minha ereção"))
                .body("content[1].dataPublicacao", is("2018-01-01"))
                .body("content[1].generoLiterarioNome", is("Aventura"))
                .body("content[1].isbn", is("123456789112"))
                .body("content[1].editoraNome", is("Saraiva"))
                .body("content[1].autorNome", is("Olavo Wilke"))
                .statusCode(HttpStatus.OK.value());
    }

}
