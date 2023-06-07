package transformation;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;



public class JsonCRUD {

    @Autowired
    private AmazonS3 amazonS3Client;

    public  static AmazonS3 getS3Client() {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion("eu-central-1") // Reemplaza con tu región
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("AKIATTU5R5N2BLFBUHKU", "+PYQDSgceA9l6w5oCtY2bh+k1grzrO7cFRnnxHxR"))) // Reemplaza con tus credenciales
                .build();
        return s3Client;
    }




    public static List<JSONObject> transformTickerBinance(List<String> inputDataApi) throws JsonProcessingException {
        Instant now1 =  Instant.now();
        List<JSONObject> readyjsonObject =  Flux.fromIterable(inputDataApi)
                .parallel()
                .runOn(Schedulers.parallel())
                .map(json -> new JSONObject(json))
                .map(json -> json.put("timestamp",Timestamp.from(now1).getTime()))
                .map(json -> json.put("time","1m"))
                .map(json -> json.put("ID",SequenceID.getid().block()))
                .map(json -> json.put("IDB",SequenceID.sequenceid()))
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
                        StringWriter stringWriter = new StringWriter();
                        ObjectMapper mapper1 = new ObjectMapper();
                        JsonNode node2 = mapper1.readTree(json.toString());
                        JsonFactory jf = new JsonFactory();

                        JsonGenerator jsonGenerator = jf.createGenerator(stringWriter);

                        mapper1.writeTree(jsonGenerator, node2);
                        jsonGenerator.close();
                        String jsonContent = stringWriter.toString();

                        String s3Key = "databaseCrypto/binance/tickerbinance" + new Date().getTime() + json.get("symbol") + ".json";
                        InputStream inputStream = new ByteArrayInputStream(jsonContent.getBytes());
                        ObjectMetadata metadata1 = new ObjectMetadata();
                        metadata1.setContentLength(jsonContent.length());
                        PutObjectRequest putObjectRequest = new PutObjectRequest("s3-raw-data-drypto", s3Key, inputStream, metadata1);
                        PutObjectResult putObjectResult = getS3Client().putObject(putObjectRequest);


//                        JsonGenerator jg = jf.createGenerator(new File("src/main/resources/binance/tickerBinance" + new  Date().getTime() + json.get("symbol")+ ".js"), JsonEncoding.UTF8);
//                        jg.useDefaultPrettyPrinter();
//                        mapper.writeTree(jg, node2);
//                        return node2;
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

    public static List<JSONObject> eliminarCampo(List<JSONObject> jsonData, String campoAEliminar) {
        for (JSONObject json : jsonData) {
            json.remove(campoAEliminar);
        }
        return jsonData;
    }



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
        Instant now2 =  Instant.now();


        List<JSONObject> readyjsonObject =  Flux.fromIterable(listmanipulated)
                .parallel()
                .runOn(Schedulers.parallel())
                .map(string -> new JSONObject(string))
                .map(json -> {
                    JSONObject quoteObject = json.getJSONObject("quote");
                    JSONObject quoteUSDObject = quoteObject.getJSONObject("USD");
                    json.put("fully_diluted_market_cap", quoteUSDObject.getFloat("fully_diluted_market_cap"));
                    json.put("percent_change_1h", quoteUSDObject.getFloat("percent_change_1h"));
                    json.put("percent_change_24h", quoteUSDObject.getFloat("percent_change_24h"));
                    json.put("market_cap", quoteUSDObject.getFloat("market_cap"));
                    json.put("volume_change_24h", quoteUSDObject.getFloat("volume_change_24h"));
                    json.put("price", quoteUSDObject.getFloat("price"));
                    json.put("volume_24h", quoteUSDObject.getFloat("volume_24h"));
                    // Agrega más campos individuales según sea necesario
                    return json;
                })
                .map(json -> json.put("timestamp",Timestamp.from(now2).getTime()))
                .map(json -> json.put("time","1m"))
                .map(json -> json.put("ID",SequenceID.getid().block()))
                .map(json -> json.put("IDB",SequenceID.sequenceid()))
                .sequential()
                .collectList()
                .block();
        readyjsonObject = eliminarCampo(readyjsonObject, "quote");
      return readyjsonObject;
    }

    public static void csv( String m) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(m);
        StringBuilder csvData = new StringBuilder();
        csvData.append("priceChange,symbol,IDB\n");

        String value1 = jsonNode.get("priceChange").asText();
        String value2 = jsonNode.get("symbol").asText();
        String value3 = jsonNode.get("IDB").asText();

        csvData.append(value1).append(",").append(value2).append(",").append(value3).append("\n");


        // Ejemplo: Guardar el contenido en un archivo
        String csvFileName = "src/main/resources/test2.csv";
        Files.write(Paths.get(csvFileName), csvData.toString().getBytes());


    }

    public static void writeJsonTickerCoinmarketCap(List<JSONObject> jo) throws IOException {





        Flux.fromIterable(jo)
                .parallel()
                .runOn(Schedulers.parallel())
                .map(ss -> {
                    try {
                        StringWriter stringWriter = new StringWriter();
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode node2 = mapper.readTree(ss.toString());
                        JsonFactory jf = new JsonFactory();
                        JsonGenerator jsonGenerator = jf.createGenerator(stringWriter);
                        mapper.writeTree(jsonGenerator, node2);
                        jsonGenerator.close();
                        String jsonContent = stringWriter.toString();

                        String s3Key = "databaseCrypto/coinmarketCap/tickerCoinmarketCap" + new Date().getTime() + ss.get("symbol") + ".json";
                        InputStream inputStream2 = new ByteArrayInputStream(jsonContent.getBytes());
                        ObjectMetadata metadata = new ObjectMetadata();
                        metadata.setContentLength(jsonContent.length());
                        PutObjectRequest putObjectRequest = new PutObjectRequest("s3-raw-data-drypto", s3Key, inputStream2, metadata);
                        PutObjectResult putObjectResult = getS3Client().putObject(putObjectRequest);




//                        JsonGenerator jg = jf.createGenerator(new File("src/main/resources/coinmarketCap/tickerCoinmarketCap"  + new  Date().getTime() + ss.get("symbol")+".js"), JsonEncoding.UTF8);
//                        jg.useDefaultPrettyPrinter();
//                        mapper.writeTree(jg, node2);
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
            .map(json -> json.put("ID",SequenceID.getid().block()))
            .map(json -> json.put("IDB",SequenceID.sequenceid()))
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
                        StringWriter stringWriter = new StringWriter();
                        ObjectMapper mapper3 = new ObjectMapper();
                        JsonNode node2 = mapper3.readTree(json.toString());
                        JsonFactory jf = new JsonFactory();
                        JsonGenerator jsonGenerator = jf.createGenerator(stringWriter);
                        mapper3.writeTree(jsonGenerator, node2);
                        jsonGenerator.close();
                        String jsonContent = stringWriter.toString();

                        String s3Key = "databaseCrypto/mexc/tickerMEXC" + new Date().getTime() + json.get("symbol") + ".json";
                        InputStream inputStream3 = new ByteArrayInputStream(jsonContent.getBytes());
                        ObjectMetadata metadata = new ObjectMetadata();
                        metadata.setContentLength(jsonContent.length());
                        PutObjectRequest putObjectRequest = new PutObjectRequest("s3-raw-data-drypto", s3Key, inputStream3, metadata);
                        PutObjectResult putObjectResult = getS3Client().putObject(putObjectRequest);



//                        JsonGenerator jg = jf.createGenerator(new File("src/main/resources/MEXC/tickerMEXC" + new  Date().getTime() + json.get("symbol")+ json.get("symbol")+ ".js"), JsonEncoding.UTF8);
//                        jg.useDefaultPrettyPrinter();
//                        mapper.writeTree(jg, node2);
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
