package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Button backButton;

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/home.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Home");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private Button includeButton; // 🔧 Nécessaire pour accéder à la scène

    @FXML
    private void handleIncludeClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/IncludeChoice.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) includeButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Faites un choix");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(" Erreur lors du chargement de la page include-choice.fxml");
        }
    }

    @FXML
    private void handleStdinClick() {
        System.out.println("➡ STDIN/STDOUT exercise selected.");
        // TODO : rediriger vers la page des exercices STDIN/STDOUT
    }
}