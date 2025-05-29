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

import static com.example.the_knife.Utente.SessionManager.idRist;


public class DettaglioRistController extends RistorantiRistController{
    @FXML
    private Label welcomeLabel;

    @FXML
    private GridPane grid;

    SessionManager session = SessionManager.getInstance();
    private final String user = session.getUsername();
    private final int id = session.getUserId();
    private final String ruolo = session.getRuolo();
    int idRist = (int) SessionManager.idRist;

    @FXML
    public void initialize() throws IOException {
        welcomeLabel.setText("AGGIUNGI UN RISTORANTE " + user + "");
        System.out.println("Utente: "+user+ " Id: "+id+" Ruolo: "+ruolo);
        System.out.println("Id ristorante dettagliato: "+idRist);
        printDettagliRist();
        printRecensioni();
    }

    private void printDettagliRist() throws IOException {
        try {
            //prendo il ristorante dalla lista basandomi sull'idRistorante nella sessione
            Ristorante rist = super.getRistoranteById("ristoranti.json", idRist);

            Label nomeLabel = new Label("Nome: ");
            Label nome = new Label(rist.getName());
            Button modifyname = new Button("Modifica");
            modifyname.setOnAction(e -> {
                SessionManager.idScelta = 1;
                super.openPopup("Cambia nome");
            });

            Label indirizzoLabel = new Label("Indirizzo: ");
            Label indirizzo = new Label(rist.getAddress());
            Button modifyadress = new Button("Modifica");
            modifyadress.setOnAction(e -> {
                SessionManager.idScelta = 2;
                super.openPopup("Cambia indirizzo");
            });

            Label cittaLabel = new Label("Citta: ");
            Label citta = new Label(rist.getLocation());
            Button modifyCity = new Button("Modifica");
            modifyCity.setOnAction(e -> {
                SessionManager.idScelta = 3;
                super.openPopup("Cambia città");
            });

            Label cucinaLabel = new Label("Cucina: ");
            Label cucina = new Label(rist.getCuisine());
            Button modifyCusine = new Button("Modifica");
            modifyCusine.setOnAction(e -> {
                SessionManager.idScelta = 4;
                super.openPopup("Cambia nome");
            });

            Label telefonoLabel = new Label("Telefono: ");
            Label telefono = new Label(rist.getPhoneNumber());
            Button modifyPhone = new Button("Modifica");
            modifyPhone.setOnAction(e -> {
                SessionManager.idScelta = 5;
                super.openPopup("Cambia numero di telefono");
            });

            Label emailLabel = new Label("Email: ");
            Label email = new Label(rist.getEmail());
            Button modifyEmail = new Button("Modifica");
            modifyEmail.setOnAction(e -> {
                SessionManager.idScelta = 6;
                super.openPopup("Cambia email");
            });

            Label urlLabel = new Label("Url: ");
            Label url = new Label(rist.getWebsiteUrl());
            Button modifyUrl = new Button("Modifica");
            modifyUrl.setOnAction(e -> {
                SessionManager.idScelta = 7;
                super.openPopup("Cambia Url");
            });

            Label descrizioneLabel = new Label("Descrizione: ");
            Label descrizione = new Label(rist.getDescription());
            Button modifyDescription = new Button("Modifica");
            modifyDescription.setOnAction(e -> {
                SessionManager.idScelta = 8;
                super.openPopup("Cambia descrizione");
            });

            Label prezzoLabel = new Label("Prezzo: ");
            Label prezzo = new Label(rist.getPrice());
            Button modifyPrice = new Button("Modifica");
            modifyPrice.setOnAction(e -> {
                SessionManager.idScelta = 9;
                super.openPopup("Cambia prezzo");
            });

            Label numStelleLabel = new Label("Numero di stelle: ");
            Label stelle = new Label(""+ rist.getGreenStar());
            Button modifyStars = new Button("Modifica");
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

            ToggleGroup group1 = new ToggleGroup();
            RadioButton delivery = new RadioButton("Si");
            delivery.setToggleGroup(group1);
            delivery.getStyleClass().add("radio-button");

            RadioButton delivery1 = new RadioButton("No");
            delivery1.setToggleGroup(group1);
            delivery1.getStyleClass().add("radio-button");

            if (rist.isDelivery()) {
                delivery.setUserData("si");
                delivery.setSelected(true);
                delivery.setOnAction(e -> {
                    RadioButton selected1 = (RadioButton) e.getSource();
                    if (selected1.getUserData().equals("si")) {
                        try {
                            aggiornaValori("delivery","si","ristoranti.json");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            } else {
                System.out.println("delivery1 prima di setUserData: " + delivery1);
                delivery1.setUserData("no");
                delivery1.setSelected(true);
                System.out.println("delivery1 userData dopo set: " + delivery1.getUserData());
                delivery1.setOnAction(e -> {
                    RadioButton selected2 = (RadioButton) e.getSource();
                    System.out.println("UserData: [" + selected2.getUserData() + "]");

                    String scelta = String.valueOf(selected2.getUserData()).trim();
                    if ("no".equalsIgnoreCase(scelta)) {
                        System.out.println("Hai selezionato NO");
                        try {
                            aggiornaValori("booking", "no", "ristoranti.json");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            }

            Label bookingOnlineLabel = new Label("Booking online: ");
            bookingOnlineLabel.getStyleClass().add("textNormal");

            ToggleGroup group2 = new ToggleGroup();
            RadioButton bookingOnline = new RadioButton("Si");
            bookingOnline.setToggleGroup(group2);
            bookingOnline.getStyleClass().add("radio-button");

            RadioButton bookingOnline1 = new RadioButton("No");
            bookingOnline1.setToggleGroup(group2);
            bookingOnline1.getStyleClass().add("radio-button");

            if (rist.isBookingOnline()) {
                bookingOnline.setUserData("si");
                bookingOnline.setSelected(true);
                bookingOnline.setOnAction(e -> {
                    RadioButton selected3 = (RadioButton) e.getSource();
                    if (selected3.getUserData().equals("si")) {
                        try {
                            aggiornaValori("booking","si","ristoranti.json");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            } else {
                bookingOnline1.setUserData("no");
                bookingOnline1.setSelected(true);
                bookingOnline1.setOnAction(e -> {
                    RadioButton selected4 = (RadioButton) e.getSource();
                    if (selected4.getUserData().equals("no")) {
                        try {
                            aggiornaValori("booking","no","ristoranti.json");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            }
            grid.add(nomeLabel, 0, 0);
            grid.add(nome, 1, 0);
            grid.add(modifyname, 2, 0);
            grid.add(indirizzoLabel, 0, 1);
            grid.add(indirizzo, 1, 1);
            grid.add(modifyadress, 2, 1);
            grid.add(cittaLabel, 0, 2);
            grid.add(citta, 1, 2);
            grid.add(modifyCity, 2, 2);
            grid.add(cucinaLabel, 0, 3);
            grid.add(cucina, 1, 3);
            grid.add(modifyCusine, 2, 3);
            grid.add(telefonoLabel, 0, 4);
            grid.add(telefono, 1, 4);
            grid.add(modifyPhone, 2, 4);
            grid.add(emailLabel, 0, 5);
            grid.add(email, 1, 5);
            grid.add(modifyEmail, 2, 5);
            grid.add(urlLabel, 0, 6);
            grid.add(url, 1, 6);
            grid.add(modifyUrl, 2, 6);
            grid.add(descrizioneLabel, 0, 7);
            grid.add(descrizione, 1, 7);
            grid.add(modifyDescription, 2, 7);
            grid.add(prezzoLabel, 0, 8);
            grid.add(prezzo, 1, 8);
            grid.add(modifyPrice, 2, 8);
            grid.add(numStelleLabel, 0, 9);
            grid.add(stelle, 1, 9);
            grid.add(modifyStars, 2, 9);
            grid.add(ServiziLabel, 0, 10);
            grid.add(servizi, 1, 10);
            grid.add(modifyServices, 2, 10);
            grid.add(deliveryLabel, 0, 11);
            grid.add(delivery,1,11);
            grid.add(delivery1,2,11);
            grid.add(bookingOnlineLabel, 0, 12);
            grid.add(bookingOnline,1,12);
            grid.add(bookingOnline1,2,12);

            grid.setPadding(new Insets(5));
            grid.setVgap(5);
            grid.setHgap(5);

            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(50);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(150);
            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPercentWidth(50);

            grid.getColumnConstraints().addAll(col1, col2, col3);
            grid.getStyleClass().add("grid-list");



            Label recensioniLabel = new Label("Recensioni: ");
            recensioniLabel.getStyleClass().add("textNormal");

            Label numRecLabel = new Label("Numero di recensioni: ");
            numRecLabel.getStyleClass().add("textNormal");

            Label mediaRecLabel = new Label("Media recensioni: ");
            mediaRecLabel.getStyleClass().add("textNormal");


            Label numRec = new Label("Numero di recensioni: ");
            numRec.getStyleClass().add("textNormal");


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

    protected void printRecensioni() throws IOException {

    }


    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        session.idRist = null;
        super.goTo(event, "ristorantiRist.fxml");
    }

    public void handleLogOut(ActionEvent event) {
        session.idRist = null;
        super.handleLogOut(event);
    }

    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }

}
