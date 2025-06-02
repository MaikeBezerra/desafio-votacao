package com.voting.app;

import com.voting.app.api.model.input.PautaAberturaInput;
import com.voting.app.api.model.input.PautaVotoInput;
import com.voting.app.domain.model.Pauta;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class PautaControllerTest {

    @LocalServerPort
    private int port;

    private static final String BASE_PATH = "/pautas";

    private static final String PARAM_PAUTA_ID = "pautaId";

    @Test
    void deveRetornarCodigo201_CriarNovaPauta() {
        Pauta pauta = new Pauta();

        given()
            .basePath(BASE_PATH)
            .port(port)
            .contentType(ContentType.JSON)
            .body(pauta)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void deveRetornarCodigo204_AbrirPautaComDuracaoEspecifica() {
        PautaAberturaInput input = new PautaAberturaInput();
        input.setDuracao(10);

        given()
            .basePath(BASE_PATH)
            .port(port)
            .contentType(ContentType.JSON)
            .pathParam(PARAM_PAUTA_ID, 1000)
            .body(input)
        .when()
            .put("/{pautaId}/abrir")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deveRetornarCodigo204_AbrirPautaSemDuracaoEspecifica() {
        given()
            .basePath(BASE_PATH)
            .port(port)
            .pathParam(PARAM_PAUTA_ID, 1000)
        .when()
            .put("/{pautaId}/abrir")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deveRetornarCodigo204_VotarPauta() {
        PautaVotoInput input = new PautaVotoInput();
        input.setIdAssociado("777");
        input.setValorVoto("Sim");

        given()
            .basePath(BASE_PATH)
            .port(port)
            .contentType(ContentType.JSON)
            .pathParam(PARAM_PAUTA_ID, 1000)
            .body(input)
        .when()
            .put("/{pautaId}/votar")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deveRetornarCodigo400_VotarEmPautaNaoAberta() {
        PautaVotoInput input = new PautaVotoInput();
        input.setIdAssociado("777");
        input.setValorVoto("Sim");

        given()
            .basePath(BASE_PATH)
            .port(port)
            .contentType(ContentType.JSON)
            .pathParam(PARAM_PAUTA_ID, 1001)
            .body(input)
        .when()
            .put("/{pautaId}/votar")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .log().all();
    }

    @Test
    void deveRetornarCodigo400_VotarEmPautaFechada() {
        PautaVotoInput input = new PautaVotoInput();
        input.setIdAssociado("777");
        input.setValorVoto("Sim");

        given()
            .basePath(BASE_PATH)
            .port(port)
            .contentType(ContentType.JSON)
            .pathParam(PARAM_PAUTA_ID, 1002)
            .body(input)
        .when()
            .put("/{pautaId}/votar")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .log().all();
    }

    @Test
    void deveRetornarCodigo400_TentarVotarNovamenteEmUmaPauta() {
        PautaVotoInput input = new PautaVotoInput();
        input.setIdAssociado("999");
        input.setValorVoto("Sim");

        given()
            .basePath(BASE_PATH)
            .port(port)
            .contentType(ContentType.JSON)
            .pathParam(PARAM_PAUTA_ID, 1000)
            .body(input)
        .when()
            .put("/{pautaId}/votar")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .log().all();
    }

    @Test
    void deveRetornarCodigo200_GerarResultado() {
        given()
            .basePath(BASE_PATH)
            .port(port)
            .pathParam(PARAM_PAUTA_ID, 1000)
        .when()
            .get("/{pautaId}/resultado")
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarCodigo400_GerarResultadoDePautaNaoCadastrada() {
        given()
            .basePath(BASE_PATH)
            .port(port)
            .pathParam(PARAM_PAUTA_ID, 999)
        .when()
            .get("/{pautaId}/resultado")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
