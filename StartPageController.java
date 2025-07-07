package com.example.the_knife;
import com.example.the_knife.Ristoratore.Ristorante;
import com.example.the_knife.Utente.SessionManager;
import com.example.the_knife.Utente.Utente;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;
import java.util.*;

import static com.example.the_knife.Utente.SessionManager.idRist;


/**
 * Controller principale per la schermata iniziale dell'applicazione The_Knife.
 *
 * Gestisce la logica di inizializzazione della UI, applicazione dei filtri
 * di ricerca per i ristoranti, visualizzazione dei risultati, gestione dei preferiti
 * e navigazione tra pagine di dettaglio e recensioni.
 */
public class StartPageController {


    /** ListView che mostra i ristoranti trovati o consigliati all'utente. */
    @FXML
    private ListView<Ristorante> listaRistLabel;

    /** Campo di testo per inserire la città in cui cercare ristoranti. */
    @FXML
    private TextField cityField;

    /** ComboBox per selezionare il tipo di cucina desiderato. */
    @FXML
    private ComboBox<String> cuisineBox;

    /** ComboBox per selezionare la fascia di prezzo desiderata. */
    @FXML
    private ComboBox<String> priceBox;

    /** ComboBox per selezionare se si desidera la consegna a domicilio. */
    @FXML
    private ComboBox<String> deliveryBox;

    /** ComboBox per selezionare se si desidera prenotare online. */
    @FXML
    private ComboBox<String> bookingBox;

    /** Pulsante per avviare la ricerca in base ai filtri selezionati. */
    @FXML
    private Button searchButton;


    /**
     * Metodo di inizializzazione della UI, viene chiamato automaticamente da JavaFX.
     * Carica i ristoranti top 10, configura le icone e i filtri, e ripristina eventuali selezioni precedenti.
     *
     * @throws IOException se avviene un errore nella lettura dei file JSON.
     */

    @FXML
    private void initialize() throws IOException {
        top10Ristoranti("ristoranti.json","top10rist.json");
        //ICONA LENTE INGRANDIMENTO
        searchButton.getStyleClass().add("accent-button");
        Image lenteIngrandimento = new Image(getClass().getResource("/com/example/the_knife/icone/lenteIngrandimento.png").toExternalForm());
        ImageView iconView = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
        iconView.setFitWidth(24);
        iconView.setFitHeight(24);//setto il ridimensionamento
        searchButton.setGraphic(iconView);
        iconView.setImage(lenteIngrandimento);

        SessionManager.pagina = 0;
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
        if (SessionManager.counter == 1) {
            cityField.setText(SessionManager.luogoNomeStatico);
            cuisineBox.setValue(SessionManager.tipoCucinaStatico);
            priceBox.setValue(SessionManager.prezzoStatico);
            deliveryBox.setValue(SessionManager.deliveryStatico ? "Delivery" : "No delivery");
            bookingBox.setValue(SessionManager.bookingStatico ? "Booking" : "No booking online");
            printListRist("dettaglioRistoranteSearch.fxml","recensioneRistoranteSearch.fxml"); // mostra la lista con i filtri precedenti
        } else {
            deliveryBox.getSelectionModel().selectFirst();
            bookingBox.getSelectionModel().selectFirst();
            printListRistTop10("dettaglioRistoranteSearch.fxml","recensioneRistoranteSearch.fxml"); // mostra top 10 iniziale
        }
    }


    /**
     * Mostra nella lista i ristoranti top 10 ordinati per recensione.
     *
     * @param pagDettagli path del file FXML per la pagina di dettaglio ristorante.
     * @param pagRecensioni path del file FXML per la pagina di recensioni ristorante.
     */
    public void printListRistTop10(String pagDettagli,String pagRecensioni){
        try {
            SessionManager.counter = 0;
            System.out.println("listaRistLabel è null? " + (listaRistLabel == null));
            List<Ristorante> mieiRistoranti = getRistorantiTop("top10rist.json");
            listaRistLabel.setItems(FXCollections.observableArrayList(mieiRistoranti));

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

                        Label ratingLabel = new Label(""+ristorante.getMediaRec());
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
                                Integer idRist =  ristorante.getId();
                                SessionManager.idRist = idRist;
                                System.out.println("Id ristorante: " + idRist);
                                goTo(e, pagDettagli);
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
                                goTo(e, pagRecensioni);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        });

