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

public class LocacaoDTOTest extends IntegrationTestConfiguration {

    private String locacaoJson, locacaoJsonAtualizar, locacaoJsonLivroVazio;

    @Before
    public void setUp() {
        super.setUp();
        RestAssured.basePath = "/locacoes";
        locacaoJson = ResourceUtils.getContentFromResource("/json/criar-locacao.json");
        locacaoJsonAtualizar = ResourceUtils.getContentFromResource("/json/atualizar-locacao.json");
        locacaoJsonLivroVazio = ResourceUtils.getContentFromResource("/json/criar-locacao-livro-vazio.json");
    }

    @Test
    public void cadastrarLocacao_Retornando400BADREQUEST_QuandoDataLocacaoNulo() {
        String payload = locacaoJson
                .replace("{{clienteId}}", "dbfdd773-ab96-4b4c-bd03-9533f8916b62")
                .replace("{{livro1Id}}", "94951cf2-6ac9-4478-978b-7560ba819c4a")
                .replace("{{livro2Id}}", "5ea9104f-db57-470f-96dc-f955ecb75462")
                .replace("{{dataLocacao}}", "")
                .replace("{{dataPrevistaDevolucao}}", "2021-07-16T14:21:00");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O PREENCHIMENTO DO CAMPO DATA DE LOCAÇÃO É OBRIGATÓRIO."));
    }

    @Test
    public void cadastrarLocacao_Retornando400BADREQUEST_QuandoDataPrevistaDevolucaoNulo() {
        String payload = locacaoJson
                .replace("{{clienteId}}", "dbfdd773-ab96-4b4c-bd03-9533f8916b62")
                .replace("{{livro1Id}}", "94951cf2-6ac9-4478-978b-7560ba819c4a")
                .replace("{{livro2Id}}", "5ea9104f-db57-470f-96dc-f955ecb75462")
                .replace("{{dataLocacao}}", "2021-07-16T14:21:00")
                .replace("{{dataPrevistaDevolucao}}", "");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O PREENCHIMENTO DO CAMPO DATA PREVISTA PARA DEVOLUÇÃO É OBRIGATÓRIO."));
    }

    @Test
    public void cadastrarLocacao_Retornando400BADREQUEST_QuandoClienteIdNulo() {
        String payload = locacaoJson
                .replace("{{clienteId}}", "")
                .replace("{{livro1Id}}", "94951cf2-6ac9-4478-978b-7560ba819c4a")
                .replace("{{livro2Id}}", "5ea9104f-db57-470f-96dc-f955ecb75462")
                .replace("{{dataLocacao}}", "2021-07-16T14:21:00")
                .replace("{{dataPrevistaDevolucao}}", "2021-07-16T14:21:00");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O PREENCHIMENTO DO CAMPO CLIENTE É OBRIGATÓRIO."));
    }

    @Test
    public void cadastrarLocacao_Retornando400BADREQUEST_QuandoLivroIdVazio() {
        String payload = locacaoJsonLivroVazio
                .replace("{{clienteId}}", "261d2278-3aa4-4890-a5ac-15be139e388d")
                .replace("{{dataLocacao}}", "2021-07-16T14:21:00")
                .replace("{{dataPrevistaDevolucao}}", "2021-07-16T14:21:00");

        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO LIVROS NÃO DEVE ESTAR EM VAZIO."));
    }

    @Test
    public void atualizarLocacao_Retornando400BADREQUEST_QuandoLivroIdVazio() {
        String payload = locacaoJsonAtualizar
                .replace("{{dataDevolucao}}", "");
        given()
                .pathParam("locacaoId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{locacaoId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O PREENCHIMENTO DO CAMPO DATA DE DEVOLUÇÃO É OBRIGATÓRIO."));
    }

}
