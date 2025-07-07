package com.example.the_knife;

import com.example.the_knife.Exceptions.MaxNumRecensioniException;
import com.example.the_knife.Ristoratore.*;
import com.example.the_knife.Utente.SessionManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.the_knife.Utente.SessionManager.idRist;

/**
 * Controller per la visualizzazione e gestione delle recensioni dei ristoranti da parte dell'utente.
 * Permette di aggiungere, modificare, rimuovere recensioni e visualizzare la lista associata a un ristorante.
 */
public class RecensioneRistoranteSearchController extends RecensioniRistController {

    /** Etichetta di benvenuto nella UI */
    @FXML
    private Label welcomeLabel;

    /** Lista visiva delle recensioni nel pannello UI */
    @FXML
    private ListView<Recensione> listaRecLabel;

    /** Bottone per aggiungere una recensione */
    @FXML
    private Button ButtonRecensione;

    /** Campo di input per il commento della recensione */
    @FXML
    private TextField CommentoFiled;

    /** Campo di input per il rating della recensione */
    @FXML
    private TextField RatingField;

    /** Griglia contenente i campi per aggiungere recensioni */
    @FXML
    private GridPane grigliaRec;

    /** Gestore della sessione utente */
    SessionManager session = SessionManager.getInstance();

    /** Username dell'utente loggato */
    private final String user = session.getUsername();

    /** ID dell'utente loggato */
    private final int id = session.getUserId();

    /** Ruolo dell'utente loggato */
    private final String ruolo = session.getRuolo();

