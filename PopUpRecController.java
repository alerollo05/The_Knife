package com.example.the_knife.Ristoratore;

import com.example.the_knife.InputValidator;
import com.example.the_knife.Utente.SessionManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

public class PopUpRecController extends RecensioniRistController{

    @FXML
    private Label label1;

    @FXML
    private Button okButton;

    @FXML
    private Button noButton;

    @FXML
    private TextField txt1;

    public void initialize(){

            label1.setText("Risposta:");
            txt1.setPromptText("Inserisci la risposta:");
        okButton.setOnAction(e -> {
            try{
                String risposta = txt1.getText();
                //rispondiAllaRecensione();
                handleClose(e);//chiudi finestra popUp
            }catch (IOException ex){
                throw new RuntimeException(ex);
            }
        });
    }


    public void handleClose(ActionEvent event) throws IOException {
        // Chiude la finestra corrente
        SessionManager.idScelta = 0;

        if (mainController != null) {
            mainController.initialize(); //aggiorna la lista ristoranti nel padre
        }
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        //super.goTo(event, "dettaglioRist.fxml");
        stage.close();
        //Al massimo posso aggiornare la pagina rifacendo di nuovo goTo per leggere il dato nuovo
    }
    public void handleCloseAnnulla(ActionEvent event) throws IOException {
        // Chiude la finestra corrente
        SessionManager.idScelta = 0;
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        //super.goTo(event, "dettaglioRist.fxml");
        stage.close();
        //Al massimo posso aggiornare la pagina rifacendo di nuovo goTo per leggere il dato nuovo
    }

    //SERVE PER AGGIORNARE LA PAGINA DI STAMPA DOPO MODIFICA DEL POP UP
    protected DettaglioRistController mainController;

    public void setMainController(DettaglioRistController controller) {
        this.mainController = controller;
    }

    public static void rispondiAllaRecensione(String nomeRistorante, String autoreRecensione, String risposta, String fileJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File(fileJson));
            JsonNode ristorantiNode = root.get("ristoranti");

            List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
            List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);

            boolean trovatoRistorante = false;
            boolean trovatoRecensione = false;

            for (Ristorante r : listaModificabile) {
                if (r.Name.equalsIgnoreCase(nomeRistorante)) {
                    trovatoRistorante = true;
                    if (r.recensioni != null) {
                        for (Recensione rec : r.recensioni) {
                            if (rec.author.equalsIgnoreCase(autoreRecensione)) {
                                rec.risposta = risposta;
                                trovatoRecensione = true;
                                System.out.println("Risposta aggiornata per la recensione di '" + autoreRecensione + "'.");
                                break;
                            }
                        }
                    }
                    break;
                }
            }

            if (!trovatoRistorante) {
                System.out.println("Ristorante '" + nomeRistorante + "' non trovato.");
            } else if (!trovatoRecensione) {
                System.out.println("Recensione di '" + autoreRecensione + "' non trovata.");
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
