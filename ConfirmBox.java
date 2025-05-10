import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

        static boolean answer;

        public static boolean display(String title, String message){
            Stage window = new Stage();
            Label label1 = new Label(message);
            window.initModality(Modality.APPLICATION_MODAL);//rende la finestra per forza da guardare per andare avanti con la finestra sotto
            window.setTitle(title);
            window.setMinHeight(250); // definiamo la grandezza della finestra di alert
            window.setMinWidth(250);

            //Creazione bottoni si/no
            Button yesButton = new Button("Yes");
            Button noButton = new Button("No");

            yesButton.setOnAction(e -> {
                answer = true;
                window.close();
            });//imposto la risposta da passare al Main yes quindi answer = TRUE

            noButton.setOnAction(e -> {
                answer = false;
                window.close();
            });//imposto la risposta da passare al Main no quindi answer = FALSE

            VBox layout = new VBox(10);
            layout.getChildren().addAll(label1,yesButton,noButton); //Scrivo a schermo i due bottoni e la scritta
            layout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait(); // aspetta che venga chiusa prima di tornare alla scena principale

            return answer;
        }
    }

