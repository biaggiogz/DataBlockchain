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

    static String symbol = "LINK";

    public static JSONObject transformTickerBinance(String json) throws JsonProcessingException {
        Instant now =  Instant.now();
        JSONObject jo = new JSONObject(json);
        jo.put("timestamp",Timestamp.from(now).getTime());
        jo.put("volumebinance",Float.valueOf(jo.get("volume").toString()));
        jo.remove("volume");
        jo.put("time","1m");


//        ObjectMapper mapper = new ObjectMapper();
//        Ticker ticker = mapper.readerFor(Ticker.class).readValue(jo.toString());
        return  jo;
    }

    public static void writeJsonTickerBinance(JSONObject jo) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node2 =mapper.readTree(jo.toString());
        JsonFactory jf = new JsonFactory();
        JsonGenerator jg = jf.createGenerator(new File("src/main/resources/binance/tickerBinance" + new  Date().getTime() +".js"), JsonEncoding.UTF8);
        jg.useDefaultPrettyPrinter();
        mapper.writeTree(jg, node2);
    }

    public static JSONObject transformTickerCoinmarketCap(String json) throws JsonProcessingException {
        JsonNode node = new ObjectMapper().readTree(json);

        JSONObject jo2 = new JSONObject(node.toString());
        Instant now =  Instant.now();
        Long e = Timestamp.from(now).getTime();
        jo2.put("timestamp",e);
        jo2.put("symbol",symbol);
        jo2.put("time","1m");

        return  jo2;
    }

    public static JSONObject transformTickerBybit(String json) throws JsonProcessingException {

        JsonNode node = new ObjectMapper().readTree(json);
        String jsonn =  node.get("result").toString();
        JSONObject jo = new JSONObject(jsonn);
        jo.put("volumeBybit",Float.valueOf(jo.get("volume").toString()));
        jo.remove("volume");
        Instant now =  Instant.now();
        jo.put("timestamp",Timestamp.from(now).getTime());
        jo.put("time","1m");


        return  jo;
    }

    public static void writeJsonTickerCoinmarketCap(JSONObject jo) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node3 =mapper.readTree(jo.toString());
        JsonFactory jf = new JsonFactory();
        JsonGenerator jg = jf.createGenerator(new File("src/main/resources/coinmarketCap/tickerCoinmarketCap" + new  Date().getTime() +".js"), JsonEncoding.UTF8);
        jg.useDefaultPrettyPrinter();
        mapper.writeTree(jg, node3);
    }

    public static void writeJsonTickerBYbit(JSONObject jo) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node2 =mapper.readTree(jo.toString());
        JsonFactory jf = new JsonFactory();
        JsonGenerator jg = jf.createGenerator(new File("src/main/resources/byBit/tickerbyBit" + new  Date().getTime() +".js"), JsonEncoding.UTF8);
        jg.useDefaultPrettyPrinter();
        mapper.writeTree(jg, node2);
    }
}
