package com.example.the_knife;

import com.example.the_knife.Exceptions.MaxNumRecensioniException;
import com.example.the_knife.Exceptions.MioRistoException;
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
import java.util.Iterator;
import java.util.List;

import static com.example.the_knife.Utente.SessionManager.idRist;
/**
 * Controller della schermata di visualizzazione delle recensioni dei ristoranti nella modalità di ricerca.
 * <p>
 * Estende {@link RecensioniRistController} e gestisce l'inserimento, modifica, rimozione e visualizzazione
 * delle recensioni utente, oltre alla gestione dinamica della griglia per l'interfaccia grafica.
 * </p>
 */
public class RecensioneRistoranteSearchController extends RecensioniRistController {

    /** Etichetta di benvenuto */
    @FXML
    private Label welcomeLabel;
    /** Lista delle recensioni visibili nell'interfaccia */
    @FXML
    private ListView<Recensione> listaRecLabel;
    /** Bottone per inviare una nuova recensione */
    @FXML
    private Button ButtonRecensione;
    /** Campo di input per il commento */
    @FXML
    private TextField CommentoFiled;
    /** Campo di input per il rating */
    @FXML
    private TextField RatingField;
    /** Griglia che contiene i campi per l'inserimento/modifica recensione */
    @FXML
    private GridPane grigliaRec;
    /** Istanza singleton della sessione utente corrente. */
    SessionManager session = SessionManager.getInstance();
    /** Username dell'utente attualmente loggato, ottenuto dalla sessione. */
    private final String user = session.getUsername();
    /** ID univoco dell'utente attualmente loggato. */
    private final int id = session.getUserId();
    /** Ruolo dell'utente loggato (es. "cliente", "ristoratore"). */
    private final String ruolo = session.getRuolo();

