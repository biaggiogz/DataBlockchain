package actions;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;


public class Get {

        static String content = "Content-Type";
        static String charset = "application/json; charset=UTF-8";


    public static  List<String> getTickerBinance(){
//        String dot ="https://api.binance.com/api/v3/ticker?symbol=DOTUSDT&windowSize=1m";
//        String link = "https://api.binance.com/api/v3/ticker?symbol=LINKUSDT&windowSize=1m";
        String eth = "https://api.binance.com/api/v3/ticker?symbol=ETHUSDT&windowSize=1m";
        String btc = "https://api.binance.com/api/v3/ticker?symbol=BTCUSDT&windowSize=1m";
        List<String> parameters = Arrays.asList(eth, btc);

        Flux<String> parametersCoinFlux = Flux.fromIterable(parameters);
        List<String> outDataApi = parametersCoinFlux.flatMap(parameterCoin -> Mono.fromSupplier(() ->
                        {
                            try {
                                return clientBinance(parameterCoin);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return null;
                        })
                        .subscribeOn(Schedulers.parallel()))
                .collectList().block();
        return outDataApi;
    }

    public static String clientBinance(String parameterCoin) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(parameterCoin))
                .header(content, charset)
                .header("X-MBX-APIKEY","KWb3olcOQB07kFykqFXWjZMvzXMT7RFw9K28AADHAvt7DYqfxIQ1cY7nApAyYXq2")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    //----------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------
    public static  List<String> getTickerCoinmarketCap(){
//        String link ="https://pro-api.coinmarketcap.com/v2/cryptocurrency/quotes/latest?symbol=LINK";
//        String dot = "https://pro-api.coinmarketcap.com/v2/cryptocurrency/quotes/latest?symbol=DOT";
        String eth = "https://pro-api.coinmarketcap.com/v2/cryptocurrency/quotes/latest?symbol=ETH";
        String btc = "https://pro-api.coinmarketcap.com/v2/cryptocurrency/quotes/latest?symbol=BTC";
        List<String> parameters = Arrays.asList( eth, btc);

        Flux<String> parametersCoinFlux = Flux.fromIterable(parameters);

        List<String> outDataApi = parametersCoinFlux.flatMap(parameterCoin -> Mono.fromSupplier(() ->
                        {
                            try {
                                return clientCoinmarketCap(parameterCoin);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return null;
                        })
                        .subscribeOn(Schedulers.parallel()))
                        .collectList().block();
        return outDataApi;
    }

    public static String clientCoinmarketCap(String parameterCoin) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(parameterCoin))
                .header(content, charset)
                .header("X-CMC_PRO_API_KEY","ccd927fc-c51d-4d5b-9245-5dafefb7021b")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
//----------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------
    public static String clientMEXC(String parameterCoin) throws IOException, InterruptedException {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(parameterCoin))
                .header(content, charset)
//                .header("X-MEXC-APIKEY","ccd927fc-c51d-4d5b-9245-5dafefb7021b")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static  List<String> getTickerMEXC(){
//        String dot ="https://api.mexc.com/api/v3/ticker/24hr?symbol=DOTUSDT";
//        String link = "https://api.mexc.com/api/v3/ticker/24hr?symbol=LINKUSDT";
        String eth = "https://api.mexc.com/api/v3/ticker/24hr?symbol=ETHUSDT";
        String btc = "https://api.mexc.com/api/v3/ticker/24hr?symbol=BTCUSDT";

        List<String> parameters = Arrays.asList( eth, btc);
        Flux<String> parametersCoinFlux = Flux.fromIterable(parameters);
        List<String> outDataApi = parametersCoinFlux.flatMap(parameterCoin -> Mono.fromSupplier(() ->
                        {
                            try {
                                return clientMEXC(parameterCoin);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return null;
                        })
                        .subscribeOn(Schedulers.parallel()))
                        .collectList().block();
        return outDataApi;
    }






}
