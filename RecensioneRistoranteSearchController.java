package com.example.the_knife;

import com.example.the_knife.Ristoratore.Recensione;
import com.example.the_knife.Ristoratore.Ristorante;
import com.example.the_knife.Ristoratore.RistorantiRistController;
import com.example.the_knife.Utente.SessionManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.example.the_knife.Utente.SessionManager.idRist;

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
                    if(SessionManager.pagina ==1 || SessionManager.pagina ==2) {
                        if (user.equals(recensione.getAuthor())) {
                            Button modifica = new Button();
                            modifica.getStyleClass().add("accent-button");
                            Image icona = new Image(getClass().getResource("/com/example/the_knife/icone/modifica.png").toExternalForm());
                            ImageView iconView = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
                            iconView.setFitWidth(24);
                            iconView.setFitHeight(24);//setto il ridimensionamento
                            modifica.setGraphic(iconView);
                            iconView.setImage(icona);
                            modifica.setOnAction(e -> {
                                //open pop up con cambio di rating
                            });
                            grid.add(modifica, 0, 5);


                            Button elimina = new Button();
                            elimina.getStyleClass().add("accent-button");
                            Image icona1 = new Image(getClass().getResource("/com/example/the_knife/icone/cestino.png").toExternalForm());
                            ImageView iconView2 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
                            iconView2.setFitWidth(24);
                            iconView2.setFitHeight(24);//setto il ridimensionamento
                            elimina.setGraphic(iconView2);
                            iconView2.setImage(icona1);
                            elimina.setOnAction(e -> {
                                //Eliminazione della recensione
                                try {
                                    removeRec("ristoranti.json");
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

    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        if(SessionManager.pagina == 0) {
            if(SessionManager.counter != 1) SessionManager.counter = 0;
            super.goTo(event, "startPage.fxml");
        }else if(SessionManager.pagina == 1) {
            super.goTo(event, "Ristoratore/dashBoardRist.fxml");
        }else{
            super.goTo(event, "Cliente/dashBoardClient.fxml");
        }
    }

    public void aggiungiRecensione(Recensione newRec, String fileJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();// Crea un'istanza di ObjectMapper
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        File file = new File(fileJson);

        JsonNode root = mapper.readTree(file);
        JsonNode ristorantiNode = root.get("ristoranti");

        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);

        for (Ristorante r : listaModificabile) {
            if (r.getId() == idRist){
                r.recensioni.add(newRec);
            }
        }
        ObjectNode nuovoRoot = mapper.createObjectNode();
        nuovoRoot.set("ristoranti", mapper.valueToTree(listaModificabile));

        // Sovrascrive il file
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileJson), nuovoRoot);

    }

    public void removeRec(String fileJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        File file = new File(fileJson);

        // Se il file non esiste, termina senza copiare nulla
        if (!file.exists()) {
            System.err.println("File JSON non trovato: " + fileJson);
            return;
        }

        JsonNode root = mapper.readTree(file);
        JsonNode ristorantiNode = root.get("ristoranti");

        if (ristorantiNode == null || !ristorantiNode.isArray()) {
            System.err.println("Formato JSON non valido: nodo 'ristoranti' mancante o non è un array.");
            return;
        }

        // Conversione in lista di oggetti
        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);

        boolean removed = false;

        for (Ristorante r : listaModificabile) {
            if (r.getId() == idRist) {
                Iterator<Recensione> iterator = r.getRecensioni().iterator();
                while (iterator.hasNext()) {
                    Recensione rec = iterator.next();
                    if (rec.getAuthor().equals(user)) {
                        iterator.remove();
                        removed = true;
                        System.out.println("Recensione rimossa.");
                    }
                }
            }
        }

        if (removed) {
            ObjectNode nuovoRoot = mapper.createObjectNode();
            nuovoRoot.set("ristoranti", mapper.valueToTree(listaModificabile));
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, nuovoRoot);
            System.out.println("File JSON aggiornato correttamente.");
        } else {
            System.out.println("Nessuna modifica effettuata nel file.");
        }
    }
    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }

}
