package com.example.the_knife.Cliente;

import com.example.the_knife.Ristoratore.Recensione;
import com.example.the_knife.Ristoratore.Ristorante;
import com.example.the_knife.Utente.SessionManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.the_knife.Utente.SessionManager.idRist;

public class RecensioniClientController extends dashBoardClientController{
    @FXML
    private Label welcomeLabel;

    SessionManager session = SessionManager.getInstance();
    private final String user = session.getUsername();
    private final int id = session.getUserId();
    private final String ruolo = session.getRuolo();

    public void handleLogOut(ActionEvent event) {
        super.handleLogOut(event);
    }

    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }

    @FXML
    public void initialize() {
        welcomeLabel.setText("LE TUE RECENSIONI " + user + "");
        System.out.println("Utente: "+user+ " Id: "+id+" Ruolo: "+ruolo);
    }

    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        super.goBack(event);
    }

    @FXML
    protected void onProfileClick(ActionEvent event) throws IOException {
        super.onProfileClick(event);
    }
    @FXML
    protected void onRistorantiClick(ActionEvent event) throws IOException {
        super.onRistorantiClick(event);
    }

    public void aggiungiRecensione(Recensione newRec,String fileJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();// Crea un'istanza di ObjectMapper
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        File file = new File(fileJson);

        JsonNode root = mapper.readTree(file);
        JsonNode ristorantiNode = root.get("ristoranti");

        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);

        double somma = 0;

        for (Ristorante r : listaModificabile) {
            if (r.getId() == idRist){
                r.recensioni.add(newRec);
                r.setNumRec(r.getNumRec()+1);
            }
            // aggiorna la media delle recensioni dopo aver aggiunto la recensione
            for (Recensione rec : r.recensioni) {
                somma += rec.rating;
                double media = somma / r.getNumRec();
                media = Math.round(media * 100.0) / 100.0; // arrotonda a due cifre decimali
                r.setMediaRec(media);
            }
        }

        ObjectNode nuovoRoot = mapper.createObjectNode();
        nuovoRoot.set("ristoranti", mapper.valueToTree(listaModificabile));

        // Sovrascrive il file
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileJson), nuovoRoot);

    }

    public static String VaiAcapo(String input) {
        String risultato = "";
        int lunghezza = input.length();
        for (int i = 0; i < lunghezza; i += 60) {       // Cicla ogni 63 caratteri
            int fine = Math.min(i + 60, lunghezza);     // Calcola la fine del blocco (senza superare la fine della stringa)
            risultato += input.substring(i, fine);      // Aggiunge il blocco da 'i' a 'fine' alla stringa finale
            if (fine < lunghezza) {                     // Se non siamo all'ultimo blocco...
                risultato += "\n";                      // ...aggiungiamo un a capo (\n)
            }
        }
        return risultato;
    }

}
