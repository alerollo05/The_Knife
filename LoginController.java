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
 * Controller per la gestione del login e della registrazione dell'utente.
 * Gestisce la visibilità delle password, la validazione dei campi e
 * la comunicazione con il file JSON degli utenti.
 */
public class LoginController extends StartPageController {

    // FXML - Campi legati alla GUI
    @FXML private TextField usernameField;
    @FXML private TextField userRegister;
    @FXML private TextField nomeField;
    @FXML private TextField cognomeField;
    @FXML private ToggleGroup ruoloToggleGroup;
    @FXML private TextField numTel;
    @FXML private DatePicker dataNascita;
    @FXML private TextField indirizzo;
    @FXML private TextField emailField;
    @FXML private RadioButton ruolo;
    @FXML private PasswordField passRegister;
    @FXML private TextField passRegisterVisible;
    @FXML private CheckBox showPasswordCheckBox;
    @FXML private PasswordField passwordField;
    @FXML private TextField passwordFieldVisible;
    @FXML private CheckBox showLoginPasswordCheckBox;

    /**
     * Inizializza lo stato della schermata: reset contatori, imposta toggle visibilità password, carica icone.
     *
     * @throws IOException se le icone non vengono caricate correttamente.
     */
    public void initialize() throws IOException {
        SessionManager.counter = 0;
        SessionManager.counter1 = 0;
        SessionManager.counter2 = 0;

        passRegisterVisible.textProperty().bindBidirectional(passRegister.textProperty());

        Image occhioAperto = new Image(getClass().getResource("/com/example/the_knife/icone/occhioAperto.png").toExternalForm());
        Image occhioChiuso = new Image(getClass().getResource("/com/example/the_knife/icone/occhioChiuso.png").toExternalForm());

        ImageView iconView = new ImageView();
        iconView.setFitWidth(24);
        iconView.setFitHeight(24);
        iconView.setImage(occhioChiuso);
        showPasswordCheckBox.setGraphic(iconView);
        showPasswordCheckBox.selectedProperty().addListener((obs, oldVal, selected) -> {
            passRegister.setVisible(!selected);
            passRegister.setManaged(!selected);
            iconView.setImage(selected ? occhioAperto : occhioChiuso);
            showPasswordCheckBox.setGraphic(iconView);
            passRegisterVisible.setVisible(selected);
            passRegisterVisible.setManaged(selected);
        });

        ImageView iconView2 = new ImageView();
        iconView2.setFitWidth(24);
        iconView2.setFitHeight(24);
        iconView2.setImage(occhioChiuso);
        showLoginPasswordCheckBox.setGraphic(iconView2);
        passwordFieldVisible.textProperty().bindBidirectional(passwordField.textProperty());
        showLoginPasswordCheckBox.selectedProperty().addListener((obs, oldVal, selected) -> {
            passwordField.setVisible(!selected);
            passwordField.setManaged(!selected);
            iconView2.setImage(selected ? occhioAperto : occhioChiuso);
            showLoginPasswordCheckBox.setGraphic(iconView2);
            passwordFieldVisible.setVisible(selected);
            passwordFieldVisible.setManaged(selected);
        });
    }

    /**
     * Gestisce il login controllando username e password con hash BCrypt.
     *
     * @param event Evento generato dal pulsante di login.
     * @throws Exception in caso di errori di accesso al file JSON.
     */
    @FXML
    private void handleLogin(ActionEvent event) throws Exception {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        Utente utente = login(user, pass);

        if (utente != null && utente.getRuolo().equals("Cliente")) {
            SessionManager.getInstance().login(user, utente.getId(), "cliente");
            goTo(event, "Cliente/dashBoardClient.fxml");
        } else if (utente != null && utente.getRuolo().equals("Ristoratore")) {
            SessionManager.getInstance().login(user, utente.getId(), "ristoratore");
            goTo(event, "Ristoratore/dashBoardRist.fxml");
        }
    }

