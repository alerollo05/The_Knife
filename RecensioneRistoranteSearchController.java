package com.example.the_knife;

import com.example.the_knife.Ristoratore.Recensione;
import com.example.the_knife.Ristoratore.RistorantiRistController;
import com.example.the_knife.Utente.SessionManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;


import java.io.IOException;
import java.util.List;

public class RecensioneRistoranteSearchController extends RistorantiRistController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private ListView<Recensione> listaRecLabel;

    SessionManager session = SessionManager.getInstance();
    private final String user = session.getUsername();
    private final int id = session.getUserId();
    private final String ruolo = session.getRuolo();

    public void initialize() {
        welcomeLabel.setText("LE RECENSIONI " + user);
        System.out.println("Utente: " + user + " Id: " + id + " Ruolo: " + ruolo);
        printListRec();
    }

    public void printListRec() {
        List<Recensione> mieRecensioni = visualizzaRecensioniPerNomeRistorante("ristoranti.json");
        listaRecLabel.setItems(FXCollections.observableArrayList(mieRecensioni));

        listaRecLabel.setCellFactory(param -> new ListCell<>() {


            protected void updateItem(Recensione recensione, boolean empty) {
                super.updateItem(recensione, empty);
                param.getStyleClass().add("list-rist2");
                if (empty || recensione == null) {
                    setText(null);
                    setGraphic(null);
                } else {

                    GridPane grid = new GridPane();
                    grid.setHgap(10);
                    grid.setVgap(5);
                    grid.setPadding(new Insets(5));

                    Label campoAutore = new Label("Autore:");
                    campoAutore.getStyleClass().add("textNormal");
                    Label autore = new Label(recensione.getAuthor());
                    autore.getStyleClass().add("textNormal");

                    Label campoCommento = new Label("Commento:");
                    campoCommento.getStyleClass().add("textNormal");
                    Label commenti = new Label(recensione.getComment());
                    commenti.getStyleClass().add("textNormal");

                    Label campoData = new Label("Data Pubblicazione:");
                    campoData.getStyleClass().add("textNormal");
                    Label dateRec = new Label(recensione.getDate());
                    dateRec.getStyleClass().add("textNormal");

                    Label campoRisposta = new Label("Risposta:");
                    campoRisposta.getStyleClass().add("textNormal");
                    Label risposta = new Label(recensione.getRisposta());
                    risposta.getStyleClass().add("textNormal");


                    Label campoRating = new Label("Rating:");
                    campoRating.getStyleClass().add("textNormal");
                    Label rating = new Label("" + recensione.getRating());
                    rating.getStyleClass().add("textNormal");

                    grid.add(campoAutore, 0, 0);
                    grid.add(autore, 1, 0);
                    grid.add(campoCommento, 0, 1);
                    grid.add(commenti, 1, 1);
                    grid.add(campoData, 0, 2);
                    grid.add(dateRec, 1, 2);
                    grid.add(campoRisposta, 0, 3);
                    grid.add(risposta, 1, 3);
                    grid.add(campoRating, 0, 4);
                    grid.add(rating, 1, 4);
                    grid.getStyleClass().add("grid-list");
                    setGraphic(grid);
                }
            }
        });
    }

    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        SessionManager.idRist = null;
        SessionManager.counter = 1;
        SessionManager.counter1 = 1;
        if(SessionManager.pagina == 0) {
            SessionManager.counter = 1;
            super.goTo(event, "startPage.fxml");
        }else if(SessionManager.pagina == 1) {
            SessionManager.counter1 = 1;
            super.goTo(event, "Ristoratore/dashBoardRist.fxml");
        }else{
            SessionManager.counter2 = 1;
            super.goTo(event, "Cliente/dashBoardClient.fxml");
        }
    }

    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }

}
