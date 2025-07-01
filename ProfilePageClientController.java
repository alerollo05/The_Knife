package com.example.the_knife.Cliente;


import com.example.the_knife.Ristoratore.PopUpProfController;
import com.example.the_knife.Ristoratore.ProfilePageRistController;
import com.example.the_knife.Utente.ListaUtenti;
import com.example.the_knife.Utente.SessionManager;
import com.example.the_knife.Utente.Utente;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class ProfilePageClientController extends ProfilePageRistController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private GridPane grid;

    private ProfilePageRistController mainController;

    SessionManager session = SessionManager.getInstance();
    private String user = session.getUsername();
    private int id = session.getUserId();
    private String ruolo = session.getRuolo();

    public void handleLogOut(ActionEvent event) {
        super.handleLogOut(event);
    }
    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }

    @FXML
    public void initialize() {
        welcomeLabel.setText("PROFILO CLIENTE DI " + user + "");
        System.out.println("Utente: "+user+ "Id: "+id+"Ruolo: "+ruolo);
        printDettaglioUtente("fileUtenti.json","/com/example/the_knife/Ristoratore/popUpProf.fxml");
    }



    public void setMainController(ProfilePageRistController controller) {
        this.mainController = controller;
    }


    @FXML
    protected void onProfileClick(ActionEvent event) throws IOException {
        super.onProfileClick(event);
    }
    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        super.goTo(event, "dashBoardClient.fxml");
    }
    @FXML
    protected void onRecensioniClick(ActionEvent event) throws IOException {
        super.goTo(event,"recensioniClient.fxml");
    }
    @FXML
    protected void onRistorantiClick(ActionEvent event) throws IOException {
        super.goTo(event,"ristorantiClient.fxml");
    }

    private Utente getUtente(String fileJson){
        try {

            ObjectMapper mapper = new ObjectMapper();// Crea un'istanza di ObjectMapper
            mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
            File file = new File(fileJson);// Crea un oggetto File per rappresentare il file JSON passato come parametro

            if (!file.exists()) {
                System.out.println("File non trovato: " + file.getAbsolutePath());
                return null;
            }

            ListaUtenti lista = mapper.readValue(new File(fileJson), ListaUtenti.class);

            for(Utente u : lista.Utenti){
                if(u.getUsername().equalsIgnoreCase(user)){
                    return u;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}