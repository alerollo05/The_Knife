package com.example.the_knife;

import com.example.the_knife.Ristoratore.Ristorante;
import com.example.the_knife.Ristoratore.RistorantiRistController;
import com.example.the_knife.Utente.SessionManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.the_knife.Utente.SessionManager.idRist;

public class DettaglioRistoranteSearchController extends RistorantiRistController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private GridPane grid;

    @FXML
    public void initialize() throws IOException {
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


            Label indirizzoLabel = new Label("Indirizzo: ");
            indirizzoLabel.getStyleClass().add("textNormal");
            Label indirizzo = new Label(rist.getAddress());
            indirizzo.getStyleClass().add("textNormal");

            Label cittaLabel = new Label("Citta: ");
            cittaLabel.getStyleClass().add("textNormal");
            Label citta = new Label(rist.getLocation());
            citta.getStyleClass().add("textNormal");

            Label cucinaLabel = new Label("Cucina: ");
            cucinaLabel.getStyleClass().add("textNormal");
            Label cucina = new Label(rist.getCuisine());
            cucina.getStyleClass().add("textNormal");

            Label telefonoLabel = new Label("Telefono: ");
            telefonoLabel.getStyleClass().add("textNormal");
            Label telefono = new Label(rist.getPhoneNumber());
            telefono.getStyleClass().add("textNormal");

            Label emailLabel = new Label("Email: ");
            emailLabel.getStyleClass().add("textNormal");
            Label email = new Label(rist.getEmail());
            email.getStyleClass().add("textNormal");


            Label urlLabel = new Label("Url: ");
            urlLabel.getStyleClass().add("textNormal");
            Label url = new Label(rist.getWebsiteUrl());
            url.getStyleClass().add("textNormal");

            Label descrizioneLabel = new Label("Descrizione: ");
            descrizioneLabel.getStyleClass().add("textNormal");
            Label descrizione = new Label(rist.getDescription());
            descrizione.getStyleClass().add("textNormal");

            Label prezzoLabel = new Label("Prezzo: ");
            prezzoLabel.getStyleClass().add("textNormal");
            Label prezzo = new Label(rist.getPrice());
            prezzo.getStyleClass().add("textNormal");

            Label numStelleLabel = new Label("Numero di stelle: ");
            numStelleLabel.getStyleClass().add("textNormal");
            Label stelle = new Label("" + rist.getGreenStar());
            stelle.getStyleClass().add("textNormal");

            Label ServiziLabel = new Label("Servizi: ");
            ServiziLabel.getStyleClass().add("textNormal");
            Label servizi = new Label(rist.getFacilitiesAndServices());
            servizi.getStyleClass().add("textNormal");


            Label deliveryLabel = new Label("Delivery: ");
            deliveryLabel.getStyleClass().add("textNormal");

            Label delivery;

// Seleziona il valore corrente del ristorante
            if (rist.isDelivery()) {
                delivery = new Label("Si");
                delivery.getStyleClass().add("textNormal");
            } else {
                delivery = new Label("No");
                delivery.getStyleClass().add("textNormal");
            }

            Label bookingOnlineLabel = new Label("Booking online: ");
            bookingOnlineLabel.getStyleClass().add("textNormal");
// Seleziona il valore corrente del ristorante
            Label booking;
            if (rist.isBookingOnline()) {
                booking = new Label("Si");
                booking.getStyleClass().add("textNormal");
            } else {
                booking = new Label("No");
                booking.getStyleClass().add("textNormal");
            }



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


            grid.add(nomeLabel, 0, 0); grid.add(nome, 1, 0);
            grid.add(indirizzoLabel, 0, 1); grid.add(indirizzo, 1, 1);
            grid.add(cittaLabel, 0, 2); grid.add(citta, 1, 2);
            grid.add(cucinaLabel, 0, 3); grid.add(cucina, 1, 3);
            grid.add(telefonoLabel, 0, 4); grid.add(telefono, 1, 4);
            grid.add(emailLabel, 0, 5); grid.add(email, 1, 5);
            grid.add(urlLabel, 0, 6); grid.add(url, 1, 6);
            grid.add(descrizioneLabel, 0, 7); grid.add(descrizione, 1, 7);
            grid.add(prezzoLabel, 0, 8); grid.add(prezzo, 1, 8);
            grid.add(numStelleLabel, 0, 9); grid.add(stelle, 1, 9);
            grid.add(ServiziLabel, 0, 10); grid.add(servizi, 1, 10);
            grid.add(deliveryLabel, 0, 11); grid.add(delivery, 1, 11);
            grid.add(bookingOnlineLabel, 0, 12); grid.add(booking, 1, 12);

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


    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }
}
