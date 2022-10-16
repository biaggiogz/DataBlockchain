import actions.Get;
import org.json.JSONObject;
import transformation.JsonCRUD;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

//        Runnable drawRunnable = new Runnable() {
//            public void run() {
//                try {
//                    JSONObject    jo = JsonCRUD.transformTickerBinance(Get.getTickerBinance());
//                    JsonCRUD.writeJsonTickerBinance(jo);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        };
//
//        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
//        exec.scheduleAtFixedRate(drawRunnable , 0, 1, TimeUnit.MINUTES);

                Runnable drawRunnable = new Runnable() {
            public void run() {
                try {
                    JSONObject    jo = JsonCRUD.transformTickerCoinmarketCap(Get.getTickerCoinmarketCap());
                    JsonCRUD.writeJsonTickerCoinmarketCap(jo);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(drawRunnable , 0, 1, TimeUnit.MINUTES);






    }

}
