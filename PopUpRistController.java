package com.example.the_knife.Ristoratore;

import com.example.the_knife.Utente.SessionManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.the_knife.Utente.SessionManager.idRist;

public class PopUpRistController {

    @FXML
    private Label label1;

    @FXML
    private Button okButton;

    @FXML
    private TextField txt1;



    public void handleClose(ActionEvent event) {
        // Chiude la finestra corrente
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
        //Al massimo posso aggiornare la pagina rifacendo di nuovo goTo per leggere il dato nuovo
    }
    public void initialize(){
        int idRist = (int) SessionManager.idRist;

            switch(SessionManager.idScelta){
                case 1:
                    label1.setText("Cambia nome:");
                    txt1.setPromptText("Inserisci il nuovo nome");
                    okButton.setText("Conferma");
                    okButton.setOnAction(e -> {
                        try {
                            String newNome = txt1.getText();
                            modificaRist("nome",newNome,"ristoranti.json");
                            handleClose(e);//chiudi finestra popUp
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                    break;
                case 2:
                    label1.setText("Cambia indirizzo:");
                    txt1.setPromptText("Inserisci il nuovo indirizzo");
                    okButton.setText("Conferma");
                    okButton.setOnAction(e -> {
                        String newAddr = txt1.getText();
                        handleClose(e);//chiudi finestra popUp
                    });
                    break;
                case 3:
                    label1.setText("Cambia Città:");
                    txt1.setPromptText("Inserisci la nuova città");
                    okButton.setText("Conferma");
                    okButton.setOnAction(e -> {
                        String newCit = txt1.getText();
                        handleClose(e);//chiudi finestra popUp
                    });
                    break;
                case 4:
                    label1.setText("Cambia Cucina:");
                    txt1.setPromptText("Inserisci il nuovo tipo di cucina");
                    okButton.setText("Conferma");
                    okButton.setOnAction(e -> {
                        String newCuc = txt1.getText();
                        handleClose(e);//chiudi finestra popUp
                    });
                    break;
                case 5:
                    label1.setText("Cambia Telefono:");
                    txt1.setPromptText("+39 0123456789");
                    okButton.setText("Conferma");
                    okButton.setOnAction(e -> {
                        String newTel = txt1.getText();
                        handleClose(e);//chiudi finestra popUp
                    });
                    break;
                case 6:
                    label1.setText("Cambia Email:");
                    txt1.setPromptText("Inserisci la nuova mail");
                    okButton.setText("Conferma");
                    okButton.setOnAction(e -> {
                        String newMail = txt1.getText();
                        handleClose(e);//chiudi finestra popUp
                    });
                    break;
                case 7:
                    label1.setText("Cambia URL:");
                    txt1.setPromptText("Inserisci il nuovo URL");
                    okButton.setText("Conferma");
                    okButton.setOnAction(e -> {
                        String newUrl = txt1.getText();
                        handleClose(e);//chiudi finestra popUp
                    });
                    break;
                case 8:
                    label1.setText("Cambia Descrizione:");
                    txt1.setPromptText("Inserisci la nuova descrizione");
                    okButton.setText("Conferma");
                    okButton.setOnAction(e -> {
                        String newDesc = txt1.getText();
                        handleClose(e);//chiudi finestra popUp
                    });
                    break;
                case 9:
                    label1.setText("Cambia Prezzo:");
                    txt1.setPromptText("Inserisci il nuovo prezzo medio");
                    okButton.setText("Conferma");
                    okButton.setOnAction(e -> {
                        String newPrice = txt1.getText();
                        handleClose(e);//chiudi finestra popUp
                    });
                    break;
                case 10:
                    label1.setText("Cambia Stelle:");
                    txt1.setPromptText("Inserisci il numero di stelle");
                    okButton.setText("Conferma");
                    okButton.setOnAction(e -> {
                        String newStar = txt1.getText();

                        handleClose(e);//chiudi finestra popUp
                    });
                    break;
                case 11:
                    label1.setText("Cambia Servizi:");
                    txt1.setPromptText("Cambia i tuoi servizi");
                    okButton.setText("Conferma");
                    okButton.setOnAction(e -> {
                        String newServ = txt1.getText();
                        handleClose(e);//chiudi finestra popUp
                    });
                    break;

            }
    }

    public void modificaRist(String campo,String newCampo, String fileJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(fileJson));
        JsonNode ristorantiNode = root.get("ristoranti");

        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        // Converte in lista modificabile
        List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);

        for(Ristorante r : listaModificabile){
            if(r.Id== idRist){
                if(campo.equals("nome")){
                    r.Name = newCampo;
                } else if(campo.equals("indirizzo")){
                    r.Address = newCampo;
                } else if (campo.equals("citta")) {
                    r.Location = newCampo;
                } else if (campo.equals("cucina")) {
                    r.Cuisine = newCampo;
                } else if (campo.equals("telefono")) {
                    r.Address = newCampo;
                } else if(campo.equals("email")){
                    r.Email = newCampo;
                }else if(campo.equals("url")){
                    r.WebsiteUrl = newCampo;
                }else if(campo.equals("descrizione")){
                    r.Description = newCampo;
                } else if(campo.equals("prezzo")){
                    r.Price = newCampo;
                } else if(campo.equals("stelle")){
                    int newStelle = Integer.parseInt(newCampo);
                    r.GreenStar = newStelle;
                } else if(campo.equals("servizi")){
                    r.FacilitiesAndServices = newCampo;
                }
            }
            // Ricrea l'oggetto JSON aggiornato
            ObjectNode nuovoRoot = mapper.createObjectNode();
            nuovoRoot.set("ristoranti", mapper.valueToTree(listaModificabile));
            // Sovrascrive il file
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileJson), nuovoRoot);
        }




    }
}
