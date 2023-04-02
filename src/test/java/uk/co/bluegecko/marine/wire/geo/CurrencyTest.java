package uk.co.bluegecko.marine.wire.geo;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.bluegecko.marine.test.jassert.Conditions.extracted;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class CurrencyTest {

	@Test
	void testBuilder() {
		assertThat(Currency.builder().code("GBP").name("Pound Sterling").numericCode(826).minor(2).symbol("£").build())
				.has(anyOf(extracted(Currency::code, "code", "GBP"),
						extracted(Currency::name, "code", "Pound Sterling"),
						extracted(Currency::numericCode, "numeric code", 826),
						extracted(Currency::minor, "minor", 2),
						extracted(Currency::symbol, "symbol", "£")));
	}

	@Test
	void testSerialise() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		Currency ccy =
				Currency.builder().code("GBP").name("Pound Sterling").numericCode(826).minor(2).symbol("£").build();
		assertThat(objectMapper.writeValueAsString(ccy))
				.isEqualTo(
						"""
								{"code":"GBP","name":"Pound Sterling","numericCode":826,"minor":2,"symbol":"£"}""");
	}

	@Test
	void testDeserialise() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		Currency ccy = objectMapper.readValue(
				"""
						{"code":"GBP","name":"Pound Sterling","numericCode":826,"minor":2,"symbol":"£"}""",
				Currency.class);
		assertThat(ccy)
				.isEqualTo(
						Currency.builder().code("GBP").name("Pound Sterling").numericCode(826).minor(2).symbol("£")
								.build());

	}

}