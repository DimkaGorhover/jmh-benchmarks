package benchmarks.json;

import benchmarks.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@Getter
@EqualsAndHashCode(doNotUseGetters = true)
public class JacksonUser implements User {

    private final int id;
    private final String name;
    private final String[] phones;
    private final List<Double> cards;
    private final Gender gender;

    @JsonCreator
    @Builder
    public JacksonUser(@JsonProperty("id") int id,
                       @JsonProperty("name") String name,
                       @JsonProperty("phones") String[] phones,
                       @JsonProperty("cards") List<Double> cards,
                       @JsonProperty("gender") Gender gender) {
        this.id = id;
        this.name = name;
        this.phones = phones;
        this.cards = cards;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("name", name)
                .append("phones", phones)
                .append("cards", cards)
                .append("gender", gender)
                .toString();
    }
}
