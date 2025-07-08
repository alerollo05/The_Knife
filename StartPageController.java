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
 * Controller della schermata iniziale dell'applicazione.
 * Si occupa dell'inizializzazione dell'interfaccia, della gestione dei filtri e della ricerca dei ristoranti.
 */
public class StartPageController {
    /**
     * ListView per visualizzare i ristoranti disponibili.
     */
    @FXML
    private ListView<Ristorante> listaRistLabel;
    /** Campo per inserire la città desiderata. */
    @FXML private TextField cityField;
    /** ComboBox per selezionare il tipo di cucina. */
    @FXML private ComboBox<String> cuisineBox;
    /** ComboBox per selezionare la fascia di prezzo. */
    @FXML private ComboBox<String> priceBox;
    /** ComboBox per selezionare l'opzione delivery. */
    @FXML private ComboBox<String> deliveryBox;
    /** ComboBox per selezionare l'opzione di prenotazione. */
    @FXML private ComboBox<String> bookingBox;
    /** Bottone per avviare la ricerca. */
    @FXML private Button searchButton;

    /**
     * Metodo chiamato automaticamente all'avvio della schermata.
     * Inizializza i filtri, carica le immagini dei pulsanti e popola i dati iniziali.
     *
     * @throws IOException se avviene un errore nel caricamento dei file JSON.
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
     * Visualizza nella ListView i 10 ristoranti migliori letti da un file JSON,
     * con pulsanti per accedere ai dettagli e recensioni e un'opzione per gestire i preferiti.
     *
     * @param pagDettagli percorso alla pagina dettagli del ristorante
     * @param pagRecensioni percorso alla pagina recensioni del ristorante
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
     * Popola la {@link ListView} con i ristoranti trovati in base ai filtri applicati.
     * <p>
     * Ogni ristorante è visualizzato con nome, tipo di cucina, valutazione media,
     * e tre pulsanti: per accedere ai dettagli, visualizzare le recensioni e gestire i preferiti.
     * L'aspetto visivo è dinamico in base allo stato del ristorante e alla pagina corrente.
     * </p>
     *
     * @param pagDettagli    percorso alla pagina FXML dei dettagli del ristorante
     * @param pagRecensioni  percorso alla pagina FXML delle recensioni del ristorante
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
     * Aggiunge il ristorante attualmente selezionato ai preferiti dell'utente loggato.
     * <p>
     * Il metodo legge il file JSON specificato, individua l'utente attuale,
     * e aggiunge l'ID del ristorante (contenuto in {@link SessionManager#idRist})
     * alla lista dei preferiti, se non già presente. Il file viene poi sovrascritto
     * con i dati aggiornati.
     * </p>
     *
     * @param fileJson percorso del file JSON contenente l'elenco utenti
     * @throws IOException se si verificano errori nella lettura o scrittura del file
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
     * Rimuove il ristorante attualmente selezionato dai preferiti dell'utente loggato.
     * <p>
     * Il metodo legge il file JSON specificato, individua l'utente attuale tramite
     * {@link SessionManager#getInstance()}, e rimuove l'ID del ristorante selezionato
     * (contenuto in {@link SessionManager#idRist}) dalla lista dei preferiti, se presente.
     * Se la rimozione ha successo, il file viene aggiornato con i dati modificati.
     * </p>
     *
     * @param fileJson percorso del file JSON contenente gli utenti con i preferiti
     * @throws IOException se si verificano errori durante la lettura o scrittura del file
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
     * Restituisce la lista dei ristoranti associati a un determinato ristoratore.
     * <p>
     * Il metodo legge un file JSON contenente un array di ristoranti, filtra quelli
     * il cui {@code idRistoratore} corrisponde all'ID fornito, e li restituisce come lista.
     * </p>
     *
     * @param fileRisto percorso del file JSON contenente l'elenco dei ristoranti
     * @param id        identificativo del ristoratore di cui si vogliono i ristoranti
     * @return una lista di ristoranti gestiti dal ristoratore specificato
     * @throws IOException se si verifica un errore nella lettura del file
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
     * Restituisce la lista dei ristoranti contenuti in un file JSON.
     * <p>
     * Il metodo legge il file specificato, estrae il nodo {@code "ristoranti"},
     * lo deserializza in oggetti {@link Ristorante}, e restituisce la lista completa.
     * Questo metodo è tipicamente usato per recuperare i ristoranti migliori o filtrati precedentemente.
     * </p>
     *
     * @param fileRisto percorso del file JSON contenente l'elenco dei ristoranti
     * @return una lista di oggetti {@link Ristorante} letti dal file
     * @throws IOException se si verifica un errore nella lettura del file o nel parsing del JSON
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
     * Gestisce la transizione alla schermata di login dell'applicazione.
     * <p>
     * Questo metodo viene chiamato da un'azione dell'interfaccia utente (es. click su un bottone)
     * e carica il file FXML della login, impostando la scena corrente su quella nuova.
     * Imposta inoltre dimensioni fisse e disattiva la modalità massimizzata per la finestra.
     * </p>
     *
     * @param event l'evento generato dal componente che ha attivato l'azione
     * @throws IOException se si verifica un errore nel caricamento del file FXML
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
     * Cambia la scena attuale dell'applicazione caricando il file FXML specificato.
     * <p>
     * Questo metodo è generico e consente la navigazione verso qualsiasi pagina FXML,
     * impostando la nuova scena con dimensioni fisse e disabilitando il ridimensionamento della finestra.
     * </p>
     *
     * @param event    l'evento che ha originato la richiesta di cambio scena (es. click su un bottone)
     * @param location il percorso relativo del file FXML da caricare (es. {@code "/path/to/page.fxml"})
     * @throws IOException se il file FXML non può essere caricato correttamente
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
     * Chiude l'applicazione in modo immediato.
     * <p>
     * Questo metodo viene tipicamente invocato da un'azione dell'interfaccia utente,
     * come la pressione di un pulsante "Esci" o "Chiudi". Termina l'intera JVM con codice di uscita 0.
     * </p>
     *
     * @param event l'evento associato al componente UI che ha attivato la chiusura
     */
    @FXML
    public void closeProgram(ActionEvent event) {
        System.exit(0);
    }
    /**
     * Gestisce il click sul pulsante di ricerca.
     * <p>
     * Quando l'utente avvia una ricerca, questo metodo azzera il contatore dei risultati
     * memorizzato in {@link SessionManager#counter}, quindi richiama il metodo
     * {@code printListRist} per visualizzare i ristoranti filtrati sulla base dei criteri selezionati.
     * Utilizza pagine FXML specifiche per la visualizzazione dei dettagli e delle recensioni dei risultati della ricerca.
     * </p>
     */
    @FXML
    private void onSearchClicked() {
        SessionManager.counter = 0; // reset per applicare nuovi filtri
        printListRist("dettaglioRistoranteSearch.fxml","recensioneRistoranteSearch.fxml");
    }

