package actions;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.TimeZone;

public class Get {


        static  String symbol = "LINK";
        static String content = "Content-Type";
        static String charset = "application/json; charset=UTF-8";

    public static String getTickerBinance() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.binance.com/api/v3/ticker?symbol="+symbol+ "USDT&windowSize=1m"))
                .header(content, charset)
                .header("X-MBX-APIKEY","KWb3olcOQB07kFykqFXWjZMvzXMT7RFw9K28AADHAvt7DYqfxIQ1cY7nApAyYXq2")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static String getTickerCoinmarketCap() throws IOException, InterruptedException {
        String coinmarketCap = "https://pro-api.coinmarketcap.com/v2/cryptocurrency/quotes/latest?symbol=" + symbol;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(coinmarketCap))
                .header(content, charset)
                .header("X-CMC_PRO_API_KEY","ccd927fc-c51d-4d5b-9245-5dafefb7021b")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static String getTickerMEXC() throws IOException, InterruptedException {

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        String MEXC = "https://api.mexc.com/api/v3/ticker/24hr?symbol=" + symbol + "USDT";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(MEXC))
                .header(content, charset)
//                .header("X-MEXC-APIKEY","ccd927fc-c51d-4d5b-9245-5dafefb7021b")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        return response.body();
    }






}
