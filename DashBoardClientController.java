package com.example.the_knife.Cliente;

import com.example.the_knife.Utente.SessionManager;
import com.example.the_knife.LoginController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

/**
 * Controller per la dashboard del cliente.
 * Estende {@link LoginController} e gestisce l'interfaccia grafica per la ricerca
 * dei ristoranti secondo filtri specifici (città, cucina, prezzo, delivery, prenotazione),
 * nonché la navigazione tra le diverse schermate dell'applicazione.
 */
public class DashBoardClientController extends LoginController {

    /**
     * Istanza del gestore di sessione per accedere ai dati dell'utente loggato.
     */
    SessionManager session = SessionManager.getInstance();

    /**
     * Nome utente attualmente loggato.
     */
    private String user = session.getUsername();

    /**
     * ID dell'utente attualmente loggato.
     */
    private int id = session.getUserId();

    /**
     * Ruolo dell'utente attualmente loggato.
     */
    private String ruolo = session.getRuolo();

    @FXML private TextField cityField;
    @FXML private ComboBox<String> cuisineBox;
    @FXML private ComboBox<String> priceBox;
    @FXML private ComboBox<String> deliveryBox;
    @FXML private ComboBox<String> bookingBox;
    @FXML private Button searchButton;

    /**
     * Gestisce il logout dell'utente:
     * <ul>
     *     <li>Elimina i dati salvati nella sessione</li>
     *     <li>Reimposta i contatori</li>
     *     <li>Effettua il redirect alla pagina di login</li>
     * </ul>
     *
     * @param event l'evento generato dal pulsante di logout
     */
    public void handleLogOut(ActionEvent event) {
        SessionManager.getInstance().logout();
        SessionManager.counter2 = 0;
        SessionManager.counter = 0;
        try {
            super.goTo(event, "/com/example/the_knife/loginPage.fxml");
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Chiude in modo sicuro l'applicazione.
     *
     * @param event l'evento generato dalla richiesta di chiusura
     */
    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }

    /**
     * Inizializza la dashboard cliente:
     * <ul>
     *     <li>Imposta i filtri disponibili (cucina, prezzo, delivery, prenotazione)</li>
     *     <li>Configura l’icona del pulsante di ricerca</li>
     *     <li>Ripristina eventuali filtri salvati se si torna indietro</li>
     *     <li>Mostra i risultati filtrati o la top 10 iniziale</li>
     * </ul>
     */
    @FXML
    public void initialize() {
        SessionManager.menu = 0;
        System.out.println("Utente: "+user+ "Id: "+id+"Ruolo: "+ruolo);

        searchButton.getStyleClass().add("accent-button");
        Image lenteIngrandimento = new Image(getClass().getResource("/com/example/the_knife/icone/lenteIngrandimento.png").toExternalForm());
        ImageView iconView = new ImageView();
        iconView.setFitWidth(24);
        iconView.setFitHeight(24);
        searchButton.setGraphic(iconView);
        iconView.setImage(lenteIngrandimento);

        SessionManager.pagina = 2;

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

        if (SessionManager.counter2 == 1) {
            cityField.setText(SessionManager.luogoNomeStatico);
            cuisineBox.setValue(SessionManager.tipoCucinaStatico);
            priceBox.setValue(SessionManager.prezzoStatico);
            deliveryBox.setValue(SessionManager.deliveryStatico ? "Delivery" : "No delivery");
            bookingBox.setValue(SessionManager.bookingStatico ? "Booking" : "No booking online");
            super.printListRist(
                    "/com/example/the_knife/dettaglioRistoranteSearch.fxml",
                    "/com/example/the_knife/recensioneRistoranteSearch.fxml"
            );
        } else {
            deliveryBox.getSelectionModel().selectFirst();
            bookingBox.getSelectionModel().selectFirst();
            super.printListRistTop10(
                    "/com/example/the_knife/dettaglioRistoranteSearch.fxml",
                    "/com/example/the_knife/recensioneRistoranteSearch.fxml"
            );
        }
    }

    /**
     * Esegue la ricerca dei ristoranti applicando i filtri selezionati.
     */
    @FXML
    private void onSearchClicked() {
        SessionManager.counter2 = 0;
        super.printListRist(
                "/com/example/the_knife/dettaglioRistoranteSearch.fxml",
                "/com/example/the_knife/recensioneRistoranteSearch.fxml"
        );
    }

    /**
     * Naviga alla pagina del profilo del cliente.
     *
     * @param event l'evento generato dal click sul pulsante profilo
     * @throws IOException se la pagina non può essere caricata
     */
    @FXML
    protected void onProfileClick(ActionEvent event) throws IOException {
        SessionManager.counter2 = 0;
        goTo(event,"profilePageClient.fxml");
    }

    /**
     * Naviga alla lista dei ristoranti per il cliente.
     *
     * @param event l'evento generato dal click sul pulsante ristoranti
     * @throws IOException se la pagina non può essere caricata
     */
    @FXML
    protected void onRistorantiClick(ActionEvent event) throws IOException {
        SessionManager.counter2 = 0;
        goTo(event,"ristorantiClient.fxml");
    }

    /**
     * Torna alla dashboard cliente.
     *
     * @param event l'evento generato dal click sul pulsante di ritorno
     * @throws IOException se la pagina non può essere caricata
     */
    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        SessionManager.counter2 = 0;
        super.goTo(event, "dashBoardClient.fxml");
    }
}
