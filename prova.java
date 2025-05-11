package jsonLibreriaJackson;
import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class prova {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 try {
	            ObjectMapper mapper = new ObjectMapper();
	            RistorantiWrapper data = mapper.readValue(new File("prova.json"), RistorantiWrapper.class);

	            for (Ristorante ristorante : data.ristoranti) {
	                System.out.println(ristorante.Name + " - " + ristorante.Location + " - " + ristorante.PhoneNumber);
	            }
	            
	            // Crea nuovo ristorante
	            Ristorante nuovo = new Ristorante();
	            
	            System.out.println();
	            nuovo.Name = "Nuovo Ristorante";
	            nuovo.Address = "Via Nuova 123, Roma, Italia";
	            nuovo.Location = "Rome, Italy";
	            nuovo.Price = "€€";
	            nuovo.Cuisine = "Italian";
	            nuovo.Longitude = 12.4964;
	            nuovo.Latitude = 41.9028;
	            nuovo.PhoneNumber = 390612345678.0;
	            nuovo.Url = "https://example.com";
	            nuovo.WebsiteUrl = "https://nuovoristorante.it";
	            nuovo.Award = "1 Star";
	            nuovo.GreenStar = 1;
	            nuovo.FacilitiesAndServices = "Air conditioning, Terrace";
	            nuovo.Description = "Un accogliente ristorante italiano nel cuore di Roma.";

	            // Aggiungi alla lista
	            data.ristoranti.add(nuovo);

	            // Scrivi di nuovo nel file
	            mapper.writerWithDefaultPrettyPrinter().writeValue(new File ("prova.json"), data);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	}

}
