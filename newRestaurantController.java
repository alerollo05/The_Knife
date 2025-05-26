package com.example.the_knife.Ristoratore;

import com.example.the_knife.Utente.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class newRestaurantController extends dashBoardRistController {

    //creo le variabili che mi servono per immagazzinare i dati che l'utente immette in input
    @FXML
    private Label welcomeLabel;
    @FXML
    private TextField nameRist;
    @FXML
    private TextField addressRist;
    @FXML
    private TextField locationRist;
    @FXML
    private TextField priceRist;
    @FXML
    private TextField mailRist;
    @FXML
    private TextField cousineRist;
    @FXML
    private TextField telRist;
    @FXML
    private TextField UrlRist;
    @FXML
    private TextField serviceRist;
    @FXML
    private TextField descriptionRist;
    @FXML
    private TextField starsRist;
    @FXML
    private ToggleGroup DeliveryToggleGroup;
    @FXML
    private ToggleGroup BookingToggleGroup;


    //Prendo i dati dalla sessione
    SessionManager session = SessionManager.getInstance();
    private final String user = session.getUsername();
    private final int id = session.getUserId();
    private final String ruolo = session.getRuolo();

    public void handleLogOut(ActionEvent event) {
        super.handleLogOut(event);
    }

    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }

    @FXML
    public void initialize() {
        welcomeLabel.setText("AGGIUNGI UN RISTORANTE " + user + "");
        System.out.println("Utente: "+user+ " Id: "+id+" Ruolo: "+ruolo);
    }

    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        super.goTo(event, "ristorantiRist.fxml");
    }

    @FXML
    protected void onProfileClick(ActionEvent event) throws IOException {
        super.onProfileClick(event);
    }
    @FXML
    protected void onRistorantiClick(ActionEvent event) throws IOException {
        super.onRistorantiClick(event);
    }
    @FXML
    protected void handleAddRist(ActionEvent event) throws IOException {
        String name = nameRist.getText();
        name = name.trim();
        String address = addressRist.getText();
        String location = locationRist.getText();
        String price = priceRist.getText();
        price = price.trim();
        String cousine = cousineRist.getText();
        cousine = cousine.trim();
        String tel = telRist.getText();
        tel.trim();
        String Url = UrlRist.getText();
        Url = Url.trim();
        String service = serviceRist.getText();
        service = service.trim();
        String description = descriptionRist.getText();
        description = description.trim();
        String stars = starsRist.getText();
        stars = stars.trim();
        String delivery = DeliveryToggleGroup.getSelectedToggle().getUserData().toString();
        String booking = BookingToggleGroup.getSelectedToggle().getUserData().toString();


    }
}
