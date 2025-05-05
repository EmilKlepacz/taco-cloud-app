package sia.tacocloudclient.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

// Abstract interfaces like Pagable or Sort cannot be deserialized.
// to fix com.fasterxml.jackson.databind.exc.InvalidDefinitionException
@JsonIgnoreProperties({"pageable", "sort"})

public class HelperPage<T> extends PageImpl<T> {
    // 	@JsonCreator to tell Jackson to use that constructor to deserialize the page structure
    @JsonCreator
    public HelperPage(@JsonProperty("content") List<T> content,
                      @JsonProperty("number") int number,
                      @JsonProperty("size") int size,
                      @JsonProperty("totalElements") long totalElements) {
        super(content, PageRequest.of(number, size), totalElements);
    }
}
