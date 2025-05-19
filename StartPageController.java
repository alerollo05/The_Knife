package com.example.the_knife;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class StartPageController {
    @FXML
    private TextField locationSearch;
    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
        Scene loginScene = new Scene(loader.load(),900,800);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(loginScene);
        stage.setTitle("The_Knife");
        stage.show();
    }
    @FXML
    public void goTo(ActionEvent event, String location) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
        Scene loginScene = new Scene(loader.load(),900,800);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(loginScene);
        stage.setTitle("The_Knife");
        stage.show();
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
