package br.com.biblioteca.validation;

import br.com.biblioteca.IntegrationTestConfiguration;
import br.com.biblioteca.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

public class LivroDTOTest extends IntegrationTestConfiguration {

    private String livroJson;

    @Before
    public void setUp() {
        super.setUp();
        RestAssured.basePath = "/livros";
        livroJson = ResourceUtils.getContentFromResource("/json/criar-livro.json");
    }

    @Test
    public void cadastrarLivro_Retornando400BADREQUEST_QuandoTituloVazio() {
        String payload = livroJson
                .replace("{{autorId}}", "7603b344-c226-40f1-a46a-1e387a0fbf78")
                .replace("{{editoraId}}", "cecc747d-ff9d-4d94-89d8-4f3f49aa887e")
                .replace("{{generoLiterarioId}}", "b893356c-af66-49ca-95be-adf44b7d4f04")
                .replace("{{titulo}}", "")
                .replace("{{dataPublicacao}}", "2018-01-01")
                .replace("{{isbn}}", "28172874989");

        given()
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO TITULO NÃO DEVE ESTAR EM BRANCO."));
    }

    @Test
    public void cadastrarLivro_Retornando400BADREQUEST_QuandoTituloMaior50Caracteres() {
        String payload = livroJson
                .replace("{{autorId}}", "af601a08-b4b0-4f4a-aa32-bdbba937695e")
                .replace("{{editoraId}}", "0d443c16-0fb1-4c01-a6be-1ee4cdd91a88")
                .replace("{{generoLiterarioId}}", "c570cb83-2a92-44b2-a0bc-bfe8829df3dd")
                .replace("{{titulo}}", "Teste titulo maior que 50 caracteresTeste titulo maior que 50 caracteres")
                .replace("{{dataPublicacao}}", "2018-01-01")
                .replace("{{isbn}}", "28172874989");

        given()
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO TITULO NÃO DEVE CONTER MAIS DO QUE 50 CARACTERES."));
    }

    @Test
    public void cadastrarLivro_Retornando400BADREQUEST_QuandoDataPublicacaoNulo() {
        String payload = livroJson
                .replace("{{autorId}}", "af601a08-b4b0-4f4a-aa32-bdbba937695e")
                .replace("{{editoraId}}", "0d443c16-0fb1-4c01-a6be-1ee4cdd91a88")
                .replace("{{generoLiterarioId}}", "c570cb83-2a92-44b2-a0bc-bfe8829df3dd")
                .replace("{{titulo}}", "Clean Code")
                .replace("{{dataPublicacao}}", "")
                .replace("{{isbn}}", "28172874989");

        given()
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O PREENCHIMENTO DO CAMPO DATA DE PUBLICAÇÃO É OBRIGATÓRIO."));
    }

    @Test
    public void cadastrarLivro_Retornando400BADREQUEST_QuandoEditoraIdNulo() {
        String payload = livroJson
                .replace("{{autorId}}", "af601a08-b4b0-4f4a-aa32-bdbba937695e")
                .replace("{{editoraId}}", "")
                .replace("{{generoLiterarioId}}", "c570cb83-2a92-44b2-a0bc-bfe8829df3dd")
                .replace("{{titulo}}", "Clean Code")
                .replace("{{dataPublicacao}}", "1994-05-01")
                .replace("{{isbn}}", "28172874989");

        given()
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O PREENCHIMENTO DO CAMPO EDITORA É OBRIGATÓRIO."));
    }

    @Test
    public void cadastrarLivro_Retornando400BADREQUEST_QuandoGeneroLiterarioIdNulo() {
        String payload = livroJson
                .replace("{{autorId}}", "af601a08-b4b0-4f4a-aa32-bdbba937695e")
                .replace("{{editoraId}}", "611a7399-5663-428d-ac64-785a35cfb9d6")
                .replace("{{generoLiterarioId}}", "")
                .replace("{{titulo}}", "Clean Code")
                .replace("{{dataPublicacao}}", "1994-05-01")
                .replace("{{isbn}}", "28172874989");

        given()
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O PREENCHIMENTO DO CAMPO GÊNERO LITERÁRIO É OBRIGATÓRIO."));
    }

    @Test
    public void cadastrarLivro_Retornando400BADREQUEST_QuandoAutorIdNulo() {
        String payload = livroJson
                .replace("{{autorId}}", "")
                .replace("{{editoraId}}", "611a7399-5663-428d-ac64-785a35cfb9d6")
                .replace("{{generoLiterarioId}}", "9739c3ba-516d-4178-ba94-97ed9c35d2c2")
                .replace("{{titulo}}", "Clean Code")
                .replace("{{dataPublicacao}}", "1994-05-01")
                .replace("{{isbn}}", "28172874989");

        given()
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O PREENCHIMENTO DO CAMPO AUTOR É OBRIGATÓRIO."));
    }

    @Test
    public void cadastrarLivro_Retornando400BADREQUEST_QuandoIsbnVazio() {
        String payload = livroJson
                .replace("{{autorId}}", "15540cf3-1fbb-41a0-8d5d-fc66961fdbf7")
                .replace("{{editoraId}}", "611a7399-5663-428d-ac64-785a35cfb9d6")
                .replace("{{generoLiterarioId}}", "9739c3ba-516d-4178-ba94-97ed9c35d2c2")
                .replace("{{titulo}}", "Clean Code")
                .replace("{{dataPublicacao}}", "1994-05-01")
                .replace("{{isbn}}", "");

        given()
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO ISBN NÃO DEVE ESTAR EM BRANCO."));
    }

