package actions;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Get {



    public static String getTickerBinance() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.binance.com/api/v3/ticker?symbol=MIRUSDT&windowSize=1m"))
                .header("Content-Type", "application/json; charset=UTF-8")
                .header("X-MBX-APIKEY","KWb3olcOQB07kFykqFXWjZMvzXMT7RFw9K28AADHAvt7DYqfxIQ1cY7nApAyYXq2")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static String getTickerCoinmarketCap() throws IOException, InterruptedException {
        String coinmarketCap = "https://pro-api.coinmarketcap.com/v2/cryptocurrency/quotes/latest?symbol=PHA";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(coinmarketCap))
                .header("Content-Type", "application/json; charset=UTF-8")
                .header("X-CMC_PRO_API_KEY","ccd927fc-c51d-4d5b-9245-5dafefb7021b")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        return response.body();
    }




}
