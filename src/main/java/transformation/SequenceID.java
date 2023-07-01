package transformation;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SequenceID {

    private static final AtomicLong SEQUENCE_GENERATOR = new AtomicLong();



    public static String sequenceid(){

        // Crea un objeto SimpleDateFormat con el formato deseado
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
// Obtiene la fecha actual
        Date fecha = new Date();


// Formatea la fecha en una cadena de texto sin espacios
        String fechaFormateada = formato.format(fecha).replace(" ", "").replace("/","")
                .replace("_","").replace(":","");

// Imprime la fecha formateada
        return fechaFormateada;

    }



    public static Mono<String> generarId() {
        return Mono.fromSupplier(() -> UUID.randomUUID().toString());
    }

    public static Mono<String> getid(){

        String after = generarId().block();
        String before = after.replace("-","").substring(0,11).concat(sequenceid());
        Mono<String> mono = Mono.just(before);
        return mono;


    }



}
