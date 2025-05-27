package com.example.the_knife.Ristoratore;

import com.example.the_knife.Utente.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class ProfilePageRistController extends dashBoardRistController {
    @FXML
    private Label welcomeLabel;

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
        welcomeLabel.setText("PROFILO DI " + user + "");
        System.out.println("Utente: "+user+ "Id: "+id+"Ruolo: "+ruolo);
    }


    @FXML
    public void onAddRistClick(ActionEvent event) throws IOException {
        super.onAddRistClick(event);
    }

    @FXML
    protected void onRistorantiClick(ActionEvent event) throws IOException {
        super.onRistorantiClick(event);
    }

    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        super.goBack(event);
    }
}