    @Test
    public void atualizarLivro_Retornando400BADREQUEST_QuandoTituloVazio() {
        String payload = livroJson
                .replace("{{autorId}}", "f374b74f-4c5b-4683-8f07-9d44d66e6456")
                .replace("{{editoraId}}", "fe540d19-6957-465c-a344-6a9db9ad945d")
                .replace("{{generoLiterarioId}}", "20c6360a-fcec-43c5-8067-b347fd8ee7b6")
                .replace("{{titulo}}", "")
                .replace("{{dataPublicacao}}", "1994-08-01")
                .replace("{{isbn}", "31231231");

        given()
                .pathParam("livroId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{livroId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO TITULO NÃO DEVE ESTAR EM BRANCO."));
    }

    @Test
    public void atualizarLivro_Retornando400BADREQUEST_QuandoTituloMaior50Caracteres() {
        String payload = livroJson
                .replace("{{autorId}}", "f374b74f-4c5b-4683-8f07-9d44d66e6456")
                .replace("{{editoraId}}", "fe540d19-6957-465c-a344-6a9db9ad945d")
                .replace("{{generoLiterarioId}}", "20c6360a-fcec-43c5-8067-b347fd8ee7b6")
                .replace("{{titulo}}", "Teste titulo maior que 50 caracteresTeste titulo maior que 50 caracteres")
                .replace("{{dataPublicacao}}", "1994-08-01")
                .replace("{{isbn}", "31231231");

        given()
                .pathParam("livroId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{livroId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO TITULO NÃO DEVE CONTER MAIS DO QUE 50 CARACTERES."));
    }

    @Test
    public void atualizarLivro_Retornando400BADREQUEST_QuandoDataPublicacaoNulo() {
        String payload = livroJson
                .replace("{{autorId}}", "f374b74f-4c5b-4683-8f07-9d44d66e6456")
                .replace("{{editoraId}}", "fe540d19-6957-465c-a344-6a9db9ad945d")
                .replace("{{generoLiterarioId}}", "20c6360a-fcec-43c5-8067-b347fd8ee7b6")
                .replace("{{titulo}}", "Clean Code")
                .replace("{{dataPublicacao}}", "")
                .replace("{{isbn}", "31231231");

        given()
                .pathParam("livroId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{livroId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O PREENCHIMENTO DO CAMPO DATA DE PUBLICAÇÃO É OBRIGATÓRIO."));
    }

    @Test
    public void atualizarLivro_Retornando400BADREQUEST_QuandoEditoraIdNulo() {
        String payload = livroJson
                .replace("{{autorId}}", "f374b74f-4c5b-4683-8f07-9d44d66e6456")
                .replace("{{editoraId}}", "")
                .replace("{{generoLiterarioId}}", "20c6360a-fcec-43c5-8067-b347fd8ee7b6")
                .replace("{{titulo}}", "Clean Code")
                .replace("{{dataPublicacao}}", "1994-05-01")
                .replace("{{isbn}", "31231231");

        given()
                .pathParam("livroId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{livroId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O PREENCHIMENTO DO CAMPO EDITORA É OBRIGATÓRIO."));
    }

    @Test
    public void atualizarLivro_Retornando400BADREQUEST_QuandoGeneroLiterarioIdNulo() {
        String payload = livroJson
                .replace("{{autorId}}", "f374b74f-4c5b-4683-8f07-9d44d66e6456")
                .replace("{{editoraId}}", "147ca835-ccb1-4645-81c3-c06f4614368f")
                .replace("{{generoLiterarioId}}", "")
                .replace("{{titulo}}", "Clean Code")
                .replace("{{dataPublicacao}}", "1994-05-01")
                .replace("{{isbn}", "31231231");

        given()
                .pathParam("livroId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{livroId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O PREENCHIMENTO DO CAMPO GÊNERO LITERÁRIO É OBRIGATÓRIO."));
    }

    @Test
    public void atualizarLivro_Retornando400BADREQUEST_QuandoAutorIdNulo() {
        String payload = livroJson
                .replace("{{autorId}}", "")
                .replace("{{editoraId}}", "611a7399-5663-428d-ac64-785a35cfb9d6")
                .replace("{{generoLiterarioId}}", "9739c3ba-516d-4178-ba94-97ed9c35d2c2")
                .replace("{{titulo}}", "Clean Code")
                .replace("{{dataPublicacao}}", "1994-05-01")
                .replace("{{isbn}}", "28172874989");

        given()
                .pathParam("livroId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{livroId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O PREENCHIMENTO DO CAMPO AUTOR É OBRIGATÓRIO."));
    }

    @Test
    public void atualizarLivro_Retornando400BADREQUEST_QuandoIsbnVazio() {
        String payload = livroJson
                .replace("{{autorId}}", "96ad6a13-8732-447e-bf6f-114707576446")
                .replace("{{editoraId}}", "611a7399-5663-428d-ac64-785a35cfb9d6")
                .replace("{{generoLiterarioId}}", "9739c3ba-516d-4178-ba94-97ed9c35d2c2")
                .replace("{{titulo}}", "Clean Code")
                .replace("{{dataPublicacao}}", "1994-05-01")
                .replace("{{isbn}}", "");

        given()
                .pathParam("livroId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{livroId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO ISBN NÃO DEVE ESTAR EM BRANCO."));
    }

}
