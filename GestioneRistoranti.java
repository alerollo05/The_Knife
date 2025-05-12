package provaTheKnife;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GestioneRistoranti {
	public static void aggiungiRistorante(Ristorante nuovo, String fileJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Legge l'albero JSON
            JsonNode root = mapper.readTree(new File(fileJson));
            JsonNode ristorantiNode = root.get("ristoranti");

            // Deserializza in List<Ristorante>
            List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));

            // Converte in lista modificabile
            List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);

            // Aggiunge il nuovo ristorante
            listaModificabile.add(nuovo);

            System.out.println("Ristorante '" + nuovo.Name + "' aggiunto con successo.");

            // Ricrea l'oggetto JSON aggiornato
            ObjectNode nuovoRoot = mapper.createObjectNode();
            nuovoRoot.set("ristoranti", mapper.valueToTree(listaModificabile));

            // Sovrascrive il file
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileJson), nuovoRoot);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static void rimuoviRistorantePerNome(String nome, String fileJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Legge l'albero JSON
            JsonNode root = mapper.readTree(new File(fileJson));
            JsonNode ristorantiNode = root.get("ristoranti");

            // Deserializza in List<Ristorante>
            List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));

            // Converte in lista modificabile
            List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);

            // Rimuove per nome
            boolean rimosso = listaModificabile.removeIf(r -> r.Name.equalsIgnoreCase(nome));

            if (rimosso) {
                System.out.println("Ristorante '" + nome + "' rimosso con successo.");
            } else {
                System.out.println("Nessun ristorante trovato con il nome '" + nome + "'.");
            }

            // Ricrea l'oggetto JSON aggiornato
            ObjectNode nuovoRoot = mapper.createObjectNode();
            nuovoRoot.set("ristoranti", mapper.valueToTree(listaModificabile));

            // Sovrascrive il file
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileJson), nuovoRoot);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