    /**
     * Inizializza la schermata di gestione delle recensioni.
     * <p>
     * Questo metodo viene automaticamente chiamato da JavaFX al caricamento del controller.
     * In base al valore di {@link SessionManager#pagina}, determina se l'utente ha i permessi per
     * scrivere recensioni. Se sì, imposta il comportamento del pulsante per l'invio di una nuova recensione.
     * </p>
     *
     * <ul>
     *   <li>Valida l'input dell'utente (commento e valutazione)</li>
     *   <li>Crea una nuova istanza di {@link Recensione}</li>
     *   <li>Aggiunge la recensione al file JSON dei ristoranti</li>
     *   <li>Aggiorna la classifica top10 e ricarica la lista</li>
     * </ul>
     *
     * <p>
     * Se l’utente non ha i permessi, la griglia di input viene nascosta.
     * </p>
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
        }else{
            grigliaRec.setVisible(false);
            grigliaRec.setManaged(false);
        }

        printListRec();
    }
    /**
     * <p>Questo metodo:</p>
     * <ul>
     *   <li>Recupera tutte le recensioni associate al ristorante attualmente selezionato tramite {@code visualizzaRecensioniPerNomeRistorante}</li>
     *   <li>Popola la {@link ListView} {@code listaRecLabel} con queste recensioni</li>
     *   <li>Imposta un {@code CellFactory} personalizzato per visualizzare ogni recensione in una griglia formattata</li>
     *   <li>Se l'utente visualizzatore è l'autore della recensione, abilita pulsanti per modificarla o eliminarla</li>
     * </ul>
     * <p>La lista viene aggiornata dinamicamente in seguito alla modifica o rimozione di una recensione.</p>
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

                    grid.getChildren().clear(); //evita duplicati quando aggiorni il file con le modifiche
                    grid.getColumnConstraints().clear(); //reset dei vincoli

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
                            ImageView iconView = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
                            iconView.setFitWidth(24);
                            iconView.setFitHeight(24);//setto il ridimensionamento
                            modifica.setGraphic(iconView);
                            iconView.setImage(icona);
                            modifica.setOnAction(e -> {
                               openPopup("Modifica recensione");
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
     * Gestisce la navigazione alla schermata precedente in base al contesto dell'utente corrente.
     * <p>
     * Questo metodo determina quale schermata caricare in base ai valori correnti di
     * {@code SessionManager.pagina} e {@code SessionManager.menu}.
     * </p>
     *
     * <ul>
     *   <li>Se {@code pagina == 0}, torna alla schermata iniziale {@code startPage.fxml}</li>
     *   <li>Se {@code pagina == 2} (utente cliente):
     *     <ul>
     *       <li>Se {@code menu == 1}, naviga a {@code ristorantiClient.fxml}</li>
     *       <li>Altrimenti, naviga a {@code dashBoardClient.fxml}</li>
     *     </ul>
     *   </li>
     *   <li>Se {@code pagina == 1} (utente ristoratore):
     *     <ul>
     *       <li>Se {@code menu == 1}, naviga a {@code preferitiRist.fxml}</li>
     *       <li>Altrimenti, naviga a {@code dashBoardRist.fxml}</li>
     *     </ul>
     *   </li>
     * </ul>
     *
     * <p>
     * In caso di ritorno dalla pagina di ricerca, il contatore viene resettato per rimuovere i filtri.
     * </p>
     *
     * @param event l'evento generato dall'interazione dell'utente (es. click su un bottone)
     * @throws IOException se si verifica un errore nel caricamento della nuova schermata
     */
    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        if(SessionManager.pagina == 0) {
            if(SessionManager.counter != 1) SessionManager.counter = 0;
            super.goTo(event, "startPage.fxml");
        }else if(SessionManager.pagina == 2) {
            if(SessionManager.menu == 1) {
                super.goTo(event, "Cliente/ristorantiClient.fxml");
            }else {
                super.goTo(event, "Cliente/dashBoardClient.fxml");
            }
        }else if(SessionManager.pagina == 1) {
            if(SessionManager.menu == 1) {
                super.goTo(event, "Ristoratore/preferitiRist.fxml");
            }else {
                super.goTo(event, "Ristoratore/dashBoardRist.fxml");
            }
        }
    }
    /**
     * Aggiunge una nuova recensione a un ristorante specificato nel file JSON.
     *
     * <p>Il metodo legge il file contenente la lista dei ristoranti, cerca il ristorante con ID
     * corrispondente a {@code SessionManager.idRist} e aggiunge la recensione {@code newRec}.</p>
     *
     * <p>Prima di aggiungere la recensione, vengono eseguiti controlli per garantire che:</p>
     * <ul>
     *   <li>L'utente non stia cercando di recensire il proprio ristorante
     *       (lancio di {@link MioRistoException})</li>
     *   <li>L'utente non abbia già recensito lo stesso ristorante
     *       (lancio di {@link MaxNumRecensioniException})</li>
     * </ul>
     *
     * <p>Dopo l'aggiunta, aggiorna anche il numero di recensioni del ristorante e riscrive il file JSON.
     * Infine, richiama il metodo {@code aggiornaMediaRecensioni()} per ricalcolare la media.</p>
     *
     * @param newRec   la nuova recensione da aggiungere
     * @param fileJson il percorso del file JSON contenente i dati dei ristoranti
     * @throws IOException                  se si verifica un errore durante la lettura o scrittura del file JSON
     * @throws MioRistoException            se l'utente prova a recensire un ristorante di sua proprietà
     * @throws MaxNumRecensioniException    se l'utente ha già recensito quel ristorante
     */
    public void aggiungiRecensione(Recensione newRec, String fileJson) throws IOException {


        ObjectMapper mapper = new ObjectMapper();// Crea un'istanza di ObjectMapper
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        File file = new File(fileJson);

        JsonNode root = mapper.readTree(file);
        JsonNode ristorantiNode = root.get("ristoranti");

        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);

        for (Ristorante r : listaModificabile) {
            if (r.getId() == idRist) {
                if(SessionManager.getInstance().getUserId() == r.getIdRistoratore()){
                    handleInput("Errore","Non puoi recensire il tuo ristorante.");
                    throw new MioRistoException("Non puoi recensire il tuo ristorante.");
                }
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
     * Genera un nuovo identificatore univoco per una recensione da associare a un ristorante specifico.
     * <p>
     * Il metodo legge il file JSON contenente l'elenco dei ristoranti, individua il ristorante con ID
     * corrispondente a {@code SessionManager.idRist}, e calcola un nuovo ID basato sul numero di recensioni
     * già presenti in quel ristorante.
     * </p>
     * <p>
     * Il nuovo ID è determinato come: <br>
     * <code>numeroRecensioniEsistenti + 2</code><br>
     * Questo perché il valore di partenza viene inizializzato a 0, incrementato di 1 durante il ciclo, e
     * successivamente viene restituito come {@code count + 1}.
     * </p>
     * <p>
     * In caso di errori durante la lettura del file o parsing JSON, l'eccezione viene stampata su console
     * e viene restituito comunque un ID valido (di default 1).
     * </p>
     *
     * @param fileJson il percorso al file JSON contenente i dati dei ristoranti
     * @return un intero che rappresenta il prossimo ID disponibile per una recensione del ristorante corrente
     */
    public static int generaIdRec(String fileJson) {

        int count = 0;

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
            JsonNode root = mapper.readTree(new File(fileJson));
            JsonNode ristorantiNode = root.get("ristoranti");

            List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));// Deserializza in List<Ristorante>
            List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);// Converte in lista modificabile

            for (Ristorante r : listaModificabile) {
                if (r.getId() == idRist) {
                    count = r.getRecensioni().size() +1;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count+1;
    }

    /**
     * Rimuove la recensione dell'utente attualmente loggato da un ristorante specifico,
     * identificato tramite {@code SessionManager.idRist}, presente nel file JSON specificato.
     * <p>
     * Il metodo legge il contenuto del file JSON contenente la lista dei ristoranti,
     * individua il ristorante di interesse e cerca la recensione il cui autore corrisponde
     * all'utente attualmente connesso. Se trovata, la recensione viene rimossa e il numero
     * totale di recensioni del ristorante viene decrementato.
     * </p>
     * <p>
     * Dopo la rimozione, il file viene aggiornato con la nuova struttura dati e viene
     * ricalcolata la media delle recensioni tramite {@link #aggiornaMediaRecensioni(String)}.
     * </p>
     *
     * @param fileJson il percorso al file JSON contenente i dati dei ristoranti
     * @throws IOException se si verifica un errore durante la lettura o scrittura del file JSON
     */
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
                        r.setNumRec(r.getNumRec() - 1);
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

        aggiornaMediaRecensioni(fileJson);
    }
    /**
     * Aggiorna la media delle valutazioni per il ristorante attualmente selezionato,
     * identificato tramite {@link SessionManager#idRist}, all'interno del file JSON specificato.
     *
     * <p>Il metodo esegue i seguenti passaggi:</p>
     * <ul>
     *   <li>Legge il file JSON contenente la lista dei ristoranti</li>
     *   <li>Individua il ristorante corrispondente all'ID salvato nel {@code SessionManager}</li>
     *   <li>Calcola la media aritmetica delle valutazioni delle recensioni associate</li>
     *   <li>Aggiorna il campo {@code mediaRec} del ristorante</li>
     *   <li>Scrive l'intera struttura aggiornata nel file</li>
     * </ul>
     *
     * <p>Se il ristorante non ha recensioni, la media sarà impostata a {@code 0.0}.</p>
     *
     * @param fileJson il percorso del file JSON contenente l'elenco dei ristoranti e le loro recensioni
     * @throws IOException se si verifica un errore nella lettura o scrittura del file JSON
     */
    public void aggiornaMediaRecensioni(String fileJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        JsonNode root = mapper.readTree(new File(fileJson));
        JsonNode ristorantiNode = root.get("ristoranti");

        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);

        for (Ristorante r : listaModificabile) {
            if (r.getId() == idRist) {
                if (r.getRecensioni() != null && !r.getRecensioni().isEmpty()) {
                    double somma = 0;
                    for (Recensione rec : r.getRecensioni()) {
                        somma += rec.rating;
                    }
                    System.out.println(somma);
                    System.out.println("size : "+ r.getRecensioni().size());
                    double media = somma / r.getRecensioni().size() ;
                    media = Math.round(media * 10.0) / 10.0;
                    r.setMediaRec(media);
                    System.out.println("media : " + media);
                } else {
                    r.setMediaRec(0.0);
                }
                break; // trovato il ristorante, non serve continuare
            }
        }

        ObjectNode nuovoRoot = mapper.createObjectNode();
        nuovoRoot.set("ristoranti", mapper.valueToTree(listaModificabile));
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileJson), nuovoRoot);
    }
    /**
     * Modifica una recensione esistente per il ristorante identificato da {@link SessionManager#idRist},
     * se l'autore della recensione corrisponde all'utente attualmente loggato.
     * <p>
     * Il metodo aggiorna il commento, il rating e la data della recensione.
     * Dopo la modifica, aggiorna anche la media delle recensioni del ristorante e
     * riscrive l'intero file JSON aggiornato.
     * </p>
     *
     * @param commento  il nuovo testo della recensione
     * @param rating    la nuova valutazione (punteggio) assegnata al ristorante
     * @param fileJson  il percorso del file JSON contenente la lista dei ristoranti e delle recensioni
     * @throws IOException se si verifica un errore durante la lettura o scrittura del file JSON
     */
    public void modificaRecensioni(String commento, int rating, String fileJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        JsonNode root = mapper.readTree(new File(fileJson));
        JsonNode ristorantiNode = root.get("ristoranti");
        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);

        boolean trovato = false;
        System.out.println("utente : "+user);
        for (Ristorante r : listaModificabile) {
            if (r.getId() == idRist) {
                for(Recensione rec : r.getRecensioni()) {
                    if(rec.getAuthor().equals(user)) {
                        rec.setComment(commento);
                        rec.setRating(rating);
                        rec.setDate(LocalDate.now());
                        trovato = true;
                        break;
                    }
                }
            }
        }

        aggiornaMediaRecensioni(fileJson);
        if (!trovato) {
            System.out.println("Recensione non trovata o ID ristorante errato.");
        }

        ObjectNode nuovoRoot = mapper.createObjectNode();
        nuovoRoot.set("ristoranti", mapper.valueToTree(listaModificabile));
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileJson), nuovoRoot);
    }
    /**
     * Chiude l'applicazione terminando il processo corrente.
     * <p>
     * Questo metodo sovrascrive quello della superclasse per permettere eventuali personalizzazioni
     * specifiche in questa classe. Attualmente, delega completamente l'implementazione alla superclasse.
     * </p>
     *
     * @param event l'evento generato dall'interazione dell'utente (es. click su un bottone "Chiudi")
     */
    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }
    /**
     * Apre una finestra di popup modale per la modifica di una recensione.
     * <p>
     * Carica il file FXML {@code popUpRecSearch.fxml}, imposta il controller e configura la finestra
     * come modale per bloccare l'interazione con la finestra principale finché il popup non viene chiuso.
     * Il popup consente all'utente di modificare una recensione esistente.
     * </p>
     *
     * @param title Il titolo da visualizzare nella barra del popup.
     */
    public void openPopup(String title) { //aggiungere variabile stringa nei parametri passati per percorso file .fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("popUpRecSearch.fxml"));
            Parent root = loader.load();
            Stage popupStage = new Stage();

            //SERVE A AGGIORNARE I DATI QUANDO CHIUDO LA FINESTRA DI POPUP
            PopUpRecSearchController controller = loader.getController();
            controller.setMainController((RecensioneRistoranteSearchController) this);

            popupStage.setTitle(title);
            popupStage.initModality(Modality.APPLICATION_MODAL); // Blocca la finestra principale
            popupStage.setScene(new Scene(root,600,250));
            popupStage.setResizable(false); // blocca il ridimensionamento
            popupStage.showAndWait(); // Mostra la finestra e attende la chiusura
        }catch (IOException e){ System.err.println("Errore nel caricamento del file FXML:");
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Riferimento al controller principale della vista corrente.
     * <p>
     * Utilizzato dal popup di modifica recensione per aggiornare dinamicamente
     * l'interfaccia principale una volta completata l'operazione.
     * </p>
     */
    private RecensioneRistoranteSearchController mainController;
    /**
     * Imposta il controller principale per consentire l'interazione tra il popup e la vista principale.
     * <p>
     * Questo metodo viene tipicamente chiamato dal popup per ottenere un riferimento
     * alla classe chiamante, in modo da poter aggiornare l'interfaccia utente principale
     * (ad esempio, aggiornare la lista delle recensioni dopo una modifica).
     * </p>
     *
     * @param controller Il controller principale {@code RecensioneRistoranteSearchController} da associare.
     */
    public void setMainController(RecensioneRistoranteSearchController controller) {
        this.mainController = controller;
    }

}
