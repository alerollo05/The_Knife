package com.example.the_knife.Cliente;

import com.example.the_knife.Ristoratore.PopUpProfController;
import com.example.the_knife.Ristoratore.ProfilePageRistController;
import com.example.the_knife.Utente.ListaUtenti;
import com.example.the_knife.Utente.SessionManager;
import com.example.the_knife.Utente.Utente;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;

/**
 * Controller per la pagina profilo del cliente.
 * Estende {@link ProfilePageRistController} per riutilizzare funzionalità comuni,
 * ma adatta la visualizzazione e il comportamento per l’utente con ruolo "cliente".
 */
public class ProfilePageClientController extends ProfilePageRistController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private GridPane grid;

    /**
     * Riferimento al controller principale del profilo ristoratore, utilizzabile
     * se serve comunicazione tra controlli. Non utilizzato direttamente in questa classe.
     */
    private ProfilePageRistController mainController;

    /**
     * Gestione della sessione utente.
     */
    SessionManager session = SessionManager.getInstance();

    /**
     * Nome dell’utente attualmente loggato.
     */
    private String user = session.getUsername();

    /**
     * ID dell’utente attualmente loggato.
     */
    private int id = session.getUserId();

    /**
     * Ruolo dell’utente attualmente loggato.
     */
    private String ruolo = session.getRuolo();

    /**
     * Esegue il logout dell’utente, ereditato da {@link ProfilePageRistController}.
     *
     * @param event evento associato al click sul pulsante di logout
     */
    public void handleLogOut(ActionEvent event) {
        super.handleLogOut(event);
    }

    /**
     * Chiude in modo sicuro l’applicazione, ereditato da {@link ProfilePageRistController}.
     *
     * @param event evento di chiusura
     */
    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }

    /**
     * Inizializza la schermata del profilo cliente:
     * <ul>
     *     <li>Visualizza un messaggio di benvenuto</li>
     *     <li>Stampa nel terminale i dati utente</li>
     *     <li>Mostra i dettagli utente </li>
     * </ul>
     */
    @FXML
    public void initialize() {
        welcomeLabel.setText("IL TUO PROFILO " + user.toUpperCase());
        System.out.println("Utente: " + user + " Id: " + id + " Ruolo: " + ruolo);
        printDettaglioUtente("fileUtenti.json", "/com/example/the_knife/Ristoratore/popUpProf.fxml");
    }

    /**
     * Imposta il controller principale di tipo {@link ProfilePageRistController}.
     *
     * @param controller il controller da associare
     */
    public void setMainController(ProfilePageRistController controller) {
        this.mainController = controller;
    }

    /**
     * Naviga nuovamente alla pagina del profilo.
     * In questo caso chiama il metodo del controller padre.
     *
     * @param event evento associato al click
     * @throws IOException se la pagina non può essere caricata
     */
    @FXML
    protected void onProfileClick(ActionEvent event) throws IOException {
        super.onProfileClick(event);
    }

    /**
     * Torna alla dashboard del cliente.
     *
     * @param event evento associato alla richiesta di ritorno
     * @throws IOException se la pagina non può essere caricata
     */
    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        super.goTo(event, "dashBoardClient.fxml");
    }

    /**
     * Naviga alla lista dei ristoranti visibile al cliente.
     *
     * @param event evento associato alla richiesta di navigazione
     * @throws IOException se la pagina non può essere caricata
     */
    @FXML
    protected void onRistorantiClick(ActionEvent event) throws IOException {
        super.goTo(event, "ristorantiClient.fxml");
    }

    /**
     * Carica l’oggetto {@link Utente} corrente dal file JSON specificato.
     * La ricerca è basata sullo username della sessione.
     *
     * @param fileJson il percorso del file JSON da leggere
     * @return l'utente corrispondente, oppure {@code null} se non trovato o in caso di errore
     */
    private Utente getUtente(String fileJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
            File file = new File(fileJson);

            if (!file.exists()) {
                System.out.println("File non trovato: " + file.getAbsolutePath());
                return null;
            }

            ListaUtenti lista = mapper.readValue(new File(fileJson), ListaUtenti.class);

            for (Utente u : lista.Utenti) {
                if (u.getUsername().equalsIgnoreCase(user)) {
                    return u;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
