package uk.co.bluegecko.marine.wire.geo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.bluegecko.marine.test.jassert.Conditions;

import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.assertThat;

class ContinentTest {

	private ObjectMapper objectMapper;
	private Continent foo;

	@BeforeEach
	void setUp() {
		objectMapper = new ObjectMapper();
		foo = Continent.builder().code("F").name("Foo").build();
	}

	@Test
	void testBuilder() {
		assertThat(foo)
				.has(allOf(
						Conditions.extracted(Continent::code, "code", String::equals, "equals", "F"),
						Conditions.extracted(Continent::name, "name", String::equals, "equals", "Foo")));
	}

	@Test
	void testSerialization() throws JsonProcessingException {
		assertThat(objectMapper.writeValueAsString(foo))
				.isEqualTo("{\"code\":\"F\",\"name\":\"Foo\"}");
	}

}