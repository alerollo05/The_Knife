
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Controller per la gestione del login e della registrazione degli utenti.
 * <p>
 * Estende {@link StartPageController} e gestisce tutte le operazioni relative all'autenticazione:
 * <ul>
 *     <li>Login sicuro con password hashata tramite BCrypt</li>
 *     <li>Registrazione di nuovi utenti</li>
 *     <li>Validazione input</li>
 *     <li>Interfaccia con il file JSON degli utenti</li>
 * </ul>
 */
public class LoginController extends StartPageController {
    //creo le variabili che mi servono per immagazzinare i dati che l'utente immette in input
    /** Campo per l'username durante il login */
    @FXML
    private TextField usernameField;
    /** Campo per l'username durante la registrazione */
    @FXML
    private TextField userRegister;
    /** Campo per il nome */
    @FXML
    private TextField nomeField;
    /** Campo per il cognome */
    @FXML
    private TextField cognomeField;
    /** Gruppo di toggle per la scelta del ruolo (Cliente o Ristoratore) */
    @FXML
    private ToggleGroup ruoloToggleGroup;
    /** Campo per il numero di telefono */
    @FXML
    private TextField numTel;
    /** DatePicker per la data di nascita */
    @FXML
    private DatePicker dataNascita;
    /** Campo per l'indirizzo */
    @FXML
    private TextField indirizzo;
    /** Campo per l'email */
    @FXML
    private TextField emailField;
    /** RadioButton per selezionare il ruolo */
    @FXML
    private RadioButton ruolo;
    /** Campo password registrazione (nascosta) */
    @FXML private PasswordField passRegister;
    /** Campo password registrazione (visibile) */
    @FXML private TextField passRegisterVisible;
    /** Checkbox per mostrare/nascondere password registrazione */
    @FXML private CheckBox showPasswordCheckBox;
    /** Campo password login (nascosta) */
    @FXML private PasswordField passwordField;
    /** Campo password login (visibile) */
    @FXML private TextField passwordFieldVisible;
    /** Checkbox per mostrare/nascondere password login */
    @FXML private CheckBox showLoginPasswordCheckBox;

