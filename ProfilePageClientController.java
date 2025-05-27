package com.example.the_knife.Cliente;

import com.example.the_knife.Utente.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class ProfilePageClientController extends dashBoardClientController {
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
        welcomeLabel.setText("PROFILO CLIENTE DI " + user + "");
        System.out.println("Utente: "+user+ "Id: "+id+"Ruolo: "+ruolo);
    }

    @FXML
    protected void onProfileClick(ActionEvent event) throws IOException {
        super.onProfileClick(event);
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
    protected void onRistorantiClick(ActionEvent event) throws IOException {
        super.onRistorantiClick(event);
    }
}
