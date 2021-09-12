package br.com.biblioteca;

import br.com.biblioteca.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AntonioNunesApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public abstract class IntegrationTestConfiguration {

    protected static final Pattern UUID_PATTERN = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @LocalServerPort
    protected int port;
    private String accessToken;

    public void setUp() {
        databaseCleaner.clearTables();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        loginAdmin();
    }

    private void loginAdmin() {
        RestAssured.basePath = "/oauth/token";
        pegarAccessToken();
    }

    private void pegarAccessToken() {
        accessToken = given()
                .contentType(ContentType.URLENC)
                .auth()
                .basic("biblioteca", "biblioteca@123")
                .formParam("grant_type", "password")
                .formParam("username", "admin@biblioteca.com")
                .formParam("password", "biblioteca@123")
                .when()
                .post()
                .andReturn()
                .jsonPath().getString("access_token");
    }

    protected String getIdHeaderLocation(Response response) {
        String location = response.getHeader("Location");
        Matcher matcher = UUID_PATTERN.matcher(location);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public RequestSpecification givenAuthenticated() {
        return given()
                .auth()
                .oauth2(this.accessToken);
    }

}
