package br.com.biblioteca;

import br.com.biblioteca.domains.autor.Autor;
import br.com.biblioteca.domains.autor.AutorRepository;
import br.com.biblioteca.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;

public class AutorTest extends IntegrationTestConfiguration {

    @Autowired
    private AutorRepository autorRepository;

    private String autorJson;
    private Autor autor, autor2;
    private String autor1Id, autor2Id;

    @Before
    public void setUp() {
        super.setUp();
        RestAssured.basePath = "/autores";
        autorJson = ResourceUtils.getContentFromResource("/json/criar-autor.json");
        prepararDados();
    }

    private void prepararDados() {
        Autor autor = new Autor("Machado de Assis", "Brasileiro", LocalDate.of(1839, 5, 10));
        this.autor = autorRepository.save(autor);
        this.autor1Id = autor.getId().toString();

        Autor autor2 = new Autor("Vinicius de Moraes", "Brasileiro", LocalDate.of(1913, 8, 26));
        this.autor2 = autorRepository.save(autor2);
        this.autor2Id = autor2.getId().toString();
    }

    @Test
    public void findById_Retornando200OK() {
        givenAuthenticated()
                .pathParam("autorId", autor.getId().toString())
                .when()
                .get("/{autorId}")
                .then()
                .body("size()", is(4))
                .body("id", is(autor.getId().toString()))
                .body("nome", is("Machado de Assis"))
                .body("nacionalidade", is("Brasileiro"))
                .body("dataNascimento", is("1839-05-10"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findById_Retornando404NOTFOUND_MensagemClienteNaoEncontrado() {
        givenAuthenticated()
                .pathParam("autorId", "50b230d1-2803-42be-b71a-1f602737f2d4")
                .when()
                .get("/{autorId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("mensagem", is("AUTOR NÃO ENCONTRADO"));
    }

    @Test
    public void cadastrarAutor_Retornando201CREATED() {
        String payload = autorJson
                .replace("{{nome}}", "Augusto Cury")
                .replace("{{nacionalidade}}", "Brasileiro")
                .replace("{{dataNascimento}}", "1958-11-02");

        Response response = givenAuthenticated()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post();
        response.then()
                .statusCode(HttpStatus.CREATED.value());

        String id = getIdHeaderLocation(response);

        givenAuthenticated()
                .pathParam("autorId", id)
                .when()
                .get("/{autorId}")
                .then()
                .body("size()", is(4))
                .body("id", is(id))
                .body("nome", is("Augusto Cury"))
                .body("nacionalidade", is("Brasileiro"))
                .body("dataNascimento", is("1958-11-02"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void atualizarAutor_Retornando204NOCONTENT() {
        String payload = autorJson
                .replace("{{nome}}", "Augusto Cury")
                .replace("{{nacionalidade}}", "Brasileiro")
                .replace("{{dataNascimento}}", "1958-11-02");

        givenAuthenticated()
                .pathParam("autorId", autor.getId().toString())
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/{autorId}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deletarAutor_Retornando204NOCONTENT() {
        givenAuthenticated()
                .pathParam("autorId", autor.getId().toString())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete("/{autorId}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void findByPage_ParametroNome_Retornando200OK() {
        givenAuthenticated()
                .param("orderBy", "nome")
                .param("direction", "ASC")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("totalElements", is(2))
                .body("$", hasKey("content"))
                .body("content", everyItem(hasKey("id")))
                .body("content", everyItem(hasKey("nome")))
                .body("content", everyItem(hasKey("nacionalidade")))
                .body("content", everyItem(hasKey("dataNascimento")))
                .body("content[0].id", is(autor.getId().toString()))
                .body("content[0].nome", is("Machado de Assis"))
                .body("content[0].nacionalidade", is("Brasileiro"))
                .body("content[0].dataNascimento", is("1839-05-10"))
                .body("content[1].id", is(autor2.getId().toString()))
                .body("content[1].nome", is("Vinicius de Moraes"))
                .body("content[1].nacionalidade", is("Brasileiro"))
                .body("content[1].dataNascimento", is("1913-08-26"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findByPage_ParametroNacionalidade_Retornando200OK() {
        givenAuthenticated()
                .param("orderBy", "nacionalidade")
                .param("direction", "ASC")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("totalElements", is(2))
                .body("$", hasKey("content"))
                .body("content", everyItem(hasKey("id")))
                .body("content", everyItem(hasKey("nome")))
                .body("content", everyItem(hasKey("nacionalidade")))
                .body("content", everyItem(hasKey("dataNascimento")))
                .body("content[0].id", is(autor.getId().toString()))
                .body("content[0].nome", is("Machado de Assis"))
                .body("content[0].nacionalidade", is("Brasileiro"))
                .body("content[0].dataNascimento", is("1839-05-10"))
                .body("content[1].id", is(autor2.getId().toString()))
                .body("content[1].nome", is("Vinicius de Moraes"))
                .body("content[1].nacionalidade", is("Brasileiro"))
                .body("content[1].dataNascimento", is("1913-08-26"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findByPage_ParametroDataNascimento_Retornando200OK() {
        givenAuthenticated()
                .param("orderBy", "dataNascimento")
                .param("direction", "ASC")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("totalElements", is(2))
                .body("$", hasKey("content"))
                .body("content", everyItem(hasKey("id")))
                .body("content", everyItem(hasKey("nome")))
                .body("content", everyItem(hasKey("nacionalidade")))
                .body("content", everyItem(hasKey("dataNascimento")))
                .body("content[0].id", is(autor.getId().toString()))
                .body("content[0].nome", is("Machado de Assis"))
                .body("content[0].nacionalidade", is("Brasileiro"))
                .body("content[0].dataNascimento", is("1839-05-10"))
                .body("content[1].id", is(autor2.getId().toString()))
                .body("content[1].nome", is("Vinicius de Moraes"))
                .body("content[1].nacionalidade", is("Brasileiro"))
                .body("content[1].dataNascimento", is("1913-08-26"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void dropdown_Retornando200OK() {
        givenAuthenticated()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get("/dropdown")
                .then()
                .body("$", everyItem(hasKey("id")))
                .body("$", everyItem(hasKey("nome")))
                .body("[0].size()", is(2))
                .body("[0].id", is(autor1Id))
                .body("[0].nome", is("Machado de Assis"))
                .body("[1].size()", is(2))
                .body("[1].id", is(autor2Id))
                .body("[1].nome", is("Vinicius de Moraes"))
                .statusCode(HttpStatus.OK.value());
    }

}
