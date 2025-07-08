package com.example.the_knife;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Classe principale dell'applicazione JavaFX "The_Knife".
 * <p>
 * Questa classe estende {@link javafx.application.Application} e rappresenta il punto di ingresso
 * per l'avvio dell'interfaccia grafica. Carica la scena iniziale da un file FXML e configura lo stage principale.
 * </p>
 */
public class Main extends Application {
    /**
     * Metodo chiamato automaticamente all'avvio dell'applicazione JavaFX.
     * <p>
     * Carica l'interfaccia grafica definita in {@code startPage.fxml}, imposta la dimensione della finestra,
     * il titolo, e disabilita il ridimensionamento manuale e la massimizzazione automatica.
     * </p>
     *
     * @param stage lo stage principale fornito da JavaFX all'avvio dell'app
     * @throws IOException se si verifica un errore nel caricamento del file FXML
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
     * <p>
     * Questo metodo richiama {@link javafx.application.Application#launch(String...)} per iniziare
     * il ciclo di vita dell'applicazione.
     * </p>
     *
     * @param args eventuali argomenti da riga di comando (non usati)
     */
    public static void main(String[] args) {
        launch();
    }
}