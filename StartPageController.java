package com.example.the_knife;
import com.example.the_knife.Ristoratore.Ristorante;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartPageController {
    @FXML
    private TextField locationSearch;
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

    public List<Ristorante> cercaRistoranti(String fileJson,String luogo, String tipoCucina, String fasciaPrezzo,
                                            boolean delivery, boolean servizioOnline) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(fileJson));
        JsonNode ristorantiNode = root.get("ristoranti");
        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class)); // Supponiamo esista già
        List<Ristorante> risultati = new ArrayList<>();

        double[] coordinateLuogo = coordinate(luogo); // Es. [latitudine, longitudine]

        for (Ristorante r : ristoranti) {
            if (tipoCucina != null && !r.getCuisine().equalsIgnoreCase(tipoCucina)) continue;
            if (fasciaPrezzo != null && !r.getPrice().equalsIgnoreCase(fasciaPrezzo)) continue;
            if (delivery && !r.isDelivery()) continue;
            if (servizioOnline && !r.isBookingOnline()) continue;

            double distanzaKm = calcolaDistanzaKm(coordinateLuogo[0], coordinateLuogo[1], r.getLatitude(), r.getLongitude());
            if (distanzaKm <= 30) {
                risultati.add(r);
            }
        }
        return risultati;
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
            String urlStr = "https://nominatim.openstreetmap.org/search?q=" + encodedAddress + "&format=json";

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "JavaGeocoder/1.0"); // Nominatim richiede user-agent
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
                JsonNode firstResult = results.get(0);
                double lat = firstResult.get("lat").asDouble();
                double lon = firstResult.get("lon").asDouble();
                coord[0] = lat;
                coord[1] = lon;
            } else {
                System.out.println("Nessun risultato trovato.");
            }

        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        } catch (ProtocolException ex) {
            throw new RuntimeException(ex);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        } catch (JsonMappingException ex) {
            throw new RuntimeException(ex);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return coord;
    }

}