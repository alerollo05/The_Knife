package com.example.the_knife.Ristoratore;

import com.example.the_knife.Utente.SessionManager;
import com.example.the_knife.Utente.Utente;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
/**
 * Controller per la pagina profilo del ristoratore.
 * <p>
 * Questa classe gestisce la visualizzazione e la modifica delle informazioni personali
 * dell'utente ristoratore come nome, cognome, email, username, password, indirizzo, data di nascita e telefono.
 * Permette inoltre di aprire popup per modificare tali dati e aggiorna dinamicamente la UI.
 * </p>
 * <p>
 * Estende {@link DashBoardRistController} e utilizza {@link SessionManager} per accedere ai dati utente.
 * </p>
 */
public class ProfilePageRistController extends DashBoardRistController {

    /** Etichetta che mostra il messaggio di benvenuto con il nome utente */
    @FXML
    private Label welcomeLabel;

    /** Griglia principale che contiene i dati del profilo dell’utente */
    @FXML
    private GridPane grid;

    /** Riferimento al controller principale per gestire aggiornamenti da popup */
    private ProfilePageRistController mainController;

    /** Istanza della sessione per accedere ai dati dell’utente loggato */
    SessionManager session = SessionManager.getInstance();

    /** Username dell’utente attualmente loggato */
    private String user = session.getUsername();

    /** ID dell’utente attualmente loggato */
    private int id = session.getUserId();

