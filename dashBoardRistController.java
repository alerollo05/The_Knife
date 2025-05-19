package com.example.the_knife;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class dashBoardRistController extends loginController {

    @FXML
    private Label welcomeLabel;

    SessionManager session = SessionManager.getInstance();
    private String user = session.getUsername();
    private int id = session.getUserId();
    private String ruolo = session.getRuolo();

    public void handleLogOut(ActionEvent event) {
        SessionManager.getInstance().logout();//cancello i dati dalla sessione
        try {
            goTo(event, "loginPage.fxml");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }

    @FXML
    public void initialize() {
        welcomeLabel.setText("Benvenuto, " + user + "");
        System.out.println("Utente: "+user+ "Id: "+id+"Ruolo: "+ruolo);
    }

    @FXML
    private void onProfileClick(ActionEvent event) throws IOException {
        goTo(event,"dashBoardProfRist.fxml");
    }
}
