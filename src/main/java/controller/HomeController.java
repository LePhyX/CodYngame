package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.User;
import utils.Session;

import java.io.IOException;

public class HomeController {

    @FXML private Button profileButton;
    @FXML private Button exercisesButton;
    @FXML private Label welcomeLabel;

    @FXML
    public void initialize() {
        // Affiche directement l'utilisateur de la session
        User u = Session.getInstance().getCurrentUser();
        welcomeLabel.setText("Bienvenue, " + u.getUsername() + " !");
    }

    @FXML
    private void handleExercisesClick() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/ui/dashboard.fxml"));
            Stage stage = (Stage) exercisesButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleProfile() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/ui/profil.fxml"));
            Stage stage = (Stage) profileButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Mon Profil");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}