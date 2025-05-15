package com.example.the_knife;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class loginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField userRegister;
    @FXML
    private PasswordField passRegister;
    @FXML
    private TextField nomeField;
    @FXML
    private TextField cognomeField;
    @FXML
    private ToggleGroup ruoloToggleGroup;
    @FXML
    private TextField numTel;
    @FXML
    private DatePicker dataNascita;
    @FXML
    private TextField indirizzo;
    @FXML
    private RadioButton ruolo;

    @FXML
    private void handleLogin() {
        //METODI CHE CHIAMO

        //DEFINIZIONE handleLogin
        String user = usernameField.getText();
        String pass = passwordField.getText();
        if (user.equals("admin") && pass.equals("12345")) {
            System.out.println("Login successful");
        }
        // TODO: handle login logic
    }

    @FXML
    public void handleRegister() {

        //METODI CHE CHIAMO
        handleSubmit();

        //DEFINIZIONE handleRegister
        String newUser = userRegister.getText();
        String newPass = passRegister.getText();
        String name = nomeField.getText();
        String cognome = cognomeField.getText();
        String numerotel = numTel.getText();
        String indirizzo = this.indirizzo.getText();
        LocalDate DataNascita = dataNascita.getValue();
        RadioButton ruolo = (RadioButton) this.ruoloToggleGroup.getSelectedToggle();
        System.out.println(ruolo.getText());
        // TODO: handle registration logic
        System.out.println("Registration successful");
        System.out.println("Riepilogo:");
        System.out.println("Username: "+newUser +"\nPassword: "+newPass+"\nNome: "+name+"\nCognome:" +cognome+"\nNumero di telefono: "+numerotel+"\nData di nascita: "+DataNascita+"\nIndirizzo: "+indirizzo);
    }

    @FXML
    private void handleSubmit() {
        RadioButton selected = (RadioButton) ruoloToggleGroup.getSelectedToggle(); //casto il ruolo dal toggle group che ho definito nel file fxml
        if (selected != null) {
            System.out.println("Ruolo selezionato: " + selected.getText());
        }
    }
    @FXML
    public void goToStartPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
        Scene startScene = new Scene(loader.load(),900,800);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(startScene);

    }
}
