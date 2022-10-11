package actions;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Get {



    public String getTickerBinance() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.binance.com/api/v3/depth?symbol=LITUSDT&limit=200"))
                .header("Content-Type", "application/json; charset=UTF-8")
                .header("X-MBX-APIKEY","KWb3olcOQB07kFykqFXWjZMvzXMT7RFw9K28AADHAvt7DYqfxIQ1cY7nApAyYXq2")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        return response.body();
    }




}
