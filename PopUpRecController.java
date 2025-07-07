package com.example.the_knife.Ristoratore;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.the_knife.Utente.SessionManager.idRist;

/**
 * Controller del popup che consente al ristoratore di rispondere a una recensione.
 */
public class PopUpRecController extends RecensioniRistController {

    /** Etichetta che mostra il titolo o la richiesta. */
    @FXML
    private Label label1;

    /** Bottone per confermare l'inserimento della risposta. */
    @FXML
    private Button okButton;

    /** Bottone per annullare e chiudere la finestra. */
    @FXML
    private Button noButton;

    /** Campo di testo dove il ristoratore scrive la risposta alla recensione. */
    @FXML
    private TextField txt1;

    /**
     * Metodo chiamato all'inizializzazione del controller.
     * Imposta il testo e il comportamento del bottone di conferma.
     */
    public void initialize() {
        label1.setText("Risposta:");
        txt1.setPromptText("Inserisci la risposta:");
        okButton.setOnAction(e -> {
            try {
                String risposta = txt1.getText();
                rispondiAllaRecensione(risposta, SessionManager.idRecensione, "ristoranti.json");
                handleClose(e); // chiudi finestra popUp
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * Chiude la finestra del popup e aggiorna la schermata principale.
     *
     * @param event L'evento che ha causato la chiusura.
     * @throws IOException Se avviene un errore di I/O.
     */
    public void handleClose(ActionEvent event) throws IOException {
        SessionManager.idScelta = 0;

        if (mainController != null) {
            mainController.initialize(); // aggiorna la lista nel padre
        }
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /** Controller della schermata principale per aggiornare i dati al ritorno. */
    private RecensioniRistController mainController;

    /**
     * Imposta il controller principale per comunicazione tra finestre.
     *
     * @param controller Controller della schermata padre da aggiornare.
     */
    public void setMainController(RecensioniRistController controller) {
        this.mainController = controller;
    }

    /**
     * Chiude il popup senza effettuare modifiche.
     *
     * @param event L'evento di annullamento.
     * @throws IOException Se avviene un errore durante la chiusura.
     */
    public void handleCloseAnnulla(ActionEvent event) throws IOException {
        SessionManager.idScelta = 0;
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Risponde a una recensione presente nel file JSON dei ristoranti.
     *
     * @param risposta Testo della risposta del ristoratore.
     * @param IdRec    ID della recensione a cui rispondere.
     * @param fileJson Percorso del file JSON contenente i dati.
     */
    public static void rispondiAllaRecensione(String risposta, int IdRec, String fileJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            JsonNode root = mapper.readTree(new File(fileJson));
            JsonNode ristorantiNode = root.get("ristoranti");

            List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
            List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);

            boolean trovatoRistorante = false;
            boolean trovatoRecensione = false;

            for (Ristorante r : listaModificabile) {
                if (r.id == idRist) {
                    trovatoRistorante = true;
                    if (r.recensioni != null) {
                        for (Recensione rec : r.recensioni) {
                            if (rec.idRec == IdRec) {
                                rec.risposta = risposta;
                                trovatoRecensione = true;
                                break;
                            }
                        }
                    }
                    break;
                }
            }

            if (!trovatoRistorante) {
                System.out.println("Ristorante non trovato.");
            } else if (!trovatoRecensione) {
                System.out.println("Recensione non trovata.");
            }

            ObjectNode nuovoRoot = mapper.createObjectNode();
            nuovoRoot.set("ristoranti", mapper.valueToTree(listaModificabile));

            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileJson), nuovoRoot);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
