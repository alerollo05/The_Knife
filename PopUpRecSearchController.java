package com.example.the_knife;

import com.example.the_knife.Ristoratore.Recensione;
import com.example.the_knife.Ristoratore.RecensioniRistController;
import com.example.the_knife.Ristoratore.Ristorante;
import com.example.the_knife.Utente.SessionManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.the_knife.Utente.SessionManager.idRecensione;
import static com.example.the_knife.Utente.SessionManager.idRist;

/**
 * Controller per la finestra pop-up che permette la modifica di una recensione esistente.
 * Estende {@link RecensioneRistoranteSearchController} per riutilizzare la logica di modifica.
 */
public class PopUpRecSearchController extends RecensioneRistoranteSearchController {

    @FXML
    private Label commentoLabel;

    @FXML
    private Button okButton;

    @FXML
    private TextField commentoField;

    @FXML
    private Label ratingLabel;

    @FXML
    private TextField ratingField;

    /**
     * Inizializza i campi del pop-up e gestisce l'evento sul pulsante OK per salvare la recensione modificata.
     */
    public void initialize() {
        commentoLabel.setText("Modifica commento:");
        commentoField.setPromptText("Inserisci il commento:");
        ratingLabel.setText("Modifica valutazione:");
        ratingField.setPromptText("Inserisci la valutazione:");

        okButton.setOnAction(e -> {
            try {
                String commento = commentoField.getText();
                InputValidator.validaCommento(commento);

                String rating = ratingField.getText();
                InputValidator.validaRating(rating);

                int valutazione = Integer.parseInt(rating);

                super.modificaRecensioni(commento, valutazione, "ristoranti.json");
                super.top10Ristoranti("ristoranti.json", "top10rist.json");
                handleClose(e); // Chiudi finestra pop-up
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * Chiude la finestra pop-up e aggiorna la lista delle recensioni nel controller principale.
     *
     * @param event Evento di chiusura.
     * @throws IOException se il controller principale non riesce ad aggiornare la lista.
     */
    public void handleClose(ActionEvent event) throws IOException {
        SessionManager.idScelta = 0;

        if (mainController != null) {
            mainController.printListRec();
        }

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Permette l'aggiornamento della finestra principale associata.
     */
    private RecensioneRistoranteSearchController mainController;

    /**
     * Imposta il controller principale per aggiornare la lista delle recensioni al termine della modifica.
     *
     * @param controller Controller principale da notificare dopo la modifica.
     */
    public void setMainController(RecensioneRistoranteSearchController controller) {
        this.mainController = controller;
    }

    /**
     * Chiude la finestra pop-up senza salvare modifiche.
     *
     * @param event Evento generato dal pulsante "Annulla".
     * @throws IOException se la finestra non può essere chiusa correttamente.
     */
    public void handleCloseAnnulla(ActionEvent event) throws IOException {
        SessionManager.idScelta = 0;

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
