package transformation;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.xspec.S;
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
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
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

                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode jsonNode = objectMapper.readTree(json.toString());

                        String value1 = jsonNode.get("IDB").asText();
                        String value2 = jsonNode.get("symbol").asText();
                        String value3 = jsonNode.get("ID").asText();
                        Double value4 = jsonNode.get("priceChange").asDouble();
                        Double value5 = jsonNode.get("quoteVolume").asDouble();
                        Double value6 = jsonNode.get("volume").asDouble();
                        Double value7 = jsonNode.get("priceChangePercent").asDouble();
                        String value8 = jsonNode.get("timestamp").asText();
                        String value9 = jsonNode.get("time").asText();


                        StringWriter stringWriter = new StringWriter();

                        CSVPrinter csvPrinter = new CSVPrinter(stringWriter, CSVFormat.DEFAULT);
                        List<String> headers = Arrays.asList("IDB","symbol","ID","priceChange","quoteVolume","volume","priceChangePercent","timestamp","time");

                        csvPrinter.printRecord(headers);

                        csvPrinter.printRecord(value1, value2, value3,value4,value5,value6,value7,value8,value9);

                        csvPrinter.close();

                        String csvData = stringWriter.toString();

                        String bucketName = "s3-raw-data-drypto";
                        String s3Key = "databaseCrypto/binance/tickerBinance" + new Date().getTime() + json.get("symbol") + ".csv";

                        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();

                        ObjectMetadata metadata = new ObjectMetadata();
                        metadata.setContentLength(csvData.length());

                        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3Key, new ByteArrayInputStream(csvData.getBytes()), metadata);

                        s3Client.putObject(putObjectRequest);


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
                        json.put("symbolcm", json.get("symbol").toString() + "USDT");
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
        readyjsonObject = eliminarCampo(readyjsonObject, "symbol");
      return readyjsonObject;
    }


    public static void writeJsonTickerCoinmarketCap(List<JSONObject> jo) throws IOException {

        Flux.fromIterable(jo)
                .parallel()
                .runOn(Schedulers.parallel())
                .map(ss -> {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode jsonNode = objectMapper.readTree(ss.toString());

                        String value1 = jsonNode.get("IDB").asText();
                           String value2 = jsonNode.get("symbolcm").asText();
                          String value3 = jsonNode.get("ID").asText();
                        Double value4 = jsonNode.get("percent_change_1h").asDouble();
                        Double value5 = jsonNode.get("percent_change_24h").asDouble();
                        Double value6 = jsonNode.get("market_cap").asDouble();
                        Double value7 = jsonNode.get("volume_change_24h").asDouble();
                        Double value8 = jsonNode.get("price").asDouble();
                        Double value9 = jsonNode.get("volume_24h").asDouble();
                        String value10 = jsonNode.get("timestamp").asText();
                        String value11 = jsonNode.get("fully_diluted_market_cap").asText();
                        Double value12 = jsonNode.get("circulating_supply").asDouble();
                        Double value13 = jsonNode.get("total_supply").asDouble();
                        String value14 = jsonNode.get("time").asText();

                        StringWriter stringWriter = new StringWriter();

                        CSVPrinter csvPrinter = new CSVPrinter(stringWriter, CSVFormat.DEFAULT);
                        List<String> headers = Arrays.asList("IDB","symbolcm","ID","percent_change_1h","percent_change_24h",
                                "market_cap","volume_change_24h","price","volume_24h","timestamp",
                                "fully_diluted_market_cap","circulating_supply","total_supply",
                                "time");

                        csvPrinter.printRecord(headers);

                        csvPrinter.printRecord(value1, value2, value3,value4,value5,value6,value7,value8,value9,value10,value11,value12,value13,value14);

                        csvPrinter.close();

                        String csvData = stringWriter.toString();

                        String bucketName = "s3-raw-data-drypto";
                        String s3Key = "databaseCrypto/coinmarketCap/tickerCoinmarketCap" + new Date().getTime() + ss.get("symbolcm") + ".csv";

                        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();

                        ObjectMetadata metadata = new ObjectMetadata();
                        metadata.setContentLength(csvData.length());

                        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3Key, new ByteArrayInputStream(csvData.getBytes()), metadata);

                        s3Client.putObject(putObjectRequest);


                        return jsonNode;
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

                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode jsonNode = objectMapper.readTree(json.toString());

                        String value1 = jsonNode.get("IDB").asText();
                        String value2 = jsonNode.get("symbol").asText();
                        String value3 = jsonNode.get("ID").asText();
                        Double value4 = jsonNode.get("priceChange").asDouble();
                        Double value5 = jsonNode.get("quoteVolume").asDouble();
                        Double value6 = jsonNode.get("volume").asDouble();
                        Double value7 = jsonNode.get("priceChangePercent").asDouble();
                        String value8 = jsonNode.get("timestamp").asText();
                        String value9 = jsonNode.get("time").asText();

                        StringWriter stringWriter = new StringWriter();

                        CSVPrinter csvPrinter = new CSVPrinter(stringWriter, CSVFormat.DEFAULT);
                        List<String> headers = Arrays.asList("IDB","symbol","ID","priceChange","quoteVolume","volume","priceChangePercent","timestamp","time");

                        csvPrinter.printRecord(headers);

                        csvPrinter.printRecord(value1, value2, value3,value4,value5,value6,value7,value8,value9);

                        csvPrinter.close();

                        String csvData = stringWriter.toString();

                        String bucketName = "s3-raw-data-drypto";
                        String s3Key = "databaseCrypto/mexc/tickerMexc" + new Date().getTime() + json.get("symbol") + ".csv";

                        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();

                        ObjectMetadata metadata = new ObjectMetadata();
                        metadata.setContentLength(csvData.length());

                        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3Key, new ByteArrayInputStream(csvData.getBytes()), metadata);

                        s3Client.putObject(putObjectRequest);


                        return jsonNode;
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

    public static List<JSONObject> eliminarCampo(List<JSONObject> jsonData, String campoAEliminar) {
        for (JSONObject json : jsonData) {
            json.remove(campoAEliminar);
        }
        return jsonData;
    }

    public  static AmazonS3 getS3Client() {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion("eu-central-1") // Reemplaza con tu región
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("AKIATTU5R5N2BLFBUHKU", "+PYQDSgceA9l6w5oCtY2bh+k1grzrO7cFRnnxHxR"))) // Reemplaza con tus credenciales
                .build();
        return s3Client;
    }
}
