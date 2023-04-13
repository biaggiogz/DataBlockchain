import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BinanceTest {


    @Test
    public void givenUsingLowLevelApi_whenParsingJsonStringIntoJsonNode_thenCorrect()
            throws JsonParseException, IOException {
  TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        System.out.println(TimeZone.getDefault().getDisplayName());



    }
}
