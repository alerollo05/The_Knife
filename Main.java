package provaTheKnife;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            RistorantiWrapper data = mapper.readValue(new File("prova.json"), RistorantiWrapper.class);

            for (Ristorante ristorante : data.ristoranti) {
                System.out.println(ristorante.Name + " - " + ristorante.Location);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
