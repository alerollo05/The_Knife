package com.example.the_knife.Ristoratore;

import com.example.the_knife.InputValidator;
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

/**
 * Controller per il popup che consente la modifica dei dati di un ristorante.
 * Estende {@link DettaglioRistController} per aggiornare la vista principale dopo la modifica.
 */
public class PopUpRistController extends DettaglioRistController {

    /** Etichetta per mostrare il tipo di modifica all'utente. */
    @FXML
    private Label label1;

    /** Bottone per confermare e salvare la modifica. */
    @FXML
    private Button okButton;

    /** Campo di testo per l'inserimento del nuovo valore da parte dell'utente. */
    @FXML
    private TextField txt1;

    /**
     * Chiude la finestra del popup e aggiorna la vista principale, se presente.
     *
     * @param event Evento generato dal bottone di conferma.
     * @throws IOException In caso di errore nella chiusura dello stage.
     */
    public void handleClose(ActionEvent event) throws IOException {
        SessionManager.idScelta = 0;

        if (mainController != null) {
            mainController.initialize();
        }

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Annulla l'operazione e chiude il popup senza effettuare modifiche.
     *
     * @param event Evento generato dal bottone di annullamento.
     * @throws IOException In caso di errore nella chiusura dello stage.
     */
    public void handleCloseAnnulla(ActionEvent event) throws IOException {
        SessionManager.idScelta = 0;
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Inizializza la vista del popup in base all'opzione scelta dall'utente (tramite {@link SessionManager#idScelta}).
     * Imposta le label, i placeholder e le azioni del bottone di conferma dinamicamente.
     */
    public void initialize() {
        switch (SessionManager.idScelta) {
            case 1:
                label1.setText("Cambia nome:");
                txt1.setPromptText("Inserisci il nuovo nome");
                okButton.setOnAction(e -> {
                    try {
                        String newNome = txt1.getText();
                        InputValidator.validaNomeRist(newNome);
                        modificaRist("nome", newNome, "ristoranti.json");
                        handleClose(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 2:
                label1.setText("Cambia indirizzo:");
                txt1.setPromptText("Inserisci il nuovo indirizzo");
                okButton.setOnAction(e -> {
                    try {
                        String newAdress = txt1.getText();
                        InputValidator.validaIndirizzo(newAdress);
                        modificaRist("indirizzo", newAdress, "ristoranti.json");
                        handleClose(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 3:
                label1.setText("Cambia Città:");
                txt1.setPromptText("Inserisci la nuova città");
                okButton.setOnAction(e -> {
                    try {
                        String newCity = txt1.getText();
                        InputValidator.validaLuogo(newCity);
                        modificaRist("citta", newCity, "ristoranti.json");
                        handleClose(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 4:
                label1.setText("Cambia Cucina:");
                txt1.setPromptText("Inserisci il nuovo tipo di cucina");
                okButton.setOnAction(e -> {
                    try {
                        String newCuisine = txt1.getText();
                        InputValidator.validaLuogo(newCuisine);
                        modificaRist("cucina", newCuisine, "ristoranti.json");
                        handleClose(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 5:
                label1.setText("Cambia Telefono:");
                txt1.setPromptText("+39 0123456789");
                okButton.setOnAction(e -> {
                    try {
                        String newTel = txt1.getText();
                        InputValidator.validaTelefono(newTel);
                        modificaRist("telefono", newTel, "ristoranti.json");
                        handleClose(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 6:
                label1.setText("Cambia Email:");
                txt1.setPromptText("Inserisci la nuova mail");
                okButton.setOnAction(e -> {
                    try {
                        String newMail = txt1.getText();
                        InputValidator.validaEmail(newMail);
                        modificaRist("email", newMail, "ristoranti.json");
                        handleClose(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 7:
                label1.setText("Cambia URL:");
                txt1.setPromptText("Inserisci il nuovo URL");
                okButton.setOnAction(e -> {
                    try {
                        String newUrl = txt1.getText();
                        InputValidator.validaUrl(newUrl);
                        modificaRist("url", newUrl, "ristoranti.json");
                        handleClose(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 8:
                label1.setText("Cambia Descrizione:");
                txt1.setPromptText("Inserisci la nuova descrizione");
                okButton.setOnAction(e -> {
                    try {
                        String newDesc = txt1.getText();
                        InputValidator.validaDescrizione(newDesc);
                        modificaRist("descrizione", newDesc, "ristoranti.json");
                        handleClose(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 9:
                label1.setText("Cambia Prezzo:");
                txt1.setPromptText("Inserisci il nuovo prezzo medio");
                okButton.setOnAction(e -> {
                    try {
                        String newPrice = txt1.getText();
                        InputValidator.validaPrezzo(newPrice);
                        modificaRist("prezzo", newPrice, "ristoranti.json");
                        handleClose(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 10:
                label1.setText("Cambia Stelle:");
                txt1.setPromptText("Inserisci il numero di stelle");
                okButton.setOnAction(e -> {
                    try {
                        String newStelle = txt1.getText();
                        InputValidator.validaStelle(newStelle);
                        modificaRist("stelle", newStelle, "ristoranti.json");
                        handleClose(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 11:
                label1.setText("Cambia Servizi:");
                txt1.setPromptText("Cambia i tuoi servizi");
                okButton.setOnAction(e -> {
                    try {
                        String newServ = txt1.getText();
                        InputValidator.validaServizio(newServ);
                        modificaRist("servizi", newServ, "ristoranti.json");
                        handleClose(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            default:
                break;
        }
    }

    /** Controller padre da aggiornare dopo la modifica. */
    protected DettaglioRistController mainController;

    /**
     * Imposta il controller principale per permettere aggiornamenti dopo la modifica.
     *
     * @param controller Controller padre da aggiornare.
     */
    public void setMainController(DettaglioRistController controller) {
        this.mainController = controller;
    }

    /**
     * Esegue la modifica del campo specificato di un ristorante nel file JSON.
     *
     * @param campo    Campo da aggiornare (es. "nome", "email").
     * @param newCampo Nuovo valore da assegnare al campo.
     * @param fileJson Percorso del file JSON dei ristoranti.
     * @throws IOException In caso di errori nella lettura o scrittura del file.
     */
    public void modificaRist(String campo, String newCampo, String fileJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        JsonNode root = mapper.readTree(new File(fileJson));
        JsonNode ristorantiNode = root.get("ristoranti");

        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);

        for (Ristorante r : listaModificabile) {
            if (r.id == SessionManager.idRist) {
                if (campo.equals("nome")) {
                    r.name = newCampo;
                } else if (campo.equals("indirizzo")) {
                    r.address = newCampo;
                } else if (campo.equals("citta")) {
                    r.location = newCampo;
                } else if (campo.equals("cucina")) {
                    r.cuisine = newCampo;
                } else if (campo.equals("telefono")) {
                    r.phoneNumber = newCampo;
                } else if (campo.equals("email")) {
                    r.email = newCampo;
                } else if (campo.equals("url")) {
                    r.websiteUrl = newCampo;
                } else if (campo.equals("descrizione")) {
                    r.description = newCampo;
                } else if (campo.equals("prezzo")) {
                    r.price = newCampo;
                } else if (campo.equals("stelle")) {
                    int newStelle = Integer.parseInt(newCampo);
                    r.greenStar = newStelle;
                } else if (campo.equals("servizi")) {
                    r.facilitiesAndServices = newCampo;
                }
            }
        }

        // Aggiorna il file JSON con i nuovi dati
        ObjectNode nuovoRoot = mapper.createObjectNode();
        nuovoRoot.set("ristoranti", mapper.valueToTree(listaModificabile));
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileJson), nuovoRoot);
    }
}
