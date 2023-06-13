package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import transformation.SequenceID;

import java.io.IOException;
import java.util.concurrent.*;

@SpringBootApplication
@EnableScheduling
public class Demo {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        SpringApplication.run(Demo.class, args);
        SequenceID.sequenceid();
        parallel2();

    }

//    public static void parallel(){
//
//
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//        Democall dc = new Democall();
//        // Programamos la repetición de la llamada cada minuto
//        scheduler.scheduleAtFixedRate(() -> {
//            // Creamos una lista de CompletableFuture para llamar a los métodos de forma asíncrona
//            List<CompletableFuture<Void>> futures = new ArrayList<>();
//            futures.add(CompletableFuture.runAsync(() -> {
//                try {
//                    dc.executeMEXC();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }));
//            futures.add(CompletableFuture.runAsync(() -> {
//                try {
//                    dc.executeBinance();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }));
//            futures.add(CompletableFuture.runAsync(() -> {
//                try {
//                    dc.executeCoinMarketCap();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }));
//
//            // Esperamos a que todos los CompletableFuture hayan terminado
//            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
//
//        }, 0, 1, TimeUnit.MINUTES); // Repetimos la llamada cada minuto
//        }


  public static void  parallel2() throws ExecutionException, InterruptedException {



      ExecutorService executor = Executors.newFixedThreadPool(3);
      Democall dc = new Democall();
      while (true) {
          CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
              try {
                  dc.executeMEXC();
              } catch (IOException e) {
                  e.printStackTrace();
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }

              return "Resultado del método 1";
          }, executor);

          CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
              try {
                  dc.executeBinance();
              } catch (IOException e) {
                  e.printStackTrace();
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }

              return "Resultado del método 2";
          }, executor);

          CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
              try {
                  dc.executeCoinMarketCap();
              } catch (IOException e) {
                  e.printStackTrace();
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              return "Resultado del método 3";
          }, executor);

          CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1,future3, future2);
//          CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1);
          try {
              combinedFuture.get(); // Esperar a que ambos métodos terminen
          } catch (Exception e) {
              e.printStackTrace();
          }

          // Aquí se pueden acceder a los resultados de los métodos llamando a future1.get() y future2.get()
//          System.out.println(future1.get());
//          System.out.println(future2.get());
//          System.out.println(future3.get());
          System.out.println("It´s working");

          Thread.sleep(60000); // Esperar 1 minuto antes de volver a llamar a los métodos
      }

      // Liberar recursos
  }
  }













