package com.example.the_knife;
import com.example.the_knife.Ristoratore.Ristorante;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StartPageController {

    @FXML
    private TextField locationSearch;

    @FXML private TextField cityField;
    @FXML private ComboBox<String> cuisineBox;
    @FXML private ComboBox<String> priceBox;
    @FXML private ComboBox<String> deliveryBox;
    @FXML private ComboBox<String> bookingBox;
    @FXML private Button searchButton;

    @FXML
    private void initialize() {

        cuisineBox.setItems(FXCollections.observableArrayList(
                "Mediterranea", "Italiana", "Giapponese",
                "Francese", "Cinese", "Messicana", "Indiana"));
        cuisineBox.setMaxWidth(50);
        priceBox.setItems(FXCollections.observableArrayList(
                "€", "€€", "€€€", "€€€€"));
        priceBox.setMaxWidth(50);

        deliveryBox.setItems(FXCollections.observableArrayList(
                "Con delivery", "Senza delivery"));
        deliveryBox.setMaxWidth(50);
        bookingBox.setItems(FXCollections.observableArrayList(
                "Booking online", "No booking online"));
        bookingBox.setMaxWidth(50);

        //selezione di default tutti i servizi
        deliveryBox.getSelectionModel().selectFirst();
        bookingBox.getSelectionModel().selectFirst();

    }

    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
            Scene loginScene = new Scene(loader.load(),900,800);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(loginScene);
            stage.setTitle("The_Knife");
            stage.setResizable(false); // Impedisce il resize manuale
            stage.setMaximized(false); // Impedisce l'avvio in modalità massimizzata
            stage.show();
        } catch (IOException e) {
            System.err.println("Errore nel caricamento del file FXML:");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Il path al file FXML è nullo o errato:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Errore imprevisto:");
            e.printStackTrace();
        }
    }
    @FXML
    public void goTo(ActionEvent event, String location) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
            Scene loginScene = new Scene(loader.load(), 900, 800);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(loginScene);
            stage.setTitle("The_Knife");
            stage.setResizable(false); // Impedisce il resize manuale
            stage.setMaximized(false); // Impedisce l'avvio in modalità massimizzata
            stage.show();
        } catch (IOException e) {
            System.err.println("Errore nel caricamento del file FXML:");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Il path al file FXML è nullo o errato:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Errore imprevisto:");
            e.printStackTrace();
        }
    }

    @FXML
    public void closeProgram(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void onSearchClicked(){
        String location = locationSearch.getText();
        System.out.println(location);
        System.out.println("Searched");
    }

    @FXML
    public void cercaRist() throws IOException {
        boolean dev = true;
        boolean bok = true;
        String city     = cityField.getText().trim();
        String cuisine  = cuisineBox.getValue();
        String price    = priceBox.getValue();
        String delivery = deliveryBox.getValue();
        String booking  = bookingBox.getValue();
        if(!delivery.equals("Senza delivery")){
            dev = false;
        }
        if(!booking.equals("No booking online")){
            bok = false;
        }
        System.out.println("Cerca rist"+city+" "+cuisine+" "+price+" "+delivery+" "+booking);
        List<Ristorante> listaRist = cercaRistoranti("ristoranti.json",city,cuisine,price,dev,bok);
        ordinaRist(listaRist);
        for(Ristorante r : listaRist ){
            System.out.println(r.getName() +" "+r.getMediaRec() );
        }
    }

    public void aggiungiRistorante(Ristorante nuovo, String fileJson) {
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

            System.out.println("Ristorante '" + nuovo.getName() + "' aggiunto con successo.");

            // Ricrea l'oggetto JSON aggiornato
            ObjectNode nuovoRoot = mapper.createObjectNode();
            nuovoRoot.set("ristoranti", mapper.valueToTree(listaModificabile));

            // Sovrascrive il file
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileJson), nuovoRoot);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Ristorante> cercaRistoranti(String fileJson,String luogoOrName, String tipoCucina, String fasciaPrezzo,
                                            boolean delivery, boolean servizioOnline) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(fileJson));
        JsonNode ristorantiNode = root.get("ristoranti");
        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class)); // Supponiamo esista già
        List<Ristorante> risultati = new ArrayList<>();
        
        for (Ristorante r : ristoranti) {
            // Controllo se il nome corrisponde esattamente
            if (luogoOrName != null && luogoOrName.equalsIgnoreCase(r.getName())) {
                risultati.clear(); // nel caso ci siano già risultati
                risultati.add(r);
                return risultati; // restituisce solo questo ristorante
            }
            if (tipoCucina != null && !r.getCuisine().equalsIgnoreCase(tipoCucina)) continue;
            if (fasciaPrezzo != null && !r.getPrice().equalsIgnoreCase(fasciaPrezzo)) continue;
            if (delivery && !r.isDelivery()) continue;
            if (servizioOnline && !r.isBookingOnline()) continue;

            double[] coordinateLuogo = coordinate(luogoOrName); // Es. [latitudine, longitudine]
            double distanzaKm = calcolaDistanzaKm(coordinateLuogo[0], coordinateLuogo[1], r.getLatitude(), r.getLongitude());
            if (distanzaKm <= 100) {
                risultati.add(r);
            }
        }
        return risultati;
    }

    public void top10Ristoranti(String fileJson,String filetop10) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(fileJson));
        JsonNode tuttiRistNode = root.get("ristoranti");
        List<Ristorante> tuttiRist = Arrays.asList(mapper.treeToValue(tuttiRistNode, Ristorante[].class));

        JsonNode top10RistNode = root.get("ristoranti");
        List<Ristorante> top10Rist = Arrays.asList(mapper.treeToValue(top10RistNode, Ristorante[].class));

        List<Ristorante> listaModificabileTop10 = new ArrayList<>(top10Rist);

        ordinaRist(tuttiRist);
        for(int i=0 ; i<10; i++){
            aggiungiRistorante(tuttiRist.get(i),filetop10);
        }

        ObjectNode nuovoRoot = mapper.createObjectNode();
        nuovoRoot.set("ristoranti", mapper.valueToTree(listaModificabileTop10));
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filetop10), nuovoRoot);

    }

    public void ordinaRist(List<Ristorante> risultati) {
        quickSort(risultati, 0, risultati.size() - 1);
    }

    private void quickSort(List<Ristorante> lista, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(lista, low, high);
            quickSort(lista, low, pivotIndex - 1);
            quickSort(lista, pivotIndex + 1, high);
        }
    }

    private int partition(List<Ristorante> lista, int low, int high) {
        Ristorante pivot = lista.get(high); // Usiamo l'intero oggetto per confrontare entrambi i campi
        int i = low - 1;

        for (int j = low; j < high; j++) {
            Ristorante current = lista.get(j);
           // Se MediaRec è uguale, confronta NumRec sempre in decrescente
            if (current.getMediaRec() > pivot.getMediaRec() ||
                    (current.getMediaRec() == pivot.getMediaRec() && current.getNumRec() > pivot.getNumRec())
            ) {
                i++;
                Collections.swap(lista, i, j);
            }
        }
        Collections.swap(lista, i + 1, high);
        return i + 1;
    }


    public double calcolaDistanzaKm(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Raggio della terra in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }
    public static double[] coordinate(String indirizzo) {
        double[] coord = new double[2];
        try {
            String encodedAddress = URLEncoder.encode(indirizzo, "UTF-8");
            String urlStr = "https://nominatim.openstreetmap.org/search?q=" + encodedAddress + "&format=json&addressdetails=1";

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "JavaGeocoder/1.0");
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode results = objectMapper.readTree(response.toString());

            if (results.isArray() && results.size() > 0) {
                JsonNode bestResult = null;
                for (JsonNode result : results) {
                    String type = result.has("type") ? result.get("type").asText() : "";
                    if (type.equals("city") || type.equals("town") || type.equals("village")) {
                        bestResult = result;
                        break;
                    }
                }

                if (bestResult == null) {
                    bestResult = results.get(0); // fallback
                }

                double lat = bestResult.get("lat").asDouble();
                double lon = bestResult.get("lon").asDouble();
                coord[0] = lat;
                coord[1] = lon;
            } else {
                System.out.println("Nessun risultato trovato.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return coord;
    }


}