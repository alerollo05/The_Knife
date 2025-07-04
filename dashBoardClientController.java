package com.example.the_knife.Cliente;

import com.example.the_knife.Utente.SessionManager;
import com.example.the_knife.Utente.Utente;
import com.example.the_knife.loginController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.the_knife.Utente.SessionManager.idRist;

public class dashBoardClientController extends loginController {



    SessionManager session = SessionManager.getInstance();
    private String user = session.getUsername();
    private int id = session.getUserId();
    private String ruolo = session.getRuolo();

    @FXML private TextField cityField;
    @FXML private ComboBox<String> cuisineBox;
    @FXML private ComboBox<String> priceBox;
    @FXML private ComboBox<String> deliveryBox;
    @FXML private ComboBox<String> bookingBox;
    @FXML private Button searchButton;

    public void handleLogOut(ActionEvent event) {
        SessionManager.getInstance().logout();//cancello i dati dalla sessione
        SessionManager.counter2 = 0;
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
    public void initialize() {
        System.out.println("Utente: "+user+ "Id: "+id+"Ruolo: "+ruolo);

        //ICONA LENTE INGRANDIMENTO
        searchButton.getStyleClass().add("accent-button");
        Image lenteIngrandimento = new Image(getClass().getResource("/com/example/the_knife/icone/lenteIngrandimento.png").toExternalForm());
        ImageView iconView = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
        iconView.setFitWidth(24);
        iconView.setFitHeight(24);//setto il ridimensionamento
        searchButton.setGraphic(iconView);
        iconView.setImage(lenteIngrandimento);

        SessionManager.pagina = 2;
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
        if (SessionManager.counter2 == 1) {
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
        SessionManager.counter2 = 0; // reset per applicare nuovi filtri
        super.printListRist("/com/example/the_knife/dettaglioRistoranteSearch.fxml","/com/example/the_knife/recensioneRistoranteSearch.fxml");
    }

    @FXML
    protected void onProfileClick(ActionEvent event) throws IOException {
        SessionManager.counter2 = 0;
        goTo(event,"profilePageClient.fxml");
    }

    @FXML
    protected void onRistorantiClick(ActionEvent event) throws IOException {
        SessionManager.counter2 = 0;
        goTo(event,"ristorantiClient.fxml");
    }
    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        SessionManager.counter2 = 0;
        super.goTo(event, "dashBoardClient.fxml");
    }
}
