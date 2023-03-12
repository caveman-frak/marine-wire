package uk.co.bluegecko.marine.wire.geo;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.assertThat;

class ContinentTest {

	Continent africa;

	@BeforeEach
	void setUp() {
		africa = Continent.builder().code("F").name("Foo").build();
	}

	@Test
	void testBuilder() {
		assertThat(africa)
				.has(allOf(
						new Condition<>(c -> c.code().equals("F"), "Code = F"),
						new Condition<>(c -> c.name().equals("Foo"), "Name = Foo")));
	}
}