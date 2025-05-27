package com.example.the_knife.Ristoratore;

import com.example.the_knife.Utente.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class DettaglioRistController extends RistorantiRistController{
    @FXML
    private Label welcomeLabel;

    SessionManager session = SessionManager.getInstance();
    private final String user = session.getUsername();
    private final int id = session.getUserId();
    private final String ruolo = session.getRuolo();
    int idRist = (int) session.idRist;

    @FXML
    public void initialize() {
        welcomeLabel.setText("AGGIUNGI UN RISTORANTE " + user + "");
        System.out.println("Utente: "+user+ " Id: "+id+" Ruolo: "+ruolo);
        System.out.println("Id ristorante dettagliato: "+idRist);
    }

    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        session.idRist = null;
        super.goTo(event, "ristorantiRist.fxml");
    }

    @FXML
    protected void onRistorantiClick(ActionEvent event) throws IOException {
        super.onRistorantiClick(event);
    }
    public void handleLogOut(ActionEvent event) {
        session.idRist = null;
        super.handleLogOut(event);
    }

    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }
}
