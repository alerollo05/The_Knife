package com.example.the_knife.Ristoratore;

import com.example.the_knife.Utente.SessionManager;
import com.example.the_knife.loginController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class dashBoardRistController extends loginController {


    SessionManager session = SessionManager.getInstance();
    private String user = session.getUsername();
    private int id = session.getUserId();
    private String ruolo = session.getRuolo();

    @FXML private TextField cityField;
    @FXML private ComboBox<String> cuisineBox;
    @FXML private ComboBox<String> priceBox;
    @FXML private ComboBox<String> deliveryBox;
    @FXML private ComboBox<String> bookingBox;



    public void handleLogOut(ActionEvent event) {
        SessionManager.getInstance().logout();//cancello i dati dalla sessione
        SessionManager.counter1 = 0;
        SessionManager.counter = 0;
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
        System.out.println("Utente: "+user+ "Id: "+id+"Ruolo: "+ruolo);
        SessionManager.pagina = 1;
        cuisineBox.setItems(FXCollections.observableArrayList(
                "Mediterranea", "Italiana", "Giapponese",
                "Francese", "Cinese", "Messicana", "Indiana"));
        cuisineBox.setMaxWidth(60);

        priceBox.setItems(FXCollections.observableArrayList(
                "€", "€€", "€€€", "€€€€"));
        priceBox.setMaxWidth(60);

        deliveryBox.setItems(FXCollections.observableArrayList(
                "Delivery", "No delivery"));
        deliveryBox.setMaxWidth(60);

        bookingBox.setItems(FXCollections.observableArrayList(
                "Booking", "No booking online"));
        bookingBox.setMaxWidth(60);

        // Ripristino dei filtri se si sta tornando indietro
        if (SessionManager.counter1 == 1) {
            cityField.setText(SessionManager.luogoNomeStatico);
            cuisineBox.setValue(SessionManager.tipoCucinaStatico);
            priceBox.setValue(SessionManager.prezzoStatico);
            deliveryBox.setValue(SessionManager.deliveryStatico ? "Delivery" : "No delivery");
            bookingBox.setValue(SessionManager.bookingStatico ? "Booking" : "No booking online");
            super.printListRist("/com/example/the_knife/dettaglioRistoranteSearch.fxml","/com/example/the_knife/recensioneRistoranteSearch.fxml"); // mostra la lista con i filtri precedenti
        } else {
            deliveryBox.getSelectionModel().selectFirst();
            bookingBox.getSelectionModel().selectFirst();
            super.printListRistTop10("/com/example/the_knife/dettaglioRistoranteSearch.fxml","/com/example/the_knife/recensioneRistoranteSearch.fxml"); // mostra top 10 iniziale
        }
    }

    @FXML
    private void onSearchClicked() {
        SessionManager.counter1 = 0; // reset per applicare nuovi filtri
        super.printListRist("/com/example/the_knife/dettaglioRistoranteSearch.fxml","/com/example/the_knife/recensioneRistoranteSearch.fxml");
    }

    @FXML
    protected void onProfileClick(ActionEvent event) throws IOException {
        SessionManager.counter1 = 0;
        super.goTo(event,"profilePageRist.fxml");
    }
    @FXML
    protected void onRistorantiClick(ActionEvent event) throws IOException {
        SessionManager.counter1 = 0;
        super.goTo(event,"ristorantiRist.fxml");
    }
    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        super.goTo(event, "dashBoardRist.fxml");
    }
    @FXML
    protected void onAddRistClick(ActionEvent event) throws IOException {
        SessionManager.counter1 = 0;
        super.goTo(event,"newRist.fxml");
    }


}
