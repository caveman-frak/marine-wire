package uk.co.bluegecko.marine.wire.geo;

import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.bluegecko.marine.test.jassert.Conditions.extracted;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContinentTest {

	private ObjectMapper objectMapper;
	private Continent foo;
	private String json;

	@BeforeEach
	void setUp() {
		objectMapper = JsonMapper.builder().build();
		foo = Continent.builder().code("F").name("Foo").build();
		json = """
				{"code":"F","name":"Foo"}""";
	}

	@Test
	void testBuilder() {
		assertThat(foo)
				.has(allOf(
						extracted(Continent::code, "code", "F"),
						extracted(Continent::name, "name", "Foo")));
	}

	@Test
	void testSerialization() throws JsonProcessingException {
		assertThat(objectMapper.writeValueAsString(foo))
				.isEqualTo(json);
	}

	@Test
	void testDeserialization() throws JsonProcessingException {
		assertThat(objectMapper.readValue(json, Continent.class))
				.isInstanceOf(Continent.class)
				.isEqualTo(foo);
	}

}