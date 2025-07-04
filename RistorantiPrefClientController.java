package com.example.the_knife.Cliente;

import com.example.the_knife.Ristoratore.Ristorante;
import com.example.the_knife.Utente.SessionManager;
import com.example.the_knife.Utente.Utente;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RistorantiPrefClientController extends dashBoardClientController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private ListView<Ristorante> listaRistLabel;

    SessionManager session = SessionManager.getInstance();
    private final String user = session.getUsername();
    private final int id = session.getUserId();
    private final String ruolo = session.getRuolo();

    @FXML
    public void initialize(){
        welcomeLabel.setText("I TUOI RISTORANTI PREFERITI " + user + "");
        System.out.println("Utente: "+user+ " Id: "+id+" Ruolo: "+ruolo);
            printListPrefUte();
    }

    public void printListPrefUte() {
        try {
            System.out.println("eseguo");
            List<Ristorante> risultati = prefeUte("fileUtenti.json", "ristoranti.json");

            listaRistLabel.setItems(FXCollections.observableArrayList(risultati));

            listaRistLabel.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(Ristorante ristorante, boolean empty) {
                    super.updateItem(ristorante, empty);
                    param.getStyleClass().add("list-rist3");
                    if (empty || ristorante == null) {
                        setText(null);
                        Label nomeLabel = new Label("Nessun risultato trovato");
                        nomeLabel.getStyleClass().add("textNormal");
                        setGraphic(null);
                    } else {

                        GridPane grid = new GridPane();
                        grid.setHgap(10);
                        grid.setVgap(5);
                        grid.setPadding(new Insets(5));

                        Label nomeLabel = new Label(ristorante.getName());
                        nomeLabel.getStyleClass().add("textNormal");

                        Label cucinaLabel = new Label(ristorante.getCuisine());
                        cucinaLabel.getStyleClass().add("textNormal");

                        Label ratingLabel = new Label("" + ristorante.getMediaRec());
                        ratingLabel.getStyleClass().add("textNormal");

                        //ICONA DETTAGGLIO
                        Button dettaglioButton = new Button();
                        dettaglioButton.getStyleClass().add("accent-button");
                        Image icona = new Image(getClass().getResource("/com/example/the_knife/icone/dettaglio.png").toExternalForm());
                        ImageView iconView3 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
                        iconView3.setFitWidth(24);
                        iconView3.setFitHeight(24);//setto il ridimensionamento
                        dettaglioButton.setGraphic(iconView3);
                        iconView3.setImage(icona);
                        dettaglioButton.setOnAction(e -> {
                            try {
                                Integer idRist = ristorante.getId();
                                SessionManager.idRist = idRist;
                                SessionManager.counter = 1;
                                SessionManager.counter1 = 1;
                                SessionManager.counter2 = 1;
                                System.out.println("Id ristorante: " + idRist);
                                goTo(e, "/com/example/the_knife/dettaglioRistoranteSearch.fxml");
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        });

                        //ICONA RECENSIONE
                        Button recensioneButton = new Button();
                        recensioneButton.getStyleClass().add("accent-button");
                        Image icona2 = new Image(getClass().getResource("/com/example/the_knife/icone/recensioni.png").toExternalForm());
                        ImageView iconView4 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
                        iconView4.setFitWidth(24);
                        iconView4.setFitHeight(24);//setto il ridimensionamento
                        recensioneButton.setGraphic(iconView4);
                        iconView4.setImage(icona2);

                        recensioneButton.setOnAction(e -> {
                            try {
                                Integer idRist = (Integer) ristorante.getId();
                                SessionManager.idRist = idRist;
                                System.out.println("Id ristorante: " + idRist);
                                goTo(e, "/com/example/the_knife/recensioneRistoranteSearch.fxml");
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        });

                        //BOTTONE PREFETITI
                        //CARICO LE IMMAGINI DI ICONA PER IL BOTTONE DEI PREFERITI
                        Button prefButton = new Button();
                        prefButton.getStyleClass().add("cuore-button");
                        Image cuoreVuoto = new Image(getClass().getResource("/com/example/the_knife/icone/cuoreVuoto.png").toExternalForm());
                        Image cuorePieno = new Image(getClass().getResource("/com/example/the_knife/icone/cuorePieno.png").toExternalForm());

                        ImageView iconView2 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
                        iconView2.setFitWidth(24);
                        iconView2.setFitHeight(24);//setto il ridimensionamento
                        prefButton.setGraphic(iconView2);
                        boolean isPref = false;
                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
                            File file = new File("fileUtenti.json");
                            JsonNode root = mapper.readTree(file);
                            JsonNode utentiNode = root.get("Utenti");
                            List<com.example.the_knife.Utente.Utente> utenti = Arrays.asList(
                                    mapper.treeToValue(utentiNode, com.example.the_knife.Utente.Utente[].class)
                            );
                            for (com.example.the_knife.Utente.Utente u : utenti) {
                                if (u.getUsername().equals(SessionManager.getInstance().getUsername())) {
                                    if (u.getPreferiti() != null && u.getPreferiti().contains(ristorante.getId())) {
                                        isPref = true;
                                    }
                                    break;
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        if (isPref)
                            iconView2.setImage(cuorePieno); //imposto inizialmente il bottone cuore pieno o vuoto in base a com'è prima della modifiche
                        else iconView2.setImage(cuoreVuoto);

                        prefButton.setOnAction(e -> {
                            try {
                                SessionManager.idRist = ristorante.getId();

                                // Rileggi lo stato aggiornato dei preferiti ogni volta
                                ObjectMapper mapper = new ObjectMapper();
                                mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
                                File file = new File("fileUtenti.json");
                                JsonNode root = mapper.readTree(file);
                                JsonNode utentiNode = root.get("Utenti");
                                List<Utente> utenti = Arrays.asList(mapper.treeToValue(utentiNode, Utente[].class));

                                boolean currentlyInFavorites = false;

                                for (Utente u : utenti) {
                                    if (u.getUsername().equals(SessionManager.getInstance().getUsername())) {
                                        currentlyInFavorites = u.getPreferiti() != null && u.getPreferiti().contains(SessionManager.idRist);
                                        break;
                                    }
                                }

                                if (currentlyInFavorites) {
                                    removePrefe("fileUtenti.json");
                                    printListPrefUte();
                                    iconView2.setImage(cuoreVuoto);
                                } else {
                                    addPrefe("fileUtenti.json");
                                    iconView2.setImage(cuorePieno);
                                }

                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        });

                        // Aggiunta dei nodi in celle precise
                        grid.add(nomeLabel, 0, 0);
                        grid.add(cucinaLabel, 1, 0);
                        grid.add(ratingLabel, 2, 0);
                        grid.add(dettaglioButton, 3, 0);
                        grid.add(recensioneButton, 4, 0);
                        grid.add(prefButton, 5, 0);

                        ColumnConstraints col1 = new ColumnConstraints();
                        col1.setPercentWidth(35);
                        ColumnConstraints col2 = new ColumnConstraints();
                        col2.setPercentWidth(35);
                        ColumnConstraints col3 = new ColumnConstraints();
                        col3.setPercentWidth(20);
                        ColumnConstraints col4 = new ColumnConstraints();
                        col4.setPercentWidth(25);
                        ColumnConstraints col5 = new ColumnConstraints();
                        col5.setPercentWidth(25);
                        ColumnConstraints col6 = new ColumnConstraints();
                        col6.setPercentWidth(25);
                        grid.getColumnConstraints().addAll(col1, col2, col3, col4, col5, col6);
                        grid.getStyleClass().add("grid-list");
                        setGraphic(grid);
                        }
                    }
            });
        }catch (IOException e){
            System.err.println("Errore nella lettura del file ristoranti.json");
            e.printStackTrace();
        }catch (NullPointerException e){
            System.err.println("File ristoranti.json non trovato");
            e.printStackTrace();
        }catch(RuntimeException e) {
            System.err.println("Errore nella lettura del file ristoranti.json");
            e.printStackTrace();
        }
        catch (Exception e){
            System.err.println("Errore nella lettura del file ristoranti.json");
            e.printStackTrace();
        }
    }

    public List<Ristorante> prefeUte(String fileUte,String fileRist) throws IOException {
        List<Ristorante> risultati = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        File file1 = new File(fileUte);
        File file2 = new File(fileRist);

        JsonNode rootRist = mapper.readTree(file2);

        JsonNode ristorantiNode = rootRist.get("ristoranti");
        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        JsonNode rootUte = mapper.readTree(file1);
        JsonNode utentiNode = rootUte.get("Utenti");
        List<Utente> utenti = Arrays.asList(mapper.treeToValue(utentiNode, Utente[].class));

        for (Utente u : utenti) {
            if (u.getUsername().equals(SessionManager.getInstance().getUsername())) {
                List<Integer> preferiti = u.getPreferiti();
                if (preferiti == null) {
                    System.out.println("Nessun preferito per l'utente: " + u.getUsername());
                    return risultati;
                }

                for (Ristorante r : ristoranti) {
                    if (preferiti.contains(r.getId())) {
                        risultati.add(r);
                    }
                }
            }
        }
        System.out.println("Preferiti trovati: " + risultati);
        return risultati;
    }



    @FXML
    protected void onRistorantiClick(ActionEvent event) throws IOException {
        onRistorantiClick(event);
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
}
