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
public record Currency(
		@Range(min = 0, max = 99999)
		Integer line,
		@NonNull @Length(min = 3, max = 3)
		String code,
		@NonNull @Length(min = 3)
		String name,
		@Range(min = 0, max = 999)
		int numericCode,
		@Range(min = 0, max = 4)
		int minor,
		String symbol) implements Batchable {

}