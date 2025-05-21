package com.example.the_knife;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import org.mindrot.jbcrypt.BCrypt;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class loginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField userRegister;
    @FXML
    private PasswordField passRegister;
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
    private RadioButton ruolo;


    @FXML
    private void handleLogin() throws Exception {
        //METODI CHE CHIAMO

        //DEFINIZIONE handleLogin
        String user = usernameField.getText();
        String pass = passwordField.getText();

        Utente utente = new Utente();
        utente= login(user,pass);

        // TODO: handle login logic
    }

    @FXML
    private void handleRegister () throws TelefonoNonValidoException {

        //METODI CHE CHIAMO
        handleSubmit();

        //DEFINIZIONE handleRegister
        String newUser = userRegister.getText();
        String newPass = generaHash(passRegister.getText());
        String name = nomeField.getText();
        String cognome = cognomeField.getText();
        String numerotel = numTel.getText();
        numerotel = numerotel.replaceAll("[\\s-]", "");//rimuove spazi e trattini
        String indirizzo = this.indirizzo.getText();
        LocalDate DataNascita = dataNascita.getValue();
        RadioButton ruolo = (RadioButton) this.ruoloToggleGroup.getSelectedToggle();
        String role = ruolo.getText();

        // Controlla che l indirizzo contenga almeno una lettera e un numero
        if (!indirizzo.matches(".*\\d.*") || !indirizzo.matches(".*[a-zA-Z].*")) {
            throw new IllegalArgumentException("L'indirizzo deve contenere almeno una lettera e un numero.");
        }
        // Controlla che l indirizzo non contenga caratteri non validi
        if (!indirizzo.matches("^[\\p{L}0-9.,'\\-\\s]+$")) {
            throw new IllegalArgumentException("L'indirizzo contiene caratteri non validi.");
        }
        // Verifico che il numero inizi con + seguito da 1-4 cifre (prefisso internazionale),
        // Poi abbia da 6 a 12 cifre, che possono essere separate da spazi o trattini.
        // Non accetta caratteri diversi da spazi o trattini.
        if (!numerotel.matches("^\\+\\d{1,4}\\d{6,12}$")) {
            throw new TelefonoNonValidoException("Numero di telefono non valido. Deve iniziare con + seguito da prefisso e numero, e deve avere minimo 6 e massimo 12 cifre.");
        }


        try {
            Utente nuovo = new Utente(name,cognome,indirizzo,newUser,newPass,DataNascita,numerotel,role,generaId(role,"fileUtenti.json"));
            aggiungiUtente(nuovo, "fileUtenti.json");

        } catch (Exception e) {
            e.printStackTrace();
        }


        // TODO: handle registration logic
        System.out.println("Registration successful");
        System.out.println("Riepilogo:");
        System.out.println("Username: "+newUser +"\nPassword: "+newPass+"\nNome: "+name+"\nCognome:" +cognome+"\nNumero di telefono: "+numerotel+"\nData di nascita: "+DataNascita+"\nIndirizzo: "+indirizzo);
    }

    @FXML
    private void handleSubmit() {
        RadioButton selected = (RadioButton) ruoloToggleGroup.getSelectedToggle(); //casto il ruolo dal toggle group che ho definito nel file fxml
        if (selected != null) {
            System.out.println("Ruolo selezionato: " + selected.getText());
        }
    }

    // Metodo per convertire password in pawword cifrata
    public static String generaHash(String passwordNormal) {
        return BCrypt.hashpw(passwordNormal, BCrypt.gensalt());
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

    // Metodo per aggiungere(registrare) un nuovo utente al file degli Utenti
    public static void aggiungiUtente(Utente nuovo, String fileJson) {
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
                    throw new UtenteExeption("Utente già registrato");
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metodo per login sicuro con BCrypt
    public static Utente login(String username, String passwordInserita) throws Exception {
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
                    System.out.println("Password errata per utente: " + u.Username);
                    return null;
                }
            }
        }
        System.out.println("Username non trovato");
        return null;
    }




}