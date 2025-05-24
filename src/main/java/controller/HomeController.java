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

/**
 * Controller for the home screen.
 * Displays a welcome message and provides navigation to the profile and exercises.
 */
public class HomeController {

    @FXML private Button profileButton;
    @FXML private Button exercisesButton;
    @FXML private Label welcomeLabel;

    /**
     * Initializes the view with the current user's name.
     */
    @FXML
    public void initialize() {
        User u = Session.getInstance().getCurrentUser();
        welcomeLabel.setText("Welcome, " + u.getUsername() + "!");
    }

    /**
     * Handles the "Exercises" button click.
     * Redirects to the dashboard view.
     */
    @FXML
    private void handleExercisesClick() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ui/dashboard.fxml"));
            Stage stage = (Stage) exercisesButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the "Profile" button click.
     * Redirects to the user's profile page.
     */
    @FXML
    private void handleProfile() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ui/profil.fxml"));
            Stage stage = (Stage) profileButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("My Profile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
