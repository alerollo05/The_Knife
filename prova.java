package jsonLibreriaJackson;
import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class prova {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Crea il nuovo oggetto ristorante
    	            Ristorante nuovo = new Ristorante();
    	            
    	            nuovo.Id = 1;
    	            nuovo.Name = "PEPO";
    	            nuovo.Address = "Via Nuova 123, Roma, Italia";
    	            nuovo.Location = "Rome, Italy";
    	            nuovo.Price = "€€";
    	            nuovo.Cuisine = "Italian";
    	            nuovo.Longitude = 12.4964;
    	            nuovo.Latitude = 41.9028;
    	            nuovo.PhoneNumber = "390612345678";
    	            nuovo.Url = "https://example.com";
    	            nuovo.WebsiteUrl = "https://nuovoristorante.it";
    	            nuovo.Award = "1 Star";
    	            nuovo.GreenStar = 1;
    	            nuovo.FacilitiesAndServices = "Air conditioning, Terrace";
    	            nuovo.Description = "Un accogliente ristorante italiano nel cuore di Roma.";
    	            nuovo.Delivery = true;
    	            
    	            GestioneRistoranti.aggiungiRistorante(nuovo, "prova.json");
    	            GestioneRistoranti.rimuoviRistorantePerNome("Steirereck im Stadtpark", "prova.json");
    	            
    	            
    	            /*ObjectMapper mapper = new ObjectMapper();
    	            File file = new File("prova.json");

    	            // Leggi i dati esistenti
    	            RistorantiWrapper data = mapper.readValue(file, RistorantiWrapper.class);

    	            for (Ristorante ristorante : data.ristoranti) {
    	                System.out.println(ristorante.Name + " - " + ristorante.Location + " - " + ristorante.PhoneNumber);
    	            }*/



	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	}

}
