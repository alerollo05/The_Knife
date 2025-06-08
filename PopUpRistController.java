package com.example.the_knife.Ristoratore;

import com.example.the_knife.InputValidator;

import com.example.the_knife.Utente.SessionManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PopUpRistController extends DettaglioRistController {

    @FXML
    private Label label1;

    @FXML
    private Button okButton;

    @FXML
    private Button noButton;

    @FXML
    private TextField txt1;

    public void handleClose(ActionEvent event) throws IOException {
        // Chiude la finestra corrente
        SessionManager.idScelta = 0;

        if (mainController != null) {
            mainController.initialize(); //aggiorna la lista ristoranti nel padre
        }

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        //super.goTo(event, "dettaglioRist.fxml");
        stage.close();
        //Al massimo posso aggiornare la pagina rifacendo di nuovo goTo per leggere il dato nuovo
    }
    public void handleCloseAnnulla(ActionEvent event) throws IOException {
        // Chiude la finestra corrente
        SessionManager.idScelta = 0;
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        //super.goTo(event, "dettaglioRist.fxml");
        stage.close();
        //Al massimo posso aggiornare la pagina rifacendo di nuovo goTo per leggere il dato nuovo
    }
    public void initialize(){
            switch(SessionManager.idScelta){
                case 1:
                    label1.setText("Cambia nome:");
                    txt1.setPromptText("Inserisci il nuovo nome");
                    okButton.setText("Conferma");
                    okButton.setOnAction(e -> {
                        try {
                            String newNome = txt1.getText();
                            InputValidator.validaNomeRist(newNome);
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
                        try{
                            String newAdress = txt1.getText();
                            InputValidator.validaIndirizzo(newAdress);
                            modificaRist("indirizzo",newAdress,"ristoranti.json");
                            handleClose(e);//chiudi finestra popUp
                        }catch (IOException ex){
                            throw new RuntimeException(ex);
                        }
                    });
                    break;
                case 3:
                    label1.setText("Cambia Città:");
                    txt1.setPromptText("Inserisci la nuova città");
                    okButton.setText("Conferma");
                    okButton.setOnAction(e -> {
                       try{
                           String newCity = txt1.getText();
                           InputValidator.validaLuogo("newCity");
                           modificaRist("citta",newCity,"ristoranti.json");
                           handleClose(e);//chiudi finestra popUp
                       }catch (IOException ex){
                           throw new RuntimeException(ex);
                       }
                    });
                    break;
                case 4:
                    label1.setText("Cambia Cucina:");
                    txt1.setPromptText("Inserisci il nuovo tipo di cucina");
                    okButton.setText("Conferma");
                    okButton.setOnAction(e -> {
                        try{
                            String newCuisine = txt1.getText();
                            InputValidator.validaLuogo("newCuisine");
                            modificaRist("cucina",newCuisine,"ristoranti.json");
                            handleClose(e);//chiudi finestra popUp
                        }catch (IOException ex){
                            throw new RuntimeException(ex);
                        }
                    });
                    break;
                case 5:
                    label1.setText("Cambia Telefono:");
                    txt1.setPromptText("+39 0123456789");
                    okButton.setText("Conferma");
                    okButton.setOnAction(e -> {
                        try{
                            String newTel = txt1.getText();
                            InputValidator.validaTelefono(newTel);
                            modificaRist("telefono",newTel,"ristoranti.json");
                            handleClose(e);//chiudi finestra popUp
                        }catch (IOException ex){
                            throw new RuntimeException(ex);
                        }
                    });
                    break;
                case 6:
                    label1.setText("Cambia Email:");
                    txt1.setPromptText("Inserisci la nuova mail");
                    okButton.setText("Conferma");
                    okButton.setOnAction(e -> {
                        try{
                            String newMail = txt1.getText();
                            InputValidator.validaEmail(newMail);
                            modificaRist("email",newMail,"ristoranti.json");
                            handleClose(e);//chiudi finestra popUp
                        }catch (IOException ex){
                            throw new RuntimeException(ex);
                        }
                    });
                    break;
                case 7:
                    label1.setText("Cambia URL:");
                    txt1.setPromptText("Inserisci il nuovo URL");
                    okButton.setText("Conferma");
                    okButton.setOnAction(e -> {
                        try{
                            String newUrl = txt1.getText();
                            InputValidator.validaUrl(newUrl);
                            modificaRist("url",newUrl,"ristoranti.json");
                            handleClose(e);//chiudi finestra popUp
                        }catch (IOException ex){
                            throw new RuntimeException(ex);
                        }
                    });
                    break;
                case 8:
                    label1.setText("Cambia Descrizione:");
                    txt1.setPromptText("Inserisci la nuova descrizione");
                    okButton.setText("Conferma");
                    okButton.setOnAction(e -> {
                        try{
                            String newDesc = txt1.getText();
                            InputValidator.validaDescrizione(newDesc);
                            modificaRist("descrizione",newDesc,"ristoranti.json");
                            handleClose(e);//chiudi finestra popUp
                        }catch (IOException ex){
                            throw new RuntimeException(ex);
                        }
                    });
                    break;
                case 9:
                    label1.setText("Cambia Prezzo:");
                    txt1.setPromptText("Inserisci il nuovo prezzo medio");
                    okButton.setText("Conferma");
                    okButton.setOnAction(e -> {
                        try{
                            String newPrice = txt1.getText();
                            InputValidator.validaPrezzo(newPrice);
                            modificaRist("prezzo",newPrice,"ristoranti.json");
                            handleClose(e);//chiudi finestra popUp
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                    break;
                case 10:
                    label1.setText("Cambia Stelle:");
                    txt1.setPromptText("Inserisci il numero di stelle");
                    okButton.setText("Conferma");
                    okButton.setOnAction(e -> {
                        try{
                            String newStelle = txt1.getText();
                            InputValidator.validaStelle(newStelle);
                            modificaRist("stelle",newStelle,"ristoranti.json");
                            handleClose(e);//chiudi finestra popUp
                        }catch (IOException ex){
                            throw new RuntimeException(ex);
                        }
                    });
                    break;
                case 11:
                    label1.setText("Cambia Servizi:");
                    txt1.setPromptText("Cambia i tuoi servizi");
                    okButton.setText("Conferma");
                    okButton.setOnAction(e -> {
                        try{
                            String newServ = txt1.getText();
                            InputValidator.validaServizio(newServ);
                            modificaRist("servizi",newServ,"ristoranti.json");
                            handleClose(e);//chiudi finestra popUp
                        }catch (IOException ex){
                            throw new RuntimeException(ex);
                        }
                    });
                    break;

            }
    }

    //SERVE PER AGGIORNARE LA PAGINA DI STAMPA DOPO MODIFICA DEL POP UP
    protected DettaglioRistController mainController;

    public void setMainController(DettaglioRistController controller) {
        this.mainController = controller;
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
                    r.PhoneNumber = newCampo;
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
