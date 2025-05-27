package com.example.the_knife.Ristoratore;

import com.example.the_knife.Exceptions.InputMancanteExeption;
import com.example.the_knife.Exceptions.TelefonoNonValidoException;
import com.example.the_knife.Exceptions.paeseNonValidoExeption;
import com.example.the_knife.Utente.SessionManager;
import com.example.the_knife.Utente.Utente;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class newRestaurantController extends dashBoardRistController {

    //creo le variabili che mi servono per immagazzinare i dati che l'utente immette in input
    @FXML
    private Label welcomeLabel;
    @FXML
    private TextField nameRist;
    @FXML
    private TextField addressRist;
    @FXML
    private TextField locationRist;
    @FXML
    private TextField priceRist;
    @FXML
    private TextField cousineRist;
    @FXML
    private TextField telRist;
    @FXML
    private TextField UrlRist;
    @FXML
    private TextField serviceRist;
    @FXML
    private TextField descriptionRist;
    @FXML
    private TextField starsRist;
    @FXML
    private ToggleGroup DeliveryToggleGroup;
    @FXML
    private ToggleGroup BookingToggleGroup;


    //Prendo i dati dalla sessione
    SessionManager session = SessionManager.getInstance();
    private final String user = session.getUsername();
    private final int id = session.getUserId();
    private final String ruolo = session.getRuolo();

    public void handleLogOut(ActionEvent event) {
        super.handleLogOut(event);
    }

    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }

    @FXML
    public void initialize() {
        welcomeLabel.setText("AGGIUNGI UN RISTORANTE " + user + "");
        System.out.println("Utente: "+user+ " Id: "+id+" Ruolo: "+ruolo);
    }

    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        super.goTo(event, "ristorantiRist.fxml");
    }

    @FXML
    protected void onProfileClick(ActionEvent event) throws IOException {
        super.onProfileClick(event);
    }
    @FXML
    protected void onRistorantiClick(ActionEvent event) throws IOException {
        super.onRistorantiClick(event);
    }
    @FXML
    protected void handleAddRist(ActionEvent event) throws IOException {
        int Id = generaId("ristoranti.json");
        int idRistoratore = id;
        String name = nameRist.getText();
        name = name.trim();
        String address = addressRist.getText();
        String location = locationRist.getText();
        String price = priceRist.getText();
        price = price.trim();
        String cousine = cousineRist.getText();
        cousine = cousine.trim();
        String tel = telRist.getText();
        tel.trim();
        String Url = UrlRist.getText();
        Url = Url.trim();
        String service = serviceRist.getText();
        service = service.trim();
        String description = descriptionRist.getText();
        description = description.trim();
        String stars = starsRist.getText();
        stars = stars.trim();
        RadioButton delivery = (RadioButton) this.DeliveryToggleGroup.getSelectedToggle();
        String deliveryText = delivery.getText();
        boolean d= false;
        if(deliveryText.equalsIgnoreCase("Si")){
            d = true;
        }

        // Controllo che l'utente ha inserito tutti i campi
        if(name.isEmpty() || address.isEmpty() || location.isEmpty() || price.isEmpty() || cousine.isEmpty() ||
                tel.isEmpty() || Url.isEmpty() || service.isEmpty() || description.isEmpty() || stars.isEmpty()) {
            handleInput("Errore", "Qualche campo non è stato inserito.");
            throw new InputMancanteExeption("Qualche campo non è stato inserito.");
        }

        // Controlla che l indirizzo contenga almeno una lettera e un numero
        if (!address.matches(".*\\d.*") || !address.matches(".*[a-zA-Z].*")) {
            handleInput("Errore", "L'indirizzo deve contenere almeno una lettera e un numero.");
            throw new IllegalArgumentException("L'indirizzo deve contenere almeno una lettera e un numero.");
        }
        // Controlla che l indirizzo non contenga caratteri non validi
        if (!address.matches("^[\\p{L}0-9.,'\\-\\s]+$")) {
            handleInput("Errore", "L'indirizzo contiene caratteri non validi.");
            throw new IllegalArgumentException("L'indirizzo contiene caratteri non validi.");
        }
        // Verifico che il numero inizi con + seguito da 1-4 cifre (prefisso internazionale),
        // Poi abbia da 6 a 12 cifre, che possono essere separate da spazi o trattini.
        // Non accetta caratteri diversi da spazi o trattini.
        if (!tel.matches("^\\+\\d{1,4}\\d{6,12}$")) {
             handleInput("Errore", "Numero di telefono non valido. Deve iniziare con + seguito da prefisso e numero, e deve avere minimo 6 e massimo 12 cifre.");
            throw new TelefonoNonValidoException("Numero di telefono non valido. Deve iniziare con + seguito da prefisso e numero, e deve avere minimo 6 e massimo 12 cifre.");
        }
        // Controllo che il campo prezzo medio contenga solo caratteri come $/£/€ e che vadano da 1 a 4 caratteri
         if(!price.matches("([£]{1,4}|[$]{1,4}|[€]{1,4})")){
             handleInput("Errore", "Formato prezzo non valido, formati ammessi : $/£/€.");
             throw new TelefonoNonValidoException("Formato prezzo non valido, formati ammessi : $/£/€.");
        }
        // Controllo che il campo luogo sia formato da almeno due caratteri separati dalla virgola
         if(location != null && location.matches("\\s*[^,\\s].*?,\\s*[^,\\s].*")){
            handleInput("Errore", "Formato del paese inserito non valido o non inserito.");
            throw new paeseNonValidoExeption("Formato del paese inserito non valido.");
         }
        // Controllo che il campo del tipo di cucina contenga solo lettere
        if(!cousine.matches("[a-zA-Z]+")){
            handleInput("Errore", "Il campo tipo di cucina può contenere solo lettere.");
            throw new paeseNonValidoExeption("Il campo tipo di cucina può contenere solo lettere.");
        }

        RadioButton booking = (RadioButton) this.BookingToggleGroup.getSelectedToggle();
        String bookingText = booking.getText();
        boolean b= false;
        if(deliveryText.equalsIgnoreCase("Si")){
            b = true;
        }
        String email ="prova@gmail.com";
        double[] coord = new double[2];
        coord = coordinate(address);// latitudine in pos 0 e long in pos 1
        int stelle = Integer.parseInt(stars); // metto come intero il campo stelle

        Ristorante nuovo = new Ristorante(Id, idRistoratore, name, address, location, price, cousine,coord[0], coord[1],
                tel, Url, stelle, service,description,d, b, email);

        aggiungiRistorante(nuovo,"ristoranti.json");


    }

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
    public static int generaId(String fileJson) {

        int count = 0;

        try {
            ObjectMapper mapper = new ObjectMapper();
            // Legge l'albero JSON
            JsonNode root = mapper.readTree(new File(fileJson));
            JsonNode ristorantiNode = root.get("ristoranti");

            List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));// Deserializza in List<Ristorante>
            List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);// Converte in lista modificabile

            for (Ristorante r : listaModificabile) {
                if (r.Id!=0) {
                    count++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count+1;
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
