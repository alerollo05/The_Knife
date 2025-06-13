package com.example.the_knife.Ristoratore;

import com.example.the_knife.Utente.SessionManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class RecensioniRistController extends RistorantiRistController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private ListView<Recensione> listaRecLabel;

    private RecensioniRistController mainController;

    SessionManager session = SessionManager.getInstance();
    private final String user = session.getUsername();
    private final int id = session.getUserId();
    private final String ruolo = session.getRuolo();
    /*
    Label recensioniLabel = new Label("Recensioni: ");
            recensioniLabel.getStyleClass().add("textNormal");

    Label numRecLabel = new Label("Numero di recensioni: ");
            numRecLabel.getStyleClass().add("textNormal");

    Label mediaRecLabel = new Label("Media recensioni: ");
            mediaRecLabel.getStyleClass().add("textNormal");

    Label numRec = new Label("Numero di recensioni: ");
            numRec.getStyleClass().add("textNormal"); */

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

                    Button modRec = new Button("Modifica");
                    modRec.getStyleClass().add("accent-button");
                    modRec.setOnAction(e -> {
                        SessionManager.idRecensione = recensione.getId();
                            openPopupRec("Risposta");
                    });

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
                    grid.add(modRec, 2, 3);
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
        super.goTo(event, "ristorantiRist.fxml");
    }

    public void handleLogOut(ActionEvent event) {
        SessionManager.idRist = null;
        super.handleLogOut(event);
    }

    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }

    public void openPopupRec(String title) { //aggiungere variabile stringa nei parametri passati per percorso file .fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("popUpRec.fxml"));
            Parent root = loader.load();
            Stage popupStage = new Stage();

            //SERVE A AGGIORNARE I DATI QUANDO CHIUDO LA FINESTRA DI POPUP
            PopUpRecController controller = loader.getController();
            controller.setMainController((RecensioniRistController) this);

            popupStage.setTitle(title);
            popupStage.initModality(Modality.APPLICATION_MODAL); // Blocca la finestra principale
            popupStage.setScene(new Scene(root,500,200));
            popupStage.setResizable(false); // blocca il ridimensionamento
            popupStage.showAndWait(); // Mostra la finestra e attende la chiusura
        }catch (IOException e){ System.err.println("Errore nel caricamento del file FXML:");
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setMainController(RecensioniRistController controller) {
        this.mainController = controller;
    }
}
