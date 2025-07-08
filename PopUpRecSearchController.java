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
 * Controller per la finestra popup di modifica recensione.
 * <p>
 * Consente all'utente di aggiornare commento e valutazione di una recensione già esistente.
 * Dopo la conferma, aggiorna il file JSON e ricalcola la classifica dei Top 10 ristoranti.
 * </p>
 */
public class PopUpRecSearchController extends RecensioneRistoranteSearchController {

    /** Etichetta per il campo commento. */
        @FXML
        private Label commentoLabel;
    /** Bottone di conferma modifica. */
        @FXML
        private Button okButton;
    /** Campo di input per il nuovo commento. */
        @FXML
        private TextField commentoField;
    /** Etichetta per il campo rating. */
        @FXML
        private Label ratingLabel;
    /** Campo di input per il nuovo rating. */
        @FXML
        private TextField ratingField;

    /**
     * Inizializza la finestra popup con i testi e placeholder corretti.
     * Imposta l'azione del bottone OK per validare i dati, aggiornare la recensione
     * e chiudere la finestra.
     */
        public void initialize(){

            commentoLabel.setText("Modifica commento:");
            commentoField.setPromptText("Inserisci il commento:");
            ratingLabel.setText("Modifica valutazione:");
            ratingField.setPromptText("Inserisci la valutazione:");
            okButton.setOnAction(e -> {
                try{
                    String commento = commentoField.getText();
                    InputValidator.validaCommento(commento);
                    String rating = ratingField.getText();
                    InputValidator.validaRating(rating);
                    int valutazione = Integer.parseInt(rating);
                    super.modificaRecensioni(commento,valutazione,"ristoranti.json");
                    super.top10Ristoranti("ristoranti.json","top10rist.json");
                    handleClose(e);//chiudi finestra popUp
                }catch (IOException ex){
                    throw new RuntimeException(ex);
                }
            });
        }

    /**
     * Chiude la finestra popup e aggiorna la lista recensioni nella schermata principale,
     * se il controller principale è stato assegnato.
     *
     * @param event l'evento di click sul pulsante "OK"
     * @throws IOException se si verifica un errore durante la gestione della chiusura
     */
        public void handleClose(ActionEvent event) throws IOException {
            // Chiude la finestra corrente
            SessionManager.idScelta = 0;

            if (mainController != null) {
                mainController.printListRec();
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
            //Al massimo posso aggiornare la pagina rifacendo di nuovo goTo per leggere il dato nuovo
        }

        //SERVE PER AGGIORNARE LA PAGINA DI STAMPA DOPO MODIFICA DEL POP UP
    /** Riferimento al controller principale da aggiornare al termine della modifica. */
        private RecensioneRistoranteSearchController mainController;
    /**
     * Imposta il controller principale per consentire al popup di aggiornare la vista principale
     * al termine della modifica della recensione.
     *
     * @param controller il controller principale {@code RecensioneRistoranteSearchController} da associare
     */
        public void setMainController(RecensioneRistoranteSearchController controller) {
            this.mainController = controller;
        }
    /**
     * Chiude la finestra popup senza applicare modifiche.
     *
     * @param event l'evento di click sul pulsante "Annulla"
     * @throws IOException se si verifica un errore durante la chiusura
     */
        public void handleCloseAnnulla(ActionEvent event) throws IOException {
            // Chiude la finestra corrente
            SessionManager.idScelta = 0;
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            //super.goTo(event, "dettaglioRist.fxml");
            stage.close();
            //Al massimo posso aggiornare la pagina rifacendo di nuovo goTo per leggere il dato nuovo
        }

    }
