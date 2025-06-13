package com.example.the_knife.Ristoratore;

import com.example.the_knife.InputValidator;
import com.example.the_knife.Utente.SessionManager;
import com.example.the_knife.Utente.Utente;
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

public class PopUpProfController {


    @FXML
    private Label label1;

    @FXML
    private Button okButton;

    @FXML
    private TextField txt1;

    String username = SessionManager.username;

    public void initialize() {
        switch (SessionManager.idScelta) {
            case 1:
                label1.setText("Cambia nome:");
                txt1.setPromptText("Inserisci il nuovo nome");
                okButton.setText("Conferma");
                okButton.setOnAction(e -> {
                    try {
                        String newNome = txt1.getText();
                        InputValidator.validaNomeUte(newNome);
                        modificaUte("nome",newNome,"fileUtenti.json");
                        handleClose(e);//chiudi finestra popUp
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 2:
                label1.setText("Cambia cognome:");
                txt1.setPromptText("Inserisci il cognome");
                okButton.setText("Conferma");
                okButton.setOnAction(e -> {
                    try {
                        String newCognome = txt1.getText();
                        InputValidator.validaCogno(newCognome);
                        modificaUte("cognome",newCognome,"fileUtenti.json");
                        handleClose(e);//chiudi finestra popUp
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 3:
                label1.setText("Cambia email:");
                txt1.setPromptText("Inserisci il email");
                okButton.setText("Conferma");
                okButton.setOnAction(e -> {
                    try {
                        String newEmail = txt1.getText();
                        InputValidator.validaEmail(newEmail);
                        modificaUte("email",newEmail,"fileUtenti.json");
                        handleClose(e);//chiudi finestra popUp
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 4:
                label1.setText("Cambia username:");
                txt1.setPromptText("Inserisci l'username");
                okButton.setText("Conferma");
                okButton.setOnAction(e -> {
                    try {
                        String newUser = txt1.getText();
                        InputValidator.validaUsername(newUser);
                        modificaUte("username",newUser,"fileUtenti.json");
                        handleClose(e);//chiudi finestra popUp
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 5:
                label1.setText("Cambia password:");
                txt1.setPromptText("Inserisci la password");
                okButton.setText("Conferma");
                okButton.setOnAction(e -> {
                    try {
                        String newPass = txt1.getText();
                        InputValidator.validaPassword(newPass);
                        modificaUte("password",newPass,"fileUtenti.json");
                        handleClose(e);//chiudi finestra popUp
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 6:
                label1.setText("Cambia indirizzo:");
                txt1.setPromptText("Inserisci l'indirizzo");
                okButton.setText("Conferma");
                okButton.setOnAction(e -> {
                    try {
                        String newAdd = txt1.getText();
                        InputValidator.validaIndirizzo(newAdd);
                        modificaUte("indirizzo",newAdd,"fileUtenti.json");
                        handleClose(e);//chiudi finestra popUp
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 7:
                label1.setText("Cambia data di nascita:");
                txt1.setPromptText("Inserisci il email");
                okButton.setText("Conferma");
                okButton.setOnAction(e -> {
                    try {
                        String newDate = txt1.getText();
                        modificaUte("data",newDate,"fileUtenti.json");
                        handleClose(e);//chiudi finestra popUp
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            case 8:
                label1.setText("Cambia numero di telefono:");
                txt1.setPromptText("Inserisci il numero");
                okButton.setText("Conferma");
                okButton.setOnAction(e -> {
                    try {
                        String newTel = txt1.getText();
                        InputValidator.validaTelefono(newTel);
                        modificaUte("telefono",newTel,"fileUtenti.json");
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

       // if (mainController != null) {
       //     mainController.initialize(); //aggiorna la lista ristoranti nel padre
       // }

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

    public void modificaUte(String campo,String newCampo, String fileJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        JsonNode root = mapper.readTree(new File(fileJson));
        JsonNode utenteNode = root.get("Utenti");

        List<Utente> utenti = Arrays.asList(mapper.treeToValue(utenteNode, Utente[].class));
        // Converte in lista modificabile
        List<Utente> listaModificabile = new ArrayList<>(utenti);

        for(Utente u : listaModificabile){
            if(u.getUsername().equals(username)){
                if(campo.equals("nome")){
                    u.setNome(newCampo);
                } else if(campo.equals("indirizzo")){
                    u.setIndirizzo(newCampo);
                } else if (campo.equals("cognome")) {
                    u.setCognome(newCampo);
                } else if (campo.equals("telefono")) {
                    u.setTelefono(newCampo);
                } else if(campo.equals("email")){
                    u.setEmail(newCampo);
                } else if(campo.equals("password")){
                    u.setPassword(newCampo);
                } else if (campo.equals("username")){
                    u.setUsername(newCampo);
                } else if (campo.equals("data")) {
                    //u.setDataDiNascita(newCampo);
                }
            }
            }
            // Ricrea l'oggetto JSON aggiornato
            ObjectNode nuovoRoot = mapper.createObjectNode();
            nuovoRoot.set("Utenti", mapper.valueToTree(listaModificabile));
            // Sovrascrive il file
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileJson), nuovoRoot);
        }
}
