import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
public class AlertBox {

    public static void display(String title, String message){
        Stage window = new Stage();
        Label label1 = new Label(message);
        window.initModality(Modality.APPLICATION_MODAL);//rende la finestra per forza da guardare per andare avanti con la finestra sotto
        window.setTitle(title);
        window.setMinHeight(250); // definiamo la grandezza della finestra di alert
        window.setMinWidth(250);
        Button closeButton = new Button("Chiudi la finestra");
        closeButton.setOnAction(e -> window.close());//chiudi la finestra di alert una volta cliccato il bottone

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label1,closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait(); // aspetta che venga chiusa prima di tornare alla scena principale
    }
}
