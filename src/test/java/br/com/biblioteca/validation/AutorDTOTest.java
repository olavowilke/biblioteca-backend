package br.com.biblioteca.validation;

import br.com.biblioteca.IntegrationTestConfiguration;
import br.com.biblioteca.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class AutorDTOTest extends IntegrationTestConfiguration {

    private String autorJson;

    @Before
    public void setUp() {
        super.setUp();
        RestAssured.basePath = "/autores";
        autorJson = ResourceUtils.getContentFromResource("/json/criar-autor.json");
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoNomeVazio() {
        String payload = autorJson
                .replace("{{nome}}", "")
                .replace("{{nacionalidade}}", "Brasileiro")
                .replace("{{dataNascimento}}", "1994-05-01");

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
    public void cadastrar_Retornando400BADREQUEST_QuandoNomeMaior50Caracteres() {
        String payload = autorJson
                .replace("{{nome}}", "Teste para validar quando o nome supera 50 caracteres")
                .replace("{{nacionalidade}}", "Brasileiro")
                .replace("{{dataNascimento}}", "1994-05-04");

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
    public void cadastrar_Retornando400BADREQUEST_QuandoNacionalidadeVazio() {
        String payload = autorJson
                .replace("{{nome}}", "Roberto")
                .replace("{{nacionalidade}}", "")
                .replace("{{dataNascimento}}", "1994-05-01");

        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO NACIONALIDADE NÃO DEVE ESTAR EM BRANCO."));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoNacionalidadeMaior50Caracteres() {
        String payload = autorJson
                .replace("{{nome}}", "Roberto")
                .replace("{{nacionalidade}}", "Teste para validar quando o nome supera 50 caracteres")
                .replace("{{dataNascimento}}", "1994-05-04");

        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO NACIONALIDADE NÃO DEVE CONTER MAIS DO QUE 50 CARACTERES."));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoDataNascimentoNulo() {
        String payload = autorJson
                .replace("{{nome}}", "Roberto")
                .replace("{{nacionalidade}}", "Brasileiro")
                .replace("{{dataNascimento}}", "");

        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O PREENCHIMENTO DO CAMPO DATA DE NASCIMENTO É OBRIGATÓRIO."));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoDataNascimentoFuturo() {
        String payload = autorJson
                .replace("{{nome}}", "Roberto")
                .replace("{{nacionalidade}}", "Brasileiro")
                .replace("{{dataNascimento}}", "2030-08-01");

        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", Is.is("O CAMPO DATA DE NASCIMENTO DEVE SER UMA DATA PASSADA."));
    }

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoNomeVazio() {
        String payload = autorJson
                .replace("{{nome}}", "")
                .replace("{{nacionalidade}}", "Brasileiro")
                .replace("{{dataNascimento}}", "1994-05-01");

        given()
                .pathParam("autorId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{autorId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO NOME NÃO DEVE ESTAR EM BRANCO."));
    }

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoNomeMaior50Caracteres() {
        String payload = autorJson
                .replace("{{nome}}", "Teste para validar quando o nome supera 50 caracteres")
                .replace("{{nacionalidade}}", "Brasileiro")
                .replace("{{dataNascimento}}", "1994-05-04");

        given()
                .pathParam("autorId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{autorId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO NOME NÃO DEVE CONTER MAIS DO QUE 50 CARACTERES."));
    }

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoNacionalidadeVazio() {
        String payload = autorJson
                .replace("{{nome}}", "Leonardo")
                .replace("{{nacionalidade}}", "")
                .replace("{{dataNascimento}}", "1994-05-01");

        given()
                .pathParam("autorId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{autorId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO NACIONALIDADE NÃO DEVE ESTAR EM BRANCO."));
    }

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoNacionalidadeMaior50Caracteres() {
        String payload = autorJson
                .replace("{{nome}}", "Leonardo")
                .replace("{{nacionalidade}}", "Teste para validar quando o nome supera 50 caracteres")
                .replace("{{dataNascimento}}", "1994-05-04");

        given()
                .pathParam("autorId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{autorId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO NACIONALIDADE NÃO DEVE CONTER MAIS DO QUE 50 CARACTERES."));
    }

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoDataNascimentoNulo() {
        String payload = autorJson
                .replace("{{nome}}", "Leonardo")
                .replace("{{nacionalidade}}", "Brasileiro")
                .replace("{{dataNascimento}}", "");

        given()
                .pathParam("autorId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{autorId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O PREENCHIMENTO DO CAMPO DATA DE NASCIMENTO É OBRIGATÓRIO."));
    }

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoDataNascimentoFuturo() {
        String payload = autorJson
                .replace("{{nome}}", "Leonardo")
                .replace("{{nacionalidade}}", "Brasileiro")
                .replace("{{dataNascimento}}", "2030-08-01");

        given()
                .pathParam("autorId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{autorId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", Is.is("O CAMPO DATA DE NASCIMENTO DEVE SER UMA DATA PASSADA."));
    }

}
