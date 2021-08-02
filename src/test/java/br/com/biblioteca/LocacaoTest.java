package br.com.biblioteca;

import br.com.biblioteca.domains.autor.Autor;
import br.com.biblioteca.domains.autor.AutorRepository;
import br.com.biblioteca.domains.cliente.*;
import br.com.biblioteca.domains.editora.Editora;
import br.com.biblioteca.domains.editora.EditoraRepository;
import br.com.biblioteca.domains.estoque.Estoque;
import br.com.biblioteca.domains.estoque.EstoqueRepository;
import br.com.biblioteca.domains.genero_literario.GeneroLiterario;
import br.com.biblioteca.domains.genero_literario.GeneroLiterarioRepository;
import br.com.biblioteca.domains.livro.Livro;
import br.com.biblioteca.domains.livro.LivroRepository;
import br.com.biblioteca.domains.locacao.Locacao;
import br.com.biblioteca.domains.locacao.LocacaoRepository;
import br.com.biblioteca.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LocacaoTest extends IntegrationTestConfiguration {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private GeneroLiterarioRepository generoLiterarioRepository;
    @Autowired
    private EditoraRepository editoraRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EstoqueRepository estoqueRepository;
    @Autowired
    private LocacaoRepository locacaoRepository;

    private String locacaoJson, locacaoJsonAtualizar;
    private GeneroLiterario generoLiterario1;
    private Autor autor1;
    private Editora editora1;
    private Livro livro1, livro2, livro3, livro4, livro5, livro6;
    private Cliente cliente1, cliente2;
    private Estoque estoque1, estoque2, estoque3, estoque4;
    private String livro1Id, livro2Id, livro3Id, livro4Id, livro5Id, livro6Id;
    private String cliente1Id, cliente2Id;
    private String editora1Id;
    private String autor1Id;
    private String generoLiterario1Id;
    private String locacao1Id, locacao2Id;
    private Locacao locacao1, locacao2;

    @Before
    public void setUp() {
        super.setUp();
        RestAssured.basePath = "/locacoes";
        locacaoJson = ResourceUtils.getContentFromResource("/json/criar-locacao.json");
        locacaoJsonAtualizar = ResourceUtils.getContentFromResource("/json/atualizar-locacao.json");
        prepararDados();
    }

    private void prepararDados() {
        this.generoLiterario1 = new GeneroLiterario("Romance");
        this.generoLiterario1Id = generoLiterario1.getId().toString();

        this.autor1 = new Autor("Lima Barreto", "Brasileiro", LocalDate.of(1881, 5, 13));
        this.autor1Id = autor1.getId().toString();

        this.editora1 = new Editora("Panini");
        this.editora1Id = editora1.getId().toString();

        this.cliente1 = new Cliente("Victor Pietro", "05609692016",
                LocalDate.of(1995, 4, 1),
                new Telefone("43", "994587546", TipoTelefone.CELULAR),
                new Endereco("Rua memes", "456", "86707005", "Arapongas", "PR", ""));
        this.cliente2 = new Cliente("Leonardo Arruda", "59375822095",
                LocalDate.of(1995, 6, 2),
                new Telefone("11", "994562542", TipoTelefone.CELULAR),
                new Endereco("Rua jajaja", "728", "86707004", "Sao Paulo", "SP", ""));
        this.cliente1Id = cliente1.getId().toString();
        this.cliente2Id = cliente2.getId().toString();

        this.livro1 = new Livro("O triste fim de Policarpo Quaresma", LocalDate.of(1915, 1, 1), editora1, generoLiterario1, "28172874989", autor1);
        this.livro2 = new Livro("O triste fim da minha ereção", LocalDate.of(2018, 1, 1), editora1, generoLiterario1, "28172874989", autor1);
        this.livro3 = new Livro("O triste fim da minha ereção", LocalDate.of(2018, 1, 1), editora1, generoLiterario1, "28172874989", autor1);
        this.livro4 = new Livro("O triste fim da minha ereção", LocalDate.of(2018, 1, 1), editora1, generoLiterario1, "28172874989", autor1);
        this.livro5 = new Livro("O triste fim da minha ereção", LocalDate.of(2018, 1, 1), editora1, generoLiterario1, "28172874989", autor1);
        this.livro6 = new Livro("O triste fim da minha ereção", LocalDate.of(2018, 1, 1), editora1, generoLiterario1, "28172874989", autor1);
        this.livro1Id = livro1.getId().toString();
        this.livro2Id = livro2.getId().toString();
        this.livro3Id = livro3.getId().toString();
        this.livro4Id = livro4.getId().toString();
        this.livro5Id = livro5.getId().toString();
        this.livro6Id = livro6.getId().toString();

        this.estoque1 = new Estoque(livro1, BigDecimal.TEN);
        this.estoque2 = new Estoque(livro2, BigDecimal.TEN);
        this.estoque3 = new Estoque(livro3, BigDecimal.ZERO);
        this.estoque4 = new Estoque(livro4, BigDecimal.ZERO);

        List<Livro> livrosAtualizar = List.of(livro1, livro2);
        this.locacao1 = new Locacao(LocalDateTime.of(2021, 7, 10, 14, 0, 0), LocalDateTime.of(2030, 7, 10, 14, 0, 0), LocalDateTime.of(2030, 7, 10, 14, 0, 0), cliente1, livrosAtualizar);
        this.locacao2 = new Locacao(LocalDateTime.of(2021, 7, 10, 14, 0, 0), LocalDateTime.of(2030, 7, 10, 14, 0, 0), LocalDateTime.of(2030, 7, 10, 14, 0, 0), cliente1, livrosAtualizar);
        locacao1Id = locacao1.getId().toString();
        locacao2Id = locacao2.getId().toString();

        List<Livro> livros = List.of(livro1, livro2, livro3, livro4);
        List<Cliente> clientes = List.of(cliente1, cliente2);
        List<Estoque> estoques = List.of(estoque1, estoque2, estoque3, estoque4);
        List<Locacao> locacoes = List.of(locacao1, locacao2);

        generoLiterarioRepository.save(generoLiterario1);
        editoraRepository.save(editora1);
        autorRepository.save(autor1);
        livroRepository.saveAll(livros);
        clienteRepository.saveAll(clientes);
        estoqueRepository.saveAll(estoques);
        locacaoRepository.saveAll(locacoes);
    }

    @Test
    public void cadastrarLocacao_Retornando201CREATED() {
        String payload = locacaoJson
                .replace("{{clienteId}}", cliente1Id)
                .replace("{{livro1Id}}", livro1Id)
                .replace("{{livro2Id}}", livro2Id)
                .replace("{{dataLocacao}}", "2021-07-15T14:21:00")
                .replace("{{dataPrevistaDevolucao}}", "2030-07-16T14:21:00");

        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());

        List<UUID> livrosIds = List.of(livro1.getId(), livro2.getId());
        List<Estoque> estoques = estoqueRepository.findByLivroIdInOrderByLivroNomeAsc(livrosIds);
        Estoque estoqueA = estoques.get(0);
        Estoque estoqueB = estoques.get(1);
        Assert.assertEquals(estoqueA.getQuantidadeDisponivel(), BigDecimal.valueOf(9));
        Assert.assertEquals(estoqueB.getQuantidadeDisponivel(), BigDecimal.valueOf(9));
    }

    @Test
    public void cadastrarLocacao_Retornando400BADREQUEST_QuandoDataDevolucaoPrevistaFinalDeSemana() {
        String payload = locacaoJson
                .replace("{{clienteId}}", cliente1Id)
                .replace("{{livro1Id}}", livro1Id)
                .replace("{{livro2Id}}", livro2Id)
                .replace("{{dataLocacao}}", "2021-07-15T14:21:00")
                .replace("{{dataPrevistaDevolucao}}", "2030-07-20T14:21:00");

        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("DEVOLUÇÕES APENAS EM DIAS ÚTEIS"));
    }

    @Test
    public void cadastrarLocacao_Retornando400BADREQUEST_QuandoLivroSemEstoque() {
        String payload = locacaoJson
                .replace("{{clienteId}}", cliente1Id)
                .replace("{{livro1Id}}", livro3Id)
                .replace("{{livro2Id}}", livro4Id)
                .replace("{{dataLocacao}}", "2021-07-15T14:21:00")
                .replace("{{dataPrevistaDevolucao}}", "2030-07-16T14:21:00");

        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("mensagem", is("LIVRO INDISPONÍVEL NO ESTOQUE"));
    }

    @Test
    public void cadastrarLocacao_Retornando400BADREQUEST_QuandoLivroSemEstoqueAtribuido() {
        String payload = locacaoJson
                .replace("{{clienteId}}", cliente1Id)
                .replace("{{livro1Id}}", livro5Id)
                .replace("{{livro2Id}}", livro6Id)
                .replace("{{dataLocacao}}", "2021-07-15T14:21:00")
                .replace("{{dataPrevistaDevolucao}}", "2030-07-16T14:21:00");

        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body("mensagem", is("UM OU MAIS LIVROS NÃO ESTÃO ATRIBUIDOS À UM ESTOQUE"));
    }

    @Test
    public void findByPage_ParametroClienteNome_Retornando200OK() {
        given()
                .param("orderBy", "cliente.nome")
                .param("direction", "DESC")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("totalElements", is(2))
                .body("$", hasKey("content"))
                .body("content", everyItem(hasKey("id")))
                .body("content", everyItem(hasKey("dataLocacao")))
                .body("content", everyItem(hasKey("dataPrevistaDevolucao")))
                .body("content", everyItem(hasKey("dataDevolucao")))
                .body("content", everyItem(hasKey("situacao")))
                .body("content", everyItem(hasKey("clienteNome")))
                .body("content[0].size()", is(6))
                .body("content[0].id", is(locacao1Id))
                .body("content[0].dataLocacao", is("2021-07-10T14:00:00"))
                .body("content[0].dataPrevistaDevolucao", is("2030-07-10T14:00:00"))
                .body("content[0].dataDevolucao", is("2030-07-10T14:00:00"))
                .body("content[0].situacao", is("EM_ANDAMENTO"))
                .body("content[0].clienteNome", is("Victor Pietro"))
                .body("content[1].size()", is(6))
                .body("content[1].id", is(locacao2Id))
                .body("content[1].dataLocacao", is("2021-07-10T14:00:00"))
                .body("content[1].dataPrevistaDevolucao", is("2030-07-10T14:00:00"))
                .body("content[1].dataDevolucao", is("2030-07-10T14:00:00"))
                .body("content[1].situacao", is("EM_ANDAMENTO"))
                .body("content[1].clienteNome", is("Victor Pietro"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findByPage_ParametroSituacao_Retornando200OK() {
        given()
                .param("orderBy", "situacao")
                .param("direction", "DESC")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("totalElements", is(2))
                .body("$", hasKey("content"))
                .body("content", everyItem(hasKey("id")))
                .body("content", everyItem(hasKey("dataLocacao")))
                .body("content", everyItem(hasKey("dataPrevistaDevolucao")))
                .body("content", everyItem(hasKey("dataDevolucao")))
                .body("content", everyItem(hasKey("situacao")))
                .body("content", everyItem(hasKey("clienteNome")))
                .body("content[0].size()", is(6))
                .body("content[0].id", is(locacao1Id))
                .body("content[0].dataLocacao", is("2021-07-10T14:00:00"))
                .body("content[0].dataPrevistaDevolucao", is("2030-07-10T14:00:00"))
                .body("content[0].dataDevolucao", is("2030-07-10T14:00:00"))
                .body("content[0].situacao", is("EM_ANDAMENTO"))
                .body("content[0].clienteNome", is("Victor Pietro"))
                .body("content[1].size()", is(6))
                .body("content[1].id", is(locacao2Id))
                .body("content[1].dataLocacao", is("2021-07-10T14:00:00"))
                .body("content[1].dataPrevistaDevolucao", is("2030-07-10T14:00:00"))
                .body("content[1].dataDevolucao", is("2030-07-10T14:00:00"))
                .body("content[1].situacao", is("EM_ANDAMENTO"))
                .body("content[1].clienteNome", is("Victor Pietro"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findByPage_ParametroDataLocacao_Retornando200OK() {
        given()
                .param("orderBy", "dataLocacao")
                .param("direction", "DESC")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("totalElements", is(2))
                .body("$", hasKey("content"))
                .body("content", everyItem(hasKey("id")))
                .body("content", everyItem(hasKey("dataLocacao")))
                .body("content", everyItem(hasKey("dataPrevistaDevolucao")))
                .body("content", everyItem(hasKey("dataDevolucao")))
                .body("content", everyItem(hasKey("situacao")))
                .body("content", everyItem(hasKey("clienteNome")))
                .body("content[0].size()", is(6))
                .body("content[0].id", is(locacao1Id))
                .body("content[0].dataLocacao", is("2021-07-10T14:00:00"))
                .body("content[0].dataPrevistaDevolucao", is("2030-07-10T14:00:00"))
                .body("content[0].dataDevolucao", is("2030-07-10T14:00:00"))
                .body("content[0].situacao", is("EM_ANDAMENTO"))
                .body("content[0].clienteNome", is("Victor Pietro"))
                .body("content[1].size()", is(6))
                .body("content[1].id", is(locacao2Id))
                .body("content[1].dataLocacao", is("2021-07-10T14:00:00"))
                .body("content[1].dataPrevistaDevolucao", is("2030-07-10T14:00:00"))
                .body("content[1].dataDevolucao", is("2030-07-10T14:00:00"))
                .body("content[1].situacao", is("EM_ANDAMENTO"))
                .body("content[1].clienteNome", is("Victor Pietro"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findByPage_ParametroDataDevolucao_Retornando200OK() {
        given()
                .param("orderBy", "dataDevolucao")
                .param("direction", "DESC")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("totalElements", is(2))
                .body("$", hasKey("content"))
                .body("content", everyItem(hasKey("id")))
                .body("content", everyItem(hasKey("dataLocacao")))
                .body("content", everyItem(hasKey("dataPrevistaDevolucao")))
                .body("content", everyItem(hasKey("dataDevolucao")))
                .body("content", everyItem(hasKey("situacao")))
                .body("content", everyItem(hasKey("clienteNome")))
                .body("content[0].size()", is(6))
                .body("content[0].id", is(locacao1Id))
                .body("content[0].dataLocacao", is("2021-07-10T14:00:00"))
                .body("content[0].dataPrevistaDevolucao", is("2030-07-10T14:00:00"))
                .body("content[0].dataDevolucao", is("2030-07-10T14:00:00"))
                .body("content[0].situacao", is("EM_ANDAMENTO"))
                .body("content[0].clienteNome", is("Victor Pietro"))
                .body("content[1].size()", is(6))
                .body("content[1].id", is(locacao2Id))
                .body("content[1].dataLocacao", is("2021-07-10T14:00:00"))
                .body("content[1].dataPrevistaDevolucao", is("2030-07-10T14:00:00"))
                .body("content[1].dataDevolucao", is("2030-07-10T14:00:00"))
                .body("content[1].situacao", is("EM_ANDAMENTO"))
                .body("content[1].clienteNome", is("Victor Pietro"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findByPage_ParametroDataPrevistaDevolucao_Retornando200OK() {
        given()
                .param("orderBy", "dataPrevistaDevolucao")
                .param("direction", "DESC")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("totalElements", is(2))
                .body("$", hasKey("content"))
                .body("content", everyItem(hasKey("id")))
                .body("content", everyItem(hasKey("dataLocacao")))
                .body("content", everyItem(hasKey("dataPrevistaDevolucao")))
                .body("content", everyItem(hasKey("dataDevolucao")))
                .body("content", everyItem(hasKey("situacao")))
                .body("content", everyItem(hasKey("clienteNome")))
                .body("content[0].size()", is(6))
                .body("content[0].id", is(locacao1Id))
                .body("content[0].dataLocacao", is("2021-07-10T14:00:00"))
                .body("content[0].dataPrevistaDevolucao", is("2030-07-10T14:00:00"))
                .body("content[0].dataDevolucao", is("2030-07-10T14:00:00"))
                .body("content[0].situacao", is("EM_ANDAMENTO"))
                .body("content[0].clienteNome", is("Victor Pietro"))
                .body("content[1].size()", is(6))
                .body("content[1].id", is(locacao2Id))
                .body("content[1].dataLocacao", is("2021-07-10T14:00:00"))
                .body("content[1].dataPrevistaDevolucao", is("2030-07-10T14:00:00"))
                .body("content[1].dataDevolucao", is("2030-07-10T14:00:00"))
                .body("content[1].situacao", is("EM_ANDAMENTO"))
                .body("content[1].clienteNome", is("Victor Pietro"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void atualizar_Retornando204NOCONTENT() {
        String payload = locacaoJsonAtualizar
                .replace("{{dataDevolucao}}", "2030-07-16T14:21:00");

        given()
                .pathParam("locacaoId", locacao1Id)
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/{locacaoId}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        List<UUID> livrosIds = List.of(livro1.getId(), livro2.getId());
        List<Estoque> estoques = estoqueRepository.findByLivroIdInOrderByLivroNomeAsc(livrosIds);
        Estoque estoqueA = estoques.get(0);
        Estoque estoqueB = estoques.get(1);
        Assert.assertEquals(estoqueA.getQuantidadeDisponivel(), BigDecimal.valueOf(11));
        Assert.assertEquals(estoqueB.getQuantidadeDisponivel(), BigDecimal.valueOf(11));
    }

    @Test
    public void atualizar_Retornando404NOTFOUND_QuandoIdIncorreto() {
        String payload = locacaoJsonAtualizar
                .replace("{{dataDevolucao}}", "2030-07-16T14:21:00");

        given()
                .pathParam("locacaoId", "f989621e-c601-4123-8f3e-fc1ab06a8bff")
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/{locacaoId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("mensagem", is("LOCAÇÃO NÃO ENCONTRADA"));
    }

}