    /**
     * Inizializza il controller configurando il comportamento dei checkbox per mostrare/nascondere
     * le password, associazioni tra campi visibili e invisibili e reset dei contatori di sessione.
     *
     * @throws IOException se si verifica un errore di I/O durante la lettura di risorse
     */
    public void initialize() throws IOException{
        // Password registrazione a scomparsa
        SessionManager.counter = 0;
        SessionManager.counter1 = 0;
        SessionManager.counter2 = 0;

        passRegisterVisible.textProperty().bindBidirectional(passRegister.textProperty());
        //BOTTONE PREFETITI
        //CARICO LE IMMAGINI DI ICONA PER IL BOTTONE DEI PREFERITI

        Image occhioAperto = new Image(getClass().getResource("/com/example/the_knife/icone/occhioAperto.png").toExternalForm());
        Image occhioChiuso = new Image(getClass().getResource("/com/example/the_knife/icone/occhioChiuso.png").toExternalForm());

        ImageView iconView = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
        iconView.setFitWidth(24);
        iconView.setFitHeight(24);//setto il ridimensionamento
        iconView.setImage(occhioChiuso); // Stato iniziale
        showPasswordCheckBox.setGraphic(iconView);
        showPasswordCheckBox.selectedProperty().addListener((obs, oldVal, selected) -> {
            passRegister.setVisible(!selected);
            passRegister.setManaged(!selected);
            if (!selected) {
                 iconView.setImage(occhioChiuso); //imposto inizialmente il bottone cuore pieno o vuoto in base a com'è prima della modifiche
                showPasswordCheckBox.setGraphic(iconView);
            } else {
                iconView.setImage(occhioAperto); //imposto inizialmente il bottone cuore pieno o vuoto in base a com'è prima della modifiche
                showPasswordCheckBox.setGraphic(iconView);
            }
            passRegisterVisible.setVisible(selected);
            passRegisterVisible.setManaged(selected);
        });

        // Password Login a scomparsa
        ImageView iconView2 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
        iconView2.setFitWidth(24);
        iconView2.setFitHeight(24);//setto il ridimensionamento
        iconView2.setImage(occhioChiuso); // Stato iniziale
        showLoginPasswordCheckBox.setGraphic(iconView2);
        passwordFieldVisible.textProperty().bindBidirectional(passwordField.textProperty());
        showLoginPasswordCheckBox.selectedProperty().addListener((obs, oldVal, selected) -> {
            passwordField.setVisible(!selected);
            passwordField.setManaged(!selected);
            if (!selected) {
                iconView2.setImage(occhioChiuso); //imposto inizialmente il bottone cuore pieno o vuoto in base a com'è prima della modifiche
                showLoginPasswordCheckBox.setGraphic(iconView2);
            } else {
                iconView2.setImage(occhioAperto); //imposto inizialmente il bottone cuore pieno o vuoto in base a com'è prima della modifiche
                showLoginPasswordCheckBox.setGraphic(iconView2);
            }
            passwordFieldVisible.setVisible(selected);
            passwordFieldVisible.setManaged(selected);
        });
    }
    /**
     * Gestisce il processo di login.
     * Valida le credenziali, autentica l'utente, crea una sessione e reindirizza alla dashboard.
     *
     * @param event evento che ha attivato il login
     * @throws Exception se si verifica un errore durante il processo di autenticazione
     */
    @FXML
    private void handleLogin(ActionEvent event) throws Exception {
        //METODI CHE CHIAMO

        //DEFINIZIONE handleLogin
        String user = usernameField.getText();
        String pass = passwordField.getText();

        Utente utente = new Utente();
        utente= login(user,pass);//faccio la login con i dati inseriti

        //CREO LA SESSIONE

        if(utente != null && utente.getRuolo().equals("Cliente")){
            SessionManager.getInstance().login(user, utente.getId(), "cliente");
            goTo(event,"Cliente/dashBoardClient.fxml");//vado alla pagina del cliente
        }else if(utente != null && utente.getRuolo().equals("Ristoratore")){
            SessionManager.getInstance().login(user, utente.getId(), "ristoratore");
            goTo(event,"Ristoratore/dashBoardRist.fxml");//vado alla pagina del ristoratore
        }

    }
    /**
     * Chiude l'applicazione.
     *
     * @param event evento generato dal clic su un bottone di uscita
     */
    @FXML
    public void closeProgram(ActionEvent event) {
        System.exit(0);
    }
    /**
     * Gestisce il processo di registrazione utente, valida i dati e aggiorna il file JSON degli utenti.
     * Crea un nuovo utente e lo salva se i dati sono validi.
     */
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
        String newEmail = emailField.getText();
        InputValidator.validaEmail(newEmail);



        // Codifica della password
        newPass = generaHash(passRegister.getText());

        try {
            Utente nuovo = new Utente(name,cognome,indirizzo,newUser,newEmail,newPass,DataNascita,numerotel,role,generaId(role,"fileUtenti.json"),null);
            aggiungiUtente(nuovo, "fileUtenti.json");

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(ruolo.getText());
        System.out.println("Registration successful");
        System.out.println("Riepilogo:");
        System.out.println("Username: "+newUser +"\nPassword: "+newPass+"\nNome: "+name+"\nCognome:" +cognome+"\nNumero di telefono: "+numerotel+"\nData di nascita: "+DataNascita+"\nIndirizzo: "+indirizzo);


    }

