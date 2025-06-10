package com.example.the_knife;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class StartPageController {
    @FXML
    private TextField locationSearch;
    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
        try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
        Scene loginScene = new Scene(loader.load(),900,800);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(loginScene);
        stage.setTitle("The_Knife");
        stage.setResizable(false); // Impedisce il resize manuale
        stage.setMaximized(false); // Impedisce l'avvio in modalità massimizzata
        stage.show();
    } catch (IOException e) {
        System.err.println("Errore nel caricamento del file FXML:");
        e.printStackTrace();
    } catch (NullPointerException e) {
        System.err.println("Il path al file FXML è nullo o errato:");
        e.printStackTrace();
    } catch (Exception e) {
        System.err.println("Errore imprevisto:");
        e.printStackTrace();
    }
    }
    @FXML
    public void goTo(ActionEvent event, String location) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
            Scene loginScene = new Scene(loader.load(), 900, 800);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(loginScene);
            stage.setTitle("The_Knife");
            stage.setResizable(false); // Impedisce il resize manuale
            stage.setMaximized(false); // Impedisce l'avvio in modalità massimizzata
            stage.show();
        } catch (IOException e) {
            System.err.println("Errore nel caricamento del file FXML:");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Il path al file FXML è nullo o errato:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Errore imprevisto:");
            e.printStackTrace();
        }
    }

    @FXML
    public void closeProgram(ActionEvent event) {
        System.exit(0);
    }
    @FXML
    private void onSearchClicked(){
        String location = locationSearch.getText();
        System.out.println(location);
        System.out.println("Searched");
    }

}
