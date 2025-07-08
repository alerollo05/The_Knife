package com.example.the_knife.Ristoratore;

import com.example.the_knife.Utente.SessionManager;
import com.example.the_knife.LoginController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
/**
 * Controller per la dashboard principale del ristoratore.
 *
 * <p>Questa classe gestisce l'interfaccia iniziale mostrata dopo il login
 * di un utente con ruolo "ristoratore". Permette l'accesso rapido alle funzionalità
 * principali come la gestione dei ristoranti, la modifica del profilo, la ricerca
 * con filtri avanzati e l'aggiunta di nuovi ristoranti.</p>
 *
 * <p>Estende {@link LoginController} per riutilizzare le funzionalità comuni
 * come il cambio schermata o l’uscita dal programma.</p>
 */
public class DashBoardRistController extends LoginController {

    /** Gestore della sessione corrente. */
    SessionManager session = SessionManager.getInstance();

    /** Username dell’utente loggato. */
    private String user = session.getUsername();

    /** ID dell’utente loggato. */
    private int id = session.getUserId();

    /** Ruolo dell’utente loggato. */
    private String ruolo = session.getRuolo();

    // Campi FXML per input ricerca e filtri

    @FXML private TextField cityField;
    @FXML private ComboBox<String> cuisineBox;
    @FXML private ComboBox<String> priceBox;
    @FXML private ComboBox<String> deliveryBox;
    @FXML private ComboBox<String> bookingBox;
    @FXML private Button searchButton;

    /**
     * Esegue il logout dell'utente e reindirizza alla schermata di login.
     *
     * @param event Evento del click sul pulsante logout.
     */
    public void handleLogOut(ActionEvent event) {
        SessionManager.getInstance().logout();//cancello i dati dalla sessione
        SessionManager.counter1 = 0;
        SessionManager.counter = 0;
        SessionManager.counter2 = 0;
        try {
            super.goTo(event, "/com/example/the_knife/loginPage.fxml");//metto il path relativo intero per uscire e tornare alla login che si trova in una cartella meno profonda di quella dei ristoratori
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Chiude l'applicazione in maniera sicura.
     *
     * @param event Evento associato alla chiusura.
     */
    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }
    /**
     * Inizializza i componenti della dashboard.
     * Imposta le comboBox, carica icone e mostra la lista ristoranti (filtrata o top 10).
     *
     * @throws IOException se si verifica un errore nel caricamento dei dati iniziali.
     */
    @FXML
    public void initialize() throws IOException {
        SessionManager.menu = 0;
        System.out.println("Utente: "+user+ "Id: "+id+"Ruolo: "+ruolo);


        //ICONA LENTE INGRANDIMENTO
        searchButton.getStyleClass().add("accent-button");
        Image lenteIngrandimento = new Image(getClass().getResource("/com/example/the_knife/icone/lenteIngrandimento.png").toExternalForm());
        ImageView iconView = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
        iconView.setFitWidth(24);
        iconView.setFitHeight(24);//setto il ridimensionamento
        searchButton.setGraphic(iconView);
        iconView.setImage(lenteIngrandimento);


        SessionManager.pagina = 1;
        cuisineBox.setItems(FXCollections.observableArrayList(
                "Mediterranea", "Italiana", "Giapponese",
                "Francese", "Cinese", "Messicana", "Indiana"));
        cuisineBox.setMaxWidth(60);

        priceBox.setItems(FXCollections.observableArrayList(
                "€", "€€", "€€€", "€€€€"));
        priceBox.setMaxWidth(60);

        deliveryBox.setItems(FXCollections.observableArrayList(
                "Delivery", "No delivery"));
        deliveryBox.setMaxWidth(60);

        bookingBox.setItems(FXCollections.observableArrayList(
                "Booking", "No booking online"));
        bookingBox.setMaxWidth(60);

        // Ripristino dei filtri se si sta tornando indietro
        if (SessionManager.counter1 == 1) {
            cityField.setText(SessionManager.luogoNomeStatico);
            cuisineBox.setValue(SessionManager.tipoCucinaStatico);
            priceBox.setValue(SessionManager.prezzoStatico);
            deliveryBox.setValue(SessionManager.deliveryStatico ? "Delivery" : "No delivery");
            bookingBox.setValue(SessionManager.bookingStatico ? "Booking" : "No booking online");
            super.printListRist("/com/example/the_knife/dettaglioRistoranteSearch.fxml","/com/example/the_knife/recensioneRistoranteSearch.fxml"); // mostra la lista con i filtri precedenti
        } else {
            deliveryBox.getSelectionModel().selectFirst();
            bookingBox.getSelectionModel().selectFirst();
            super.printListRistTop10("/com/example/the_knife/dettaglioRistoranteSearch.fxml","/com/example/the_knife/recensioneRistoranteSearch.fxml"); // mostra top 10 iniziale
        }
    }
    /**
     * Avvia la ricerca filtrata dei ristoranti in base ai parametri selezionati.
     */
    @FXML
    private void onSearchClicked() {
        SessionManager.counter1 = 0; // reset per applicare nuovi filtri
        super.printListRist("/com/example/the_knife/dettaglioRistoranteSearch.fxml","/com/example/the_knife/recensioneRistoranteSearch.fxml");
    }
    /**
     * Reindirizza alla schermata del profilo utente.
     *
     * @param event Evento di click sul pulsante profilo.
     * @throws IOException se la schermata non può essere caricata.
     */
    @FXML
    protected void onProfileClick(ActionEvent event) throws IOException {
        SessionManager.counter1 = 0;
        super.goTo(event,"profilePageRist.fxml");
    }
    /**
     * Reindirizza alla schermata dei ristoranti gestiti dal ristoratore.
     *
     * @param event Evento di click.
     * @throws IOException se la schermata non può essere caricata.
     */
    @FXML
    protected void onRistorantiClick(ActionEvent event) throws IOException {
        SessionManager.counter1 = 0;
        super.goTo(event,"ristorantiRist.fxml");
    }
    /**
     * Reindirizza alla lista dei ristoranti preferiti.
     *
     * @param event Evento di click.
     * @throws IOException se la schermata non può essere caricata.
     */
    @FXML
    protected void onPreferitiClick(ActionEvent event) throws IOException {
        SessionManager.counter1 = 0;
        super.goTo(event,"preferitiRist.fxml");
    }
    /**
     * Torna alla schermata della dashboard ristoratore.
     *
     * @param event Evento di click.
     * @throws IOException se la schermata non può essere caricata.
     */
    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        super.goTo(event, "dashBoardRist.fxml");
    }
    /**
     * Reindirizza alla schermata per l'aggiunta di un nuovo ristorante.
     *
     * @param event Evento di click.
     * @throws IOException se la schermata non può essere caricata.
     */
    @FXML
    protected void onAddRistClick(ActionEvent event) throws IOException {
        SessionManager.counter1 = 0;
        super.goTo(event,"newRist.fxml");
    }


}
