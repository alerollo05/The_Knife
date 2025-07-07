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
 * Controller che gestisce la visualizzazione dei dettagli di un ristorante selezionato
 * nella pagina di ricerca per gli utenti.
 */
public class DettaglioRistoranteSearchController extends RistorantiRistController {

    /** Label di benvenuto o intestazione */
    @FXML
    private Label welcomeLabel;

    /** Griglia in cui vengono mostrati i dettagli del ristorante */
    @FXML
    private GridPane grid;

    /**
     * Metodo di inizializzazione che imposta l'intestazione e richiama la stampa dei dettagli.
     * @throws IOException se la lettura del file JSON fallisce
     */
    @FXML
    public void initialize() throws IOException {
        welcomeLabel.setText("DETTAGLIO DEL RISTORANTE");
        printDettagliRist();
    }

    /**
     * Recupera e stampa tutti i dettagli del ristorante selezionato nella griglia.
     * @throws IOException se ci sono problemi di accesso al file JSON
     */
    private void printDettagliRist() throws IOException {
        try {
            grid.getChildren().clear();
            grid.getColumnConstraints().clear();

            Ristorante rist = super.getRistoranteById("ristoranti.json", idRist);

            // Creazione delle etichette per ogni attributo
            Label nomeLabel = new Label("Nome: ");
            nomeLabel.getStyleClass().add("textNormal");
            Label nome = new Label(rist.getName());
            nome.getStyleClass().add("textNormal");

            Label indirizzoLabel = new Label("Indirizzo: ");
            indirizzoLabel.getStyleClass().add("textNormal");
            Label indirizzo = new Label(rist.getAddress());
            indirizzo.getStyleClass().add("textNormal");

            Label cittaLabel = new Label("Città: ");
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
            Label stelle = new Label(String.valueOf(rist.getGreenStar()));
            stelle.getStyleClass().add("textNormal");

            Label serviziLabel = new Label("Servizi: ");
            serviziLabel.getStyleClass().add("textNormal");
            Label servizi = new Label(rist.getFacilitiesAndServices());
            servizi.getStyleClass().add("textNormal");

            Label deliveryLabel = new Label("Delivery: ");
            deliveryLabel.getStyleClass().add("textNormal");
            Label delivery = new Label(rist.isDelivery() ? "Si" : "No");
            delivery.getStyleClass().add("textNormal");

            Label bookingOnlineLabel = new Label("Booking online: ");
            bookingOnlineLabel.getStyleClass().add("textNormal");
            Label booking = new Label(rist.isBookingOnline() ? "Si" : "No");
            booking.getStyleClass().add("textNormal");

            // Layout griglia
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

            // Aggiunta al layout
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
            grid.add(serviziLabel, 0, 10); grid.add(servizi, 1, 10);
            grid.add(deliveryLabel, 0, 11); grid.add(delivery, 1, 11);
            grid.add(bookingOnlineLabel, 0, 12); grid.add(booking, 1, 12);

        } catch (Exception e) {
            System.err.println("Errore nella lettura del file ristoranti.json");
            e.printStackTrace();
        }
    }

    /**
     * Gestisce il ritorno alla schermata precedente in base al contesto di navigazione.
     * @param event l'evento generato dal click sul bottone
     * @throws IOException se si verifica un errore nel cambio di scena
     */
    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        if (SessionManager.pagina == 0) {
            if (SessionManager.counter != 1) SessionManager.counter = 0;
            super.goTo(event, "startPage.fxml");
        } else if (SessionManager.pagina == 2) {
            if (SessionManager.menu == 1) {
                super.goTo(event, "Cliente/ristorantiClient.fxml");
            } else {
                super.goTo(event, "Cliente/dashBoardClient.fxml");
            }
        } else if (SessionManager.pagina == 1) {
            if (SessionManager.menu == 1) {
                super.goTo(event, "Ristoratore/preferitiRist.fxml");
            } else {
                super.goTo(event, "Ristoratore/dashBoardRist.fxml");
            }
        }
    }

    /**
     * Chiude l'applicazione.
     * @param event evento generato dall'azione di chiusura
     */
    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }
}