    /** Ruolo dell’utente attualmente loggato (es. "ristoratore") */
    private String ruolo = session.getRuolo();
    /**
     * Metodo chiamato al logout dell’utente. Chiude la sessione.
     *
     * @param event evento associato al logout
     */
    public void handleLogOut(ActionEvent event) {
        super.handleLogOut(event);
    }
    /**
     * Chiude il programma quando richiesto.
     *
     * @param event evento associato alla chiusura dell'applicazione
     */
    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }
    /**
     * Metodo chiamato all'inizializzazione del controller.
     * Inizializza l’interfaccia utente con i dati del profilo dell’utente loggato.
     */
    @FXML
    public void initialize() {
        welcomeLabel.setText("IL TUO PROFILO " + user.toUpperCase());
        System.out.println("Utente: "+user+ "Id: "+id+"Ruolo: "+ruolo);
        printDettaglioUtente("fileUtenti.json", "popUpProf.fxml");
    }
    /**
     * Visualizza i dettagli dell'utente nella griglia e imposta i pulsanti per la modifica.
     *
     * @param fileJson      percorso al file JSON contenente gli utenti
     * @param filePopUpFXML percorso al file FXML del popup di modifica
     */
    public void printDettaglioUtente(String fileJson,String filePopUpFXML) {
        try{

            grid.getChildren().clear(); //evita duplicati quando aggiorni il file con le modifiche
            grid.getColumnConstraints().clear(); //reset dei vincoli

            Utente utente = riepilogoUtente(fileJson);

            System.out.println(utente.getEmail());

            Label nomeLabel = new Label("Nome: ");
            nomeLabel.getStyleClass().add("textNormal");
            Label nome = new Label(utente.getNome());
            nome.getStyleClass().add("textNormal");
            Button modifyname = new Button();
            modifyname.getStyleClass().add("accent-button");
            //ICONA MODIFICA
            Image modifica = new Image(getClass().getResource("/com/example/the_knife/icone/modifica.png").toExternalForm());
            ImageView iconView = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
            iconView.setFitWidth(24);
            iconView.setFitHeight(24);//setto il ridimensionamento
            modifyname.setGraphic(iconView);
            iconView.setImage(modifica);
            modifyname.setOnAction(e -> {
                SessionManager.idScelta = 1;
                openPopupProf("Cambia nome",filePopUpFXML);
            });

            Label cognomeLabel = new Label("Cognome: ");
            cognomeLabel.getStyleClass().add("textNormal");
            Label cognome = new Label(utente.getCognome());
            cognome.getStyleClass().add("textNormal");
            Button modifycognome = new Button();
            modifycognome.getStyleClass().add("accent-button");
            //ICONA MODIFICA
            Image modifica2 = new Image(getClass().getResource("/com/example/the_knife/icone/modifica.png").toExternalForm());
            ImageView iconView2 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
            iconView2.setFitWidth(24);
            iconView2.setFitHeight(24);//setto il ridimensionamento
            modifycognome.setGraphic(iconView2);
            iconView2.setImage(modifica2);
            modifycognome.setOnAction(e -> {
                SessionManager.idScelta = 2;
                openPopupProf("Cambia cognome",filePopUpFXML);
            });

            Label emailLabel = new Label("Email: ");
            emailLabel.getStyleClass().add("textNormal");
            Label email = new Label(utente.getEmail());
            email.getStyleClass().add("textNormal");
            Button modifyemail = new Button();
            modifyemail.getStyleClass().add("accent-button");
            //ICONA MODIFICA
            Image modifica3 = new Image(getClass().getResource("/com/example/the_knife/icone/modifica.png").toExternalForm());
            ImageView iconView3 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
            iconView3.setFitWidth(24);
            iconView3.setFitHeight(24);//setto il ridimensionamento
            modifyemail.setGraphic(iconView3);
            iconView3.setImage(modifica3);
            modifyemail.setOnAction(e -> {
                SessionManager.idScelta = 3;
                openPopupProf("Cambia email",filePopUpFXML);
            });

            Label usernameLabel = new Label("Username: ");
            usernameLabel.getStyleClass().add("textNormal");
            Label username = new Label(utente.getUsername());
            username.getStyleClass().add("textNormal");
            Button modifyusername = new Button();
            modifyusername.getStyleClass().add("accent-button");
            //ICONA MODIFICA
            Image modifica4 = new Image(getClass().getResource("/com/example/the_knife/icone/modifica.png").toExternalForm());
            ImageView iconView4 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
            iconView4.setFitWidth(24);
            iconView4.setFitHeight(24);//setto il ridimensionamento
            modifyusername.setGraphic(iconView4);
            iconView4.setImage(modifica4);
            modifyusername.setOnAction(e -> {
                SessionManager.idScelta = 4;
                openPopupProf("Cambia username",filePopUpFXML);
            });

            Label passwordLabel = new Label("Password: ");
            passwordLabel.getStyleClass().add("textNormal");
            Label password = new Label("Password nascosta...");
            password.getStyleClass().add("textNormal");
            Button modifypassword = new Button();
            modifypassword.getStyleClass().add("accent-button");
            //ICONA MODIFICA
            Image modifica5 = new Image(getClass().getResource("/com/example/the_knife/icone/modifica.png").toExternalForm());
            ImageView iconView5 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
            iconView5.setFitWidth(24);
            iconView5.setFitHeight(24);//setto il ridimensionamento
            modifypassword.setGraphic(iconView5);
            iconView5.setImage(modifica5);
            modifypassword.setOnAction(e -> {
                SessionManager.idScelta = 5;
                openPopupProf("Cambia password",filePopUpFXML);
            });

            Label indirizzoLabel = new Label("Indirizzo: ");
            indirizzoLabel.getStyleClass().add("textNormal");
            Label indirizzo = new Label(utente.getIndirizzo());
            indirizzo.getStyleClass().add("textNormal");
            Button modifindirizzo = new Button();
            modifindirizzo.getStyleClass().add("accent-button");
            //ICONA MODIFICA
            Image modifica6 = new Image(getClass().getResource("/com/example/the_knife/icone/modifica.png").toExternalForm());
            ImageView iconView6 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
            iconView6.setFitWidth(24);
            iconView6.setFitHeight(24);//setto il ridimensionamento
            modifindirizzo.setGraphic(iconView6);
            iconView6.setImage(modifica6);
            modifindirizzo.setOnAction(e -> {
                SessionManager.idScelta = 6;
                openPopupProf("Cambia indirizzo",filePopUpFXML);
            });

            Label dataLabel = new Label("Data di nascita: ");
            dataLabel.getStyleClass().add("textNormal");
            Label data = new Label(""+utente.getDataDiNascita());
            data.getStyleClass().add("textNormal");
            Button modifodata = new Button();
            modifodata.getStyleClass().add("accent-button");
            //ICONA MODIFICA
            Image modifica7 = new Image(getClass().getResource("/com/example/the_knife/icone/modifica.png").toExternalForm());
            ImageView iconView7 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
            iconView7.setFitWidth(24);
            iconView7.setFitHeight(24);//setto il ridimensionamento
            modifodata.setGraphic(iconView7);
            iconView7.setImage(modifica7);
            modifodata.setOnAction(e -> {
                SessionManager.idScelta = 7;
                openPopupProf("Cambia data di nascita",filePopUpFXML);
            });

            Label TelefonoLabel = new Label("Telefono: ");
            TelefonoLabel.getStyleClass().add("textNormal");
            Label Telefono = new Label(utente.getTelefono());
            Telefono.getStyleClass().add("textNormal");
            Button modiftelefono = new Button();
            modiftelefono.getStyleClass().add("accent-button");
            //ICONA MODIFICA
            Image modifica8 = new Image(getClass().getResource("/com/example/the_knife/icone/modifica.png").toExternalForm());
            ImageView iconView8 = new ImageView();//creo l'immagine visibile in nel bottone quando poi gli assegnerò le due immagini
            iconView8.setFitWidth(24);
            iconView8.setFitHeight(24);//setto il ridimensionamento
            modiftelefono.setGraphic(iconView8);
            iconView8.setImage(modifica8);
            modiftelefono.setOnAction(e -> {
                SessionManager.idScelta = 8;
                openPopupProf("Cambia telefono",filePopUpFXML);
            });

            Label RuoloLabel = new Label("Ruolo: ");
            RuoloLabel.getStyleClass().add("textNormal");
            Label Ruolo = new Label(utente.getRuolo());
            Ruolo.getStyleClass().add("textNormal");

            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(20);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(60);
            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPercentWidth(20);
            grid.getColumnConstraints().addAll(col1, col2, col3);

            grid.setPrefWidth(Double.MAX_VALUE);
            grid.setMaxWidth(Double.MAX_VALUE);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(15));
            grid.getStyleClass().add("list-rist");

            //forzatura dei label a crescere orizzontalmente
            GridPane.setFillWidth(nome, true);
            GridPane.setFillWidth(cognome, true);
            GridPane.setFillWidth(email, true);
            GridPane.setFillWidth(username, true);
            GridPane.setFillWidth(password, true);
            GridPane.setFillWidth(indirizzo, true);
            GridPane.setFillWidth(data, true);
            GridPane.setFillWidth(Telefono, true);
            GridPane.setFillWidth(Ruolo, true);

            grid.add(nomeLabel,0,0);
            grid.add(nome,1,0);
            grid.add(modifyname,2,0);
            grid.add(cognomeLabel,0,1);
            grid.add(cognome,1,1);
            grid.add(modifycognome,2,1);
            grid.add(emailLabel,0,2);
            grid.add(email,1,2);
            grid.add(modifyemail,2,2);
            grid.add(usernameLabel,0,3);
            grid.add(username,1,3);
            grid.add(modifyusername,2,3);
            grid.add(passwordLabel,0,4);
            grid.add(password,1,4);
            grid.add(modifypassword,2,4);
            grid.add(indirizzoLabel,0,5);
            grid.add(indirizzo,1,5);
            grid.add(modifindirizzo,2,5);
            grid.add(dataLabel,0,6);
            grid.add(data,1,6);
            grid.add(modifodata,2,6);
            grid.add(TelefonoLabel,0,7);
            grid.add(Telefono,1,7);
            grid.add(modiftelefono,2,7);
            grid.add(RuoloLabel,0,8);
            grid.add(Ruolo,1,8);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Apre una finestra di popup per modificare un campo del profilo.
     *
     * @param title          titolo della finestra popup
     * @param filePopUpFXML  file FXML della finestra popup
     */
    public void openPopupProf(String title,String filePopUpFXML) { //aggiungere variabile stringa nei parametri passati per percorso file .fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(filePopUpFXML));
            Parent root = loader.load();
            Stage popupStage = new Stage();

            //SERVE A AGGIORNARE I DATI QUANDO CHIUDO LA FINESTRA DI POPUP
            PopUpProfController controller = loader.getController();
            controller.setMainController((ProfilePageRistController) this);

            popupStage.setTitle(title);
            popupStage.initModality(Modality.APPLICATION_MODAL); // Blocca la finestra principale
            popupStage.setScene(new Scene(root,650,200));
            popupStage.setResizable(false); // blocca il ridimensionamento
            popupStage.showAndWait(); // Mostra la finestra e attende la chiusura
        }catch (IOException e){ System.err.println("Errore nel caricamento del file FXML:");
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Imposta il controller principale, utile per aggiornare la UI dopo modifiche nei popup.
     *
     * @param controller riferimento al controller della pagina profilo
     */
    public void setMainController(ProfilePageRistController controller) {
        this.mainController = controller;
    }

    /**
     * Carica l’oggetto {@link Utente} dal file JSON corrispondente all’utente loggato.
     *
     * @param fileUte percorso al file JSON
     * @return {@link Utente} trovato oppure null se non esiste
     * @throws IOException in caso di errore di lettura del file
     */
    public Utente riepilogoUtente(String fileUte) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // <-- fondamentale!
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // opzionale ma utile
        JsonNode root = mapper.readTree(new File(fileUte));
        JsonNode utentiNode = root.get("Utenti");

        List<Utente> utenti = Arrays.asList(mapper.treeToValue(utentiNode, Utente[].class));

        for (Utente u : utenti) {
            if (u.getUsername().equals(user)) {
                return u; // trovato il ristorante con id univoco
            }
        }
        return null;
    }

    /**
     * Metodo associato al pulsante per aggiungere un nuovo ristorante.
     *
     * @param event evento di click
     * @throws IOException in caso di errore nel caricamento della schermata
     */
    @FXML
    public void onAddRistClick(ActionEvent event) throws IOException {
        super.onAddRistClick(event);
    }
    /**
     * Metodo associato al pulsante che porta alla lista dei ristoranti del ristoratore.
     *
     * @param event evento di click
     * @throws IOException in caso di errore nel caricamento della schermata
     */
    @FXML
    protected void onRistorantiClick(ActionEvent event) throws IOException {
        super.onRistorantiClick(event);
    }
    /**
     * Metodo associato al pulsante "indietro", che riporta alla schermata precedente.
     *
     * @param event evento di click
     * @throws IOException in caso di errore nel caricamento della schermata
     */
    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        super.goBack(event);
    }
}
