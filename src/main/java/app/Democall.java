package app;

import actions.Get;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import transformation.JsonCRUD;

import java.io.IOException;
import java.util.Date;
@Component
@EnableAsync
public class Democall {
    @Async
    @Scheduled(cron = "0    *    *    *    *    *")
    public void executeCoinMarketCap() throws IOException, InterruptedException {

                try {
                    JSONObject jo = JsonCRUD.transformTickerCoinmarketCap(Get.getTickerCoinmarketCap());
                    JsonCRUD.writeJsonTickerCoinmarketCap(jo);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
    }

    @Async
    @Scheduled(cron = "0    *    *    *    *    *")
    public void executeBinance() throws IOException, InterruptedException {
        try {
                    JSONObject    jo = JsonCRUD.transformTickerBinance(Get.getTickerBinance());
                    JsonCRUD.writeJsonTickerBinance(jo);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
    }
}