                        // Aggiunta dei nodi in celle precise
                        grid.add(nomeLabel, 0, 0);
                        grid.add(cucinaLabel, 1, 0);
                        grid.add(ratingLabel, 2, 0);
                        grid.add(dettaglioButton, 3, 0);
                        grid.add(recensioneButton, 4, 0);

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

                        if (SessionManager.pagina == 2 || SessionManager.pagina == 1) {
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

                            if(isPref) iconView2.setImage(cuorePieno); //imposto inizialmente il bottone cuore pieno o vuoto in base a com'è prima della modifiche
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
                                        iconView2.setImage(cuoreVuoto);
                                    } else {
                                        addPrefe("fileUtenti.json");
                                        iconView2.setImage(cuorePieno);
                                    }

                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            });

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
                            grid.add(prefButton, 5, 0);
                            ColumnConstraints col6 = new ColumnConstraints();
                            col6.setPercentWidth(25);
                            grid.getColumnConstraints().addAll(col1, col2, col3, col4, col5,col6);
                            grid.getStyleClass().add("grid-list");
                            setGraphic(grid);
                        }

                        // Espansione colonne
                        if(SessionManager.pagina == 0) {
                            ColumnConstraints col1 = new ColumnConstraints();
                            col1.setPercentWidth(35);
                            ColumnConstraints col2 = new ColumnConstraints();
                            col2.setPercentWidth(35);
                            ColumnConstraints col3 = new ColumnConstraints();
                            col3.setPercentWidth(25);
                            ColumnConstraints col4 = new ColumnConstraints();
                            col4.setPercentWidth(35);
                            ColumnConstraints col5 = new ColumnConstraints();
                            col5.setPercentWidth(35);
                            grid.getColumnConstraints().addAll(col1, col2, col3, col4, col5);
                            grid.getStyleClass().add("grid-list");
                            setGraphic(grid);
                        }
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

    /**
     * Stampa nella lista i ristoranti trovati con i filtri impostati.
     *
     * @param pagDettagli path del file FXML per la pagina di dettaglio ristorante.
     * @param pagRecensioni path del file FXML per la pagina di recensioni ristorante.
     */
    public void printListRist(String pagDettagli,String pagRecensioni){
        try {
            System.out.println("listaRistLabel è null? " + (listaRistLabel == null));
            List<Ristorante> mieiRistoranti = cercaRist();
            listaRistLabel.setItems(FXCollections.observableArrayList(mieiRistoranti));

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

                        Label ratingLabel = new Label(""+ristorante.getMediaRec());
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
                                Integer idRist =  ristorante.getId();
                                SessionManager.idRist = idRist;
                                SessionManager.counter = 1;
                                SessionManager.counter1 = 1;
                                SessionManager.counter2 = 1;
                                System.out.println("Id ristorante: " + idRist);
                                goTo(e, pagDettagli);
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
                                goTo(e, pagRecensioni);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        });

                        // Aggiunta dei nodi in celle precise
                        grid.add(nomeLabel, 0, 0);
                        grid.add(cucinaLabel, 1, 0);
                        grid.add(ratingLabel, 2, 0);
                        grid.add(dettaglioButton, 3, 0);
                        grid.add(recensioneButton, 4, 0);

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

                        if (SessionManager.pagina == 2 || SessionManager.pagina == 1) {
                            boolean isPref = false;
                            try {
                                ObjectMapper mapper = new ObjectMapper();
                                mapper.registerModule(new JavaTimeModule());
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

                            if(isPref) iconView2.setImage(cuorePieno); //imposto inizialmente il bottone cuore pieno o vuoto in base a com'è prima della modifiche
                            else iconView2.setImage(cuoreVuoto);

                            prefButton.setOnAction(e -> {
                                try {
                                    SessionManager.idRist = ristorante.getId();

                                    // Rileggi lo stato aggiornato dei preferiti ogni volta
                                    ObjectMapper mapper = new ObjectMapper();
                                    mapper.registerModule(new JavaTimeModule());
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
                                        iconView2.setImage(cuoreVuoto);
                                    } else {
                                        addPrefe("fileUtenti.json");
                                        iconView2.setImage(cuorePieno);
                                    }

                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            });

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
                            grid.add(prefButton, 5, 0);
                            ColumnConstraints col6 = new ColumnConstraints();
                            col6.setPercentWidth(25);
                            grid.getColumnConstraints().addAll(col1, col2, col3, col4, col5,col6);
                            grid.getStyleClass().add("grid-list");
                            setGraphic(grid);
                        }

                        // Espansione colonne
                        if(SessionManager.pagina == 0) {
                            ColumnConstraints col1 = new ColumnConstraints();
                            col1.setPercentWidth(35);
                            ColumnConstraints col2 = new ColumnConstraints();
                            col2.setPercentWidth(35);
                            ColumnConstraints col3 = new ColumnConstraints();
                            col3.setPercentWidth(25);
                            ColumnConstraints col4 = new ColumnConstraints();
                            col4.setPercentWidth(35);
                            ColumnConstraints col5 = new ColumnConstraints();
                            col5.setPercentWidth(35);
                            grid.getColumnConstraints().addAll(col1, col2, col3, col4, col5);
                            grid.getStyleClass().add("grid-list");
                            setGraphic(grid);
                        }
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


    /**
     * Aggiunge il ristorante corrente ai preferiti dell'utente.
     *
     * @param fileJson percorso del file JSON che contiene i dati utente.
     * @throws IOException in caso di errore durante la lettura/scrittura del file.
     */
    public void addPrefe(String fileJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        File file = new File(fileJson);

        JsonNode root = mapper.readTree(file);
        JsonNode utentiNode = root.get("Utenti");

        List<Utente> utenti = Arrays.asList(mapper.treeToValue(utentiNode, Utente[].class));
        List<Utente> listaModificabile = new ArrayList<>(utenti);

        for (Utente u : listaModificabile) {
            if (u.getUsername().equals(SessionManager.getInstance().getUsername())) {
                if (u.getPreferiti() == null) {
                    u.setPreferiti(new ArrayList<>());
                }
                if (!u.getPreferiti().contains(SessionManager.idRist)) {
                    u.getPreferiti().add(SessionManager.idRist);
                }
                break;  // utente trovato: esco dal ciclo
            }
        }

        ObjectNode nuovoRoot = mapper.createObjectNode();
        nuovoRoot.set("Utenti", mapper.valueToTree(listaModificabile));


        mapper.writerWithDefaultPrettyPrinter().writeValue(file, nuovoRoot);

    }


    /**
     * Rimuove il ristorante corrente dai preferiti dell'utente.
     *
     * @param fileJson percorso del file JSON che contiene i dati utente.
     * @throws IOException in caso di errore durante la lettura/scrittura del file.
     */
    public void removePrefe(String fileJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        File file = new File(fileJson);

        JsonNode root = mapper.readTree(file);
        JsonNode utentiNode = root.get("Utenti");

        List<Utente> utenti = Arrays.asList(mapper.treeToValue(utentiNode, Utente[].class));
        List<Utente> listaModificabile = new ArrayList<>(utenti);

        boolean removed = false;

        for (Utente u : listaModificabile) {
            if (u.getUsername().equals(SessionManager.getInstance().getUsername())) {
                List<Integer> preferiti = u.getPreferiti();

                if (preferiti != null && preferiti.contains(SessionManager.idRist)) {
                    removed = preferiti.remove(Integer.valueOf(SessionManager.idRist));
                    System.out.println("Ristorante " + SessionManager.idRist + " rimosso dai preferiti.");
                } else {
                    System.out.println("Ristorante " + SessionManager.idRist + " NON era nei preferiti.");
                }
            }
        }

        if (removed) {
            ObjectNode nuovoRoot = mapper.createObjectNode();
            nuovoRoot.set("Utenti", mapper.valueToTree(listaModificabile));
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, nuovoRoot);
            System.out.println("File JSON aggiornato correttamente.");
        } else {
            System.out.println("Nessuna modifica effettuata nel file.");
        }
    }



    /**
     * Recupera tutti i ristoranti gestiti da un determinato ristoratore.
     *
     * @param fileRisto percorso del file JSON dei ristoranti.
     * @param id ID del ristoratore.
     * @return lista dei ristoranti gestiti dal ristoratore specificato.
     * @throws IOException se il file non può essere letto.
     */
    public List<Ristorante> getRistoranti(String fileRisto, int id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
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

    /**
     * Restituisce la lista dei top ristoranti (es. i primi 10) da un file JSON.
     *
     * @param fileRisto percorso del file contenente i ristoranti.
     * @return lista dei ristoranti top.
     * @throws IOException in caso di errore nella lettura del file.
     */
    public List<Ristorante> getRistorantiTop(String fileRisto) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        JsonNode root = mapper.readTree(new File(fileRisto));
        JsonNode ristorantiNode = root.get("ristoranti");

        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        List<Ristorante> mieiRisto = new ArrayList<>();
        mieiRisto.addAll(ristoranti);
        return mieiRisto;
    }


    /**
     * Passa alla schermata di login.
     *
     * @param event evento che ha generato l'azione.
     * @throws IOException se il file FXML non è stato caricato correttamente.
     */
    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
            Scene loginScene = new Scene(loader.load(),900,800);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(loginScene);
            stage.setTitle("The_Knife");
            stage.setResizable(false); // Impedisce il resize manuale
            stage.setMaximized(false); // Impedisce l'avvio in modalità massimizzata
            stage.show();
        } catch (IOException e) {
            System.err.println("Errore nel caricamento del file FXML:");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Il path al file FXML è nullo o errato:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Errore imprevisto:");
            e.printStackTrace();
        }
    }

    /**
     * Passa a una schermata indicata dal percorso FXML.
     *
     * @param event evento generato dal bottone.
     * @param location path del file FXML da caricare.
     * @throws IOException se il caricamento fallisce.
     */
    @FXML
    public void goTo(ActionEvent event, String location) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
            Scene loginScene = new Scene(loader.load(), 900, 800);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(loginScene);
            stage.setTitle("The_Knife");
            stage.setResizable(false); // Impedisce il resize manuale
            stage.setMaximized(false); // Impedisce l'avvio in modalità massimizzata
            stage.show();
        } catch (IOException e) {
            System.err.println("Errore nel caricamento del file FXML:");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Il path al file FXML è nullo o errato:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Errore imprevisto:");
            e.printStackTrace();
        }
    }


    /**
     * Chiude il programma.
     *
     * @param event evento generato dalla pressione del bottone \"Chiudi\".
     */
    @FXML
    public void closeProgram(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Esegue la ricerca e aggiorna la lista in base ai filtri selezionati.
     */

    @FXML
    private void onSearchClicked() {
        SessionManager.counter = 0; // reset per applicare nuovi filtri
        printListRist("dettaglioRistoranteSearch.fxml","recensioneRistoranteSearch.fxml");
    }


    /**
     * Calcola i top 10 ristoranti da un file e li salva in un nuovo file JSON.
     *
     * @param fileJson file di origine contenente tutti i ristoranti.
     * @param filetop10 file di destinazione per i top 10.
     * @throws IOException se qualcosa va storto durante la scrittura.
     */
    public void top10Ristoranti(String fileJson, String filetop10) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        // Leggi tutti i ristoranti
        JsonNode rootR = mapper.readTree(new File(fileJson));
        JsonNode tuttiRistNode = rootR.get("ristoranti");
        List<Ristorante> tuttiRist = Arrays.asList(mapper.treeToValue(tuttiRistNode, Ristorante[].class));

        // Ordina tutti i ristoranti (presumo per rating medio)
        ordinaRist(tuttiRist); // Assicurati che funzioni!

        // Prendi i primi 10
        List<Ristorante> top10 = tuttiRist.stream().limit(10).toList();

        // Scrivi nel nuovo file
        ObjectNode nuovoRoot = mapper.createObjectNode();
        nuovoRoot.set("ristoranti", mapper.valueToTree(top10));
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filetop10), nuovoRoot);
    }

    /**
     * Ordina una lista di ristoranti in base alla media recensioni e numero recensioni.
     *
     * @param risultati lista dei ristoranti da ordinare.
     */
    public void ordinaRist(List<Ristorante> risultati) {
        quickSort(risultati, 0, risultati.size() - 1);
    }


    /**
     * Implementa l’algoritmo di ordinamento QuickSort.
     */
    private void quickSort(List<Ristorante> lista, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(lista, low, high);
            quickSort(lista, low, pivotIndex - 1);
            quickSort(lista, pivotIndex + 1, high);
        }
    }


    /**
     * Implementa l’algoritmo di ordinamento QuickSort.
     */
    private int partition(List<Ristorante> lista, int low, int high) {
        Ristorante pivot = lista.get(high); // Usiamo l'intero oggetto per confrontare entrambi i campi
        int i = low - 1;

        for (int j = low; j < high; j++) {
            Ristorante current = lista.get(j);
            // Se MediaRec è uguale, confronta NumRec sempre in decrescente
            if (current.getMediaRec() > pivot.getMediaRec() ||
                    (current.getMediaRec() == pivot.getMediaRec() && current.getNumRec() > pivot.getNumRec())
            ) {
                i++;
                Collections.swap(lista, i, j);
            }
        }
        Collections.swap(lista, i + 1, high);
        return i + 1;
    }

    /**
     * Aggiunge un nuovo ristorante al file JSON esistente.
     *
     * @param nuovo oggetto Ristorante da aggiungere.
     * @param fileJson file di destinazione JSON.
     */
    public static void aggiungiRistorante(Ristorante nuovo, String fileJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            File file = new File(fileJson);
            JsonNode root;

            // Se il file esiste e non è vuoto, lo leggiamo
            if (file.exists() && file.length() > 0) {
                root = mapper.readTree(file);
            } else {
                root = mapper.createObjectNode();  // nuovo root se il file è vuoto
            }

            List<Ristorante> listaRistoranti = new ArrayList<>();

            if (root.has("ristoranti") && root.get("ristoranti").isArray()) {
                JsonNode ristorantiNode = root.get("ristoranti");

                listaRistoranti = mapper.readValue(
                        ristorantiNode.traverse(),
                        new TypeReference<List<Ristorante>>() {}
                );
            }

            // Aggiunta del nuovo ristorante
            listaRistoranti.add(nuovo);
            System.out.println("Ristorante '" + nuovo.getName() + "' aggiunto con successo.");

            // Nuovo root JSON con lista aggiornata
            ObjectNode nuovoRoot = mapper.createObjectNode();
            nuovoRoot.set("ristoranti", mapper.valueToTree(listaRistoranti));

            // Scrittura nel file
            mapper.writeValue(file, nuovoRoot);

        } catch (Exception e) {
            System.err.println("Errore durante l'aggiunta del ristorante:");
            e.printStackTrace();
        }
    }

    public static void removeRistorante(String fileRisto) throws IOException {
        ObjectMapper mapper = new ObjectMapper();    mapper.registerModule(new JavaTimeModule());
        JsonNode root = mapper.readTree(new File(fileRisto));
        JsonNode ristorantiNode = root.get("ristoranti");
        if (ristorantiNode == null || !ristorantiNode.isArray()) {
            System.out.println("Il campo 'ristoranti' non è presente o non è un array.");
            return;    }
        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);
        boolean removed = false;    Iterator<Ristorante> iterator = listaModificabile.iterator();
        while (iterator.hasNext()) {
            Ristorante r = iterator.next();
            if (r.getId() == idRist) {
                iterator.remove();
                removed = true;        }
        }
        if (removed) {        ObjectNode nuovoRoot = mapper.createObjectNode();
            nuovoRoot.set("ristoranti", mapper.valueToTree(listaModificabile));
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileRisto), nuovoRoot);
            System.out.println("File JSON aggiornato correttamente.");    } else {
            System.out.println("Nessuna modifica effettuata nel file.");    }
    }

    /**
     * Esegue una ricerca dei ristoranti in base ai filtri attivi.
     *
     * @return lista dei ristoranti corrispondenti.
     * @throws IOException se si verifica un errore nella lettura del file.
     */
    @FXML
    public List<Ristorante> cercaRist() throws IOException {
        String city = cityField.getText().trim();
        String cuisine = cuisineBox.getValue();
        String price = priceBox.getValue();
        String delivery = deliveryBox.getValue();
        String booking = bookingBox.getValue();
        boolean dev = delivery.equals("Delivery");             // voglio delivery
        boolean bok = booking.equals("Booking");               // voglio prenotazione online

        //CAMPI STATICI
        if(SessionManager.counter == 0){
            SessionManager.luogoNomeStatico = city;
            SessionManager.tipoCucinaStatico = cuisine;
            SessionManager.prezzoStatico = price;
            SessionManager.deliveryStatico = dev;
            SessionManager.bookingStatico = bok;
        }
        System.out.println("Cerca rist" + city + " " + cuisine + " " + price + " " + delivery + " " + booking);
        List<Ristorante> listaRist ;
        if(SessionManager.counter == 0){
            listaRist = cercaRistoranti("ristoranti.json", city, cuisine, price, dev, bok);
        }else {
            listaRist = cercaRistoranti("ristoranti.json", SessionManager.luogoNomeStatico, SessionManager.tipoCucinaStatico, SessionManager.prezzoStatico, SessionManager.deliveryStatico, SessionManager.bookingStatico);
        }
            if (!listaRist.isEmpty()) ordinaRist(listaRist);
            for (Ristorante r : listaRist) {
                System.out.println(r.getName());
                System.out.println(r.getMediaRec());
            }
            return listaRist;
        }

    /**
     * Cerca i ristoranti che corrispondono ai parametri passati.
     *
     * @param fileJson file contenente i ristoranti.
     * @param luogoOrName città o nome del ristorante.
     * @param tipoCucina tipo di cucina.
     * @param fasciaPrezzo fascia di prezzo.
     * @param delivery true se si desidera consegna a domicilio.
     * @param servizioOnline true se si desidera prenotazione online.
     * @return lista dei ristoranti filtrati.
     * @throws IOException in caso di errore nella lettura.
     */
    public List<Ristorante> cercaRistoranti(String fileJson, String luogoOrName, String tipoCucina, String fasciaPrezzo,
                                            boolean delivery, boolean servizioOnline) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        JsonNode root = mapper.readTree(new File(fileJson));
        JsonNode ristorantiNode = root.get("ristoranti");
        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        List<Ristorante> risultati = new ArrayList<>();

        double[] coordinateLuogo = null;
        if (luogoOrName != null && !luogoOrName.isBlank()) {
            coordinateLuogo = coordinate(luogoOrName);  // CHIAMATA UNA SOLA VOLTA!
        }

        for (Ristorante r : ristoranti) {
            System.out.println(delivery + " " + servizioOnline);
            if (luogoOrName != null && luogoOrName.equalsIgnoreCase(r.getName())) {
                risultati.clear();
                risultati.add(r);
                return risultati;
            }
            if (tipoCucina != null && !r.getCuisine().equalsIgnoreCase(tipoCucina)) continue;
            if (fasciaPrezzo != null && !r.getPrice().equalsIgnoreCase(fasciaPrezzo)) continue;
            if (delivery != r.isDelivery()) continue;
            if (servizioOnline != r.isBookingOnline()) continue;

            if (coordinateLuogo != null && coordinateLuogo[0] != 0 && coordinateLuogo[1] != 0) {
                double distanzaKm = calcolaDistanzaKm(coordinateLuogo[0], coordinateLuogo[1], r.getLatitude(), r.getLongitude());
                if (distanzaKm <= 100) {
                    risultati.add(r);
                }
            } else {
                risultati.add(r); // fallback se coordinate non trovate
            }
        }

        return risultati;
    }

    /**
     * Calcola la distanza in chilometri tra due coordinate geografiche.
     *
     * @param lat1 latitudine punto A.
     * @param lon1 longitudine punto A.
     * @param lat2 latitudine punto B.
     * @param lon2 longitudine punto B.
     * @return distanza in km.
     */
    public double calcolaDistanzaKm(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Raggio della terra in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    /**
     * Ottiene le coordinate (latitudine e longitudine) di un indirizzo testuale usando il servizio OpenStreetMap.
     *
     * @param indirizzo nome o descrizione del luogo.
     * @return array contenente latitudine e longitudine.
     */
    public static double[] coordinate(String indirizzo) {
        double[] coord = new double[2];
        try {
            String encodedAddress = URLEncoder.encode(indirizzo, "UTF-8");
            String urlStr = "https://nominatim.openstreetmap.org/search?q=" + encodedAddress + "&format=json&addressdetails=1";

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "JavaGeocoder/1.0");
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            JsonNode results = objectMapper.readTree(response.toString());

            if (results.isArray() && results.size() > 0) {
                JsonNode bestResult = null;
                for (JsonNode result : results) {
                    String type = result.has("type") ? result.get("type").asText() : "";
                    if (type.equals("city") || type.equals("town") || type.equals("village")) {
                        bestResult = result;
                        break;
                    }
                }

                if (bestResult == null) {
                    bestResult = results.get(0); // fallback
                }

                double lat = bestResult.get("lat").asDouble();
                double lon = bestResult.get("lon").asDouble();
                coord[0] = lat;
                coord[1] = lon;
            } else {
                System.out.println("Nessun risultato trovato.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return coord;
    }

}