    /**
     * Mostra i 10 ristoranti più votati da un file JSON sorgente e li salva in un altro file.
     *
     * @param fileJson il file JSON contenente i ristoranti totali
     * @param filetop10 il file dove salvare i top 10 ristoranti
     * @throws IOException se avviene un errore di lettura o scrittura
     */
    public void top10Ristoranti(String fileJson, String filetop10) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        // Leggi tutti i ristoranti
        JsonNode rootR = mapper.readTree(new File(fileJson));
        JsonNode tuttiRistNode = rootR.get("ristoranti");
        List<Ristorante> tuttiRist = Arrays.asList(mapper.treeToValue(tuttiRistNode, Ristorante[].class));

        // Ordina tutti i ristoranti
        ordinaRist(tuttiRist); // Assicurati che funzioni!

        // Prendi i primi 10
        List<Ristorante> top10 = tuttiRist.stream().limit(10).toList();

        // Scrivi nel nuovo file
        ObjectNode nuovoRoot = mapper.createObjectNode();
        nuovoRoot.set("ristoranti", mapper.valueToTree(top10));
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filetop10), nuovoRoot);
    }

    /**
     * Ordina la lista di ristoranti in base a un criterio definito dal metodo {@code quickSort}.
     * <p>
     * Il criterio di ordinamento dipende dall'implementazione di {@code quickSort},
     * che può basarsi su valutazione, nome, prezzo o altri attributi.
     * L'ordinamento avviene in-place sulla lista passata come parametro.
     * </p>
     *
     * @param risultati la lista di ristoranti da ordinare
     */
    public void ordinaRist(List<Ristorante> risultati) {
        quickSort(risultati, 0, risultati.size() - 1);
    }

    /**
     * Ordina ricorsivamente una lista di ristoranti utilizzando l'algoritmo QuickSort.
     * <p>
     * L'ordinamento viene effettuato in-place tra gli indici {@code low} e {@code high}.
     * Il criterio di confronto utilizzato è definito nel metodo {@code partition}, tipicamente basato
     * su una proprietà del ristorante come la valutazione media.
     * </p>
     *
     * @param lista la lista di ristoranti da ordinare
     * @param low   indice iniziale del sottoarray da ordinare
     * @param high  indice finale del sottoarray da ordinare
     */
    private void quickSort(List<Ristorante> lista, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(lista, low, high);
            quickSort(lista, low, pivotIndex - 1);
            quickSort(lista, pivotIndex + 1, high);
        }
    }
    /**
     * Partiziona la lista di ristoranti per l'algoritmo QuickSort secondo un criterio di ordinamento decrescente.
     * <p>
     * Il criterio di confronto è il seguente:
     * <ul>
     *   <li>Priorità più alta alla {@code media recensioni} ({@code getMediaRec()})</li>
     *   <li>In caso di parità, viene usato il numero di recensioni ({@code getNumRec()}) come discriminante secondario</li>
     * </ul>
     * Entrambi i confronti sono in ordine decrescente. Alla fine del metodo, tutti gli elementi con priorità maggiore
     * o uguale al pivot si troveranno prima del pivot nella lista.
     * </p>
     *
     * @param lista la lista di ristoranti da partizionare
     * @param low   indice iniziale del sottoarray
     * @param high  indice finale del sottoarray (che rappresenta il pivot)
     * @return la posizione finale del pivot dopo la partizione
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
     * Aggiunge un nuovo ristorante alla lista memorizzata in un file JSON.
     * <p>
     * Il metodo gestisce sia il caso in cui il file esista ed abbia già dati,
     * sia quello in cui il file sia vuoto o non esista ancora. Il nuovo ristorante
     * viene aggiunto all'array JSON sotto il campo {@code "ristoranti"} e il file viene
     * sovrascritto con la versione aggiornata.
     * </p>
     *
     * @param nuovo    il ristorante da aggiungere alla lista
     * @param fileJson il percorso del file JSON contenente la lista di ristoranti
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
    /**
     * Rimuove dal file JSON il ristorante il cui ID corrisponde a {@link SessionManager#idRist}.
     * <p>
     * Il metodo legge un file JSON che contiene un array di ristoranti nel campo {@code "ristoranti"},
     * cerca il ristorante con l'ID corrispondente all'ID memorizzato in {@link SessionManager#idRist},
     * lo rimuove se presente, e riscrive il file con la lista aggiornata.
     * Se il campo {@code "ristoranti"} è mancante o non è un array, il metodo non effettua alcuna operazione.
     * </p>
     *
     * @param fileRisto il percorso del file JSON contenente la lista dei ristoranti
     * @throws IOException se si verifica un errore durante la lettura o scrittura del file
     */
    public static void removeRistorante(String fileRisto) throws IOException {
        ObjectMapper mapper = new ObjectMapper();    mapper.registerModule(new JavaTimeModule());
        JsonNode root = mapper.readTree(new File(fileRisto));
        JsonNode ristorantiNode = root.get("ristoranti");
        if (ristorantiNode == null || !ristorantiNode.isArray()) {
            System.out.println("Il campo 'ristoranti' non è presente o non è un array.");
            return;
        }
        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);
        boolean removed = false;    Iterator<Ristorante> iterator = listaModificabile.iterator();
        while (iterator.hasNext()) {
            Ristorante r = iterator.next();
            if (r.getId() == idRist) {
                iterator.remove();
                removed = true;
            }
        }
        if (removed) {
            ObjectNode nuovoRoot = mapper.createObjectNode();
            nuovoRoot.set("ristoranti", mapper.valueToTree(listaModificabile));
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileRisto), nuovoRoot);
            System.out.println("File JSON aggiornato correttamente.");
        } else {
            System.out.println("Nessuna modifica effettuata nel file.");
        }
    }

    /**
     * Esegue la ricerca dei ristoranti in base ai filtri selezionati dall'utente nell'interfaccia grafica.
     * <p>
     * I filtri includono: città, tipo di cucina, fascia di prezzo, disponibilità del servizio di delivery
     * e possibilità di prenotazione online. Se è la prima ricerca effettuata nella sessione
     * ({@code SessionManager.counter == 0}), i filtri correnti vengono salvati in variabili statiche
     * per essere riutilizzati in ricerche successive. I risultati vengono ordinati secondo un criterio
     * definito nel metodo {@code ordinaRist} (tipicamente per valutazione).
     * </p>
     *
     * @return una lista di ristoranti che soddisfano i criteri di ricerca selezionati
     * @throws IOException se si verifica un errore nella lettura del file JSON dei ristoranti
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
     * Filtra e restituisce una lista di ristoranti che soddisfano i criteri di ricerca specificati.
     *
     * <p>Il metodo legge i ristoranti da un file JSON e applica una serie di filtri:</p>
     * <ul>
     *     <li>Ricerca esatta per nome del ristorante (prioritaria)</li>
     *     <li>Tipo di cucina</li>
     *     <li>Fascia di prezzo</li>
     *     <li>Disponibilità del servizio di delivery</li>
     *     <li>Disponibilità del servizio di prenotazione online</li>
     *     <li>Prossimità geografica entro 100 km, se è stato fornito un luogo valido</li>
     * </ul>
     *
     * <p>
     * Se il campo {@code luogoOrName} corrisponde esattamente al nome di un ristorante,
     * il metodo restituisce immediatamente solo quel risultato.
     * </p>
     *
     * @param fileJson       percorso al file JSON contenente la lista dei ristoranti
     * @param luogoOrName    nome del ristorante da cercare o città per la geolocalizzazione
     * @param tipoCucina     tipo di cucina richiesto (es. "Italiana", "Cinese"), {@code null} per ignorare
     * @param fasciaPrezzo   fascia di prezzo (es. "€", "€€€"), {@code null} per ignorare
     * @param delivery       {@code true} se si desiderano solo ristoranti con servizio delivery
     * @param servizioOnline {@code true} se si desiderano solo ristoranti prenotabili online
     * @return una lista di ristoranti che soddisfano i criteri specificati
     * @throws IOException se si verifica un errore nella lettura del file JSON
     */
    public List<Ristorante> cercaRistoranti(String fileJson, String luogoOrName, String tipoCucina,
                                            String fasciaPrezzo, boolean delivery, boolean servizioOnline) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        JsonNode root = mapper.readTree(new File(fileJson));
        JsonNode ristorantiNode = root.get("ristoranti");
        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        List<Ristorante> risultati = new ArrayList<>();

        boolean haLuogo = luogoOrName != null && !luogoOrName.isBlank();
        double[] coordinateLuogo = haLuogo ? coordinate(luogoOrName) : null;

        for (Ristorante r : ristoranti) {
            // Match per nome esatto → priorità massima, restituisco solo quello
            if (haLuogo && luogoOrName.equalsIgnoreCase(r.getName())) {
                return List.of(r);
            }

            // Filtri base
            if (tipoCucina != null && !r.getCuisine().equalsIgnoreCase(tipoCucina)) continue;
            if (fasciaPrezzo != null && !r.getPrice().equalsIgnoreCase(fasciaPrezzo)) continue;
            if (r.isDelivery() != delivery) continue;
            if (r.isBookingOnline() != servizioOnline) continue;

            // Filtro per distanza (se coordinate valide)
            if (coordinateLuogo != null && coordinateLuogo[0] != 0 && coordinateLuogo[1] != 0) {
                double distanzaKm = calcolaDistanzaKm(coordinateLuogo[0], coordinateLuogo[1], r.getLatitude(), r.getLongitude());
                if (distanzaKm <= 100) {
                    risultati.add(r);
                }
            } else {
                risultati.add(r); // fallback se coordinate non disponibili
            }
        }

        return risultati;
    }



    /**
     * Calcola la distanza in chilometri tra due punti geografici sulla superficie terrestre
     * usando la formula dell'Haversine.
     * <p>
     * I punti sono definiti tramite latitudine e longitudine in gradi decimali.
     * Il risultato rappresenta la distanza approssimata tra i due punti seguendo
     * la superficie terrestre (geodetica).
     * </p>
     *
     * @param lat1 latitudine del primo punto (in gradi)
     * @param lon1 longitudine del primo punto (in gradi)
     * @param lat2 latitudine del secondo punto (in gradi)
     * @param lon2 longitudine del secondo punto (in gradi)
     * @return la distanza approssimativa in chilometri tra i due punti
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
     * Ottiene le coordinate geografiche (latitudine e longitudine) di un indirizzo testuale
     * utilizzando il servizio di geocodifica di OpenStreetMap (Nominatim).
     * <p>
     * L'indirizzo viene codificato e passato a un endpoint HTTP. Se la risposta JSON contiene
     * risultati validi, il metodo restituisce un array contenente la latitudine e la longitudine
     * del risultato più rilevante (priorità: città, paese, villaggio).
     * In caso di errore o risultato assente, l'array restituito conterrà valori pari a 0.
     * </p>
     *
     * @param indirizzo l'indirizzo o nome della città da convertire in coordinate geografiche
     * @return un array di double dove {@code [0]} è la latitudine e {@code [1]} è la longitudine
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