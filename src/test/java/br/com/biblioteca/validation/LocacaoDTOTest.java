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

    private String locacaoJsonCriar, locacaoJsonAtualizar, locacaoJsonLivroVazio;

    @Before
    public void setUp() {
        super.setUp();
        RestAssured.basePath = "/locacoes";
        locacaoJsonCriar = ResourceUtils.getContentFromResource("/json/criar-locacao.json");
        locacaoJsonAtualizar = ResourceUtils.getContentFromResource("/json/atualizar-locacao.json");
        locacaoJsonLivroVazio = ResourceUtils.getContentFromResource("/json/criar-locacao-livro-vazio.json");
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoDataLocacaoNulo() {
        String payload = locacaoJsonCriar
                .replace("{{clienteId}}", "dbfdd773-ab96-4b4c-bd03-9533f8916b62")
                .replace("{{livro1Id}}", "94951cf2-6ac9-4478-978b-7560ba819c4a")
                .replace("{{livro2Id}}", "5ea9104f-db57-470f-96dc-f955ecb75462")
                .replace("{{dataLocacao}}", "")
                .replace("{{dataPrevistaDevolucao}}", "2030-07-20T14:21:00");
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
    public void cadastrar_Retornando400BADREQUEST_QuandoDataLocacaoFuturo() {
        String payload = locacaoJsonCriar
                .replace("{{clienteId}}", "dbfdd773-ab96-4b4c-bd03-9533f8916b62")
                .replace("{{livro1Id}}", "94951cf2-6ac9-4478-978b-7560ba819c4a")
                .replace("{{livro2Id}}", "5ea9104f-db57-470f-96dc-f955ecb75462")
                .replace("{{dataLocacao}}", "2030-08-01T14:21:00")
                .replace("{{dataPrevistaDevolucao}}", "2040-07-16T14:21:00");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO DATA DE LOCAÇÃO DEVE SER UMA DATA PASSADA OU PRESENTE."));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoDataPrevistaDevolucaoNulo() {
        String payload = locacaoJsonCriar
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
    public void cadastrar_Retornando400BADREQUEST_QuandoDataPrevistaDevolucaoPassado() {
        String payload = locacaoJsonCriar
                .replace("{{clienteId}}", "dbfdd773-ab96-4b4c-bd03-9533f8916b62")
                .replace("{{livro1Id}}", "94951cf2-6ac9-4478-978b-7560ba819c4a")
                .replace("{{livro2Id}}", "5ea9104f-db57-470f-96dc-f955ecb75462")
                .replace("{{dataLocacao}}", "2021-07-16T14:21:00")
                .replace("{{dataPrevistaDevolucao}}", "2010-07-16T14:21:00");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO DATA PREVISTA PARA DEVOLUÇÃO DEVE SER UMA DATA FUTURA."));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoClienteIdNulo() {
        String payload = locacaoJsonCriar
                .replace("{{clienteId}}", "")
                .replace("{{livro1Id}}", "94951cf2-6ac9-4478-978b-7560ba819c4a")
                .replace("{{livro2Id}}", "5ea9104f-db57-470f-96dc-f955ecb75462")
                .replace("{{dataLocacao}}", "2021-07-16T14:21:00")
                .replace("{{dataPrevistaDevolucao}}", "2030-07-16T14:21:00");
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
    public void cadastrar_Retornando400BADREQUEST_QuandoLivroIdVazio() {
        String payload = locacaoJsonLivroVazio
                .replace("{{clienteId}}", "261d2278-3aa4-4890-a5ac-15be139e388d")
                .replace("{{dataLocacao}}", "2021-07-16T14:21:00")
                .replace("{{dataPrevistaDevolucao}}", "2030-07-16T14:21:00");

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
    public void atualizar_Retornando400BADREQUEST_QuandoLivroIdVazio() {
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

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoDataDevolucaoPassado() {
        String payload = locacaoJsonAtualizar
                .replace("{{dataDevolucao}}", "2010-07-16T14:21:00");
        given()
                .pathParam("locacaoId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{locacaoId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO DATA DE DEVOLUÇÃO DEVE SER UMA DATA FUTURA."));
    }

}
