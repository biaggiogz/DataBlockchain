package transformation;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;



public class JsonCRUD {




    public static List<JSONObject> transformTickerBinance(List<String> inputDataApi) throws JsonProcessingException {
        Instant now =  Instant.now();
        List<JSONObject> readyjsonObject =  Flux.fromIterable(inputDataApi)
                .parallel()
                .runOn(Schedulers.parallel())
                .map(json -> new JSONObject(json))
                .map(json -> json.put("timestamp",Timestamp.from(now).getTime()))
                .map(json -> json.put("time","1m"))
                .sequential()
                .collectList()
                .block();
        return  readyjsonObject;
    }

    public static void writeJsonTickerBinance(List<JSONObject> inputjsonObject) throws IOException {


          Flux.fromIterable(inputjsonObject)
                .parallel()
                .runOn(Schedulers.parallel())
                .map(json -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode node2 = mapper.readTree(json.toString());
                        JsonFactory jf = new JsonFactory();
                        JsonGenerator jg = jf.createGenerator(new File("src/main/resources/binance/tickerBinance" + new  Date().getTime() + json.get("symbol")+ ".js"), JsonEncoding.UTF8);
                        jg.useDefaultPrettyPrinter();
                        mapper.writeTree(jg, node2);
                        return node2;
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return json;
                })
                .sequential()
                  .subscribe();
    }
//----------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------
    public static List<JSONObject> transformTickerCoinmarketCap(List<String> inputDataApi) throws JsonProcessingException {
        List<String> listmanipulated = Flux.fromIterable(inputDataApi)
                .parallel()
                .runOn(Schedulers.parallel())
                .map(data -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        JsonNode l = objectMapper.readTree(data.toString());
                        return l;
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                    return null;
                })
                .map(jsonNode -> jsonNode.get("data").fields().next().getValue())
                .map(jsonNode -> jsonNode.toString().substring(1,jsonNode.toString().length()-1))
                .sequential()
                .collectList()
                .block();
        Instant now =  Instant.now();

        List<JSONObject> readyjsonObject =  Flux.fromIterable(listmanipulated)
                .parallel()
                .runOn(Schedulers.parallel())
                .map(string -> new JSONObject(string))
                .map(json -> json.put("timestamp",Timestamp.from(now).getTime()))
                .map(json -> json.put("time","1m"))
                .sequential()
                .collectList()
                .block();
      return readyjsonObject;
    }

    public static void writeJsonTickerCoinmarketCap(List<JSONObject> jo) throws IOException {
        Flux.fromIterable(jo)
                .parallel()
                .runOn(Schedulers.parallel())
                .map(ss -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode node2 = mapper.readTree(ss.toString());
                        JsonFactory jf = new JsonFactory();
                        JsonGenerator jg = jf.createGenerator(new File("src/main/resources/coinmarketCap/tickerCoinmarketCap"  + new  Date().getTime() + ss.get("symbol")+".js"), JsonEncoding.UTF8);
                        jg.useDefaultPrettyPrinter();
                        mapper.writeTree(jg, node2);
                        return node2;
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return ss;
                })
                .sequential()
                .subscribe();
    }
//----------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------
    public static List<JSONObject> transformTickerMEXC(List<String> inputDataApi) throws JsonProcessingException {
        Instant now =  Instant.now();
    List<JSONObject> readyjsonObject =  Flux.fromIterable(inputDataApi)
            .parallel()
            .runOn(Schedulers.parallel())
            .map(json -> new JSONObject(json))
            .map(json -> json.put("timestamp",Timestamp.from(now).getTime()))
            .map(json -> json.put("time","1m"))
            .sequential()
            .collectList()
            .block();
    return  readyjsonObject;
}

    public static void writeJsonTickerMEXC(List<JSONObject> inputjsonObject) throws IOException {

        Flux.fromIterable(inputjsonObject)
                .parallel()
                .runOn(Schedulers.parallel())
                .map(json -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode node2 = mapper.readTree(json.toString());
                        JsonFactory jf = new JsonFactory();
                        JsonGenerator jg = jf.createGenerator(new File("src/main/resources/MEXC/tickerMEXC" + new  Date().getTime() + json.get("symbol")+ json.get("symbol")+ ".js"), JsonEncoding.UTF8);
                        jg.useDefaultPrettyPrinter();
                        mapper.writeTree(jg, node2);
                        return node2;
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return json;
                })
                .sequential()
                .subscribe();


    }
}
