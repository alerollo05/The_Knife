package com.example.the_knife.Ristoratore;

import com.example.the_knife.Exceptions.VecchiaPasswordException;
import com.example.the_knife.InputValidator;
import com.example.the_knife.Utente.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

import static com.example.the_knife.InputValidator.handleInput;

/**
 * Controller per il pop-up di modifica delle informazioni del profilo utente.
 * Gestisce le modifiche ai campi come nome, cognome, email, username, password, indirizzo, data di nascita e telefono.
 */
public class PopUpProfController {

    /** Etichetta che descrive il campo da modificare */
    @FXML
    private Label label1;

    /** Bottone di conferma */
    @FXML
    private Button okButton;

    /** Campo di input principale (es: nuovo nome, vecchia password, ecc.) */
    @FXML
    private TextField txt1;

    /** Campo di input secondario (usato ad esempio per la nuova password) */
    @FXML
    private TextField txt2;

    /** Selettore per la data (usato per cambiare la data di nascita) */
    @FXML
    private DatePicker date1;

    /** Username corrente dell'utente attivo nella sessione */
    public String username = SessionManager.getInstance().getUsername();

    /** Controller padre per aggiornare la vista una volta chiuso il pop-up */
    private ProfilePageRistController mainController;

    /**
     * Inizializza il contenuto del pop-up in base alla scelta dell’utente (SessionManager.idScelta).
     * Ogni caso configura il layout e associa l'azione del pulsante OK in base al campo da modificare.
     */
    public void initialize() {
        switch (SessionManager.idScelta) {
            case 1:
                label1.setText("Cambia nome:");
                txt1.setPromptText("Inserisci il nuovo nome");
                date1.setVisible(false); date1.setManaged(false);
                txt2.setVisible(false); txt2.setManaged(false);
                okButton.setOnAction(e -> {
                    try {
                        String newNome = txt1.getText();
                        InputValidator.validaNomeUte(newNome);
                        InputValidator.modificaUte(username,"nome", newNome, "fileUtenti.json");
                        handleClose(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 2:
                label1.setText("Cambia cognome:");
                txt1.setPromptText("Inserisci il cognome");
                date1.setVisible(false); date1.setManaged(false);
                txt2.setVisible(false); txt2.setManaged(false);
                okButton.setOnAction(e -> {
                    try {
                        String newCognome = txt1.getText();
                        InputValidator.validaCogno(newCognome);
                        InputValidator.modificaUte(username,"cognome", newCognome, "fileUtenti.json");
                        handleClose(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 3:
                label1.setText("Cambia email:");
                txt1.setPromptText("Inserisci il email");
                date1.setVisible(false); date1.setManaged(false);
                txt2.setVisible(false); txt2.setManaged(false);
                okButton.setOnAction(e -> {
                    try {
                        String newEmail = txt1.getText();
                        InputValidator.validaEmail(newEmail);
                        InputValidator.modificaUte(username,"email", newEmail, "fileUtenti.json");
                        handleClose(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 4:
                label1.setText("Cambia username:");
                txt1.setPromptText("Inserisci l'username");
                date1.setVisible(false); date1.setManaged(false);
                txt2.setVisible(false); txt2.setManaged(false);
                okButton.setOnAction(e -> {
                    try {
                        String newUser = txt1.getText();
                        InputValidator.validaUsername(newUser);
                        InputValidator.modificaUte(username,"username", newUser, "fileUtenti.json");
                        handleClose(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 5:
                label1.setText("Cambia password:");
                txt1.setPromptText("Ins vecchia password");
                txt2.setPromptText("Ins nuova password");
                date1.setVisible(false); date1.setManaged(false);
                okButton.setOnAction(e -> {
                    try {
                        String oldPass = txt1.getText();
                        boolean ris = InputValidator.verificaPassword(username,oldPass);
                        if (!ris) {
                            handleInput("Errore","Vecchia password errata");
                            throw new VecchiaPasswordException("Vecchia password errata");
                        }
                        String newPass = txt2.getText();
                        InputValidator.validaPassword(newPass);
                        InputValidator.modificaUte(username,"password", newPass, "fileUtenti.json");
                        handleClose(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 6:
                label1.setText("Cambia indirizzo:");
                txt1.setPromptText("Inserisci l'indirizzo");
                date1.setVisible(false); date1.setManaged(false);
                txt2.setVisible(false); txt2.setManaged(false);
                okButton.setOnAction(e -> {
                    try {
                        String newAdd = txt1.getText();
                        InputValidator.validaIndirizzo(newAdd);
                        InputValidator.modificaUte(username,"indirizzo", newAdd, "fileUtenti.json");
                        handleClose(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 7:
                label1.setText("Cambia data di nascita:");
                date1.setPromptText("dd/mm/aaaa");
                txt1.setVisible(false); txt1.setManaged(false);
                txt2.setVisible(false); txt2.setManaged(false);
                okButton.setOnAction(e -> {
                    try {
                        LocalDate newDate = date1.getValue();
                        InputValidator.modificaUteData(username,"data", newDate, "fileUtenti.json");
                        handleClose(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 8:
                label1.setText("Cambia telefono:");
                txt1.setPromptText("Inserisci il numero");
                date1.setVisible(false); date1.setManaged(false);
                txt2.setVisible(false); txt2.setManaged(false);
                okButton.setOnAction(e -> {
                    try {
                        String newTel = txt1.getText();
                        InputValidator.validaTelefono(newTel);
                        InputValidator.modificaUte(username,"telefono", newTel, "fileUtenti.json");
                        handleClose(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
        }
    }

    /**
     * Chiude la finestra pop-up e reimposta lo stato della sessione.
     * Se è stato impostato un controller principale, aggiorna la vista principale.
     *
     * @param event L'evento di chiusura (es. click sul bottone OK)
     * @throws IOException se la finestra non può essere chiusa correttamente
     */
    public void handleClose(ActionEvent event) throws IOException {
        SessionManager.idScelta = 0;
        if (mainController != null) {
            mainController.initialize(); // aggiorna la pagina principale
        }
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Imposta il controller principale (tipicamente la pagina profilo).
     * Serve per aggiornare la UI principale dopo una modifica.
     *
     * @param controller Istanza del controller padre
     */
    public void setMainController(ProfilePageRistController controller) {
        this.mainController = controller;
    }

    /**
     * Chiude la finestra pop-up senza salvare modifiche.
     * Usato dal bottone "Annulla".
     *
     * @param event L'evento di click sul pulsante di annullamento
     * @throws IOException se la finestra non può essere chiusa correttamente
     */
    public void handleCloseAnnulla(ActionEvent event) throws IOException {
        SessionManager.idScelta = 0;
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
