package josef.monteiro.ws.response.offers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL) @JsonPropertyOrder({
    "source", "exchanges"
}) public class Offers {

  @JsonProperty("source") public Source source;
  @JsonProperty("exchanges") public List<Exchange> exchanges = null;

}

