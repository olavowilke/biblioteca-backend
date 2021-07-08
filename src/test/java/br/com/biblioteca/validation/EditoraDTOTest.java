package br.com.biblioteca.validation;

import br.com.biblioteca.IntegrationTestConfiguration;
import br.com.biblioteca.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class EditoraDTOTest extends IntegrationTestConfiguration {

    private String editoraJson;

    @Before
    public void setUp() {
        super.setUp();
        RestAssured.basePath = "/editoras";
        editoraJson = ResourceUtils.getContentFromResource("/json/criar-editora.json");
    }

    @Test
    public void cadastrarEditora_Retornando400BADREQUEST_QuandoNomeMaiorQue50Caracteres() {
        String payload = editoraJson
                .replace("{{nome}}", "Teste para validar quando o nome supera 50 caracteres");

        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO NOME NÃO DEVE CONTER MAIS DO QUE 50 CARACTERES."));
    }

    @Test
    public void cadastrarEditora_Retornando400BADREQUEST_QuandoNomeVazio() {
        String payload = editoraJson
                .replace("{{nome}}", "");

        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO NOME NÃO DEVE ESTAR EM BRANCO."));
    }

    @Test
    public void atualizarEditora_Retornando400BADREQUEST_QuandoNomeMaiorQue50Caracteres() {
        String payload = editoraJson
                .replace("{{nome}}", "Teste para validar quando o nome supera 50 caracteres");

        given()
                .pathParam("editoraId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{editoraId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO NOME NÃO DEVE CONTER MAIS DO QUE 50 CARACTERES."));
    }

    @Test
    public void atualizarEditora_Retornando400BADREQUEST_QuandoNomeVazio() {
        String payload = editoraJson
                .replace("{{nome}}", "");

        given()
                .pathParam("editoraId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{editoraId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO NOME NÃO DEVE ESTAR EM BRANCO."));
    }

}
