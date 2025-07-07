package com.example.the_knife.Ristoratore;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.the_knife.Utente.SessionManager.idRist;

/**
 * Controller per la gestione dei ristoranti associati al ristoratore loggato.
 * Permette la visualizzazione, eliminazione e navigazione verso i dettagli o recensioni dei ristoranti.
 */
public class RistorantiRistController extends DashBoardRistController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private ListView<Ristorante> listaRistLabel;

    SessionManager session = SessionManager.getInstance();
    private final String user = session.getUsername();
    private final int id = session.getUserId();
    private final String ruolo = session.getRuolo();

    /**
     * Inizializza la vista e carica la lista dei ristoranti dell'utente loggato.
     */
    public void initialize() throws IOException {
        welcomeLabel.setText("I TUOI RISTORANTI " + user.toUpperCase());
        System.out.println("Utente: " + user + " Id: " + id + " Ruolo: " + ruolo);
        printListRist();
    }

    /**
     * Stampa nella ListView tutti i ristoranti dell'utente corrente.
     */
    public void printListRist(){
        try {
            List<Ristorante> mieiRistoranti = this.getRistoranti("ristoranti.json", id);

            listaRistLabel.setItems(FXCollections.observableArrayList(mieiRistoranti));

            listaRistLabel.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(Ristorante ristorante, boolean empty) {
                    super.updateItem(ristorante, empty);
                    param.getStyleClass().add("list-rist2");
                    if (empty || ristorante == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        GridPane grid = new GridPane();
                        grid.setHgap(10);
                        grid.setVgap(5);
                        grid.setPadding(new Insets(5));

                        Label nomeLabel = new Label(ristorante.getName());
                        nomeLabel.getStyleClass().add("textNormal3");

                        Label indirizzoLabel = new Label(ristorante.getLocation());
                        indirizzoLabel.getStyleClass().add("textNormal3");

                        Label cucinaLabel = new Label(ristorante.getCuisine());
                        cucinaLabel.getStyleClass().add("textNormal3");

                        Label ratingLabel = new Label(""+ristorante.getMediaRec());
                        ratingLabel.getStyleClass().add("textNormal3");

                        Button dettaglioButton = new Button();
                        dettaglioButton.getStyleClass().add("accent-button");
                        Image icona = new Image(getClass().getResource("/com/example/the_knife/icone/dettaglio.png").toExternalForm());
                        ImageView iconView = new ImageView();
                        iconView.setFitWidth(24);
                        iconView.setFitHeight(24);
                        dettaglioButton.setGraphic(iconView);
                        iconView.setImage(icona);
                        dettaglioButton.setOnAction(e -> {
                            try {
                                Integer idRist =  ristorante.getId();
                                SessionManager.idRist = idRist;
                                System.out.println("Id ristorante: " + idRist);
                                goTo(e, "dettaglioRist.fxml");
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        });

                        Button recensioneButton = new Button();
                        recensioneButton.getStyleClass().add("accent-button");
                        Image icona2 = new Image(getClass().getResource("/com/example/the_knife/icone/recensioni.png").toExternalForm());
                        ImageView iconView2 = new ImageView();
                        iconView2.setFitWidth(24);
                        iconView2.setFitHeight(24);
                        recensioneButton.setGraphic(iconView2);
                        iconView2.setImage(icona2);
                        recensioneButton.setOnAction(e -> {
                            try {
                                Integer idRist = ristorante.getId();
                                SessionManager.idRist = idRist;
                                System.out.println("Id ristorante: " + idRist);
                                goTo(e, "recensioniRist.fxml");
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        });

                        Button eliminaButton = new Button();
                        eliminaButton.getStyleClass().add("accent-button");
                        Image icona3 = new Image(getClass().getResource("/com/example/the_knife/icone/cestino.png").toExternalForm());
                        ImageView iconView3 = new ImageView();
                        iconView3.setFitWidth(24);
                        iconView3.setFitHeight(24);
                        eliminaButton.setGraphic(iconView3);
                        iconView3.setImage(icona3);
                        eliminaButton.setOnAction(e -> {
                            try {
                                SessionManager.idRist = ristorante.getId();
                                removeRistorante("ristoranti.json");
                                printListRist();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        });

                        grid.add(nomeLabel, 0, 0);
                        grid.add(indirizzoLabel, 1, 0);
                        grid.add(cucinaLabel, 2, 0);
                        grid.add(ratingLabel, 3, 0);
                        grid.add(dettaglioButton, 4, 0);
                        grid.add(recensioneButton, 5, 0);
                        grid.add(eliminaButton, 6, 0);

                        ColumnConstraints col1 = new ColumnConstraints(); col1.setPercentWidth(30);
                        ColumnConstraints col2 = new ColumnConstraints(); col2.setPercentWidth(22);
                        ColumnConstraints col3 = new ColumnConstraints(); col3.setPercentWidth(17);
                        ColumnConstraints col4 = new ColumnConstraints(); col4.setPercentWidth(17);
                        ColumnConstraints col5 = new ColumnConstraints(); col5.setPercentWidth(17);
                        ColumnConstraints col6 = new ColumnConstraints(); col6.setPercentWidth(17);
                        ColumnConstraints col7 = new ColumnConstraints(); col7.setPercentWidth(17);
                        grid.getColumnConstraints().addAll(col1, col2, col3, col4, col5, col6, col7);
                        grid.getStyleClass().add("grid-list");
                        setGraphic(grid);
                    }
                }
            });
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file ristoranti.json");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("File ristoranti.json non trovato");
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.err.println("Errore nella lettura del file ristoranti.json");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Errore nella lettura del file ristoranti.json");
            e.printStackTrace();
        }
    }

    @FXML
    public void onAddRistClick(ActionEvent event) throws IOException {
        super.onAddRistClick(event);
    }

    @FXML
    public void handleLogOut(ActionEvent event) {
        super.handleLogOut(event);
    }

    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }

    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        super.goBack(event);
    }

    @FXML
    protected void onProfileClick(ActionEvent event) throws IOException {
        super.onProfileClick(event);
    }

    @FXML
    protected void onPreferitiClick(ActionEvent event) throws IOException {
        SessionManager.counter1 = 0;
        super.goTo(event,"preferitiRist.fxml");
    }

    /**
     * Recupera un ristorante dal file JSON tramite il suo ID.
     * @param fileRisto percorso al file JSON
     * @param id ID del ristorante da cercare
     * @return Ristorante trovato o null se non presente
     */
    public Ristorante getRistoranteById(String fileRisto, int id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        JsonNode root = mapper.readTree(new File(fileRisto));
        JsonNode ristorantiNode = root.get("ristoranti");

        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));

        for (Ristorante r : ristoranti) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    /**
     * Visualizza le recensioni associate al ristorante selezionato.
     * @param fileJson file JSON da cui leggere i ristoranti
     * @return lista di {@link Recensione} o null se non presenti
     */
    public static List<Recensione> visualizzaRecensioniPerNomeRistorante(String fileJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            JsonNode root = mapper.readTree(new File(fileJson));
            JsonNode ristorantiNode = root.get("ristoranti");

            List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));

            Ristorante ristorante = null;
            for (Ristorante r : ristoranti) {
                if (r.id == idRist) {
                    ristorante = r;
                    break;
                }
            }
            if (ristorante == null || ristorante.recensioni == null || ristorante.recensioni.isEmpty()) {
                return null;
            }

            return new ArrayList<>(ristorante.recensioni);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Apre un popup per la modifica dei dati del ristorante.
     * @param title titolo della finestra popup
     */
    public void openPopup(String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("popUpRist.fxml"));
            Parent root = loader.load();
            Stage popupStage = new Stage();

            PopUpRistController controller = loader.getController();
            controller.setMainController((DettaglioRistController) this);

            popupStage.setTitle(title);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(root,500,200));
            popupStage.setResizable(false);
            popupStage.showAndWait();
        } catch (IOException e) {
            System.err.println("Errore nel caricamento del file FXML:");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Rimuove un ristorante dal file JSON in base all'id salvato nella sessione.
     * @param fileRisto percorso del file JSON dei ristoranti
     * @throws IOException se la scrittura o lettura del file fallisce
     */
    public static void removeRistorante(String fileRisto) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        JsonNode root = mapper.readTree(new File(fileRisto));
        JsonNode ristorantiNode = root.get("ristoranti");

        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);

        boolean removed = false;

        for (Ristorante r : listaModificabile) {
            if (r.getId() == idRist) {
                listaModificabile.remove(r);
                removed = true;
                break;
            }
        }

        if (removed) {
            ObjectNode nuovoRoot = mapper.createObjectNode();
            nuovoRoot.set("Utenti", mapper.valueToTree(listaModificabile));
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileRisto), nuovoRoot);
            System.out.println("File JSON aggiornato correttamente.");
        } else {
            System.out.println("Nessuna modifica effettuata nel file.");
        }
    }
}
