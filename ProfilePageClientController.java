package com.example.the_knife.Cliente;


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
 * Controller della pagina profilo utente per la sezione Cliente dell'applicazione.
 * <p>
 * Estende {@link ProfilePageRistController} per riutilizzare funzionalità comuni
 * tra utenti ristoratori e clienti, adattando comportamenti specifici per il cliente.
 * </p>
 *
 * <p>
 * Gestisce la visualizzazione dei dati personali dell'utente cliente e permette la navigazione
 * verso le altre sezioni dell'applicazione come ristoranti o dashboard.
 * </p>
 */
public class ProfilePageClientController extends ProfilePageRistController {

    /** Etichetta di benvenuto che mostra il nome utente */
    @FXML
    private Label welcomeLabel;

    /** Layout principale della pagina contenente i dettagli dell'utente */
    @FXML
    private GridPane grid;

    /** Riferimento al controller principale, utilizzabile per comunicazione tra schermate */
    private ProfilePageRistController mainController;

    /** Istanza della sessione utente corrente */
    SessionManager session = SessionManager.getInstance();

    /** Username dell'utente attualmente autenticato */
    private String user = session.getUsername();

    /** ID dell'utente attualmente autenticato */
    private int id = session.getUserId();

    /** Ruolo dell'utente attualmente autenticato */
    private String ruolo = session.getRuolo();

    /**
     * Metodo chiamato al clic sul pulsante di logout.
     * <p>Effettua il logout dell'utente e ritorna alla schermata di login.</p>
     *
     * @param event evento generato dal clic
     */
    public void handleLogOut(ActionEvent event) {
        super.handleLogOut(event);
    }
    /**
     * Chiude il programma. Override dal controller padre.
     *
     * @param event evento generato dal clic
     */
    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }
    /**
     * Metodo di inizializzazione della schermata.
     * <p>Mostra il nome utente loggato e stampa i dettagli utente leggendo dal file JSON.</p>
     */
    @FXML
    public void initialize() {
        welcomeLabel.setText("IL TUO PROFILO " + user.toUpperCase());
        System.out.println("Utente: "+user+ "Id: "+id+"Ruolo: "+ruolo);
        printDettaglioUtente("fileUtenti.json","/com/example/the_knife/Ristoratore/popUpProf.fxml");
    }
    /**
     * Imposta il controller principale per supportare chiamate incrociate tra schermate.
     *
     * @param controller il controller principale da associare
     */
    public void setMainController(ProfilePageRistController controller) {
        this.mainController = controller;
    }

    /**
     * Gestisce il clic sull'icona del profilo utente.
     * <p>Rimane nella stessa pagina (profilo cliente).</p>
     *
     * @param event evento generato dal clic
     * @throws IOException in caso di errore nella navigazione
     */
    @FXML
    protected void onProfileClick(ActionEvent event) throws IOException {
        super.onProfileClick(event);
    }
    /**
     * Gestisce il ritorno alla dashboard cliente.
     *
     * @param event evento generato dal clic
     * @throws IOException in caso di errore nella navigazione
     */
    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        super.goTo(event, "dashBoardClient.fxml");
    }
    /**
     * Gestisce il clic sulla sezione ristoranti per il cliente.
     *
     * @param event evento generato dal clic
     * @throws IOException in caso di errore nella navigazione
     */

    @FXML
    protected void onRistorantiClick(ActionEvent event) throws IOException {
        super.goTo(event,"ristorantiClient.fxml");
    }

}