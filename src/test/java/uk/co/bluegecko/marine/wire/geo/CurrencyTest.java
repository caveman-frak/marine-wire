package uk.co.bluegecko.marine.wire.geo;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.bluegecko.marine.test.jassert.Conditions.extracted;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CurrencyTest {

	private ObjectMapper objectMapper;
	private Currency ccy;
	private String json;

	@BeforeEach
	void setUp() {
		objectMapper = JsonMapper.builder().build();
		ccy = Currency.builder().code("GBP").name("Pound Sterling").numericCode(826).minor(2).symbol("£").build();
		json = """
				{"code":"GBP","name":"Pound Sterling","numericCode":826,"minor":2,"symbol":"£"}""";
	}

	@Test
	void testBuilder() {
		assertThat(ccy)
				.has(anyOf(extracted(Currency::code, "code", "GBP"),
						extracted(Currency::name, "code", "Pound Sterling"),
						extracted(Currency::numericCode, "numeric code", 826),
						extracted(Currency::minor, "minor", 2),
						extracted(Currency::symbol, "symbol", "£")));
	}

	@Test
	void testSerialise() throws JsonProcessingException {
		assertThat(objectMapper.writeValueAsString(ccy))
				.isEqualTo(json);
	}

	@Test
	void testDeserialise() throws JsonProcessingException {
		assertThat(objectMapper.readValue(json, Currency.class))
				.isEqualTo(ccy);

	}

}