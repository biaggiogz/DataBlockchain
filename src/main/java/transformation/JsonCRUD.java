package transformation;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Ticker;
import org.json.JSONObject;

import javax.swing.text.html.HTMLDocument;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class JsonCRUD {

    public static JSONObject transformTickerBinance(String json) throws JsonProcessingException {

        JSONObject jo = new JSONObject(json);

        jo.remove("weightedAvgPrice");
        jo.remove("openPrice");
        jo.remove("highPrice");
        jo.remove("lowPrice");
        jo.remove("lastPrice");
        jo.remove("quoteVolume");
        jo.remove("openTime");
        jo.remove("closeTime");
        jo.remove("firstId");
        jo.remove("lastId");
        jo.remove("count");

//        ObjectMapper mapper = new ObjectMapper();
//        Ticker ticker = mapper.readerFor(Ticker.class).readValue(jo.toString());
        return  jo;
    }

    public static void writeJsonTickerBinance(JSONObject jo) throws IOException {
        Timestamp timestamp = new Timestamp(new  Date().getTime());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node2 =mapper.readTree(jo.toString());
        JsonFactory jf = new JsonFactory();
        JsonGenerator jg = jf.createGenerator(new File("src/main/resources/tickerBinance" + timestamp.toString() +".js"), JsonEncoding.UTF8);
        jg.useDefaultPrettyPrinter();
        mapper.writeTree(jg, node2);
    }

    public static JSONObject transformTickerCoinmarketCap(String json) throws JsonProcessingException {

        JSONObject jo = new JSONObject(json);



//        jo.remove("weightedAvgPrice");
//        jo.remove("openPrice");
//        jo.remove("highPrice");
//        jo.remove("lowPrice");
//        jo.remove("lastPrice");
//        jo.remove("quoteVolume");
//        jo.remove("openTime");
//        jo.remove("closeTime");
//        jo.remove("firstId");
//        jo.remove("lastId");
//        jo.remove("count");

//        ObjectMapper mapper = new ObjectMapper();
//        Ticker ticker = mapper.readerFor(Ticker.class).readValue(jo.toString());
        return  jo;
    }

    public static void writeJsonTickerCoinmarketCap(JSONObject jo) throws IOException {
        Timestamp timestamp = new Timestamp(new  Date().getTime());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node2 =mapper.readTree(jo.toString());
        JsonFactory jf = new JsonFactory();
        JsonGenerator jg = jf.createGenerator(new File("src/main/resources/tickerCoinmarketCap" + timestamp.toString() +".js"), JsonEncoding.UTF8);
        jg.useDefaultPrettyPrinter();
        mapper.writeTree(jg, node2);
    }
}
