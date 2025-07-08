
package com.example.the_knife.Ristoratore;

import com.example.the_knife.InputValidator;
import com.example.the_knife.Utente.SessionManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
 * Controller della schermata per l'aggiunta di un nuovo ristorante da parte di un ristoratore.
 * <p>
 * Consente all'utente autenticato (ruolo ristoratore) di inserire tutte le informazioni
 * relative a un nuovo ristorante, con validazione dei dati e salvataggio su file JSON.
 * </p>
 * <p>
 * L'indirizzo viene convertito in coordinate geografiche (latitudine e longitudine) tramite
 * una richiesta HTTP all'API di OpenStreetMap (Nominatim).
 * </p>
 */
public class newRestaurantController extends DashBoardRistController {

    //creo le variabili che mi servono per immagazzinare i dati che l'utente immette in input
    /** Etichetta di benvenuto nella schermata. */
    @FXML
    private Label welcomeLabel;

    /** Campo per il nome del ristorante. */
    @FXML
    private TextField nameRist;

    /** Campo per l'indirizzo del ristorante. */
    @FXML
    private TextField addressRist;

    /** Campo per la località. */
    @FXML
    private TextField locationRist;

    /** Campo per il prezzo medio. */
    @FXML
    private TextField priceRist;

    /** Campo per l'email di contatto. */
    @FXML
    private TextField mailRist;

    /** Campo per il tipo di cucina. */
    @FXML
    private ComboBox<String> cousineRist;

    /** Campo per il numero di telefono. */
    @FXML
    private TextField telRist;

    /** Campo per l'URL del sito del ristorante. */
    @FXML
    private TextField UrlRist;

    /** Campo per i servizi offerti. */
    @FXML
    private TextField serviceRist;

    /** Campo per la descrizione. */
    @FXML
    private TextField descriptionRist;

    /** Campo per le stelle (valutazione). */
    @FXML
    private TextField starsRist;

    /** ToggleGroup per indicare se è disponibile la consegna a domicilio. */
    @FXML
    private ToggleGroup DeliveryToggleGroup;

    /** ToggleGroup per indicare se è possibile prenotare. */
    @FXML
    private ToggleGroup BookingToggleGroup;


    //Prendo i dati dalla sessione
    /** Istanza singleton della sessione utente. */
    SessionManager session = SessionManager.getInstance();

    /** Username attuale dell’utente loggato. */
    private final String user = session.getUsername();

    /** ID univoco dell’utente loggato. */
    private final int id = session.getUserId();

    /** Ruolo dell’utente loggato (ristoratore, cliente, admin...). */
    private final String ruolo = session.getRuolo();

    /**
     * Esegue il logout dell’utente corrente.
     */
    public void handleLogOut(ActionEvent event) {
        super.handleLogOut(event);
    }
    /**
     * Chiude il programma in modo sicuro.
     */
    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }
    /**
     * Metodo inizializzatore chiamato automaticamente al caricamento della GUI.
     */
    @FXML
    public void initialize() {
        welcomeLabel.setText("AGGIUNGI UN RISTORANTE");
        System.out.println("Utente: "+user+ " Id: "+id+" Ruolo: "+ruolo);
    }
    /**
     * Torna alla schermata precedente dei ristoranti.
     */
    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        super.goTo(event, "ristorantiRist.fxml");
    }
    /**
     * Naviga alla pagina profilo del ristoratore.
     */
    @FXML
    protected void onProfileClick(ActionEvent event) throws IOException {
        super.onProfileClick(event);
    }
    /**
     * Naviga alla lista dei ristoranti gestiti dal ristoratore.
     */
    @FXML
    protected void onRistorantiClick(ActionEvent event) throws IOException {
        super.onRistorantiClick(event);
    }
    /**
     * Metodo chiamato al click del pulsante "Aggiungi Ristorante".
     * <p>
     * Recupera i dati dal form, li valida, genera un ID,
     * ottiene le coordinate geografiche, crea l’oggetto {@link Ristorante}
     * e lo salva nel file JSON.
     * </p>
     */
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
        String cousine = cousineRist.getValue();
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

        double[] coord;
        coord = coordinate(address);// latitudine in pos 0 e long in pos 1
        int stelle = Integer.parseInt(stars); // metto come intero il campo stelle

        Ristorante nuovo = new Ristorante(Id, idRistoratore, name, address, location, price, cousine,coord[0], coord[1],
                tel, Url, stelle, service,description,d, b, mail);

        aggiungiRistorante(nuovo,"ristoranti.json");
        //CLEAR DEL PROMPT TEXT
        nameRist.clear();
        addressRist.clear();
        locationRist.clear();
        priceRist.clear();
        telRist.clear();
        UrlRist.clear();
        serviceRist.clear();
        descriptionRist.clear();
        starsRist.clear();
        mailRist.clear();
        handleInput();
    }
    /**
     * Mostra un popup di conferma dopo l’inserimento corretto del nuovo ristorante.
     */
    protected void handleInput() {
        //if(controllo che tutti gli input siano andati bene allora mando questo messaggio)
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Inserimento nuovo ristorante");
        alert.setHeaderText("Ti sei registrato correttamente");
        alert.setContentText("I tuoi dati sono stati salvati...");
        alert.showAndWait();
        //else mando un errore specifico su un tipo di input inserito dall'utente
    }
    /**
     * Genera un nuovo ID per un ristorante basato sul numero attuale di ristoranti presenti nel file.
     *
     * @param fileJson Percorso del file JSON.
     * @return Il nuovo ID univoco.
     */
    public static int generaId(String fileJson) {

        int count = 0;

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            JsonNode root = mapper.readTree(new File(fileJson));
            JsonNode ristorantiNode = root.get("ristoranti");
            List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));// Deserializza in List<Ristorante>

            count=ristoranti.size() +1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }




}
