
package com.example.the_knife.Ristoratore;

import com.example.the_knife.Exceptions.*;
import com.example.the_knife.InputValidator;
import com.example.the_knife.Utente.SessionManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
    private TextField mailRist;
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
        InputValidator.validaNomeRist(name);
        String address = addressRist.getText();
        InputValidator.validaIndirizzo(address);
        String location = locationRist.getText();
        InputValidator.validaLuogo(location);
        String price = priceRist.getText();
        price = price.trim();
        InputValidator.validaPrezzo(price);
        String cousine = cousineRist.getText();
        cousine = cousine.trim();
        InputValidator.validaCucina(cousine);
        String tel = telRist.getText();
        tel.trim();
        InputValidator.validaTelefono(tel);
        String Url = UrlRist.getText();
        Url = Url.trim();
        InputValidator.validaUrl(Url);
        String service = serviceRist.getText();
        service = service.trim();
        InputValidator.validaServizio(service);
        String description = descriptionRist.getText();
        description = description.trim();
        InputValidator.validaDescrizione(description);
        String stars = starsRist.getText();
        stars = stars.trim();
        InputValidator.validaStelle(stars);
        String mail = mailRist.getText();
        mail = mail.trim();
        InputValidator.validaEmail(mail);
        RadioButton delivery = (RadioButton) this.DeliveryToggleGroup.getSelectedToggle();
        String deliveryText = delivery.getText();
        boolean d= false;
        if(deliveryText.equalsIgnoreCase("Si")){
            d = true;
        }


        RadioButton booking = (RadioButton) this.BookingToggleGroup.getSelectedToggle();
        String bookingText = booking.getText();
        boolean b= false;
        if(deliveryText.equalsIgnoreCase("Si")){
            b = true;
        }

        double[] coord = new double[2];
        coord = coordinate(address);// latitudine in pos 0 e long in pos 1
        int stelle = Integer.parseInt(stars); // metto come intero il campo stelle

        Ristorante nuovo = new Ristorante(Id, idRistoratore, name, address, location, price, cousine,coord[0], coord[1],
                tel, Url, stelle, service,description,d, b, mail);

        aggiungiRistorante(nuovo,"ristoranti.json");
        handleInput();
    }

    protected void handleInput() {
        //if(controllo che tutti gli input siano andati bene allora mando questo messaggio)
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Inserimento nuovo ristorante");
        alert.setHeaderText("Ti sei registrato correttamente");
        alert.setContentText("I tuoi dati sono stati salvati...");
        alert.showAndWait();
        //else mando un errore specifico su un tipo di input inserito dall'utente
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
                if (r.id!=0) {
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
