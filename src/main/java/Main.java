import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.binance.com/api/v3/depth?symbol=LITUSDT&limit=200"))
                .header("Content-Type", "application/json; charset=UTF-8")
                .header("X-MBX-APIKEY","KWb3olcOQB07kFykqFXWjZMvzXMT7RFw9K28AADHAvt7DYqfxIQ1cY7nApAyYXq2")
                .GET()
                .build();



        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());



    }
}
