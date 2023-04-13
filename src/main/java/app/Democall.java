package app;

import actions.Get;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import transformation.JsonCRUD;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

//@Component
//@EnableAsync
public class Democall {

//    @Async
//    @Scheduled(cron = "0    *    *    *    *    *")
    public  void executeBinance() throws IOException, InterruptedException {
        try {
            List<JSONObject>    jo = JsonCRUD.transformTickerBinance(Get.getTickerBinance());
            JsonCRUD.writeJsonTickerBinance(jo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//
//    @Async
//    @Scheduled(cron = "0    *    *    *    *    *")
    public  void executeCoinMarketCap() throws IOException, InterruptedException {
                try {
                    List<JSONObject> jo = JsonCRUD.transformTickerCoinmarketCap(Get.getTickerCoinmarketCap());
                    JsonCRUD.writeJsonTickerCoinmarketCap(jo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
    }



    public  void executeMEXC() throws IOException, InterruptedException {
        try {
            List<JSONObject> jo = JsonCRUD.transformTickerMEXC(Get.getTickerMEXC());
            JsonCRUD.writeJsonTickerMEXC(jo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
