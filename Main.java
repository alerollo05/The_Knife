package com.example.the_knife;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("startPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 800);
        stage.setTitle("The_Knife");
        stage.setScene(scene);
        stage.setResizable(false); // Impedisce il resize manuale
        stage.setMaximized(false); // Impedisce l'avvio in modalità massimizzata
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}