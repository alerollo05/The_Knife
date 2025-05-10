import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application  { //implementiamo EventHandler ogni volta che dobbiamo gestire un evento che arriva da un input esterno
    Stage window;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage; //solo per avere meglio in testa che lo stage è la finestra
        window.setTitle("Prova2JavaFX");

        //GridPane divido la scena in una griglia
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10)); //Mette 10 px di spazio attorno alla scena allontanando i bordi
        grid.setVgap(8);        //definisco le spaziature della griglia e delle celle
        grid.setHgap(10);

        //label name
        Label nameLabel = new Label("Username");
        GridPane.setConstraints(nameLabel,0,0); //definisce nella tabella cosa scriverci dentro e le cordinatr j e i del dove metterle

        //Name input
        TextField nameInput = new TextField("Alessandro");
        GridPane.setConstraints(nameInput,1,0); //ATTENZIONE le coordinate sulla griglia si definiscono al contrario prima colonne poi le righe

        //label età
        Label ageLabel = new Label("Età:");
        GridPane.setConstraints(ageLabel,0,1); //definisce nella tabella cosa scriverci dentro e le cordinatr j e i del dove metterle

        //Età input
        TextField ageInput = new TextField();
        ageInput.setPromptText("18");
        GridPane.setConstraints(ageInput,1,1);

        //label pasword
        Label pswLabel = new Label("Password");
        GridPane.setConstraints(pswLabel,0,2); //definisce nella tabella cosa scriverci dentro e le cordinatr i e j del dove metterle

        //Name password
        TextField pswInput = new TextField();
        pswInput.setPromptText("Password"); // mette la password che sparisce quando entro nella casella di testo (runna per capire la differenza)
        GridPane.setConstraints(pswInput,1,2);

        Button loginButton = new Button("Login"); // creo il bottone di login

        loginButton.setOnAction(e -> {
                isInt(ageInput,ageInput.getText());//vai in fondo a vedere il metodo isInt()
        });

        /*  //prendere un input classico senza controlli di tipo di dato inserito
        loginButton.setOnAction(e -> {
            System.out.println("Login Button pressed");
            System.out.println("Username: " + nameInput.getText());
            System.out.println("Password: " + pswInput.getText());

        }); */
        GridPane.setConstraints(loginButton,1,3); //seconda colonna prima  riga
        grid.getChildren().addAll(nameLabel,nameInput,ageLabel,ageInput,pswLabel,pswInput,loginButton); //aggiungo tutti i bottoni e textfield creati alla grilia

        Scene scene = new Scene(grid,800,700); //Creo la scena da mostrare a schermo
        /*
        //BorderPane
        //Definizione del menu in alto
        HBox topMenu = new HBox(); //Al posto di VBox che scende in modo verticale HBox divide in orizzontale
        Button button1 = new Button("File");
        Button button2 = new Button("Edit");
        Button button3 = new Button("View");
        topMenu.getChildren().addAll(button1,button2, button3);

        //Definizione del menu di sx
        VBox leftMenu = new VBox();
        Button button4 = new Button("4");
        Button button5 = new Button("5");
        Button button6 = new Button("6");
        leftMenu.getChildren().addAll(button4,button5,button6);

        //Setto i vari menu nelle giuste parti della scena
        //BorderPane divide la schermata per i 4 margini e il centro
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topMenu);
        borderPane.setLeft(leftMenu);
        Scene scene = new Scene(borderPane,800,700);
        */

        window.setScene(scene);
        window.show();
    }
    private boolean isInt(TextField input, String message) {
        try {
            int age = Integer.parseInt(input.getText()); //trasformo il testo in un intero se possibile se no faccio scattare number format exception
            System.out.println("User ha: " + age + " anni");
            return true;
        } catch (NumberFormatException e) { //nel caso in cui l'input nn sia un numero
            System.out.println("Error: " + message + " is not a number");
        }
        return false;
    }
}