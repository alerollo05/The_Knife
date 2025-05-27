package com.example.the_knife.Ristoratore;

import com.example.the_knife.Utente.SessionManager;
import com.example.the_knife.loginController;
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
            super.goTo(event, "/com/example/the_knife/loginPage.fxml");//metto il path relativo intero per uscire e tornare alla login che si trova in una cartella meno profonda di quella dei ristoratori
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }

    @FXML
    public void initialize() throws IOException {
        welcomeLabel.setText("Benvenuto, " + user + "");
        System.out.println("Utente: "+user+ "Id: "+id+"Ruolo: "+ruolo);
    }

    @FXML
    protected void onProfileClick(ActionEvent event) throws IOException {
        super.goTo(event,"profilePageRist.fxml");
    }
    @FXML
    protected void onRistorantiClick(ActionEvent event) throws IOException {
        super.goTo(event,"ristorantiRist.fxml");
    }
    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        super.goTo(event, "dashBoardRist.fxml");
    }
    @FXML
    protected void onAddRistClick(ActionEvent event) throws IOException {
        super.goTo(event,"newRist.fxml");
    }
}
