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

public class ClienteDTOTest extends IntegrationTestConfiguration {

    private String clienteJsonCriar, clienteJsonAtualizar;

    @Before
    public void setUp() {
        super.setUp();
        RestAssured.basePath = "/clientes";
        clienteJsonCriar = ResourceUtils.getContentFromResource("/json/criar-cliente.json");
        clienteJsonAtualizar = ResourceUtils.getContentFromResource("/json/atualizar-cliente.json");
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoNomeVazio() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "")
                .replace("{{cpf}}", "05609692016")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "43")
                .replace("{{telefone.numero}}", "994568475")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "654")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");

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
    public void cadastrar_Retornando400BADREQUEST_QuandoNomeMaior100Caracteres() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "Teste para validar mais de 100 caracteresTeste para validar mais de 100 caracteresTeste para validar mais de ")
                .replace("{{cpf}}", "05609692016")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "43")
                .replace("{{telefone.numero}}", "994568475")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "654")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");

        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO NOME NÃO DEVE CONTER MAIS DO QUE 100 CARACTERES."));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoCpfMenor11Caracteres() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "Leonardo")
                .replace("{{cpf}}", "1234567890")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "43")
                .replace("{{telefone.numero}}", "994568475")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "654")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("NÚMERO DO REGISTRO DE CONTRIBUINTE INDIVIDUAL BRASILEIRO (CPF) INVÁLIDO"));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoCpfMaior11Caracteres() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "Leonardo")
                .replace("{{cpf}}", "123456789011")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "43")
                .replace("{{telefone.numero}}", "994568475")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "654")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("NÚMERO DO REGISTRO DE CONTRIBUINTE INDIVIDUAL BRASILEIRO (CPF) INVÁLIDO"));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoCpfInvalido() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "Leonardo")
                .replace("{{cpf}}", "12345678901")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "43")
                .replace("{{telefone.numero}}", "994568475")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "654")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("NÚMERO DO REGISTRO DE CONTRIBUINTE INDIVIDUAL BRASILEIRO (CPF) INVÁLIDO"));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoCpfVazio() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "Leonardo")
                .replace("{{cpf}}", "")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "43")
                .replace("{{telefone.numero}}", "994568475")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "654")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("NÚMERO DO REGISTRO DE CONTRIBUINTE INDIVIDUAL BRASILEIRO (CPF) INVÁLIDO"));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoDDDVazio() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "Leonardo")
                .replace("{{cpf}}", "10352722967")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "")
                .replace("{{telefone.numero}}", "994568475")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "654")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO DDD DEVE CONTER 2 CARACTERES."));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoDDDMaior2Caracteres() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "Leonardo")
                .replace("{{cpf}}", "10352722967")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "123")
                .replace("{{telefone.numero}}", "994568475")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "654")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO DDD DEVE CONTER 2 CARACTERES."));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoDDDMenor2Caracteres() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "Leonardo")
                .replace("{{cpf}}", "10352722967")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "1")
                .replace("{{telefone.numero}}", "994568475")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "654")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO DDD DEVE CONTER 2 CARACTERES."));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoNumeroTelefoneVazio() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "Leonardo")
                .replace("{{cpf}}", "10352722967")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "654")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO NÚMERO DO TELEFONE NÃO DEVE ESTAR EM BRANCO."));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoTipoTelefoneNulo() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "Roberto")
                .replace("{{cpf}}", "10352722967")
                .replace("{{dataNascimento}}", "1945-04-01")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "654")
                .replace("{{endereco.complemento}}", "")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "994564215")
                .replace("\"tipoTelefone\": \"{{telefone.tipoTelefone}}\",", "");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O PREENCHIMENTO DO CAMPO TIPO DO TELEFONE É OBRIGATÓRIO."));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoNumeroEnderecoVazio() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "Leonardo")
                .replace("{{cpf}}", "10352722967")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "994561215")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO NÚMERO DO ENDEREÇO NÃO DEVE ESTAR EM BRANCO."));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoLogradouroVazio() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "Leonardo")
                .replace("{{cpf}}", "10352722967")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "994561215")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "")
                .replace("{{endereco.numero}}", "152")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO LOGRADOURO DO ENDEREÇO NÃO DEVE ESTAR EM BRANCO."));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoCepVazio() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "Leonardo")
                .replace("{{cpf}}", "10352722967")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "994561215")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua Jababa")
                .replace("{{endereco.numero}}", "152")
                .replace("{{endereco.cep}}", "")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO CEP NÃO DEVE ESTAR EM BRANCO."));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoCidadeVazio() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "Leonardo")
                .replace("{{cpf}}", "10352722967")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "994561215")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua Jababa")
                .replace("{{endereco.numero}}", "152")
                .replace("{{endereco.cep}}", "84652152")
                .replace("{{endereco.cidade}}", "")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO CIDADE NÃO DEVE ESTAR EM BRANCO."));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoEstadoVazio() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "Leonardo")
                .replace("{{cpf}}", "10352722967")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "994561215")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua Jababa")
                .replace("{{endereco.numero}}", "152")
                .replace("{{endereco.cep}}", "84652152")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "")
                .replace("{{endereco.complemento}}", "");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO ESTADO NÃO DEVE ESTAR EM BRANCO."));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoEstadoMaior2Caracteres() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "Leonardo")
                .replace("{{cpf}}", "10352722967")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "994561215")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua Jababa")
                .replace("{{endereco.numero}}", "152")
                .replace("{{endereco.cep}}", "84652152")
                .replace("{{endereco.cidade}}", "Arapongas")
                .replace("{{endereco.estado}}", "Paraná")
                .replace("{{endereco.complemento}}", "");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO ESTADO NÃO DEVE CONTER MAIS DO QUE 2 CARACTERES."));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoComplementoMaior200Caracteres() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "Leonardo")
                .replace("{{cpf}}", "10352722967")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "994561215")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua Jababa")
                .replace("{{endereco.numero}}", "152")
                .replace("{{endereco.cep}}", "84652152")
                .replace("{{endereco.cidade}}", "Arapongas")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "Teste mais 200 caracteresTeste mais 200 caracteresTeste mais 200 caracteresTeste mais 200 caracteresTeste mais 200 caracteresTeste mais 200 caracteresTeste mais 200 caracteresTeste mais 200 caracteresTeste mais 200 caracteres");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO COMPLEMENTO NÃO DEVE CONTER MAIS DO QUE 200 CARACTERES."));
    }

    @Test
    public void cadastrar_Retornando400BADREQUEST_QuandoDataNascimentoNulo() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "Leonardo")
                .replace("{{cpf}}", "10352722967")
                .replace("{{dataNascimento}}", "")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "994561215")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua Jababa")
                .replace("{{endereco.numero}}", "152")
                .replace("{{endereco.cep}}", "84652152")
                .replace("{{endereco.cidade}}", "Arapongas")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
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
    public void cadastrarCliente_Retornando400BADREQUEST_QuandoDataNascimentoFuturo() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "Leonardo")
                .replace("{{cpf}}", "10352722967")
                .replace("{{dataNascimento}}", "2030-08-01")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "994561215")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua Jababa")
                .replace("{{endereco.numero}}", "152")
                .replace("{{endereco.cep}}", "84652152")
                .replace("{{endereco.cidade}}", "Arapongas")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO DATA DE NASCIMENTO DEVE SER UMA DATA PASSADA."));
    }

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoNomeVazio() {
        String payload = clienteJsonAtualizar
                .replace("{{nome}}", "")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "43")
                .replace("{{telefone.numero}}", "994568475")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "654")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .pathParam("clienteId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{clienteId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO NOME NÃO DEVE ESTAR EM BRANCO."));
    }

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoNomeMaior100Caracteres() {
        String payload = clienteJsonAtualizar
                .replace("{{nome}}", "Teste para validar mais de 100 caracteresTeste para validar mais de 100 caracteresTeste para validar mais de ")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "43")
                .replace("{{telefone.numero}}", "994568475")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "654")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .pathParam("clienteId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{clienteId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO NOME NÃO DEVE CONTER MAIS DO QUE 100 CARACTERES."));
    }

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoDDDVazio() {
        String payload = clienteJsonAtualizar
                .replace("{{nome}}", "Leonardo")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "")
                .replace("{{telefone.numero}}", "994568475")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "654")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .pathParam("clienteId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{clienteId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO DDD DEVE CONTER 2 CARACTERES."));
    }

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoDDDMaior2Caracteres() {
        String payload = clienteJsonAtualizar
                .replace("{{nome}}", "Leonardo")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "123")
                .replace("{{telefone.numero}}", "994568475")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "654")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .pathParam("clienteId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{clienteId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO DDD DEVE CONTER 2 CARACTERES."));
    }

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoNumeroTelefoneVazio() {
        String payload = clienteJsonAtualizar
                .replace("{{nome}}", "Leonardo")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "654")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .pathParam("clienteId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{clienteId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO NÚMERO DO TELEFONE NÃO DEVE ESTAR EM BRANCO."));
    }

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoTipoTelefoneNulo() {
        String payload = clienteJsonAtualizar
                .replace("{{nome}}", "Roberto")
                .replace("{{dataNascimento}}", "1945-04-01")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "654")
                .replace("{{endereco.complemento}}", "")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "994564215")
                .replace("\"tipoTelefone\": \"{{telefone.tipoTelefone}}\",", "");
        given()
                .pathParam("clienteId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{clienteId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O PREENCHIMENTO DO CAMPO TIPO DO TELEFONE É OBRIGATÓRIO."));
    }

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoNumeroEnderecoVazio() {
        String payload = clienteJsonAtualizar
                .replace("{{nome}}", "Leonardo")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "994561215")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .pathParam("clienteId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{clienteId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO NÚMERO DO ENDEREÇO NÃO DEVE ESTAR EM BRANCO."));
    }

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoLogradouroVazio() {
        String payload = clienteJsonAtualizar
                .replace("{{nome}}", "Leonardo")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "994561215")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "")
                .replace("{{endereco.numero}}", "152")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .pathParam("clienteId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{clienteId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO LOGRADOURO DO ENDEREÇO NÃO DEVE ESTAR EM BRANCO."));
    }

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoCepVazio() {
        String payload = clienteJsonAtualizar
                .replace("{{nome}}", "Leonardo")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "994561215")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua Jababa")
                .replace("{{endereco.numero}}", "152")
                .replace("{{endereco.cep}}", "")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .pathParam("clienteId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{clienteId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO CEP NÃO DEVE ESTAR EM BRANCO."));
    }

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoCidadeVazio() {
        String payload = clienteJsonAtualizar
                .replace("{{nome}}", "Leonardo")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "994561215")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua Jababa")
                .replace("{{endereco.numero}}", "152")
                .replace("{{endereco.cep}}", "84652152")
                .replace("{{endereco.cidade}}", "")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .pathParam("clienteId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{clienteId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO CIDADE NÃO DEVE ESTAR EM BRANCO."));
    }

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoEstadoVazio() {
        String payload = clienteJsonAtualizar
                .replace("{{nome}}", "Leonardo")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "994561215")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua Jababa")
                .replace("{{endereco.numero}}", "152")
                .replace("{{endereco.cep}}", "84652152")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "")
                .replace("{{endereco.complemento}}", "");
        given()
                .pathParam("clienteId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{clienteId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO ESTADO NÃO DEVE ESTAR EM BRANCO."));
    }

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoEstadoMaior2Caracteres() {
        String payload = clienteJsonAtualizar
                .replace("{{nome}}", "Leonardo")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "994561215")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua Jababa")
                .replace("{{endereco.numero}}", "152")
                .replace("{{endereco.cep}}", "84652152")
                .replace("{{endereco.cidade}}", "Arapongas")
                .replace("{{endereco.estado}}", "Paraná")
                .replace("{{endereco.complemento}}", "");
        given()
                .pathParam("clienteId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{clienteId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO ESTADO NÃO DEVE CONTER MAIS DO QUE 2 CARACTERES."));
    }

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoComplementoMaior200Caracteres() {
        String payload = clienteJsonAtualizar
                .replace("{{nome}}", "Leonardo")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "994561215")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua Jababa")
                .replace("{{endereco.numero}}", "152")
                .replace("{{endereco.cep}}", "84652152")
                .replace("{{endereco.cidade}}", "Arapongas")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "Teste mais 200 caracteresTeste mais 200 caracteresTeste mais 200 caracteresTeste mais 200 caracteresTeste mais 200 caracteresTeste mais 200 caracteresTeste mais 200 caracteresTeste mais 200 caracteresTeste mais 200 caracteres");
        given()
                .pathParam("clienteId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{clienteId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO COMPLEMENTO NÃO DEVE CONTER MAIS DO QUE 200 CARACTERES."));
    }

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoDataNascimentoNulo() {
        String payload = clienteJsonAtualizar
                .replace("{{nome}}", "Leonardo")
                .replace("{{dataNascimento}}", "")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "994561215")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua Jababa")
                .replace("{{endereco.numero}}", "152")
                .replace("{{endereco.cep}}", "84652152")
                .replace("{{endereco.cidade}}", "Arapongas")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .pathParam("clienteId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{clienteId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O PREENCHIMENTO DO CAMPO DATA DE NASCIMENTO É OBRIGATÓRIO."));
    }

    @Test
    public void atualizar_Retornando400BADREQUEST_QuandoDataNascimentoFuturo() {
        String payload = clienteJsonAtualizar
                .replace("{{nome}}", "Leonardo")
                .replace("{{dataNascimento}}", "2030-08-01")
                .replace("{{telefone.ddd}}", "11")
                .replace("{{telefone.numero}}", "994561215")
                .replace("{{telefone.tipoTelefone}}", "CELULAR")
                .replace("{{endereco.logradouro}}", "Rua Jababa")
                .replace("{{endereco.numero}}", "152")
                .replace("{{endereco.cep}}", "84652152")
                .replace("{{endereco.cidade}}", "Arapongas")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.complemento}}", "");
        given()
                .pathParam("clienteId", "9819cd30-b241-4a85-bdfb-8c7256fd5593")
                .body(payload)
                .contentType((ContentType.JSON))
                .when()
                .put("/{clienteId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("O CAMPO DATA DE NASCIMENTO DEVE SER UMA DATA PASSADA."));
    }

}
