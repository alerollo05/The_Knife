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
 * Controller per la gestione del popup di risposta a una recensione da parte del ristoratore.
 * <p>
 * Estende {@link RecensioniRistController} per riutilizzare metodi e accesso alla sessione.
 * Permette l’inserimento e il salvataggio della risposta del ristoratore a una recensione specifica
 * associata a un ristorante.
 * </p>
 */
public class PopUpRecController extends RecensioniRistController{

    /** Etichetta che mostra il prompt della risposta. */
    @FXML
    private Label label1;

    /** Bottone per confermare l’invio della risposta alla recensione. */
    @FXML
    private Button okButton;

    /** Bottone per annullare la risposta e chiudere la finestra. */
    @FXML
    private Button noButton;

    /** Campo di testo per inserire la risposta alla recensione. */
    @FXML
    private TextField txt1;

    /** Riferimento al controller principale per aggiornare la UI dopo chiusura del popup. */
    private RecensioniRistController mainController;

    /**
     * Metodo inizializzatore del popup. Imposta il testo dell’etichetta e il prompt per il campo di testo,
     * e definisce l’azione associata al bottone OK (invio risposta).
     */
    public void initialize(){

            label1.setText("Risposta:");
            txt1.setPromptText("Inserisci la risposta:");
        okButton.setOnAction(e -> {
            try{
                String risposta = txt1.getText();
                rispondiAllaRecensione(risposta,SessionManager.idRecensione,"ristoranti.json");
                handleClose(e);//chiudi finestra popUp
            }catch (IOException ex){
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * Chiude la finestra del popup e aggiorna la schermata del controller principale (lista recensioni).
     *
     * @param event L'evento di chiusura (click su OK).
     * @throws IOException In caso di errore di input/output.
     */
    public void handleClose(ActionEvent event) throws IOException {
        // Chiude la finestra corrente
        SessionManager.idScelta = 0;

        if (mainController != null) {
            mainController.initialize(); //aggiorna la lista ristoranti nel padre
        }
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
        //Al massimo posso aggiornare la pagina rifacendo di nuovo goTo per leggere il dato nuovo
    }

    //SERVE PER AGGIORNARE LA PAGINA DI STAMPA DOPO MODIFICA DEL POP UP
    /**
     * Imposta il controller padre che ha aperto il popup, per permettere l’aggiornamento
     * della vista al momento della chiusura.
     *
     * @param controller Istanza del controller {@link RecensioniRistController}.
     */
    public void setMainController(RecensioniRistController controller) {
        this.mainController = controller;
    }
    /**
     * Chiude la finestra del popup senza effettuare modifiche.
     *
     * @param event L'evento generato dal click su "No" o "Annulla".
     * @throws IOException In caso di errore di input/output.
     */
    public void handleCloseAnnulla(ActionEvent event) throws IOException {
        // Chiude la finestra corrente
        SessionManager.idScelta = 0;
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        //super.goTo(event, "dettaglioRist.fxml");
        stage.close();
        //Al massimo posso aggiornare la pagina rifacendo di nuovo goTo per leggere il dato nuovo
    }

    /**
     * Metodo statico che aggiorna il contenuto della recensione corrispondente con una risposta fornita.
     *
     * @param risposta  Il testo della risposta da salvare.
     * @param IdRec     ID univoco della recensione a cui rispondere.
     * @param fileJson  Percorso al file JSON contenente i ristoranti.
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
                            if (rec.idRec == IdRec ) {
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
                System.out.println("Ristorante  non trovato.");
            } else if (!trovatoRecensione) {
                System.out.println("Recensione di  non trovata.");
            }

            // Ricrea l'oggetto JSON aggiornato
            ObjectNode nuovoRoot = mapper.createObjectNode();
            nuovoRoot.set("ristoranti", mapper.valueToTree(listaModificabile));

            // Sovrascrive il file
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileJson), nuovoRoot);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
