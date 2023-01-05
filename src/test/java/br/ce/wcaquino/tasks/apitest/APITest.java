package br.ce.wcaquino.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {

	@BeforeClass
	public static void setup() {
		// Setando a URL base para não precisar repetir nas requisições
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
			//.log().all() // loga algumas informações e o body, pode ser usado em caso de erro da api
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
		// Aqui poderiamos testar os cenários de criação sem data ou descrição e validar as respostas, porém já foram feitos nos testes JUnit
		RestAssured //
			.given() //
				.body("{\"task\": \"Teste via API\",\"dueDate\": \"2000-01-01\"}")
				.contentType(ContentType.JSON)
			.when() //
				.post("/todo") //
			.then() //
				.body("message",CoreMatchers.is("Due date must not be in past")) // Aqui não podemos usar a string direto, mas sim um Matcher
				.statusCode(400);
	}

}