    /**
     * Chiude il programma.
     *
     * @param event Evento associato alla chiusura del programma.
     */
    @FXML
    public void closeProgram(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Gestisce la registrazione utente dopo validazione dei campi e salvataggio nel file.
     */
    @FXML
    public void handleRegister() {
        handleRadio();

        String newUser = userRegister.getText().trim();
        InputValidator.validaUsername(newUser);

        String newPass = passRegister.getText().trim();
        InputValidator.validaPassword(newPass);

        String name = nomeField.getText().trim();
        InputValidator.validaNomeUte(newUser);

        String cognome = cognomeField.getText().trim();
        InputValidator.validaCogno(cognome);

        String numerotel = numTel.getText().trim().replaceAll("[\\s-]", "");
        InputValidator.validaTelefono(numerotel);

        String indirizzo = this.indirizzo.getText().trim();
        InputValidator.validaIndirizzo(indirizzo);

        LocalDate DataNascita = dataNascita.getValue();

        RadioButton ruolo = (RadioButton) this.ruoloToggleGroup.getSelectedToggle();
        String role = ruolo.getText();

        String newEmail = emailField.getText();
        InputValidator.validaEmail(newEmail);

        newPass = generaHash(passRegister.getText());

        try {
            Utente nuovo = new Utente(name, cognome, indirizzo, newUser, newEmail, newPass, DataNascita, numerotel, role, generaId(role, "fileUtenti.json"), null);
            aggiungiUtente(nuovo, "fileUtenti.json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Ruolo: " + ruolo.getText());
    }

    /**
     * Genera un ID per l’utente in base al ruolo.
     *
     * @param ruolo Ruolo dell’utente (Cliente o Ristoratore).
     * @param fileJson Percorso del file JSON.
     * @return ID generato.
     */
    public static int generaId(String ruolo, String fileJson) {
        if (ruolo.equalsIgnoreCase("Cliente")) return 0;

        int count = 0;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            File file = new File(fileJson);

            if (!file.exists()) return 0;

            JsonNode root = mapper.readTree(file);
            List<Utente> listaModificabile = new ArrayList<>();

            JsonNode utentiNode = root.get("Utenti");

            if (utentiNode != null && utentiNode.isArray()) {
                Utente[] utentiArray = mapper.treeToValue(utentiNode, Utente[].class);
                listaModificabile = new ArrayList<>(Arrays.asList(utentiArray));
            }

            for (Utente u : listaModificabile) {
                if (u.getRuolo().equalsIgnoreCase("Ristoratore")) {
                    count++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count + 1;
    }

    /**
     * Mostra in console il ruolo selezionato nel form di registrazione.
     */
    @FXML
    private void handleRadio() {
        RadioButton selected = (RadioButton) ruoloToggleGroup.getSelectedToggle();
        if (selected != null) {
            System.out.println("Ruolo selezionato: " + selected.getText());
        }
    }

    /**
     * Torna alla schermata iniziale.
     *
     * @param event Evento che genera il ritorno alla start page.
     * @throws IOException se il file FXML non viene trovato.
     */
    @FXML
    public void goToStartPage(ActionEvent event) throws IOException {
        goTo(event, "startPage.fxml");
    }

    /**
     * Cifra una password in hash BCrypt.
     *
     * @param passwordNormal Password in chiaro.
     * @return Password cifrata con BCrypt.
     */
    public static String generaHash(String passwordNormal) {
        return BCrypt.hashpw(passwordNormal, BCrypt.gensalt());
    }

    /**
     * Aggiunge un nuovo utente al file JSON.
     *
     * @param nuovo Nuovo utente da aggiungere.
     * @param fileJson Percorso del file JSON.
     */
    public void aggiungiUtente(Utente nuovo, String fileJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            File file = new File(fileJson);

            if (!file.exists()) return;

            ListaUtenti lista = mapper.readValue(file, ListaUtenti.class);

            for (Utente u : lista.Utenti) {
                if (u.getUsername().equalsIgnoreCase(nuovo.getUsername())) {
                    handleInput("Errore", "Utente già registrato");
                    throw new UtenteException("Utente già registrato");
                }
            }

            JsonNode root = mapper.readTree(file);
            List<Utente> listaModificabile = new ArrayList<>();

            JsonNode utentiNode = root.get("Utenti");
            if (utentiNode != null && utentiNode.isArray()) {
                Utente[] utentiArray = mapper.treeToValue(utentiNode, Utente[].class);
                listaModificabile = new ArrayList<>(Arrays.asList(utentiArray));
            }

            listaModificabile.add(nuovo);
            ObjectNode nuovoRoot = mapper.createObjectNode();
            nuovoRoot.set("Utenti", mapper.valueToTree(listaModificabile));
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, nuovoRoot);

            handleInput();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Esegue il login dell'utente verificando l'hash della password.
     *
     * @param username Username inserito.
     * @param passwordInserita Password in chiaro.
     * @return Utente autenticato, altrimenti null.
     * @throws Exception in caso di errore di lettura del file JSON.
     */
    public Utente login(String username, String passwordInserita) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ListaUtenti lista = mapper.readValue(new File("fileUtenti.json"), ListaUtenti.class);

        for (Utente u : lista.Utenti) {
            if (u.getUsername().equals(username)) {
                if (BCrypt.checkpw(passwordInserita, u.getPassword())) {
                    System.out.println("Login riuscito per utente: " + u.getUsername());
                    return u;
                } else {
                    handleInput("Errore", "Password errata per utente: " + u.getUsername());
                    return null;
                }
            }
        }
        handleInput("Errore", "Username non trovato");
        return null;
    }

    /**
     * Mostra un messaggio di successo generico (informativo).
     */
    protected void handleInput() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registrazione");
        alert.setHeaderText("Ti sei registrato correttamente");
        alert.setContentText("I tuoi dati sono stati salvati...");
        alert.showAndWait();
    }

    /**
     * Mostra un messaggio di errore personalizzato.
     *
     * @param message1 Titolo dell'errore.
     * @param message2 Descrizione dell'errore.
     */
    protected void handleInput(String message1, String message2) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Registrazione");
        alert.setHeaderText(message1);
        alert.setContentText(message2);
        alert.showAndWait();
    }
}
