package com.example.the_knife.Ristoratore;

import com.example.the_knife.InputValidator;
import com.example.the_knife.Utente.SessionManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Controller della finestra pop-up utilizzata dai ristoratori per modificare
 * dinamicamente i dati del proprio ristorante (es. nome, indirizzo, cucina, ecc.).
 *
 * <p>Questa classe estende {@link DettaglioRistController} per mantenere
 * continuità con la gestione dettagliata del ristorante. Le modifiche vengono
 * salvate nel file JSON persistente e la schermata principale viene aggiornata
 * alla chiusura del pop-up.</p>
 */
public class PopUpRistController extends DettaglioRistController {

    /** Etichetta di testo che guida l'utente sul tipo di modifica corrente */
    @FXML
    private Label label1;

    /** Pulsante di conferma per applicare la modifica selezionata */
    @FXML
    private Button okButton;

    /** Campo di input generico per l'inserimento del nuovo valore */
    @FXML
    private TextField txt1;

    /** ComboBox per la selezione del nuovo tipo di cucina */
    @FXML
    private ComboBox<String> comboCucina;

    /** Riferimento al controller principale per aggiornare la schermata chiamante */
    protected DettaglioRistController mainController;

    /**
     * Chiude la finestra pop-up e aggiorna la schermata principale, se disponibile.
     *
     * @param event evento generato dal clic su un pulsante
     * @throws IOException se si verifica un errore nella chiusura o aggiornamento
     */
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
    /**
     * Chiude la finestra pop-up senza applicare alcuna modifica.
     *
     * @param event evento generato dal clic sul pulsante Annulla
     * @throws IOException se si verifica un errore nella chiusura
     */
    public void handleCloseAnnulla(ActionEvent event) throws IOException {
        // Chiude la finestra corrente
        SessionManager.idScelta = 0;
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        //super.goTo(event, "dettaglioRist.fxml");
        stage.close();
        //Al massimo posso aggiornare la pagina rifacendo di nuovo goTo per leggere il dato nuovo
    }
    /**
     * Metodo di inizializzazione del pop-up.
     * <p>In base al valore di {@code SessionManager.idScelta}, viene mostrato
     * un campo di input testuale o un menu a tendina per permettere all’utente
     * di modificare un attributo specifico del ristorante.</p>
     */
    public void initialize(){
            switch(SessionManager.idScelta){
                case 1:
                    label1.setText("Cambia nome:");
                    txt1.setPromptText("Inserisci il nuovo nome");
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
                    okButton.setOnAction(e -> {
                        try{
                            String newCity = txt1.getText();
                            InputValidator.validaLuogo(newCity);
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
                    txt1.setVisible(false);
                    txt1.setManaged(false);
                    comboCucina.setVisible(true);
                    comboCucina.setManaged(true);;
                    comboCucina.setPromptText("Tipo di cucina");
                    comboCucina.setItems(FXCollections.observableArrayList(
                            "Mediterranea", "Italiana", "Giapponese",
                            "Francese", "Cinese", "Messicana", "Indiana","Pizzeria","Di pesce" , "Trattoria", "Di carne"));
                    okButton.setOnAction(e -> {
                        try{
                            String newCuisine = comboCucina.getValue();
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

                default: break;
            }
    }
    /**
     * Imposta il controller principale che ha aperto il pop-up, utile per aggiornare la schermata dopo la modifica.
     *
     * @param controller controller principale del dettaglio ristorante
     */
    public void setMainController(DettaglioRistController controller) {
        this.mainController = controller;
    }
    /**
     * Applica la modifica al campo specificato del ristorante nel file JSON.
     *
     * @param campo     il campo da modificare (es. "nome", "cucina")
     * @param newCampo  il nuovo valore da assegnare
     * @param fileJson  il percorso del file JSON contenente i dati dei ristoranti
     * @throws IOException in caso di errore nella lettura o scrittura del file
     */
    public void modificaRist(String campo, String newCampo, String fileJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        File file = new File(fileJson);
        JsonNode root = mapper.readTree(file);
        JsonNode ristorantiNode = root.get("ristoranti");

        List<Ristorante> ristoranti = Arrays.asList(mapper.treeToValue(ristorantiNode, Ristorante[].class));
        List<Ristorante> listaModificabile = new ArrayList<>(ristoranti);

        boolean modificato = false;

        for (Ristorante r : listaModificabile) {
            if (r.id == idRist) {
                switch (campo.toLowerCase()) {
                    case "nome":
                        r.name = newCampo;
                        break;
                    case "indirizzo":
                        r.address = newCampo;
                        break;
                    case "citta":
                        r.location = newCampo;
                        break;
                    case "cucina":
                        r.cuisine = newCampo;
                        break;
                    case "telefono":
                        r.phoneNumber = newCampo;
                        break;
                    case "email":
                        r.email = newCampo;
                        break;
                    case "url":
                        r.websiteUrl = newCampo;
                        break;
                    case "descrizione":
                        r.description = newCampo;
                        break;
                    case "prezzo":
                        r.price = newCampo;
                        break;
                    case "stelle":
                        try {
                            r.greenStar = Integer.parseInt(newCampo);
                        } catch (NumberFormatException e) {
                            System.err.println("Valore stelle non valido: " + newCampo);
                            return;
                        }
                        break;
                    case "servizi":
                        r.facilitiesAndServices = newCampo;
                        break;
                    default:
                        System.err.println("Campo non riconosciuto: " + campo);
                        return;
                }

                modificato = true;
                break; // esci dal ciclo, trovato il ristorante
            }
        }

        if (modificato) {
            ObjectNode nuovoRoot = mapper.createObjectNode();
            nuovoRoot.set("ristoranti", mapper.valueToTree(listaModificabile));
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, nuovoRoot);
            System.out.println("Modifica effettuata e file aggiornato.");
        } else {
            System.out.println("Ristorante non trovato o nessuna modifica necessaria.");
        }
    }
    
}
