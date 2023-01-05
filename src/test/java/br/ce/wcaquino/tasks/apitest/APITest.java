package br.ce.wcaquino.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {

	@BeforeClass
	public static void setup() {
		// Setando a URL base para n�o precisar repetir nas requisi��es
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}

	@Test
	public void deveRetornarTarefas() {
		RestAssured //
			.given() //
			//.log().all() // loga tudo do given, se fosse depois do when logaria do when e do then o mesmo vale
			.when() //
				.get("/todo") //
			.then() //
			//.log().all() // loga algumas informa��es e o body, pode ser usado em caso de erro da api
				.statusCode(200);
	}
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		RestAssured //
			.given() //
				.body("{\"task\": \"Teste via API\",\"dueDate\": \"2100-01-01\"}")
				.contentType(ContentType.JSON)
			.when() //
				.post("/todo") //
			.then() //
				.statusCode(201);
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		// Aqui poderiamos testar os cen�rios de cria��o sem data ou descri��o e validar as respostas, por�m j� foram feitos nos testes JUnit
		RestAssured //
			.given() //
				.body("{\"task\": \"Teste via API\",\"dueDate\": \"2000-01-01\"}")
				.contentType(ContentType.JSON)
			.when() //
				.post("/todo") //
			.then() //
				.body("message",CoreMatchers.is("Due date must not be in past")) // Aqui n�o podemos usar a string direto, mas sim um Matcher
				.statusCode(400);
	}

}

