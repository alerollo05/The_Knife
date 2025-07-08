package com.example.the_knife;

import com.example.the_knife.Ristoratore.Ristorante;
import com.example.the_knife.Ristoratore.RistorantiRistController;
import com.example.the_knife.Utente.SessionManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.the_knife.Utente.SessionManager.idRist;
/**
 * Controller per la visualizzazione dei dettagli di un ristorante selezionato nella ricerca.
 * <p>
 * Estende {@link RistorantiRistController} per ereditare funzionalità di navigazione e accesso ai dati.
 * Carica dinamicamente nella {@link GridPane} tutte le informazioni relative a un ristorante,
 * come nome, indirizzo, tipo di cucina, contatti, servizi, descrizione, e disponibilità di delivery o prenotazione online.
 * <p>
 * Il controller supporta anche il ritorno alla schermata precedente in base al ruolo e contesto dell'utente tramite {@code SessionManager}.
 * </p>
 *
 *
 */
public class DettaglioRistoranteSearchController extends RistorantiRistController {
    /**
     * Etichetta principale per il titolo della pagina.
     */
    @FXML
    private Label welcomeLabel;
    /**
     * Griglia utilizzata per mostrare i dettagli del ristorante.
     */
    @FXML
    private GridPane grid;
    /**
     * Metodo di inizializzazione automatico di JavaFX chiamato dopo il caricamento dell'FXML.
     * Imposta l'intestazione e chiama {@link #printDettagliRist()} per visualizzare i dati del ristorante.
     *
     * @throws IOException se si verifica un errore durante la lettura del file JSON dei ristoranti
     */
    @FXML
    public void initialize() throws IOException {
        welcomeLabel.setText("DETTAGLIO DEL RISTORANTE");
        printDettagliRist();
    }
    /**
     * Carica e visualizza tutti i dettagli del ristorante selezionato in una griglia.
     * I dati vengono recuperati dal file JSON usando l'id del ristorante presente in {@link SessionManager}.
     * <p>
     * Crea dinamicamente le etichette per i campi e i relativi valori, impostando gli stili e la formattazione.
     * </p>
     *
     * @throws IOException se si verifica un errore di lettura del file JSON
     */
    private void printDettagliRist() throws IOException {
        try {
            grid.getChildren().clear(); //evita duplicati quando aggiorni il file con le modifiche
            grid.getColumnConstraints().clear(); //reset dei vincoli

            Ristorante rist = super.getRistoranteById("ristoranti.json", idRist);

            Label nomeLabel = new Label("Nome: ");
            nomeLabel.getStyleClass().add("textNormal");
            Label nome = new Label(rist.getName());
            nome.getStyleClass().add("textNormal");


            Label indirizzoLabel = new Label("Indirizzo: ");
            indirizzoLabel.getStyleClass().add("textNormal");
            Label indirizzo = new Label(rist.getAddress());
            indirizzo.getStyleClass().add("textNormal");

            Label cittaLabel = new Label("Citta: ");
            cittaLabel.getStyleClass().add("textNormal");
            Label citta = new Label(rist.getLocation());
            citta.getStyleClass().add("textNormal");

            Label cucinaLabel = new Label("Cucina: ");
            cucinaLabel.getStyleClass().add("textNormal");
            Label cucina = new Label(rist.getCuisine());
            cucina.getStyleClass().add("textNormal");

            Label telefonoLabel = new Label("Telefono: ");
            telefonoLabel.getStyleClass().add("textNormal");
            Label telefono = new Label(rist.getPhoneNumber());
            telefono.getStyleClass().add("textNormal");

            Label emailLabel = new Label("Email: ");
            emailLabel.getStyleClass().add("textNormal");
            Label email = new Label(rist.getEmail());
            email.getStyleClass().add("textNormal");


            Label urlLabel = new Label("Url: ");
            urlLabel.getStyleClass().add("textNormal");
            Label url = new Label(rist.getWebsiteUrl());
            url.getStyleClass().add("textNormal");

            Label descrizioneLabel = new Label("Descrizione: ");
            descrizioneLabel.getStyleClass().add("textNormal");
            Label descrizione = new Label(rist.getDescription());
            descrizione.getStyleClass().add("textNormal");

            Label prezzoLabel = new Label("Prezzo: ");
            prezzoLabel.getStyleClass().add("textNormal");
            Label prezzo = new Label(rist.getPrice());
            prezzo.getStyleClass().add("textNormal");

            Label numStelleLabel = new Label("Numero di stelle: ");
            numStelleLabel.getStyleClass().add("textNormal");
            Label stelle = new Label("" + rist.getGreenStar());
            stelle.getStyleClass().add("textNormal");

            Label ServiziLabel = new Label("Servizi: ");
            ServiziLabel.getStyleClass().add("textNormal");
            Label servizi = new Label(rist.getFacilitiesAndServices());
            servizi.getStyleClass().add("textNormal");


            Label deliveryLabel = new Label("Delivery: ");
            deliveryLabel.getStyleClass().add("textNormal");

            Label delivery;

// Seleziona il valore corrente del ristorante
            if (rist.isDelivery()) {
                delivery = new Label("Si");
                delivery.getStyleClass().add("textNormal");
            } else {
                delivery = new Label("No");
                delivery.getStyleClass().add("textNormal");
            }

            Label bookingOnlineLabel = new Label("Booking online: ");
            bookingOnlineLabel.getStyleClass().add("textNormal");
// Seleziona il valore corrente del ristorante
            Label booking;
            if (rist.isBookingOnline()) {
                booking = new Label("Si");
                booking.getStyleClass().add("textNormal");
            } else {
                booking = new Label("No");
                booking.getStyleClass().add("textNormal");
            }



            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(20);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(60);
            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPercentWidth(20);
            grid.getColumnConstraints().addAll(col1, col2, col3);


            grid.setPrefWidth(Double.MAX_VALUE);
            grid.setMaxWidth(Double.MAX_VALUE);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(15));
            grid.getStyleClass().add("list-rist");

            //forzatura dei label a crescere orizzontalmente
            GridPane.setFillWidth(nome, true);
            GridPane.setFillWidth(indirizzo, true);
            GridPane.setFillWidth(citta, true);
            GridPane.setFillWidth(cucina, true);
            GridPane.setFillWidth(telefono, true);
            GridPane.setFillWidth(email, true);
            GridPane.setFillWidth(url, true);
            GridPane.setFillWidth(descrizione, true);
            GridPane.setFillWidth(prezzo, true);
            GridPane.setFillWidth(stelle, true);
            GridPane.setFillWidth(servizi, true);


            grid.add(nomeLabel, 0, 0); grid.add(nome, 1, 0);
            grid.add(indirizzoLabel, 0, 1); grid.add(indirizzo, 1, 1);
            grid.add(cittaLabel, 0, 2); grid.add(citta, 1, 2);
            grid.add(cucinaLabel, 0, 3); grid.add(cucina, 1, 3);
            grid.add(telefonoLabel, 0, 4); grid.add(telefono, 1, 4);
            grid.add(emailLabel, 0, 5); grid.add(email, 1, 5);
            grid.add(urlLabel, 0, 6); grid.add(url, 1, 6);
            grid.add(descrizioneLabel, 0, 7); grid.add(descrizione, 1, 7);
            grid.add(prezzoLabel, 0, 8); grid.add(prezzo, 1, 8);
            grid.add(numStelleLabel, 0, 9); grid.add(stelle, 1, 9);
            grid.add(ServiziLabel, 0, 10); grid.add(servizi, 1, 10);
            grid.add(deliveryLabel, 0, 11); grid.add(delivery, 1, 11);
            grid.add(bookingOnlineLabel, 0, 12); grid.add(booking, 1, 12);

        } catch (Exception e) {
            System.err.println("Errore nella lettura del file ristoranti.json");
            e.printStackTrace();
        }
    }
    /**
     * Metodo per la navigazione alla schermata precedente in base al contesto:
     * <ul>
     *     <li>Pagina iniziale</li>
     *     <li>Dashboard cliente o lista ristoranti</li>
     *     <li>Dashboard ristoratore o lista preferiti</li>
     * </ul>
     * <p>
     * La logica si basa su {@link SessionManager#pagina} e {@link SessionManager#menu}.
     * </p>
     *
     * @param event evento di tipo {@link ActionEvent} generato dal bottone "Indietro"
     * @throws IOException in caso di errore nella transizione tra schermate
     */
    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        if(SessionManager.pagina == 0) {
            if(SessionManager.counter != 1) SessionManager.counter = 0;
            super.goTo(event, "startPage.fxml");
        }else if(SessionManager.pagina == 2) {
            if(SessionManager.menu == 1) {
                super.goTo(event, "Cliente/ristorantiClient.fxml");
            }else {
                super.goTo(event, "Cliente/dashBoardClient.fxml");
            }
        }else if(SessionManager.pagina == 1) {
            if(SessionManager.menu == 1) {
                super.goTo(event, "Ristoratore/preferitiRist.fxml");
            }else {
                super.goTo(event, "Ristoratore/dashBoardRist.fxml");
            }
        }
    }

    /**
     * Chiude il programma. Override del metodo ereditato da {@link RistorantiRistController}.
     *
     * @param event evento di tipo {@link ActionEvent}
     */
    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }
}
