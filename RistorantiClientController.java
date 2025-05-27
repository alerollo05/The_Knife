package com.example.the_knife.Cliente;

import com.example.the_knife.Utente.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class RistorantiClientController extends dashBoardClientController {

    @FXML
    private Label welcomeLabel;

    SessionManager session = SessionManager.getInstance();
    private final String user = session.getUsername();
    private final int id = session.getUserId();
    private final String ruolo = session.getRuolo();

    @FXML
    public void initialize() {
        welcomeLabel.setText("I TUOI RISTORANTI PREFERITI " + user + "");
        System.out.println("Utente: "+user+ " Id: "+id+" Ruolo: "+ruolo);
    }
    @FXML
    protected void onRistorantiClick(ActionEvent event) throws IOException {
        onRistorantiClick(event);
    }
    @FXML
    public void onRicetteClick(ActionEvent event) throws IOException {
        onRicetteClick(event);
    }
    @FXML
    public void handleLogOut(ActionEvent event) {
        super.handleLogOut(event);
    }

    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }

    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        super.goBack(event);
    }
    @FXML
    protected void onRecensioniClick(ActionEvent event) throws IOException {
        super.onRecensioniClick(event);
    }
    @FXML
    protected void onProfileClick(ActionEvent event) throws IOException {
        super.onProfileClick(event);
    }
}
