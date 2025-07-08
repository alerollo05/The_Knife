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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
/**
 * La classe {@code RecensioniRistController} gestisce la visualizzazione delle recensioni
 * associate al ristorante del ristoratore loggato.
 * <p>
 * Estende {@link RistorantiRistController} per ereditare le funzionalità comuni legate alla
 * gestione del ristorante. Questa classe consente di visualizzare recensioni, mostrare i
 * dettagli di ciascuna recensione e permette l'inserimento di risposte da parte del ristoratore.
 * </p>
 *
 * @author [Il tuo nome]
 */
public class RecensioniRistController extends RistorantiRistController {

    /** Etichetta di benvenuto per l'intestazione della pagina. */
    @FXML
    private Label welcomeLabel;

    /** Componente ListView che mostra tutte le recensioni del ristorante. */
    @FXML
    private ListView<Recensione> listaRecLabel;

    /** Riferimento al controller principale per aggiornare i dati dopo modifiche nel popup. */
    private RecensioniRistController mainController;

    /** Istanza della sessione utente corrente. */
    SessionManager session = SessionManager.getInstance();

    /** Username dell'utente loggato. */
    private final String user = session.getUsername();

    /** ID dell'utente loggato. */
    private final int id = session.getUserId();

    /** Ruolo dell'utente loggato (es. "ristoratore"). */
    private final String ruolo = session.getRuolo();

    /**
     * Metodo di inizializzazione chiamato automaticamente da JavaFX.
     * Inizializza l'interfaccia utente e popola la lista delle recensioni.
     */
    public void initialize() {
        welcomeLabel.setText("LE RECENSIONI AL RISTORANTE");
        System.out.println("Utente: " + user + " Id: " + id + " Ruolo: " + ruolo);
        printListRec();
    }

    /**
     * Recupera e stampa le recensioni del ristorante corrente.
     * Ogni recensione è visualizzata come una cella personalizzata nella ListView.
     */
    public void printListRec() {
        List<Recensione> mieRecensioni = visualizzaRecensioniPerNomeRistorante("ristoranti.json");

        if (mieRecensioni != null && !mieRecensioni.isEmpty()) {
            listaRecLabel.setVisible(true);
            listaRecLabel.setManaged(true);
            listaRecLabel.setItems(FXCollections.observableArrayList(mieRecensioni));
        } else {
            listaRecLabel.setItems(FXCollections.observableArrayList());
            listaRecLabel.setVisible(false);
            listaRecLabel.setManaged(false);
        }

        // Cella personalizzata
        listaRecLabel.setCellFactory(param -> new ListCell<>() {
            @Override
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
                    Label dateRec = new Label(recensione.getDate().toString());
                    dateRec.getStyleClass().add("textNormal");

                    Label campoRisposta = new Label("Risposta:");
                    campoRisposta.getStyleClass().add("textNormal");
                    Label risposta = new Label(recensione.getRisposta());
                    risposta.getStyleClass().add("textNormal");

                    Button modRec = new Button();
                    modRec.getStyleClass().add("accent-button");
                    Image modifica = new Image(getClass().getResource("/com/example/the_knife/icone/modifica.png").toExternalForm());
                    ImageView iconView = new ImageView();
                    iconView.setFitWidth(24);
                    iconView.setFitHeight(24);
                    modRec.setGraphic(iconView);
                    iconView.setImage(modifica);

                    modRec.setOnAction(e -> {
                        SessionManager.idRecensione = recensione.getId();
                        openPopupRec("Risposta");
                    });

                    Label campoRating = new Label("Rating:");
                    campoRating.getStyleClass().add("textNormal");
                    Label rating = new Label("" + recensione.getRating());
                    rating.getStyleClass().add("textNormal");

                    // Aggiunta componenti al layout
                    grid.add(campoAutore, 0, 0); grid.add(autore, 1, 0);
                    grid.add(campoCommento, 0, 1); grid.add(commenti, 1, 1);
                    grid.add(campoData, 0, 2); grid.add(dateRec, 1, 2);
                    grid.add(campoRisposta, 0, 3); grid.add(risposta, 1, 3); grid.add(modRec, 2, 3);
                    grid.add(campoRating, 0, 4); grid.add(rating, 1, 4);

                    grid.getStyleClass().add("grid-list");
                    setGraphic(grid);
                }
            }
        });
    }

    /**
     * Gestisce il pulsante "Indietro", reindirizzando alla schermata dei ristoranti.
     *
     * @param event evento generato dal click
     * @throws IOException se il caricamento della schermata fallisce
     */
    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        SessionManager.idRist = null;
        super.goTo(event, "ristorantiRist.fxml");
    }

    /**
     * Gestisce il logout dell'utente, resettando la sessione e tornando alla schermata iniziale.
     *
     * @param event evento generato dal click
     */
    public void handleLogOut(ActionEvent event) {
        SessionManager.idRist = null;
        super.handleLogOut(event);
    }

    /**
     * Metodo override per chiudere l'applicazione in sicurezza.
     *
     * @param event evento generato dal sistema
     */
    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }

    /**
     * Apre un popup per permettere al ristoratore di inserire o modificare una risposta alla recensione.
     *
     * @param title titolo della finestra popup
     */
    public void openPopupRec(String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("popUpRec.fxml"));
            Parent root = loader.load();
            Stage popupStage = new Stage();

            PopUpRecController controller = loader.getController();
            controller.setMainController(this);  // passaggio del riferimento

            popupStage.setTitle(title);
            popupStage.initModality(Modality.APPLICATION_MODAL); // finestra modale
            popupStage.setScene(new Scene(root, 500, 200));
            popupStage.setResizable(false);
            popupStage.showAndWait(); // attesa chiusura finestra
        } catch (IOException e) {
            System.err.println("Errore nel caricamento del file FXML:");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Imposta il riferimento al controller principale per permettere aggiornamenti incrociati.
     *
     * @param controller il controller principale da associare
     */
    public void setMainController(RecensioniRistController controller) {
        this.mainController = controller;
    }
}
