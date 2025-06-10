package com.example.the_knife.Ristoratore;

import com.example.the_knife.Utente.SessionManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DettaglioRistController extends RistorantiRistController {


    @FXML
    private Label welcomeLabel;

    @FXML
    private GridPane grid;

    SessionManager session = SessionManager.getInstance();
    private final String user = session.getUsername();
    private final int id = session.getUserId();
    private final String ruolo = session.getRuolo();
    int idRist = SessionManager.idRist;

    @FXML
    public void initialize() throws IOException {
        welcomeLabel.setText("DETTAGLI DEL RISTORANTE " + user);
        System.out.println("Utente: " + user + " Id: " + id + " Ruolo: " + ruolo);
        System.out.println("Id ristorante dettagliato: " + idRist);
        printDettagliRist();
    }

    private void printDettagliRist() throws IOException {
        try {
            grid.getChildren().clear(); //evita duplicati quando aggiorni il file con le modifiche
            grid.getColumnConstraints().clear(); //reset dei vincoli

            Ristorante rist = super.getRistoranteById("ristoranti.json", idRist);

            Label nomeLabel = new Label("Nome: ");
            nomeLabel.getStyleClass().add("textNormal");
            Label nome = new Label(rist.getName());
            nome.getStyleClass().add("textNormal");
            Button modifyname = new Button("Modifica");
            modifyname.getStyleClass().add("accent-button");
            modifyname.setOnAction(e -> {
                SessionManager.idScelta = 1;
                super.openPopup("Cambia nome");
            });

            Label indirizzoLabel = new Label("Indirizzo: ");
            indirizzoLabel.getStyleClass().add("textNormal");
            Label indirizzo = new Label(rist.getAddress());
            indirizzo.getStyleClass().add("textNormal");
            Button modifyadress = new Button("Modifica");
            modifyadress.getStyleClass().add("accent-button");
            modifyadress.setOnAction(e -> {
                SessionManager.idScelta = 2;
                super.openPopup("Cambia indirizzo");
            });

            Label cittaLabel = new Label("Citta: ");
            cittaLabel.getStyleClass().add("textNormal");
            Label citta = new Label(rist.getLocation());
            citta.getStyleClass().add("textNormal");
            Button modifyCity = new Button("Modifica");
            modifyCity.getStyleClass().add("accent-button");
            modifyCity.setOnAction(e -> {
                SessionManager.idScelta = 3;
                super.openPopup("Cambia città");
            });

            Label cucinaLabel = new Label("Cucina: ");
            cucinaLabel.getStyleClass().add("textNormal");
            Label cucina = new Label(rist.getCuisine());
            cucina.getStyleClass().add("textNormal");
            Button modifyCusine = new Button("Modifica");
            modifyCusine.getStyleClass().add("accent-button");
            modifyCusine.setOnAction(e -> {
                SessionManager.idScelta = 4;
                super.openPopup("Cambia tipo cucina");
            });

            Label telefonoLabel = new Label("Telefono: ");
            telefonoLabel.getStyleClass().add("textNormal");
            Label telefono = new Label(rist.getPhoneNumber());
            telefono.getStyleClass().add("textNormal");
            Button modifyPhone = new Button("Modifica");
            modifyPhone.getStyleClass().add("accent-button");
            modifyPhone.setOnAction(e -> {
                SessionManager.idScelta = 5;
                super.openPopup("Cambia numero di telefono");
            });

            Label emailLabel = new Label("Email: ");
            emailLabel.getStyleClass().add("textNormal");
            Label email = new Label(rist.getEmail());
            email.getStyleClass().add("textNormal");
            Button modifyEmail = new Button("Modifica");
            modifyEmail.getStyleClass().add("accent-button");
            modifyEmail.setOnAction(e -> {
                SessionManager.idScelta = 6;
                super.openPopup("Cambia email");
            });

            Label urlLabel = new Label("Url: ");
            urlLabel.getStyleClass().add("textNormal");
            Label url = new Label(rist.getWebsiteUrl());
            url.getStyleClass().add("textNormal");
            Button modifyUrl = new Button("Modifica");
            modifyUrl.getStyleClass().add("accent-button");
            modifyUrl.setOnAction(e -> {
                SessionManager.idScelta = 7;
                super.openPopup("Cambia Url");
            });

            Label descrizioneLabel = new Label("Descrizione: ");
            descrizioneLabel.getStyleClass().add("textNormal");
            Label descrizione = new Label(rist.getDescription());
            descrizione.getStyleClass().add("textNormal");
            Button modifyDescription = new Button("Modifica");
            modifyDescription.getStyleClass().add("accent-button");
            modifyDescription.setOnAction(e -> {
                SessionManager.idScelta = 8;
                super.openPopup("Cambia descrizione");
            });

            Label prezzoLabel = new Label("Prezzo: ");
            prezzoLabel.getStyleClass().add("textNormal");
            Label prezzo = new Label(rist.getPrice());
            prezzo.getStyleClass().add("textNormal");
            Button modifyPrice = new Button("Modifica");
            modifyPrice.getStyleClass().add("accent-button");
            modifyPrice.setOnAction(e -> {
                SessionManager.idScelta = 9;
                super.openPopup("Cambia prezzo");
            });

            Label numStelleLabel = new Label("Numero di stelle: ");
            numStelleLabel.getStyleClass().add("textNormal");
            Label stelle = new Label("" + rist.getGreenStar());
            stelle.getStyleClass().add("textNormal");
            Button modifyStars = new Button("Modifica");
            modifyStars.getStyleClass().add("accent-button");
            modifyStars.setOnAction(e -> {
                SessionManager.idScelta = 10;
                super.openPopup("Cambia numero di stelle");
            });

            Label ServiziLabel = new Label("Servizi: ");
            ServiziLabel.getStyleClass().add("textNormal");
            Label servizi = new Label(rist.getFacilitiesAndServices());
            servizi.getStyleClass().add("textNormal");
            Button modifyServices = new Button("Modifica");
            modifyServices.getStyleClass().add("accent-button");
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

            // 🔧 esempio di forzatura dei label a crescere orizzontalmente
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


    protected void aggiornaValori(String campo, String scelta, String fileJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(fileJson));
        JsonNode ristorantiNode = root.get("ristoranti");

        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        // Converte in lista modificabile
        List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);

        for(Ristorante r : listaModificabile){
            if(r.Id == idRist) {
                System.out.println("Controllo Ristorante ID: " + r.Id);
                if (campo.equalsIgnoreCase("delivery")) {
                    System.out.println("Trovato Ristorante");
                    if (scelta.equalsIgnoreCase("si") && r.Delivery == false) {
                        r.Delivery = true;
                        System.out.println("Modificato Ristorante");
                    } else if (scelta.equalsIgnoreCase("no") && r.Delivery == true) {
                        r.Delivery = false;
                    }
                }
                if (campo.equalsIgnoreCase("booking")) {
                    if (scelta.equalsIgnoreCase("si") && r.BookingOnline == false) {
                        r.BookingOnline = true;
                    } else if (scelta.equalsIgnoreCase("no") && r.BookingOnline == true) {
                        r.BookingOnline = false;
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

    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        SessionManager.idRist = null;
        super.goTo(event, "ristorantiRist.fxml");
    }

    public void handleLogOut(ActionEvent event) {
        SessionManager.idRist = null;
        super.handleLogOut(event);
    }

    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }
}
