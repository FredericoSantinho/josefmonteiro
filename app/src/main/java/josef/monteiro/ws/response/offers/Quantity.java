package josef.monteiro.ws.response.offers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL) @JsonPropertyOrder({
    "base", "fee"
}) public class Quantity {

  @JsonProperty("base") public Integer base;
  @JsonProperty("fee") public Float fee;
}