    /**
     * Genera un ID univoco per un nuovo utente, basandosi sul numero di ristoratori esistenti.
     *
     * @param ruolo il ruolo dell'utente (Cliente o Ristoratore)
     * @param fileJson il percorso del file JSON degli utenti
     * @return il nuovo ID generato
     */
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
                if (u.getRuolo().equalsIgnoreCase("Ristoratore")) {
                    count++;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return count+1;

    }
    /**
     * Gestisce la selezione del ruolo tramite radio button.
     * Stampa il ruolo selezionato sulla console.
     */
    @FXML
    private void handleRadio() {
        RadioButton selected = (RadioButton) ruoloToggleGroup.getSelectedToggle(); //casto il ruolo dal toggle group che ho definito nel file fxml
        if (selected != null) {
            System.out.println("Ruolo selezionato: " + selected.getText());
        }
    }
    /**
     * Torna alla schermata iniziale.
     *
     * @param event evento associato all'azione dell'utente
     * @throws IOException se il file FXML non è trovato o non leggibile
     */
    @FXML
    public void goToStartPage(ActionEvent event) throws IOException {
        goTo(event,"startPage.fxml");
    }
    /**
     * Converte una password normale in una password hashata tramite algoritmo BCrypt.
     *
     * @param passwordNormal la password in chiaro da cifrare
     * @return la password cifrata in formato hash
     */
    // Metodo per convertire password in pawword cifrata
    public static String generaHash(String passwordNormal) {
        return BCrypt.hashpw(passwordNormal, BCrypt.gensalt());
    }
    /**
     * Aggiunge un nuovo utente alla lista nel file JSON.
     * Valida se l'utente è già registrato e, in caso contrario, aggiorna il file con i nuovi dati.
     *
     * @param nuovo l'oggetto Utente da salvare
     * @param fileJson il file JSON da aggiornare
     */
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
                if(u.getUsername().equalsIgnoreCase(nuovo.getUsername())){
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

            System.out.println("Utente '" + nuovo.getNome() + "' registrato con successo.");

            ObjectNode nuovoRoot = mapper.createObjectNode();// Crea un nuovo oggetto JSON vuoto (root)

            nuovoRoot.set("Utenti", mapper.valueToTree(listaModificabile));// Imposta il nodo "Utenti" con la lista aggiornata di utenti convertita in JSON

            mapper.writerWithDefaultPrettyPrinter().writeValue(file, nuovoRoot);// Scrive l'albero JSON aggiornato nel file, sovrascrivendolo con formattazione leggibile

            handleInput();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Esegue il login sicuro confrontando le credenziali inserite con quelle memorizzate (BCrypt).
     *
     * @param username l'username inserito
     * @param passwordInserita la password in chiaro inserita
     * @return l'oggetto {@link Utente} autenticato, o {@code null} se le credenziali sono errate
     * @throws Exception se si verifica un errore durante il processo
     */
    // Metodo per login sicuro con BCrypt
    public  Utente login(String username, String passwordInserita) throws Exception {
        ObjectMapper mapper = new ObjectMapper(); // Crea un oggetto ObjectMapper di Jackson per la deserializzazione JSON
        mapper.registerModule(new JavaTimeModule()); // Registra un modulo per la gestione corretta di LocalDate e altri tipi Java Time
        ListaUtenti lista = mapper.readValue(new File("fileUtenti.json"), ListaUtenti.class); // Deserializza il file JSON in un oggetto ListaUtenti

        for (Utente u : lista.Utenti) {
            // Se l'username corrisponde
            if (u.getUsername().equals(username)) {
                // Verifica sicura della password usando BCrypt
                // (confronta la password inserita con l'hash salvato nel file)
                if (BCrypt.checkpw(passwordInserita, u.getPassword())) {
                    System.out.println("Login riuscito per utente: " + u.getUsername());
                    return u; // Restituisce l'utente loggato
                } else {
                    handleInput("Errore", "Password errata per utente: " + u.getUsername());
                    System.err.println("Password errata per utente: " + u.getUsername());
                    return null;
                }
            }
        }
        handleInput("Errore", "Username non trovato");
        System.err.println("Username non trovato");
        return null;
    }
    /**
     * Mostra un messaggio informativo in seguito a una registrazione completata con successo.
     */
    protected void handleInput() {
        //if(controllo che tutti gli input siano andati bene allora mando questo messaggio)
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registrazione");
        alert.setHeaderText("Ti sei registrato correttamente");
        alert.setContentText("I tuoi dati sono stati salvati...");
        alert.showAndWait();
        //else mando un errore specifico su un tipo di input inserito dall'utente
    }
    /**
     * Mostra un messaggio di errore personalizzato.
     *
     * @param message1 titolo dell'errore
     * @param message2 contenuto dettagliato dell'errore
     */
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
