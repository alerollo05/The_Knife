package com.example.the_knife.Ristoratore;

import com.example.the_knife.Utente.SessionManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Controller per la visualizzazione e modifica dei dettagli di un ristorante
 * gestito dal ristoratore attualmente loggato.
 *
 * <p>Questa classe estende {@link RistorantiRistController} e consente
 * all'utente di visualizzare le informazioni di un ristorante (nome, indirizzo,
 * città, tipo di cucina, telefono, email, URL, descrizione, prezzo, stelle, servizi)
 * e di modificarle attraverso dei pulsanti interattivi o toggle (radio button)
 * per opzioni boolean come delivery e booking online.</p>
 *
 * <p>Le modifiche vengono immediatamente salvate nel file <code>ristoranti.json</code>
 * tramite aggiornamento dell'oggetto JSON usando Jackson.</p>
 *
 *
 */
public class DettaglioRistController extends RistorantiRistController {


    /** Etichetta per il titolo della schermata. */
    @FXML
    private Label welcomeLabel;

    /** Griglia principale in cui vengono disposti dinamicamente i dati del ristorante. */
    @FXML
    private GridPane grid;

    /** Istanza della sessione utente corrente. */
    SessionManager session = SessionManager.getInstance();

    /** Username dell’utente loggato. */
    private final String user = session.getUsername();

    /** ID dell’utente loggato. */
    private final int id = session.getUserId();

    /** Ruolo dell’utente loggato (es. "ristoratore"). */
    private final String ruolo = session.getRuolo();

    /** ID del ristorante da visualizzare e modificare. */
    int idRist = SessionManager.idRist;

