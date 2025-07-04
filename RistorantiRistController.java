package com.example.the_knife.Ristoratore;

import com.example.the_knife.Utente.SessionManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        printListRist();
    }

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
                        ImageView iconView = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
                        iconView.setFitWidth(24);
                        iconView.setFitHeight(24);//setto il ridimensionamento
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
                        ImageView iconView2 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
                        iconView2.setFitWidth(24);
                        iconView2.setFitHeight(24);//setto il ridimensionamento
                        recensioneButton.setGraphic(iconView2);
                        iconView2.setImage(icona2);
                        recensioneButton.setOnAction(e -> {
                            try {
                                Integer idRist = (Integer) ristorante.getId();
                                SessionManager.idRist = idRist;
                                System.out.println("Id ristorante: " + idRist);
                                goTo(e, "recensioniRist.fxml");
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        });

                        // Aggiunta dei nodi in celle precise
                        grid.add(nomeLabel, 0, 0);
                        grid.add(indirizzoLabel, 1, 0);
                        grid.add(cucinaLabel, 2, 0);
                        grid.add(ratingLabel, 3, 0);
                        grid.add(dettaglioButton, 4, 0);
                        grid.add(recensioneButton, 5, 0);

                        // Espansione colonne
                        ColumnConstraints col1 = new ColumnConstraints();
                        col1.setPercentWidth(17);
                        ColumnConstraints col2 = new ColumnConstraints();
                        col2.setPercentWidth(17);
                        ColumnConstraints col3 = new ColumnConstraints();
                        col3.setPercentWidth(17);
                        ColumnConstraints col4 = new ColumnConstraints();
                        col3.setPercentWidth(17);
                        ColumnConstraints col5 = new ColumnConstraints();
                        col4.setPercentWidth(17);
                        ColumnConstraints col6 = new ColumnConstraints();
                        col5.setPercentWidth(17);
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



    public Ristorante getRistoranteById(String fileRisto, int id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(fileRisto));
        JsonNode ristorantiNode = root.get("ristoranti");

        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));

        for (Ristorante r : ristoranti) {
            if (r.getId() == id) {
                return r; // trovato il ristorante con id univoco
            }
        }
        return null; // se non trovato
    }

    public static List<Recensione> visualizzaRecensioniPerNomeRistorante(String fileJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File(fileJson));
            JsonNode ristorantiNode = root.get("ristoranti");

            List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));

            Ristorante ristorante = null;
            for (Ristorante r : ristoranti) {
                if (r.id== idRist) {
                    ristorante = r;
                    break;
                }
            }
            if (ristorante == null) {
                System.out.println("Ristorante '" + ristorante.name + "' non trovato.");
                return null;
            }
            if (ristorante.recensioni == null || ristorante.recensioni.isEmpty()) {
                System.out.println("Il ristorante '" + ristorante.name + "' non ha recensioni.");
                return null;
            }

            List<Recensione> recensioni = new ArrayList<>(ristorante.recensioni);
            return recensioni;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void openPopup(String title) { //aggiungere variabile stringa nei parametri passati per percorso file .fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("popUpRist.fxml"));
            Parent root = loader.load();
            Stage popupStage = new Stage();

            PopUpRistController controller = loader.getController();
            controller.setMainController((DettaglioRistController) this);

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

}
