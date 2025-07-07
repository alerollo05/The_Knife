package com.example.the_knife.Ristoratore;

import com.example.the_knife.InputValidator;
import com.example.the_knife.Utente.SessionManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.List;

/**
 * Controller per la pagina di aggiunta di un nuovo ristorante da parte di un ristoratore.
 * <p>
 * Consente l’inserimento dei dati tramite form, la validazione tramite {@link InputValidator}
 * e la scrittura su file JSON. Utilizza {@link SessionManager} per ottenere le informazioni dell’utente loggato.
 */
public class NewRestaurantController extends DashBoardRistController {

    // Campi input FXML
    @FXML private Label welcomeLabel;
    @FXML private TextField nameRist, addressRist, locationRist, priceRist, mailRist, cousineRist, telRist, UrlRist, serviceRist, descriptionRist, starsRist;
    @FXML private ToggleGroup DeliveryToggleGroup, BookingToggleGroup;

    // Sessione utente
    SessionManager session = SessionManager.getInstance();
    private final String user = session.getUsername();
    private final int id = session.getUserId();
    private final String ruolo = session.getRuolo();

    /**
     * Inizializza la vista impostando il messaggio di benvenuto e stampando i dati utente in console.
     */
    @FXML
    public void initialize() {
        welcomeLabel.setText("AGGIUNGI UN RISTORANTE");
        System.out.println("Utente: " + user + " Id: " + id + " Ruolo: " + ruolo);
    }

    /**
     * Gestisce il logout utente, ereditato da {@code DashBoardRistController}.
     *
     * @param event evento associato al pulsante logout
     */
    public void handleLogOut(ActionEvent event) {
        super.handleLogOut(event);
    }

    /**
     * Chiude l'applicazione. Override del metodo padre.
     *
     * @param event evento di chiusura
     */
    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }

    /**
     * Naviga indietro alla schermata dei ristoranti.
     *
     * @param event evento di ritorno
     * @throws IOException se la navigazione fallisce
     */
    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        super.goTo(event, "ristorantiRist.fxml");
    }

    /**
     * Naviga al profilo del ristoratore.
     */
    @FXML
    protected void onProfileClick(ActionEvent event) throws IOException {
        super.onProfileClick(event);
    }

    /**
     * Naviga alla schermata dei ristoranti.
     */
    @FXML
    protected void onRistorantiClick(ActionEvent event) throws IOException {
        super.onRistorantiClick(event);
    }

    /**
     * Gestisce l’inserimento del nuovo ristorante dopo aver raccolto i dati,
     * eseguito la validazione e salvato su file JSON.
     *
     * @param event evento di conferma inserimento
     * @throws IOException se l’accesso al file JSON fallisce
     */
    @FXML
    protected void handleAddRist(ActionEvent event) throws IOException {
        int Id = generaId("ristoranti.json");
        int idRistoratore = id;

        String name = nameRist.getText().trim();
        InputValidator.validaNomeRist(name);

        String address = addressRist.getText();
        InputValidator.validaIndirizzo(address);

        String location = locationRist.getText();
        InputValidator.validaLuogo(location);

        String price = priceRist.getText().trim();
        InputValidator.validaPrezzo(price);

        String cousine = cousineRist.getText().trim();
        InputValidator.validaCucina(cousine);

        String tel = telRist.getText().trim();
        InputValidator.validaTelefono(tel);

        String Url = UrlRist.getText().trim();
        InputValidator.validaUrl(Url);

        String service = serviceRist.getText().trim();
        InputValidator.validaServizio(service);

        String description = descriptionRist.getText().trim();
        InputValidator.validaDescrizione(description);

        String stars = starsRist.getText().trim();
        InputValidator.validaStelle(stars);

        String mail = mailRist.getText().trim();
        InputValidator.validaEmail(mail);

        RadioButton delivery = (RadioButton) this.DeliveryToggleGroup.getSelectedToggle();
        boolean d = delivery.getText().equalsIgnoreCase("Si");

        RadioButton booking = (RadioButton) this.BookingToggleGroup.getSelectedToggle();
        boolean b = booking.getText().equalsIgnoreCase("Si");

        double[] coord = coordinate(address);
        int stelle = Integer.parseInt(stars);

        Ristorante nuovo = new Ristorante(Id, idRistoratore, name, address, location, price, cousine,
                coord[0], coord[1], tel, Url, stelle, service, description, d, b, mail);

        aggiungiRistorante(nuovo, "ristoranti.json");
        handleInput();
    }

    /**
     * Mostra un messaggio di conferma dopo il corretto inserimento del ristorante.
     */
    protected void handleInput() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Inserimento nuovo ristorante");
        alert.setHeaderText("Ti sei registrato correttamente");
        alert.setContentText("I tuoi dati sono stati salvati...");
        alert.showAndWait();
    }

    /**
     * Genera un nuovo ID incrementale per un ristorante leggendo dal file JSON.
     *
     * @param fileJson percorso del file JSON contenente i ristoranti
     * @return nuovo ID generato
     */
    public static int generaId(String fileJson) {
        int count = 0;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            JsonNode root = mapper.readTree(new File(fileJson));
            JsonNode ristorantiNode = root.get("ristoranti");
            List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
            count = ristoranti.size() + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Ottiene le coordinate geografiche (latitudine, longitudine) da un indirizzo
     * usando il servizio di geocoding di OpenStreetMap (Nominatim).
     *
     * @param indirizzo indirizzo del ristorante
     * @return array di double contenente latitudine [0] e longitudine [1]
     */
    public static double[] coordinate(String indirizzo) {
        double[] coord = new double[2];
        try {
            String encodedAddress = URLEncoder.encode(indirizzo, "UTF-8");
            String urlStr = "https://nominatim.openstreetmap.org/search?q=" + encodedAddress + "&format=json";

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
                JsonNode firstResult = results.get(0);
                coord[0] = firstResult.get("lat").asDouble();
                coord[1] = firstResult.get("lon").asDouble();
            } else {
                System.out.println("Nessun risultato trovato.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore durante la geocodifica: " + e.getMessage(), e);
        }

        return coord;
    }
}
