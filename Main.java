package com.example.the_knife;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe principale che avvia l'applicazione JavaFX per "The_Knife".
 */
public class Main extends Application {

    /**
     * Metodo di ingresso per l'interfaccia grafica JavaFX.
     * Carica il file FXML iniziale (startPage.fxml) e configura la finestra principale.
     *
     * @param stage Finestra principale dell'applicazione.
     * @throws IOException se il file FXML non viene trovato o non può essere caricato.
     */
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

    /**
     * Metodo main che avvia l'applicazione JavaFX.
     *
     * @param args Argomenti da linea di comando.
     */
    public static void main(String[] args) {
        launch();
    }
}
