package com.example.the_knife;
import com.example.the_knife.Ristoratore.Ristorante;
import com.example.the_knife.Utente.SessionManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StartPageController {


    @FXML
    private ListView<Ristorante> listaRistLabel;

    @FXML private TextField cityField;
    @FXML private ComboBox<String> cuisineBox;
    @FXML private ComboBox<String> priceBox;
    @FXML private ComboBox<String> deliveryBox;
    @FXML private ComboBox<String> bookingBox;
    @FXML private Button searchButton;

    @FXML
    private void initialize() {
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

                        Button dettaglioButton = new Button("Dettaglio");
                        dettaglioButton.getStyleClass().add("accent-button");
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
                        Button recensioneButton = new Button("Recensioni");
                        recensioneButton.getStyleClass().add("accent-button");
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

                        // Espansione colonne
                        ColumnConstraints col1 = new ColumnConstraints();
                        col1.setPercentWidth(65);
                        ColumnConstraints col2 = new ColumnConstraints();
                        col2.setPercentWidth(50);
                        ColumnConstraints col3 = new ColumnConstraints();
                        col3.setPercentWidth(65);
                        ColumnConstraints col4 = new ColumnConstraints();
                        col4.setPercentWidth(40);
                        ColumnConstraints col5 = new ColumnConstraints();
                        col5.setPercentWidth(40);
                        grid.getColumnConstraints().addAll(col1, col2, col3, col4, col5);
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

                        Button dettaglioButton = new Button("Dettaglio");
                        dettaglioButton.getStyleClass().add("accent-button");
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
                        Button recensioneButton = new Button("Recensioni");
                        recensioneButton.getStyleClass().add("accent-button");
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

                        // Espansione colonne
                        ColumnConstraints col1 = new ColumnConstraints();
                        col1.setPercentWidth(65);
                        ColumnConstraints col2 = new ColumnConstraints();
                        col2.setPercentWidth(50);
                        ColumnConstraints col3 = new ColumnConstraints();
                        col3.setPercentWidth(65);
                        ColumnConstraints col4 = new ColumnConstraints();
                        col4.setPercentWidth(40);
                        ColumnConstraints col5 = new ColumnConstraints();
                        col5.setPercentWidth(40);
                        grid.getColumnConstraints().addAll(col1, col2, col3, col4, col5);
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

    public List<Ristorante> getRistoranti(String fileRisto, int id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
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

    public List<Ristorante> getRistorantiTop(String fileRisto) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(fileRisto));
        JsonNode ristorantiNode = root.get("ristoranti");

        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        List<Ristorante> mieiRisto = new ArrayList<>();
        mieiRisto.addAll(ristoranti);
        return mieiRisto;
    }



    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
        try{
            SessionManager.counter = 0;
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

    @FXML
    public void closeProgram(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void onSearchClicked() {
        SessionManager.counter = 0; // reset per applicare nuovi filtri
        printListRist("dettaglioRistoranteSearch.fxml","recensioneRistoranteSearch.fxml");
    }

    public void top10Ristoranti(String fileJson,String filetop10) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(fileJson));
        JsonNode tuttiRistNode = root.get("ristoranti");
        List<Ristorante> tuttiRist = Arrays.asList(mapper.treeToValue(tuttiRistNode, Ristorante[].class));

        JsonNode top10RistNode = root.get("ristoranti");
        List<Ristorante> top10Rist = Arrays.asList(mapper.treeToValue(top10RistNode, Ristorante[].class));

        List<Ristorante> listaModificabileTop10 = new ArrayList<>(top10Rist);

        ordinaRist(tuttiRist);

        for(int i=0 ; i<10; i++){
            aggiungiRistorante(tuttiRist.get(i),filetop10);
        }

        ObjectNode nuovoRoot = mapper.createObjectNode();
        nuovoRoot.set("ristoranti", mapper.valueToTree(listaModificabileTop10));
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filetop10), nuovoRoot);
    }

    public void ordinaRist(List<Ristorante> risultati) {
        quickSort(risultati, 0, risultati.size() - 1);
    }

    private void quickSort(List<Ristorante> lista, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(lista, low, high);
            quickSort(lista, low, pivotIndex - 1);
            quickSort(lista, pivotIndex + 1, high);
        }
    }

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

    public static void aggiungiRistorante(Ristorante nuovo, String fileJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
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
    public List<Ristorante> cercaRistoranti(String fileJson, String luogoOrName, String tipoCucina, String fasciaPrezzo,
                                            boolean delivery, boolean servizioOnline) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
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