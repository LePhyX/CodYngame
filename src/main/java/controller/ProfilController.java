package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.User;
import utils.Session;
import model.SubmissionDAO;
import model.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class ProfilController {

    @FXML private Button backButton;
    @FXML private Label usernameLabel;
    @FXML private Label emailLabel;
    @FXML private Label scoreLabel;
    @FXML private Label createdAtLabel;
    @FXML private Button submissionsButton;
    @FXML private Button successButton;
    @FXML private Button failButton;
    @FXML private Label passwordLabel;
    @FXML private Button editButton;

    @FXML
    public void initialize() {
        User u = Session.getInstance().getCurrentUser();

        usernameLabel.setText("User name : "  + u.getUsername());
        emailLabel.setText   ("Email : "      + u.getEmail());
        passwordLabel.setText("Password : "   + "********");

        try (Connection conn = DatabaseConnection.getConnection()) {
            SubmissionDAO dao = new SubmissionDAO(conn);
            int score = dao.getScoreByUser(u.getId());  // Score dynamique
            scoreLabel.setText("Score : " + score);
        } catch (SQLException e) {
            e.printStackTrace();
            scoreLabel.setText("Score : (erreur)");
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy 'à' HH'h'mm");
        createdAtLabel.setText("Registered since : " + u.getCreatedAt().toLocalDateTime().format(fmt));

        submissionsButton.setText("View Submissions");

    }

    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ui/home.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEdit() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ui/edit_profile.fxml"));
            Stage stage = (Stage) editButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Profile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSubmissions() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/submission.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) submissionsButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("My Submissions");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSuccess() {
        System.out.println("✅ Aller vers la page des réussites...");
    }

    @FXML
    private void handleFails() {
        System.out.println("❌ Aller vers la page des échecs...");
    }
}