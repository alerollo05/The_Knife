package com.example.the_knife.Ristoratore;

import com.example.the_knife.Utente.ListaUtenti;
import com.example.the_knife.Utente.SessionManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RistorantiRistController extends dashBoardRistController {

    @FXML
    private Label welcomeLabel;
   /* @FXML
    private TableView<Ristorante> tableView;
    @FXML
    private TableColumn<Ristorante, String> colNome;
    @FXML
    private TableColumn<Ristorante, String> colIndirizzo;
    @FXML
    private TableColumn<Ristorante, Double> colRating;
    */
    SessionManager session = SessionManager.getInstance();
    private final String user = session.getUsername();
    private final int id = session.getUserId();
    private final String ruolo = session.getRuolo();



    public void initialize() throws IOException {

        welcomeLabel.setText("I TUOI RISTORANTI " + user + "");
        System.out.println("Utente: "+user+ " Id: "+id+" Ruolo: "+ruolo);


    }

    @FXML
    protected void onRistorantiClick(ActionEvent event) throws IOException {
        onRistorantiClick(event);
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

    public List<Ristorante> getRistoranti(String fileRisto, int id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(fileRisto));
        JsonNode ristorantiNode = root.get("ristoranti");

        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        List<Ristorante> mieiRisto = new ArrayList<>();

        for (Ristorante r : ristoranti) {
            if(r.IdRistoratore == id){
                mieiRisto.add(r);
            }
        }
        return mieiRisto;
    }

    public String elencoRisto(List<Ristorante> ristomiei) throws IOException {
        ristomiei = getRistoranti("ristoranti.json", id);
        String s;
        StringBuilder sb = new StringBuilder();
        for (Ristorante r : ristomiei) {
            sb.append(String.format("Nome: %s | Indirizzo: %s | Tipo Cucina: %s%n", r.Name, r.Address, r.Cuisine));
        }
        s = sb.toString();
        return s;
    }

}
