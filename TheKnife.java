package com.example.the_knife;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;

/**
 * Classe principale dell'applicazione JavaFX "The_Knife".
 * <p>
 * Questa classe estende {@link javafx.application.Application} e rappresenta il punto di ingresso
 * per l'avvio dell'interfaccia grafica. Carica la scena iniziale da un file FXML e configura lo stage principale.
 * </p>
 */
public class TheKnife extends Application {
    /**
     * Metodo chiamato automaticamente all'avvio dell'applicazione JavaFX.
     * <p>
     * Carica l'interfacia grafica definita in {@code startPage.fxml}, imposta la dimensione della finestra,
     * il titolo, e disabilita il ridimensionamento manuale e la massimizzazione automatica.
     * </p>
     *
     * @param stage lo stage principale fornito da JavaFX all'avvio dell'app
     *
     */
    @Override
    public void start(Stage stage) {
        try {
            URL fxmlPath = TheKnife.class.getResource("/com/example/the_knife/startPage.fxml");
            if (fxmlPath == null) {
                System.err.println("Errore: impossibile trovare il file startPage.fxml");
                Platform.exit();
                return;
            }

            FXMLLoader fxmlLoader = new FXMLLoader(fxmlPath);
            Scene scene = new Scene(fxmlLoader.load(), 950, 750);
            stage.setTitle("The_Knife");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setMaximized(false);
            stage.show();

        } catch (IOException e) {
            System.err.println("Errore nel caricamento dell'interfaccia grafica (FXML): " + e.getMessage());
            e.printStackTrace();
            Platform.exit();
        } catch (Exception e) {
            System.err.println("Errore imprevisto durante l'avvio dell'applicazione: " + e.getMessage());
            e.printStackTrace();
            Platform.exit();
        }
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
        try {
            // Log degli errori su file
            String logPath = System.getProperty("user.home") + File.separator + "TheKnife_error_log.txt";
            System.setErr(new PrintStream(logPath));
            System.out.println("Working dir: " + new File(".").getAbsolutePath());
            launch(args);
        } catch (Exception e) {
            System.err.println("Errore irreversibile:");
            e.printStackTrace();
            Platform.exit(); //chiude JavaFX pulitamente
            System.exit(1); //forza l'uscita con codice errore
        }
    }
}