package br.com.biblioteca;

import br.com.biblioteca.domains.cliente.*;
import br.com.biblioteca.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ClienteTest extends IntegrationTestConfiguration {

    @Autowired
    private ClienteRepository clienteRepository;

    private String clienteJsonCriar, clienteJsonAtualizar;
    private Cliente cliente1, cliente2;

    @Before
    public void setUp() {
        super.setUp();
        RestAssured.basePath = "/clientes";
        clienteJsonCriar = ResourceUtils.getContentFromResource("/json/criar-cliente.json");
        clienteJsonAtualizar = ResourceUtils.getContentFromResource("/json/atualizar-cliente.json");
        prepararDados();
    }

    private void prepararDados() {
        Cliente cliente1 = new Cliente("Victor Pietro", "05609692016",
                LocalDate.of(1995, 4, 1),
                new Telefone("43", "994587546", TipoTelefone.CELULAR),
                new Endereco("Rua memes", "456", "86707005", "Arapongas", "PR", ""));
        Cliente cliente2 = new Cliente("Leonardo Arruda", "59375822095",
                LocalDate.of(1995, 6, 2),
                new Telefone("11", "994562542", TipoTelefone.CELULAR),
                new Endereco("Rua jajaja", "728", "86707004", "Sao Paulo", "SP", ""));
        this.cliente1 = clienteRepository.save(cliente1);
        this.cliente2 = clienteRepository.save(cliente2);
    }

    @Test
    public void cadastrar_Retornando201CREATED_QuandoSucesso() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "Roberval")
                .replace("{{cpf}}", "69086874061")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "654")
                .replace("{{endereco.complemento}}", "")
                .replace("{{telefone.ddd}}", "43")
                .replace("{{telefone.numero}}", "994568475")
                .replace("{{telefone.tipoTelefone}}", "CELULAR");

        Response response = given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post();
        response.then()
                .statusCode(HttpStatus.CREATED.value());

        String id = getIdHeaderLocation(response);

        given()
                .pathParam("clienteId", id)
                .when()
                .get("/{clienteId}")
                .then()
                .body("size()", is(6))
                .body("id", is(id))
                .body("nome", is("Roberval"))
                .body("cpf", is("69086874061"))
                .body("dataNascimento", is("1994-04-03"))
                .body("endereco.cep", is("86705000"))
                .body("endereco.cidade", is("Siberrr"))
                .body("endereco.estado", is("PR"))
                .body("endereco.logradouro", is("Rua jababara"))
                .body("endereco.numero", is("654"))
                .body("endereco.complemento", is(""))
                .body("telefone.ddd", is("43"))
                .body("telefone.numero", is("994568475"))
                .body("telefone.tipoTelefone", is("CELULAR"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void cadastrar_Retornando409CONFLICT_QuandoCpfExistente() {
        String payload = clienteJsonCriar
                .replace("{{nome}}", "Roberval")
                .replace("{{cpf}}", "05609692016")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "654")
                .replace("{{endereco.complemento}}", "")
                .replace("{{telefone.ddd}}", "43")
                .replace("{{telefone.numero}}", "994568475")
                .replace("{{telefone.tipoTelefone}}", "CELULAR");

        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CONFLICT.value())
                .body("mensagem", is("CPF EM USO"));
    }

    @Test
    public void findById_Retornando404_NOTFOUND_QuandoIdIncorreto() {
        given()
                .pathParam("clienteId", "710a52bf-21bd-496e-9097-a31d58700f59")
                .when()
                .get("/{clienteId}")
                .then()
                .body("mensagem", is("CLIENTE NÃO ENCONTRADO"))
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void findByPage_ParametroNome_Retornando200OK() {
        given()
                .param("orderBy", "nome")
                .param("direction", "DESC")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("totalElements", is(2))
                .body("$", hasKey("content"))
                .body("content", everyItem(hasKey("id")))
                .body("content", everyItem(hasKey("nome")))
                .body("content", everyItem(hasKey("cpf")))
                .body("content", everyItem(hasKey("dataNascimento")))
                .body("content", everyItem(hasKey("telefoneCompleto")))
                .body("content[0].size()", is(5))
                .body("content[0].id", is(cliente1.getId().toString()))
                .body("content[0].nome", is("Victor Pietro"))
                .body("content[0].cpf", is("05609692016"))
                .body("content[0].dataNascimento", is("1995-04-01"))
                .body("content[0].telefoneCompleto", is("43994587546"))
                .body("content[1].size()", is(5))
                .body("content[1].id", is(cliente2.getId().toString()))
                .body("content[1].nome", is("Leonardo Arruda"))
                .body("content[1].cpf", is("59375822095"))
                .body("content[1].dataNascimento", is("1995-06-02"))
                .body("content[1].telefoneCompleto", is("11994562542"))
                .log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findByPage_ParametroCpf_Retornando200OK() {
        given()
                .param("orderBy", "cpf")
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
                .body("content", everyItem(hasKey("cpf")))
                .body("content", everyItem(hasKey("dataNascimento")))
                .body("content", everyItem(hasKey("telefoneCompleto")))
                .body("content[0].size()", is(5))
                .body("content[0].id", is(cliente1.getId().toString()))
                .body("content[0].nome", is("Victor Pietro"))
                .body("content[0].cpf", is("05609692016"))
                .body("content[0].dataNascimento", is("1995-04-01"))
                .body("content[0].telefoneCompleto", is("43994587546"))
                .body("content[1].size()", is(5))
                .body("content[1].id", is(cliente2.getId().toString()))
                .body("content[1].nome", is("Leonardo Arruda"))
                .body("content[1].cpf", is("59375822095"))
                .body("content[1].dataNascimento", is("1995-06-02"))
                .body("content[1].telefoneCompleto", is("11994562542"))
                .log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void atualizar_Retornando204NOCONTENT() {
        String payload = clienteJsonAtualizar
                .replace("{{nome}}", "Testecriar1")
                .replace("{{dataNascimento}}", "1994-04-03")
                .replace("{{endereco.cep}}", "86705000")
                .replace("{{endereco.cidade}}", "Siberrr")
                .replace("{{endereco.estado}}", "PR")
                .replace("{{endereco.logradouro}}", "Rua jababara")
                .replace("{{endereco.numero}}", "654")
                .replace("{{endereco.complemento}}", "")
                .replace("{{telefone.ddd}}", "43")
                .replace("{{telefone.numero}}", "994568475")
                .replace("{{telefone.tipoTelefone}}", "CELULAR");

        given()
                .pathParam("clienteId", cliente1.getId().toString())
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/{clienteId}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deletar_Retornando204() {
        given()
                .pathParam("clienteId", cliente1.getId().toString())
                .when()
                .delete("/{clienteId}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

}
