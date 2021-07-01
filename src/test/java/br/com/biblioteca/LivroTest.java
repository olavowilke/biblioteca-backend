package br.com.biblioteca;

import br.com.biblioteca.domains.autor.Autor;
import br.com.biblioteca.domains.autor.AutorRepository;
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

import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class LivroTest extends IntegrationTestConfiguration {

    private String livroJson;
    private Livro livro1, livro2;
    private Autor autor1, autor2;
    private String livro1Id;
    private String livro2Id;
    private String autor1Id;
    private String autor2Id;

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Before
    public void setUp() {
        super.setUp();
        RestAssured.basePath = "/livros";
        livroJson = ResourceUtils.getContentFromResource("/json/criar-livro.json");
        prepararDados();
    }

    private void prepararDados() {
        this.autor1 = new Autor("Lima Barreto", "Brasileiro", LocalDate.of(1881, 5, 13));
        this.autor2 = new Autor("Olavo Wilke", "Brasileiro", LocalDate.of(1996, 8, 1));
        this.autor1Id = autor1.getId().toString();
        this.autor2Id = autor2.getId().toString();


        this.livro1 = new Livro("O triste fim de Policarpo Quaresma", LocalDate.of(1915, 1, 1), "Revista dos Tribunaes", "Romance", "28172874989", autor1);
        this.livro2 = new Livro("O triste fim da minha ereção", LocalDate.of(2018, 1, 1), "LEO ARRUDA INC", "Científico", "28172874989", autor2);
        this.livro1Id = livro1.getId().toString();
        this.livro2Id = livro2.getId().toString();

        List<Autor> autores = List.of(autor1, autor2);
        List<Livro> livros = List.of(livro1, livro2);

        autorRepository.saveAll(autores);
        livroRepository.saveAll(livros);
    }

    @Test
    public void findById_Retornando404NotFound() {
        given()
                .pathParam("livroId", "8888b6f9-dd09-4d3b-bbd5-e58e1396f641")
                .when()
                .get("/{livroId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void findById_Retornando200OK() {
        given()
                .pathParam("livroId", livro2Id)
                .when()
                .get("/{livroId}")
                .then()
                .body("size()", is(7))
                .body("$", hasKey("id"))
                .body("$", hasKey("titulo"))
                .body("$", hasKey("dataPublicacao"))
                .body("$", hasKey("editora"))
                .body("$", hasKey("generoLiterario"))
                .body("$", hasKey("isbn"))
                .body("$", hasKey("autorId"))
                .body("id", is(livro2Id))
                .body("titulo", is("O triste fim da minha ereção"))
                .body("dataPublicacao", is("2018-01-01"))
                .body("editora", is("LEO ARRUDA INC"))
                .body("generoLiterario", is("Científico"))
                .body("isbn", is("28172874989"))
                .body("autorId", is(autor2Id))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void cadastrarLivro_Retornando201Created() {
        String payload = livroJson
                .replace("{{autorId}}", autor2Id)
                .replace("{{titulo}}", "O triste fim da minha ereção")
                .replace("{{dataPublicacao}}", "2018-01-01")
                .replace("{{editora}}", "LEO ARRUDA INC")
                .replace("{{generoLiterario}}", "Científico")
                .replace("{{isbn}}", "28172874989");

        Response response = given()
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .post();
        response.then()
                .statusCode(HttpStatus.CREATED.value());

        String id = getIdHeaderLocation(response);

        given()
                .pathParam("livroId", id)
                .when()
                .get("/{livroId}")
                .then()
                .body("size()", is(7))
                .body("$", hasKey("id"))
                .body("$", hasKey("titulo"))
                .body("$", hasKey("dataPublicacao"))
                .body("$", hasKey("editora"))
                .body("$", hasKey("generoLiterario"))
                .body("$", hasKey("isbn"))
                .body("$", hasKey("autorId"))
                .body("id", is(id))
                .body("titulo", is("O triste fim da minha ereção"))
                .body("dataPublicacao", is("2018-01-01"))
                .body("editora", is("LEO ARRUDA INC"))
                .body("generoLiterario", is("Científico"))
                .body("isbn", is("28172874989"))
                .body("autorId", is(autor2Id))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deletarLivro_Retornando204NoContent() {
        given()
                .pathParam("livroId", livro2Id)
                .when()
                .delete("/{livroId}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void atualizarLivro_Retornando204NoContent() {
        String payload = livroJson
                .replace("{{autorId}}", autor2Id)
                .replace("{{titulo}}", "O triste fim da minha ereção")
                .replace("{{dataPublicacao}}", "2018-01-01")
                .replace("{{editora}}", "LEO ARRUDA INC")
                .replace("{{generoLiterario}}", "Romance")
                .replace("{{isbn}}", "28172874989");

        given()
                .pathParam("livroId", livro2Id)
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{livroId}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given()
                .pathParam("livroId", livro2Id)
                .when()
                .get("/{livroId}")
                .then()
                .body("size()", is(7))
                .body("$", hasKey("id"))
                .body("$", hasKey("titulo"))
                .body("$", hasKey("dataPublicacao"))
                .body("$", hasKey("editora"))
                .body("$", hasKey("generoLiterario"))
                .body("$", hasKey("isbn"))
                .body("$", hasKey("autorId"))
                .body("id", is(livro2Id))
                .body("titulo", is("O triste fim da minha ereção"))
                .body("dataPublicacao", is("2018-01-01"))
                .body("editora", is("LEO ARRUDA INC"))
                .body("generoLiterario", is("Romance"))
                .body("isbn", is("28172874989"))
                .body("autorId", is(autor2Id))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void atualizarLivro_GeneroLiterarioVazio_400BadRequest() {
        String payload = livroJson
                .replace("{{autorId}}", autor2Id)
                .replace("{{titulo}}", "Dom casmurro")
                .replace("{{dataPublicacao}}", "1983-04-03")
                .replace("{{editora}}", "Livraria Garnier")
                .replace("{{generoLiterario}}", "")
                .replace("{{isbn}", "312312312312321");

        given()
                .pathParam("livroId", livro2Id)
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{livroId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void findByPage_Retornando200OK() {
        given()
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
                .body("content", everyItem(hasKey("editora")))
                .body("content", everyItem(hasKey("generoLiterario")))
                .body("content", everyItem(hasKey("autorNome")))
                .body("content", everyItem(hasKey("isbn")))
                .body("content[0].size()", is(7))
                .body("content[0].id", is(livro2Id))
                .body("content[0].titulo", is("O triste fim da minha ereção"))
                .body("content[0].dataPublicacao", is("2018-01-01"))
                .body("content[0].editora", is("LEO ARRUDA INC"))
                .body("content[0].generoLiterario", is("Científico"))
                .body("content[0].autorNome", is("Olavo Wilke"))
                .body("content[0].isbn", is("28172874989"))
                .body("content[1].size()", is(7))
                .body("content[1].id", is(livro1Id))
                .body("content[1].titulo", is("O triste fim de Policarpo Quaresma"))
                .body("content[1].dataPublicacao", is("1915-01-01"))
                .body("content[1].editora", is("Revista dos Tribunaes"))
                .body("content[1].generoLiterario", is("Romance"))
                .body("content[1].autorNome", is("Lima Barreto"))
                .body("content[1].isbn", is("28172874989"))

                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findByPage_QuandoParametroEditora_Retornando200OK() {
        given()
                .param("orderBy", "editora")
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
                .body("content", everyItem(hasKey("editora")))
                .body("content", everyItem(hasKey("generoLiterario")))
                .body("content", everyItem(hasKey("autorNome")))
                .body("content", everyItem(hasKey("isbn")))
                .body("content[0].size()", is(7))
                .body("content[0].id", is(livro2Id))
                .body("content[0].titulo", is("O triste fim da minha ereção"))
                .body("content[0].dataPublicacao", is("2018-01-01"))
                .body("content[0].editora", is("LEO ARRUDA INC"))
                .body("content[0].generoLiterario", is("Científico"))
                .body("content[0].autorNome", is("Olavo Wilke"))
                .body("content[0].isbn", is("28172874989"))
                .body("content[1].size()", is(7))
                .body("content[1].id", is(livro1Id))
                .body("content[1].titulo", is("O triste fim de Policarpo Quaresma"))
                .body("content[1].dataPublicacao", is("1915-01-01"))
                .body("content[1].editora", is("Revista dos Tribunaes"))
                .body("content[1].generoLiterario", is("Romance"))
                .body("content[1].autorNome", is("Lima Barreto"))
                .body("content[1].isbn", is("28172874989"))

                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findByPage_QuandoParametroGeneroLiterario_Retornando200OK() {
        given()
                .param("orderBy", "generoLiterario")
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
                .body("content", everyItem(hasKey("editora")))
                .body("content", everyItem(hasKey("generoLiterario")))
                .body("content", everyItem(hasKey("autorNome")))
                .body("content", everyItem(hasKey("isbn")))
                .body("content[0].id", is(livro2Id))
                .body("content[0].size()", is(7))
                .body("content[0].titulo", is("O triste fim da minha ereção"))
                .body("content[0].dataPublicacao", is("2018-01-01"))
                .body("content[0].editora", is("LEO ARRUDA INC"))
                .body("content[0].generoLiterario", is("Científico"))
                .body("content[0].autorNome", is("Olavo Wilke"))
                .body("content[0].isbn", is("28172874989"))
                .body("content[1].size()", is(7))
                .body("content[1].id", is(livro1Id))
                .body("content[1].titulo", is("O triste fim de Policarpo Quaresma"))
                .body("content[1].dataPublicacao", is("1915-01-01"))
                .body("content[1].editora", is("Revista dos Tribunaes"))
                .body("content[1].generoLiterario", is("Romance"))
                .body("content[1].autorNome", is("Lima Barreto"))
                .body("content[1].isbn", is("28172874989"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findByPage_QuandoParametroDataPublicacao_Retornando200OK() {
        given()
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
                .body("content", everyItem(hasKey("editora")))
                .body("content", everyItem(hasKey("generoLiterario")))
                .body("content", everyItem(hasKey("autorNome")))
                .body("content", everyItem(hasKey("isbn")))
                .body("content[0].size()", is(7))
                .body("content[0].id", is(livro1Id))
                .body("content[0].titulo", is("O triste fim de Policarpo Quaresma"))
                .body("content[0].dataPublicacao", is("1915-01-01"))
                .body("content[0].editora", is("Revista dos Tribunaes"))
                .body("content[0].generoLiterario", is("Romance"))
                .body("content[0].autorNome", is("Lima Barreto"))
                .body("content[0].isbn", is("28172874989"))
                .body("content[1].size()", is(7))
                .body("content[1].id", is(livro2Id))
                .body("content[1].titulo", is("O triste fim da minha ereção"))
                .body("content[1].dataPublicacao", is("2018-01-01"))
                .body("content[1].editora", is("LEO ARRUDA INC"))
                .body("content[1].generoLiterario", is("Científico"))
                .body("content[1].autorNome", is("Olavo Wilke"))
                .body("content[1].isbn", is("28172874989"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findByPage_QuandoParametroAutorNome_Retornando200OK() {
        given()
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
                .body("content", everyItem(hasKey("editora")))
                .body("content", everyItem(hasKey("generoLiterario")))
                .body("content", everyItem(hasKey("autorNome")))
                .body("content", everyItem(hasKey("isbn")))
                .body("content[0].size()", is(7))
                .body("content[0].id", is(livro1Id))
                .body("content[0].titulo", is("O triste fim de Policarpo Quaresma"))
                .body("content[0].dataPublicacao", is("1915-01-01"))
                .body("content[0].editora", is("Revista dos Tribunaes"))
                .body("content[0].generoLiterario", is("Romance"))
                .body("content[0].autorNome", is("Lima Barreto"))
                .body("content[0].isbn", is("28172874989"))
                .body("content[1].size()", is(7))
                .body("content[1].id", is(livro2Id))
                .body("content[1].titulo", is("O triste fim da minha ereção"))
                .body("content[1].dataPublicacao", is("2018-01-01"))
                .body("content[1].editora", is("LEO ARRUDA INC"))
                .body("content[1].generoLiterario", is("Científico"))
                .body("content[1].autorNome", is("Olavo Wilke"))
                .body("content[1].isbn", is("28172874989"))
                .statusCode(HttpStatus.OK.value());
    }

}
