package app;

import actions.Get;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import transformation.JsonCRUD;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
@SpringBootApplication
@EnableScheduling
public class Demo {


    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(Demo.class, args);

        Democall d = new Democall();
        d.executeBybit();
        d.executeBinance();
        d.executeCoinMarketCap();
    }

















}
