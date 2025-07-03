package com.example.the_knife.Ristoratore;

import com.example.the_knife.Exceptions.VecchiaPasswordException;
import com.example.the_knife.InputValidator;
import com.example.the_knife.Utente.ListaUtenti;
import com.example.the_knife.Utente.SessionManager;
import com.example.the_knife.Utente.Utente;
import com.example.the_knife.loginController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.the_knife.InputValidator.handleInput;


public class PopUpProfController {


    @FXML
    private Label label1;

    @FXML
    private Button okButton;

    @FXML
    private TextField txt1;

    @FXML
    private TextField txt2;

    @FXML
    private DatePicker date1;

    public String username = SessionManager.getInstance().getUsername();

    public void initialize() {
        switch (SessionManager.idScelta) {
            case 1:
                label1.setText("Cambia nome:");
                txt1.setPromptText("Inserisci il nuovo nome");
                date1.setVisible(false);//nascondo la casella della data
                date1.setManaged(false);//tolgo lo spazio occupato dalla casella della data
                txt2.setVisible(false);//nascondo la casella della conferma password
                txt2.setManaged(false);//tolgo lo spazio occupato dalla casella conferma password
                okButton.setOnAction(e -> {
                    try {
                        String newNome = txt1.getText();
                        InputValidator.validaNomeUte(newNome);
                        InputValidator.modificaUte(username,"nome", newNome, "fileUtenti.json");
                        handleClose(e);//chiudi finestra popUp
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 2:
                label1.setText("Cambia cognome:");
                txt1.setPromptText("Inserisci il cognome");
                date1.setVisible(false);//nascondo la casella della data
                date1.setManaged(false);//tolgo lo spazio occupato dalla casella della data
                txt2.setVisible(false);//nascondo la casella della conferma password
                txt2.setManaged(false);//tolgo lo spazio occupato dalla casella conferma password
                okButton.setOnAction(e -> {
                    try {
                        String newCognome = txt1.getText();
                        InputValidator.validaCogno(newCognome);
                        InputValidator.modificaUte(username,"cognome", newCognome, "fileUtenti.json");
                        handleClose(e);//chiudi finestra popUp
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 3:
                label1.setText("Cambia email:");
                txt1.setPromptText("Inserisci il email");
                date1.setVisible(false);//nascondo la casella della data
                date1.setManaged(false);//tolgo lo spazio occupato dalla casella della data
                txt2.setVisible(false);//nascondo la casella della conferma password
                txt2.setManaged(false);//tolgo lo spazio occupato dalla casella conferma password
                okButton.setOnAction(e -> {
                    try {
                        String newEmail = txt1.getText();
                        InputValidator.validaEmail(newEmail);
                        InputValidator.modificaUte(username,"email", newEmail, "fileUtenti.json");
                        handleClose(e);//chiudi finestra popUp
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 4:
                label1.setText("Cambia username:");
                txt1.setPromptText("Inserisci l'username");
                date1.setVisible(false);//nascondo la casella della data
                date1.setManaged(false);//tolgo lo spazio occupato dalla casella della data
                txt2.setVisible(false);//nascondo la casella della conferma password
                txt2.setManaged(false);//tolgo lo spazio occupato dalla casella conferma password
                okButton.setOnAction(e -> {
                    try {
                        String newUser = txt1.getText();
                        InputValidator.validaUsername(newUser);
                        InputValidator.modificaUte(username,"username", newUser, "fileUtenti.json");
                        handleClose(e);//chiudi finestra popUp
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 5:

                label1.setText("Cambia password:");
                txt1.setPromptText("Ins vecchia password");
                txt2.setPromptText("Ins nuova password");
                date1.setVisible(false);//nascondo la casella della data
                date1.setManaged(false);//tolgo lo spazio occupato dalla casella della data
                okButton.setOnAction(e -> {
                    try {
                        String oldPass = txt1.getText();
                        boolean ris = InputValidator.verificaPassword(username,oldPass);
                        if (ris == false) {
                            handleInput("Errore","Vecchia password errata");
                            throw new VecchiaPasswordException("Vecchia password errata");
                        }
                        String newPass = txt2.getText();
                        InputValidator.validaPassword(newPass);
                        InputValidator.modificaUte(username,"password", newPass, "fileUtenti.json");
                        handleClose(e);//chiudi finestra popUp
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 6:
                label1.setText("Cambia indirizzo:");
                txt1.setPromptText("Inserisci l'indirizzo");
                date1.setVisible(false);//nascondo la casella della data
                date1.setManaged(false);//tolgo lo spazio occupato dalla casella della data
                txt2.setVisible(false);//nascondo la casella della conferma password
                txt2.setManaged(false);//tolgo lo spazio occupato dalla casella conferma password
                okButton.setOnAction(e -> {
                    try {
                        String newAdd = txt1.getText();
                        InputValidator.validaIndirizzo(newAdd);
                        InputValidator.modificaUte(username,"indirizzo", newAdd, "fileUtenti.json");
                        handleClose(e);//chiudi finestra popUp
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 7:
                label1.setText("Cambia data di nascita:");
                date1.setPromptText("dd/mm/aaaa");
                txt1.setVisible(false);//nascondo la casella di testo
                txt1.setManaged(false);//tolgo lo spazio occupato dalla casella di testo
                txt2.setVisible(false);//nascondo la casella della conferma password
                txt2.setManaged(false);//tolgo lo spazio occupato dalla casella conferma password
                okButton.setOnAction(e -> {
                    try {
                        LocalDate newDate = date1.getValue();
                        InputValidator.modificaUteData(username,"data", newDate, "fileUtenti.json");
                        handleClose(e);//chiudi finestra popUp
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 8:
                label1.setText("Cambia telefono:");
                txt1.setPromptText("Inserisci il numero");
                date1.setVisible(false);//nascondo la casella della data
                date1.setManaged(false);//tolgo lo spazio occupato dalla casella della data
                txt2.setVisible(false);//nascondo la casella della conferma password
                txt2.setManaged(false);//tolgo lo spazio occupato dalla casella conferma password
                okButton.setOnAction(e -> {
                    try {
                        String newTel = txt1.getText();
                        InputValidator.validaTelefono(newTel);
                        InputValidator.modificaUte(username,"telefono", newTel, "fileUtenti.json");
                        handleClose(e);//chiudi finestra popUp
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
        }
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

    //SERVE PER AGGIORNARE LA PAGINA DI STAMPA DOPO MODIFICA DEL POP UP
    private ProfilePageRistController mainController;

    public void setMainController(ProfilePageRistController controller) {
        this.mainController = controller;
    }


    public void handleCloseAnnulla(ActionEvent event) throws IOException {
        // Chiude la finestra corrente
        SessionManager.idScelta = 0;
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
        //Al massimo posso aggiornare la pagina rifacendo di nuovo goTo per leggere il dato nuovo
    }


}