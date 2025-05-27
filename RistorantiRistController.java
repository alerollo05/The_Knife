package com.example.the_knife.Ristoratore;

import com.example.the_knife.Utente.SessionManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RistorantiRistController extends dashBoardRistController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private ListView<Ristorante> listaRistLabel;

    SessionManager session = SessionManager.getInstance();
    private final String user = session.getUsername();
    private final int id = session.getUserId();
    private final String ruolo = session.getRuolo();

    public void initialize() throws IOException {
        welcomeLabel.setText("I TUOI RISTORANTI " + user);
        System.out.println("Utente: " + user + " Id: " + id + " Ruolo: " + ruolo);

        List<Ristorante> mieiRistoranti = this.getRistoranti("ristoranti.json", id);
        listaRistLabel.setItems(FXCollections.observableArrayList(mieiRistoranti));

        listaRistLabel.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Ristorante ristorante, boolean empty) {
                super.updateItem(ristorante, empty);
                param.getStyleClass().add("list-rist");
                if (empty || ristorante == null) {
                    setText(null);
                    setGraphic(null);
                } else {

                    GridPane grid = new GridPane();
                    grid.setHgap(10);
                    grid.setVgap(5);
                    grid.setPadding(new Insets(5));

                    Label nomeLabel = new Label(ristorante.getName());
                    nomeLabel.getStyleClass().add("textNormal");

                    Label indirizzoLabel = new Label(ristorante.getAddress());
                    indirizzoLabel.getStyleClass().add("textNormal");

                    Label cucinaLabel = new Label(ristorante.getCuisine());
                    cucinaLabel.getStyleClass().add("textNormal");

                    Button dettaglioButton = new Button("Dettaglio");
                    dettaglioButton.getStyleClass().add("accent-button");
                    dettaglioButton.setOnAction(e -> {
                        try {
                            Integer idRist = (Integer) ristorante.getId();
                            session.idRist = idRist;
                            System.out.println("Id ristorante: " + idRist);
                            goTo(e,"dettaglioRist.fxml");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });

                    // Aggiunta dei nodi in celle precise
                    grid.add(nomeLabel, 0, 0);
                    grid.add(indirizzoLabel, 1, 0);
                    grid.add(cucinaLabel, 2, 0);
                    grid.add(dettaglioButton, 3, 0);

                    // Espansione colonne
                    ColumnConstraints col1 = new ColumnConstraints();
                    col1.setPercentWidth(50);
                    ColumnConstraints col2 = new ColumnConstraints();
                    col2.setPercentWidth(150);
                    ColumnConstraints col3 = new ColumnConstraints();
                    col3.setPercentWidth(50);
                    ColumnConstraints col4 = new ColumnConstraints();
                    col4.setPercentWidth(50);
                    grid.getColumnConstraints().addAll(col1, col2, col3, col4);
                    grid.getStyleClass().add("grid-list");
                    setGraphic(grid);
                }
            }
        });

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

    public List<Ristorante> getRistoranti(String fileRisto, int id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(fileRisto));
        JsonNode ristorantiNode = root.get("ristoranti");

        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        List<Ristorante> mieiRisto = new ArrayList<>();

        for (Ristorante r : ristoranti) {
            if (r.getIdRistoratore() == id) {
                mieiRisto.add(r);
            }
        }
        return mieiRisto;
    }
}
