package uk.co.bluegecko.marine.wire.geo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import uk.co.bluegecko.marine.wire.batch.Batchable;

@Builder(toBuilder = true)
@JsonInclude(value = Include.NON_NULL)
public record Country(
		@Range(min = 0, max = 99999)
		Integer line,
		@NonNull @Length(min = 2, max = 2)
		String code,
		@NonNull
		Continent continent,
		@NonNull @Length(min = 3)
		String name,
		@NonNull @Length(min = 3)
		String nativeName) implements Batchable {

}