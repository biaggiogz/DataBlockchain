package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class Ticker {
    String symbol;
    Float priceChange,priceChangePercent,volume;

    public Ticker() {

    }

    @JsonCreator
    public Ticker(@JsonProperty("symbol") String symbol,
                    @JsonProperty("priceChange") Float priceChange,
                    @JsonProperty("priceChangePercent") Float priceChangePercent,
                    @JsonProperty("volume") Float volume){
        this.symbol = symbol;
        this.priceChange = priceChange;
        this.priceChangePercent = priceChangePercent;
        this.volume = volume;
    }


}