    /**
     * Inizializza la UI e carica le recensioni. Configura il pulsante per aggiungere una recensione se necessario.
     */
    public void initialize() {
        welcomeLabel.setText("LE RECENSIONI ");
        System.out.println("Utente: " + user + " Id: " + id + " Ruolo: " + ruolo);

        if(SessionManager.pagina == 1 || SessionManager.pagina ==2) {
            ButtonRecensione.setOnAction(e -> {
                String commento = CommentoFiled.getText();
                InputValidator.validaCommento(commento);
                String rating = RatingField.getText();
                InputValidator.validaRating(rating);
                String autore = SessionManager.getInstance().getUsername();
                LocalDate data = LocalDate.now();
                int valutazione = Integer.parseInt(rating);

                Recensione rec = new Recensione(generaIdRec("ristoranti.json"), autore, valutazione, commento, data, null);
                try {
                    RatingField.clear();
                    CommentoFiled.clear();
                    aggiungiRecensione(rec, "ristoranti.json");
                    top10Ristoranti("ristoranti.json", "top10rist.json");
                    printListRec();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        } else {
            grigliaRec.setVisible(false);
            grigliaRec.setManaged(false);
        }

        printListRec();
    }

    /**
     * Visualizza la lista delle recensioni per il ristorante selezionato.
     * Le recensioni vengono formattate con autore, commento, data, rating, risposta e bottoni di modifica/eliminazione se applicabile.
     */
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
                    grid.getChildren().clear();
                    grid.getColumnConstraints().clear();

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
                    Label dateRec = new Label(""+recensione.getDate());
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

                    if(SessionManager.pagina ==1 || SessionManager.pagina ==2) {
                        if (user.equals(recensione.getAuthor())) {
                            Button modifica = new Button();
                            modifica.getStyleClass().add("accent-button");
                            Image icona = new Image(getClass().getResource("/com/example/the_knife/icone/modifica.png").toExternalForm());
                            ImageView iconView = new ImageView();
                            iconView.setFitWidth(24);
                            iconView.setFitHeight(24);
                            modifica.setGraphic(iconView);
                            iconView.setImage(icona);
                            modifica.setOnAction(e -> openPopup("Modifica recensione"));
                            grid.add(modifica, 0, 5);

                            Button elimina = new Button();
                            elimina.getStyleClass().add("accent-button");
                            Image icona1 = new Image(getClass().getResource("/com/example/the_knife/icone/cestino.png").toExternalForm());
                            ImageView iconView2 = new ImageView();
                            iconView2.setFitWidth(24);
                            iconView2.setFitHeight(24);
                            elimina.setGraphic(iconView2);
                            iconView2.setImage(icona1);
                            elimina.setOnAction(e -> {
                                try {
                                    removeRec("ristoranti.json");
                                    top10Ristoranti("ristoranti.json","top10rist.json");
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                                printListRec();
                            });
                            grid.add(elimina, 1, 5);
                        }
                    }

                    grid.getStyleClass().add("grid-list");
                    setGraphic(grid);
                }
            }
        });
    }

    /**
     * Gestisce il ritorno alla schermata precedente in base al contesto della sessione.
     *
     * @param event Evento generato dal pulsante di ritorno.
     * @throws IOException In caso di errore nel caricamento della pagina.
     */
    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        if(SessionManager.pagina == 0) {
            if(SessionManager.counter != 1) SessionManager.counter = 0;
            super.goTo(event, "startPage.fxml");
        } else if(SessionManager.pagina == 2) {
            if(SessionManager.menu == 1) {
                super.goTo(event, "Cliente/ristorantiClient.fxml");
            } else {
                super.goTo(event, "Cliente/dashBoardClient.fxml");
            }
        } else if(SessionManager.pagina == 1) {
            if(SessionManager.menu == 1) {
                super.goTo(event, "Ristoratore/preferitiRist.fxml");
            } else {
                super.goTo(event, "Ristoratore/dashBoardRist.fxml");
            }
        }
    }

    /**
     * Aggiunge una recensione nel file JSON per il ristorante corrente e aggiorna la media delle recensioni.
     *
     * @param newRec Recensione da aggiungere.
     * @param fileJson Percorso al file JSON.
     * @throws IOException In caso di errore di lettura/scrittura.
     */
    public void aggiungiRecensione(Recensione newRec, String fileJson) throws IOException {
        InputValidator.verificaRecensione(fileJson);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        File file = new File(fileJson);
        JsonNode root = mapper.readTree(file);
        JsonNode ristorantiNode = root.get("ristoranti");

        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);

        for (Ristorante r : listaModificabile) {
            if (r.getId() == idRist) {
                for (Recensione rec : r.recensioni) {
                    if (rec.author.equals(user)) {
                        handleInput("Errore", "Hai già recensito questo ristorante");
                        throw new MaxNumRecensioniException("Hai già recensito questo ristorante");
                    }
                }
                r.recensioni.add(newRec);
                r.setNumRec(r.getNumRec() + 1);
                break;
            }
        }

        ObjectNode nuovoRoot = mapper.createObjectNode();
        nuovoRoot.set("ristoranti", mapper.valueToTree(listaModificabile));
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileJson), nuovoRoot);
        aggiornaMediaRecensioni(fileJson);
    }

    /**
     * Genera un nuovo ID per una recensione in base al numero di recensioni esistenti.
     *
     * @param fileJson File JSON contenente i dati dei ristoranti.
     * @return Nuovo ID incrementale.
     */
    public static int generaIdRec(String fileJson) {
        int count = 0;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            JsonNode root = mapper.readTree(new File(fileJson));
            JsonNode ristorantiNode = root.get("ristoranti");
            List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
            for (Ristorante r : ristoranti) {
                if (r.getId() == idRist) {
                    count = r.getRecensioni().size() + 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count + 1;
    }

    /**
     * Rimuove la recensione dell'utente loggato dal file JSON.
     *
     * @param fileJson Percorso del file JSON.
     * @throws IOException In caso di errore nella scrittura del file.
     */
    public void removeRec(String fileJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        File file = new File(fileJson);

        if (!file.exists()) return;

        JsonNode root = mapper.readTree(file);
        JsonNode ristorantiNode = root.get("ristoranti");
        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);

        for (Ristorante r : listaModificabile) {
            if (r.getId() == idRist) {
                r.getRecensioni().removeIf(rec -> rec.getAuthor().equals(user));
                r.setNumRec(r.getNumRec() - 1);
            }
        }

        ObjectNode nuovoRoot = mapper.createObjectNode();
        nuovoRoot.set("ristoranti", mapper.valueToTree(listaModificabile));
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, nuovoRoot);
        aggiornaMediaRecensioni(fileJson);
    }

    /**
     * Aggiorna la media delle recensioni del ristorante selezionato nel file JSON.
     *
     * @param fileJson File JSON da aggiornare.
     * @throws IOException In caso di errore di I/O.
     */
    public void aggiornaMediaRecensioni(String fileJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        JsonNode root = mapper.readTree(new File(fileJson));
        JsonNode ristorantiNode = root.get("ristoranti");
        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));

        for (Ristorante r : ristoranti) {
            if (r.getId() == idRist) {
                double media = r.getRecensioni().stream().mapToInt(Recensione::getRating).average().orElse(0.0);
                r.setMediaRec(Math.round(media * 10.0) / 10.0);
                break;
            }
        }

        ObjectNode nuovoRoot = mapper.createObjectNode();
        nuovoRoot.set("ristoranti", mapper.valueToTree(ristoranti));
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileJson), nuovoRoot);
    }

    /**
     * Modifica la recensione esistente dell’utente nel file JSON.
     *
     * @param commento Nuovo commento.
     * @param rating Nuovo rating.
     * @param fileJson File JSON da modificare.
     * @throws IOException In caso di errore nella scrittura del file.
     */
    public void modificaRecensioni(String commento, int rating, String fileJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        JsonNode root = mapper.readTree(new File(fileJson));
        JsonNode ristorantiNode = root.get("ristoranti");
        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));

        for (Ristorante r : ristoranti) {
            if (r.getId() == idRist) {
                for (Recensione rec : r.getRecensioni()) {
                    if (rec.getAuthor().equals(user)) {
                        rec.setComment(commento);
                        rec.setRating(rating);
                        rec.setDate(LocalDate.now());
                        break;
                    }
                }
            }
        }

        aggiornaMediaRecensioni(fileJson);
        ObjectNode nuovoRoot = mapper.createObjectNode();
        nuovoRoot.set("ristoranti", mapper.valueToTree(ristoranti));
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileJson), nuovoRoot);
    }

    /**
     * Chiude l'applicazione o la schermata corrente.
     *
     * @param event Evento di chiusura.
     */
    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }

    /**
     * Apre un popup per modificare la recensione dell’utente.
     *
     * @param title Titolo della finestra popup.
     */
    public void openPopup(String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("popUpRecSearch.fxml"));
            Parent root = loader.load();
            Stage popupStage = new Stage();
            PopUpRecSearchController controller = loader.getController();
            controller.setMainController((RecensioneRistoranteSearchController) this);

            popupStage.setTitle(title);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(root, 600, 250));
            popupStage.setResizable(false);
            popupStage.showAndWait();
        } catch (IOException e) {
            System.err.println("Errore nel caricamento del file FXML:");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Controller principale per aggiornamenti da popup */
    private RecensioneRistoranteSearchController mainController;

    /**
     * Imposta il controller principale per aggiornare la vista dopo modifiche da popup.
     *
     * @param controller Controller principale.
     */
    public void setMainController(RecensioneRistoranteSearchController controller) {
        this.mainController = controller;
    }
}
