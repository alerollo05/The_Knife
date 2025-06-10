
package com.example.the_knife;

import com.example.the_knife.Exceptions.*;
import com.example.the_knife.Utente.ListaUtenti;
import com.example.the_knife.Utente.SessionManager;
import com.example.the_knife.Utente.Utente;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class loginController extends StartPageController {
    //creo le variabili che mi servono per immagazzinare i dati che l'utente immette in input
    @FXML
    private TextField usernameField;
    @FXML
    private TextField userRegister;
    @FXML
    private TextField nomeField;
    @FXML
    private TextField cognomeField;
    @FXML
    private ToggleGroup ruoloToggleGroup;
    @FXML
    private TextField numTel;
    @FXML
    private DatePicker dataNascita;
    @FXML
    private TextField indirizzo;
    @FXML
    private TextField emailField;

    @FXML
    private RadioButton ruolo;
    @FXML private PasswordField passRegister;
    @FXML private TextField passRegisterVisible;
    @FXML private CheckBox showPasswordCheckBox;

    @FXML private PasswordField passwordField;
    @FXML private TextField passwordFieldVisible;
    @FXML private CheckBox showLoginPasswordCheckBox;

    public void initialize() throws IOException{
        // Password registrazione a scomparsa
        passRegisterVisible.textProperty().bindBidirectional(passRegister.textProperty());
        showPasswordCheckBox.selectedProperty().addListener((obs, oldVal, selected) -> {
            passRegister.setVisible(!selected);
            passRegister.setManaged(!selected);
            passRegisterVisible.setVisible(selected);
            passRegisterVisible.setManaged(selected);
        });

        // Password Login a scomparsa
        passwordFieldVisible.textProperty().bindBidirectional(passwordField.textProperty());
        showLoginPasswordCheckBox.selectedProperty().addListener((obs, oldVal, selected) -> {
            passwordField.setVisible(!selected);
            passwordField.setManaged(!selected);
            passwordFieldVisible.setVisible(selected);
            passwordFieldVisible.setManaged(selected);
        });
    }

    @FXML
    private void handleLogin(ActionEvent event) throws Exception {
        //METODI CHE CHIAMO

        //DEFINIZIONE handleLogin
        String user = usernameField.getText();
        String pass = passwordField.getText();

        Utente utente = new Utente();
        utente= login(user,pass);//faccio la login con i dati inseriti

        //CREO LA SESSIONE

        if(utente != null && utente.Ruolo.equals("Cliente")){
            SessionManager.getInstance().login(user, utente.Id, "cliente");
            goTo(event,"Cliente/dashBoardClient.fxml");//vado alla pagina del cliente
        }else if(utente != null && utente.Ruolo.equals("Ristoratore")){
            SessionManager.getInstance().login(user, utente.Id, "ristoratore");
            goTo(event,"Ristoratore/dashBoardRist.fxml");//vado alla pagina del ristoratore
        }

    }
    @FXML
    public void closeProgram(ActionEvent event) {
        System.exit(0);
    }
    @FXML
    public void handleRegister() {

        //METODI CHE CHIAMO
        handleRadio();


        //DEFINIZIONE handleRegister
        String newUser = userRegister.getText();
        newUser = newUser.trim();//tolgo gli spazi esterni alla stinga inserita in input dall'utente
        InputValidator.validaUsername(newUser);
        String newPass = passRegister.getText();
        newPass = newPass.trim();
        InputValidator.validaPassword(newPass);
        String name = nomeField.getText();
        name = name.trim();
        InputValidator.validaNomeUte(newUser);
        String cognome = cognomeField.getText();
        cognome = cognome.trim();
        InputValidator.validaCogno(cognome);
        String numerotel = numTel.getText();
        numerotel = numerotel.trim();
        numerotel = numerotel.replaceAll("[\\s-]","");
        InputValidator.validaTelefono(numerotel);
        String indirizzo = this.indirizzo.getText();
        indirizzo = indirizzo.trim();
        InputValidator.validaIndirizzo(indirizzo);
        LocalDate DataNascita = dataNascita.getValue();
        RadioButton ruolo = (RadioButton) this.ruoloToggleGroup.getSelectedToggle();
        String role = ruolo.getText();



        // Codifica della password
        newPass = generaHash(passRegister.getText());

        try {
            Utente nuovo = new Utente(name,cognome,indirizzo,newUser,newPass,DataNascita,numerotel,role,generaId(role,"fileUtenti.json"));
            aggiungiUtente(nuovo, "fileUtenti.json");

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(ruolo.getText());
        System.out.println("Registration successful");
        System.out.println("Riepilogo:");
        System.out.println("Username: "+newUser +"\nPassword: "+newPass+"\nNome: "+name+"\nCognome:" +cognome+"\nNumero di telefono: "+numerotel+"\nData di nascita: "+DataNascita+"\nIndirizzo: "+indirizzo);


    }


    public static int generaId(String ruolo, String fileJson) {

        if(ruolo.equalsIgnoreCase("Cliente")){
            return 0;
        }

        int count = 0;

        try {
            ObjectMapper mapper = new ObjectMapper();// Crea un'istanza di ObjectMapper
            mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
            File file = new File(fileJson);// Crea un oggetto File per rappresentare il file JSON passato come parametro

            if (!file.exists()) {
                System.out.println("File non trovato: " + file.getAbsolutePath());
                return 0;
            }

            JsonNode root = mapper.readTree(file);// Legge l'intero contenuto del file come un albero JSON

            List<Utente> listaModificabile = new ArrayList<>();// Prepara una lista modificabile di Utente (vuota per ora)

            JsonNode utentiNode = root.get("Utenti"); // Recupera il nodo "Utenti" dall'albero JSON

            if (utentiNode != null && utentiNode.isArray()) {
                Utente[] utentiArray = mapper.treeToValue(utentiNode, Utente[].class); // Converte l'array JSON in un array Java di oggetti Utente
                listaModificabile = new ArrayList<>(Arrays.asList(utentiArray)); // Converte l'array in una lista modificabile
            }

            count = 0;
            for (Utente u : listaModificabile) {
                if (u.Ruolo.equalsIgnoreCase("Ristoratore")) {
                    count++;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return count+1;

    }
    @FXML
    private void handleRadio() {
        RadioButton selected = (RadioButton) ruoloToggleGroup.getSelectedToggle(); //casto il ruolo dal toggle group che ho definito nel file fxml
        if (selected != null) {
            System.out.println("Ruolo selezionato: " + selected.getText());
        }
    }
    @FXML
    public void goToStartPage(ActionEvent event) throws IOException {
        goTo(event,"startPage.fxml");
    }
    // Metodo per convertire password in pawword cifrata
    public static String generaHash(String passwordNormal) {
        return BCrypt.hashpw(passwordNormal, BCrypt.gensalt());
    }

    // Metodo per aggiungere un nuovo utente al file degli Utenti
    public  void aggiungiUtente(Utente nuovo, String fileJson) {
        try {

            ObjectMapper mapper = new ObjectMapper();// Crea un'istanza di ObjectMapper
            mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
            File file = new File(fileJson);// Crea un oggetto File per rappresentare il file JSON passato come parametro

            if (!file.exists()) {
                System.out.println("File non trovato: " + file.getAbsolutePath());
                return;
            }

            ListaUtenti lista = mapper.readValue(new File(fileJson), ListaUtenti.class);

            for(Utente u : lista.Utenti){
                if(u.Username.equalsIgnoreCase(nuovo.Username)){
                    handleInput("Errore", "Utente già registrato");
                    throw new UtenteException("Utente già registrato");
                }
            }

            JsonNode root = mapper.readTree(file);// Legge l'intero contenuto del file come un albero JSON

            List<Utente> listaModificabile = new ArrayList<>();// Prepara una lista modificabile di Utente (vuota per ora)

            JsonNode utentiNode = root.get("Utenti"); // Recupera il nodo "Utenti" dall'albero JSON

            // Se il nodo esiste e contiene un array
            if (utentiNode != null && utentiNode.isArray()) {
                Utente[] utentiArray = mapper.treeToValue(utentiNode, Utente[].class); // Converte l'array JSON in un array Java di oggetti Utente
                listaModificabile = new ArrayList<>(Arrays.asList(utentiArray)); // Converte l'array in una lista modificabile
            }

            listaModificabile.add(nuovo); // Aggiunge il nuovo utente alla lista

            System.out.println("Utente '" + nuovo.Nome + "' registrato con successo.");

            ObjectNode nuovoRoot = mapper.createObjectNode();// Crea un nuovo oggetto JSON vuoto (root)

            nuovoRoot.set("Utenti", mapper.valueToTree(listaModificabile));// Imposta il nodo "Utenti" con la lista aggiornata di utenti convertita in JSON

            mapper.writerWithDefaultPrettyPrinter().writeValue(file, nuovoRoot);// Scrive l'albero JSON aggiornato nel file, sovrascrivendolo con formattazione leggibile

            handleInput();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metodo per login sicuro con BCrypt
    public  Utente login(String username, String passwordInserita) throws Exception {
        ObjectMapper mapper = new ObjectMapper(); // Crea un oggetto ObjectMapper di Jackson per la deserializzazione JSON
        mapper.registerModule(new JavaTimeModule()); // Registra un modulo per la gestione corretta di LocalDate e altri tipi Java Time
        ListaUtenti lista = mapper.readValue(new File("fileUtenti.json"), ListaUtenti.class); // Deserializza il file JSON in un oggetto ListaUtenti

        for (Utente u : lista.Utenti) {
            // Se l'username corrisponde
            if (u.Username.equals(username)) {
                // Verifica sicura della password usando BCrypt
                // (confronta la password inserita con l'hash salvato nel file)
                if (BCrypt.checkpw(passwordInserita, u.Password)) {
                    System.out.println("Login riuscito per utente: " + u.Username);
                    return u; // Restituisce l'utente loggato
                } else {
                    handleInput("Errore", "Password errata per utente: " + u.Username);
                    System.err.println("Password errata per utente: " + u.Username);
                    return null;
                }
            }
        }
        handleInput("Errore", "Username non trovato");
        System.err.println("Username non trovato");
        return null;
    }

    protected void handleInput() {
        //if(controllo che tutti gli input siano andati bene allora mando questo messaggio)
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registrazione");
        alert.setHeaderText("Ti sei registrato correttamente");
        alert.setContentText("I tuoi dati sono stati salvati...");
        alert.showAndWait();
        //else mando un errore specifico su un tipo di input inserito dall'utente
    }
    protected void handleInput(String message1,String message2) {
        //if(controllo che tutti gli input siano andati bene allora mando questo messaggio)
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Registrazione");
        alert.setHeaderText(message1);
        alert.setContentText(message2);
        alert.showAndWait();
        //else mando un errore specifico su un tipo di input inserito dall'utente
    }

}
