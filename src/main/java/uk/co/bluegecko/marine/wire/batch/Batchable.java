package uk.co.bluegecko.marine.wire.batch;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import uk.co.bluegecko.marine.wire.geo.Continent;
import uk.co.bluegecko.marine.wire.geo.Country;
import uk.co.bluegecko.marine.wire.geo.Currency;

@JsonSubTypes({@Type(Continent.class), @Type(Country.class), @Type(Currency.class)})
public interface Batchable {

}