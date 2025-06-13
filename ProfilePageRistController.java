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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ProfilePageRistController extends dashBoardRistController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private GridPane grid;

    private ProfilePageRistController mainController;

    SessionManager session = SessionManager.getInstance();
    private String user = session.getUsername();
    private int id = session.getUserId();
    private String ruolo = session.getRuolo();

    public void handleLogOut(ActionEvent event) {
        super.handleLogOut(event);
    }

    @Override
    public void closeProgram(ActionEvent event) {
        super.closeProgram(event);
    }

    @FXML
    public void initialize() {
        welcomeLabel.setText("PROFILO DI " + user + "");
        System.out.println("Utente: "+user+ "Id: "+id+"Ruolo: "+ruolo);
        printDettaglioUtente();
    }

    private void printDettaglioUtente() {
        try{

            grid.getChildren().clear(); //evita duplicati quando aggiorni il file con le modifiche
            grid.getColumnConstraints().clear(); //reset dei vincoli

            Utente utente = riepilogoUtente("fileUtenti.json");

            System.out.println(utente.getEmail());

            Label nomeLabel = new Label("Nome: ");
            nomeLabel.getStyleClass().add("textNormal");
            Label nome = new Label(utente.getNome());
            nome.getStyleClass().add("textNormal");
            Button modifyname = new Button("Modifica");
            modifyname.getStyleClass().add("accent-button");
            modifyname.setOnAction(e -> {
                SessionManager.idScelta = 1;
                openPopupProf("Cambia nome");
            });

            Label cognomeLabel = new Label("Cognome: ");
            cognomeLabel.getStyleClass().add("textNormal");
            Label cognome = new Label(utente.getCognome());
            cognome.getStyleClass().add("textNormal");
            Button modifycognome = new Button("Modifica");
            modifycognome.getStyleClass().add("accent-button");
            modifycognome.setOnAction(e -> {
                SessionManager.idScelta = 2;
                openPopupProf("Cambia cognome");
            });

            Label emailLabel = new Label("Email: ");
            emailLabel.getStyleClass().add("textNormal");
            Label email = new Label(utente.getEmail());
            email.getStyleClass().add("textNormal");
            Button modifyemail = new Button("Modifica");
            modifyemail.getStyleClass().add("accent-button");
            modifyemail.setOnAction(e -> {
                SessionManager.idScelta = 3;
                openPopupProf("Cambia email");
            });

            Label usernameLabel = new Label("Username: ");
            usernameLabel.getStyleClass().add("textNormal");
            Label username = new Label(utente.getUsername());
            username.getStyleClass().add("textNormal");
            Button modifyusername = new Button("Modifica");
            modifyusername.getStyleClass().add("accent-button");
            modifyusername.setOnAction(e -> {
                SessionManager.idScelta = 4;
                openPopupProf("Cambia username");
            });

            Label passwordLabel = new Label("Password: ");
            passwordLabel.getStyleClass().add("textNormal");
            Label password = new Label("Password nascosta...");
            password.getStyleClass().add("textNormal");
            Button modifypassword = new Button("Modifica");
            modifypassword.getStyleClass().add("accent-button");
            modifypassword.setOnAction(e -> {
                SessionManager.idScelta = 5;
                openPopupProf("Cambia password");
            });

            Label indirizzoLabel = new Label("Indirizzo: ");
            indirizzoLabel.getStyleClass().add("textNormal");
            Label indirizzo = new Label(utente.getIndirizzo());
            indirizzo.getStyleClass().add("textNormal");
            Button modifindirizzo = new Button("Modifica");
            modifindirizzo.getStyleClass().add("accent-button");
            modifindirizzo.setOnAction(e -> {
                SessionManager.idScelta = 6;
                openPopupProf("Cambia indirizzo");
            });

            Label dataLabel = new Label("Data di nascita: ");
            dataLabel.getStyleClass().add("textNormal");
            Label data = new Label(""+utente.getDataDiNascita());
            data.getStyleClass().add("textNormal");
            Button modifodata = new Button("Modifica");
            modifodata.getStyleClass().add("accent-button");
            modifodata.setOnAction(e -> {
                SessionManager.idScelta = 7;
                openPopupProf("Cambia data di nascita");
            });

            Label TelefonoLabel = new Label("Telefono: ");
            TelefonoLabel.getStyleClass().add("textNormal");
            Label Telefono = new Label(utente.getTelefono());
            Telefono.getStyleClass().add("textNormal");
            Button modiftelefono = new Button("Modifica");
            modiftelefono.getStyleClass().add("accent-button");
            modiftelefono.setOnAction(e -> {
                SessionManager.idScelta = 8;
                openPopupProf("Cambia telefono");
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

    public void openPopupProf(String title) { //aggiungere variabile stringa nei parametri passati per percorso file .fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("popUpProf.fxml"));
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
    public void setMainController(ProfilePageRistController controller) {
        this.mainController = controller;
    }


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


    @FXML
    public void onAddRistClick(ActionEvent event) throws IOException {
        super.onAddRistClick(event);
    }

    @FXML
    protected void onRistorantiClick(ActionEvent event) throws IOException {
        super.onRistorantiClick(event);
    }

    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        super.goBack(event);
    }
}
