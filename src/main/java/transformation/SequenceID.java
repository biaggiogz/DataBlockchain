package transformation;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.TemporalField;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class SequenceID {

    private static final AtomicLong SEQUENCE_GENERATOR = new AtomicLong();



    public static String sequenceID(){

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




}