    /**
     * Metodo inizializzatore della vista. Carica i dettagli del ristorante selezionato
     * e li mostra nella griglia.
     *
     * @throws IOException se non è possibile leggere il file JSON contenente i dati del ristorante.
     */
    @FXML
    public void initialize() throws IOException {
        welcomeLabel.setText("DETTAGLI DEL RISTORANTE");
        System.out.println("Utente: " + user + " Id: " + id + " Ruolo: " + ruolo);
        System.out.println("Id ristorante dettagliato: " + idRist);
        printDettagliRist();
    }
    /**
     * Legge i dati del ristorante selezionato e popola dinamicamente la griglia
     * con etichette, valori e pulsanti per modificarli.
     *
     * @throws IOException se il file JSON non è leggibile o mancante.
     */
    private void printDettagliRist() throws IOException {
        try {
            grid.getChildren().clear(); //evita duplicati quando aggiorni il file con le modifiche
            grid.getColumnConstraints().clear(); //reset dei vincoli

            Ristorante rist = super.getRistoranteById("ristoranti.json", idRist);

            Label nomeLabel = new Label("Nome: ");
            nomeLabel.getStyleClass().add("textNormal");
            Label nome = new Label(rist.getName());
            nome.getStyleClass().add("textNormal");
            Button modifyname = new Button();
            modifyname.getStyleClass().add("accent-button");
            Image modifica = new Image(getClass().getResource("/com/example/the_knife/icone/modifica.png").toExternalForm());
            ImageView iconView = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
            iconView.setFitWidth(24);
            iconView.setFitHeight(24);//setto il ridimensionamento
            modifyname.setGraphic(iconView);
            iconView.setImage(modifica);
            modifyname.setOnAction(e -> {
                SessionManager.idScelta = 1;
                super.openPopup("Cambia nome");
            });

            Label indirizzoLabel = new Label("Indirizzo: ");
            indirizzoLabel.getStyleClass().add("textNormal");
            Label indirizzo = new Label(rist.getAddress());
            indirizzo.getStyleClass().add("textNormal");
            Button modifyadress = new Button();
            modifyadress.getStyleClass().add("accent-button");
            ImageView iconView2 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
            iconView2.setFitWidth(24);
            iconView2.setFitHeight(24);//setto il ridimensionamento
            modifyadress.setGraphic(iconView2);
            iconView2.setImage(modifica);
            modifyadress.setOnAction(e -> {
                SessionManager.idScelta = 2;
                super.openPopup("Cambia indirizzo");
            });

            Label cittaLabel = new Label("Citta: ");
            cittaLabel.getStyleClass().add("textNormal");
            Label citta = new Label(rist.getLocation());
            citta.getStyleClass().add("textNormal");
            Button modifyCity = new Button();
            modifyCity.getStyleClass().add("accent-button");
            ImageView iconView3 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
            iconView3.setFitWidth(24);
            iconView3.setFitHeight(24);//setto il ridimensionamento
            modifyCity.setGraphic(iconView3);
            iconView3.setImage(modifica);
            modifyCity.setOnAction(e -> {
                SessionManager.idScelta = 3;
                super.openPopup("Cambia città");
            });

            Label cucinaLabel = new Label("Cucina: ");
            cucinaLabel.getStyleClass().add("textNormal");
            Label cucina = new Label(rist.getCuisine());
            cucina.getStyleClass().add("textNormal");
            Button modifyCusine = new Button();
            modifyCusine.getStyleClass().add("accent-button");
            ImageView iconView4 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
            iconView4.setFitWidth(24);
            iconView4.setFitHeight(24);//setto il ridimensionamento
            modifyCusine.setGraphic(iconView4);
            iconView4.setImage(modifica);
            modifyCusine.setOnAction(e -> {
                SessionManager.idScelta = 4;
                super.openPopup("Cambia tipo cucina");
            });

            Label telefonoLabel = new Label("Telefono: ");
            telefonoLabel.getStyleClass().add("textNormal");
            Label telefono = new Label(rist.getPhoneNumber());
            telefono.getStyleClass().add("textNormal");
            Button modifyPhone = new Button();
            modifyPhone.getStyleClass().add("accent-button");
            ImageView iconView5 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
            iconView5.setFitWidth(24);
            iconView5.setFitHeight(24);//setto il ridimensionamento
            modifyPhone.setGraphic(iconView5);
            iconView5.setImage(modifica);
            modifyPhone.setOnAction(e -> {
                SessionManager.idScelta = 5;
                super.openPopup("Cambia numero di telefono");
            });

            Label emailLabel = new Label("Email: ");
            emailLabel.getStyleClass().add("textNormal");
            Label email = new Label(rist.getEmail());
            email.getStyleClass().add("textNormal");
            Button modifyEmail = new Button();
            modifyEmail.getStyleClass().add("accent-button");
            ImageView iconView6 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
            iconView6.setFitWidth(24);
            iconView6.setFitHeight(24);//setto il ridimensionamento
            modifyEmail.setGraphic(iconView6);
            iconView6.setImage(modifica);
            modifyEmail.setOnAction(e -> {
                SessionManager.idScelta = 6;
                super.openPopup("Cambia email");
            });

            Label urlLabel = new Label("Url: ");
            urlLabel.getStyleClass().add("textNormal");
            Label url = new Label(rist.getWebsiteUrl());
            url.getStyleClass().add("textNormal");
            Button modifyUrl = new Button();
            modifyUrl.getStyleClass().add("accent-button");
            ImageView iconView7 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
            iconView7.setFitWidth(24);
            iconView7.setFitHeight(24);//setto il ridimensionamento
            modifyUrl.setGraphic(iconView7);
            iconView7.setImage(modifica);
            modifyUrl.setOnAction(e -> {
                SessionManager.idScelta = 7;
                super.openPopup("Cambia Url");
            });

            Label descrizioneLabel = new Label("Descrizione: ");
            descrizioneLabel.getStyleClass().add("textNormal");
            Label descrizione = new Label(rist.getDescription());
            descrizione.getStyleClass().add("textNormal");
            Button modifyDescription = new Button();
            modifyDescription.getStyleClass().add("accent-button");
            ImageView iconView8 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
            iconView8.setFitWidth(24);
            iconView8.setFitHeight(24);//setto il ridimensionamento
            modifyDescription.setGraphic(iconView8);
            iconView8.setImage(modifica);
            modifyDescription.setOnAction(e -> {
                SessionManager.idScelta = 8;
                super.openPopup("Cambia descrizione");
            });

            Label prezzoLabel = new Label("Prezzo: ");
            prezzoLabel.getStyleClass().add("textNormal");
            Label prezzo = new Label(rist.getPrice());
            prezzo.getStyleClass().add("textNormal");
            Button modifyPrice = new Button();
            modifyPrice.getStyleClass().add("accent-button");
            ImageView iconView9 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
            iconView9.setFitWidth(24);
            iconView9.setFitHeight(24);//setto il ridimensionamento
            modifyPrice.setGraphic(iconView9);
            iconView9.setImage(modifica);
            modifyPrice.setOnAction(e -> {
                SessionManager.idScelta = 9;
                super.openPopup("Cambia prezzo");
            });

            Label numStelleLabel = new Label("Numero di stelle: ");
            numStelleLabel.getStyleClass().add("textNormal");
            Label stelle = new Label("" + rist.getGreenStar());
            stelle.getStyleClass().add("textNormal");
            Button modifyStars = new Button();
            modifyStars.getStyleClass().add("accent-button");
            ImageView iconView10 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
            iconView10.setFitWidth(24);
            iconView10.setFitHeight(24);//setto il ridimensionamento
            modifyStars.setGraphic(iconView10);
            iconView10.setImage(modifica);
            modifyStars.setOnAction(e -> {
                SessionManager.idScelta = 10;
                super.openPopup("Cambia numero di stelle");
            });

            Label ServiziLabel = new Label("Servizi: ");
            ServiziLabel.getStyleClass().add("textNormal");
            Label servizi = new Label(rist.getFacilitiesAndServices());
            servizi.getStyleClass().add("textNormal");
            Button modifyServices = new Button();
            modifyServices.getStyleClass().add("accent-button");
            ImageView iconView11 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
            iconView11.setFitWidth(24);
            iconView11.setFitHeight(24);//setto il ridimensionamento
            modifyServices.setGraphic(iconView11);
            iconView11.setImage(modifica);
            modifyServices.setOnAction(e -> {
                SessionManager.idScelta = 11;
                super.openPopup("Cambia servizi");
            });

            Label deliveryLabel = new Label("Delivery: ");
            deliveryLabel.getStyleClass().add("textNormal");

            //BOTTONI DI DELIVERY E BOOKING ONLINE

            ToggleGroup group1 = new ToggleGroup();

            RadioButton delivery = new RadioButton("Si");
            delivery.setToggleGroup(group1);
            delivery.setUserData("si");
            delivery.getStyleClass().add("radio-button");

            RadioButton delivery1 = new RadioButton("No");
            delivery1.setToggleGroup(group1);
            delivery1.setUserData("no");
            delivery1.getStyleClass().add("radio-button");

// Seleziona il valore corrente del ristorante
            if (rist.isDelivery()) {
                delivery.setSelected(true);
            } else {
                delivery1.setSelected(true);
            }

// Listener comune al gruppo delivery per modificare in tempo reale il valore di booking
            group1.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
                if (newToggle != null) {
                    String value = newToggle.getUserData().toString();
                    try {
                        aggiornaValori("delivery", value, "ristoranti.json");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });


            Label bookingOnlineLabel = new Label("Booking online: ");
            bookingOnlineLabel.getStyleClass().add("textNormal");

            ToggleGroup group2 = new ToggleGroup();

            RadioButton bookingOnline = new RadioButton("Si");
            bookingOnline.setToggleGroup(group2);
            bookingOnline.setUserData("si");
            bookingOnline.getStyleClass().add("radio-button");

            RadioButton bookingOnline1 = new RadioButton("No");
            bookingOnline1.setToggleGroup(group2);
            bookingOnline1.setUserData("no");
            bookingOnline1.getStyleClass().add("radio-button");

// Seleziona il valore corrente del ristorante
            if (rist.isBookingOnline()) {
                bookingOnline.setSelected(true);
            } else {
                bookingOnline1.setSelected(true);
            }

// Listener comune al gruppo booking per modificare in tempo reale il valore di booking
            group2.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
                if (newToggle != null) {
                    String value = newToggle.getUserData().toString();
                    try {
                        aggiornaValori("booking", value, "ristoranti.json");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });



            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(20);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(60);
            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPercentWidth(20);
            grid.getColumnConstraints().addAll(col1, col2, col3);


            grid.setPrefWidth(Double.MAX_VALUE);
            grid.setMaxWidth(Double.MAX_VALUE);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(15));
            grid.getStyleClass().add("list-rist");

            //forzatura dei label a crescere orizzontalmente
            GridPane.setFillWidth(nome, true);
            GridPane.setFillWidth(indirizzo, true);
            GridPane.setFillWidth(citta, true);
            GridPane.setFillWidth(cucina, true);
            GridPane.setFillWidth(telefono, true);
            GridPane.setFillWidth(email, true);
            GridPane.setFillWidth(url, true);
            GridPane.setFillWidth(descrizione, true);
            GridPane.setFillWidth(prezzo, true);
            GridPane.setFillWidth(stelle, true);
            GridPane.setFillWidth(servizi, true);


            grid.add(nomeLabel, 0, 0); grid.add(nome, 1, 0); grid.add(modifyname, 2, 0);
            grid.add(indirizzoLabel, 0, 1); grid.add(indirizzo, 1, 1); grid.add(modifyadress, 2, 1);
            grid.add(cittaLabel, 0, 2); grid.add(citta, 1, 2); grid.add(modifyCity, 2, 2);
            grid.add(cucinaLabel, 0, 3); grid.add(cucina, 1, 3); grid.add(modifyCusine, 2, 3);
            grid.add(telefonoLabel, 0, 4); grid.add(telefono, 1, 4); grid.add(modifyPhone, 2, 4);
            grid.add(emailLabel, 0, 5); grid.add(email, 1, 5); grid.add(modifyEmail, 2, 5);
            grid.add(urlLabel, 0, 6); grid.add(url, 1, 6); grid.add(modifyUrl, 2, 6);
            grid.add(descrizioneLabel, 0, 7); grid.add(descrizione, 1, 7); grid.add(modifyDescription, 2, 7);
            grid.add(prezzoLabel, 0, 8); grid.add(prezzo, 1, 8); grid.add(modifyPrice, 2, 8);
            grid.add(numStelleLabel, 0, 9); grid.add(stelle, 1, 9); grid.add(modifyStars, 2, 9);
            grid.add(ServiziLabel, 0, 10); grid.add(servizi, 1, 10); grid.add(modifyServices, 2, 10);
            grid.add(deliveryLabel, 0, 11); grid.add(delivery, 1, 11); grid.add(delivery1, 2, 11);
            grid.add(bookingOnlineLabel, 0, 12); grid.add(bookingOnline, 1, 12); grid.add(bookingOnline1, 2, 12);

        } catch (Exception e) {
            System.err.println("Errore nella lettura del file ristoranti.json");
            e.printStackTrace();
        }
    }

    /**
     * Aggiorna il valore booleano (delivery o bookingOnline) per il ristorante selezionato,
     * direttamente sul file JSON.
     *
     * @param campo     Il nome del campo da aggiornare: "delivery" o "booking".
     * @param scelta    Il nuovo valore (come stringa "si" o "no").
     * @param fileJson  Percorso del file JSON da aggiornare.
     * @throws IOException se avviene un errore durante la lettura o scrittura del file.
     */
    protected void aggiornaValori(String campo, String scelta, String fileJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        JsonNode root = mapper.readTree(new File(fileJson));
        JsonNode ristorantiNode = root.get("ristoranti");

        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        // Converte in lista modificabile
        List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);

        for(Ristorante r : listaModificabile){
            if(r.id == idRist) {
                System.out.println("Controllo Ristorante ID: " + r.id);
                if (campo.equalsIgnoreCase("delivery")) {
                    System.out.println("Trovato Ristorante");
                    if (scelta.equalsIgnoreCase("si") && r.delivery == false) {
                        r.delivery = true;
                        System.out.println("Modificato Ristorante");
                    } else if (scelta.equalsIgnoreCase("no") && r.delivery == true) {
                        r.delivery = false;
                    }
                }
                if (campo.equalsIgnoreCase("booking")) {
                    if (scelta.equalsIgnoreCase("si") && r.bookingOnline == false) {
                        r.bookingOnline = true;
                    } else if (scelta.equalsIgnoreCase("no") && r.bookingOnline == true) {
                        r.bookingOnline = false;
                    }
                }
            }
        }
        // Ricrea l'oggetto JSON aggiornato
        ObjectNode nuovoRoot = mapper.createObjectNode();
        nuovoRoot.set("ristoranti", mapper.valueToTree(listaModificabile));
        // Sovrascrive il file
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileJson), nuovoRoot);
    }
    /**
     * Gestisce l’azione del pulsante “Indietro”. Torna alla schermata dei ristoranti.
     *
     * @param event L'evento di click del pulsante.
     * @throws IOException se la schermata non può essere caricata.
     */
    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        SessionManager.idRist = null;
        super.goTo(event, "ristorantiRist.fxml");
    }
    /**
     * Chiude in modo sicuro il programma.
     *
     * @param event Evento del pulsante di chiusura.
     */
    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }
}
