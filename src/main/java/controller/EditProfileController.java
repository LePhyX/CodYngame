package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import model.User;
import utils.PasswordUtils;
import utils.Session;
import model.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditProfileController {

    @FXML private Button confirmButton;
    @FXML private Button backButton;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField currentPasswordField;
    @FXML private PasswordField newPasswordField;

    @FXML
    public void initialize() {
        // Préremplissage des champs avec les infos en session
        User u = Session.getInstance().getCurrentUser();
        usernameField.setText(u.getUsername());
        emailField.setText(u.getEmail());
    }

    @FXML
    private void handleBack() {
        // Retour à la vue Profil (ProfilController lira la Session)
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/ui/profil.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("My Profile");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Impossible de revenir en arrière.");
        }
    }

    @FXML
    private void handleConfirm() {
        User u = Session.getInstance().getCurrentUser();
        if (u == null) {
            showAlert("Error", "Aucun utilisateur connecté.");
            return;
        }

        String newUsername = usernameField.getText().trim();
        String newEmail    = emailField.getText().trim();
        String currentPwd  = currentPasswordField.getText();
        String newPwd      = newPasswordField.getText();

        // 1. Vérification du mot de passe actuel
        String hashedInput = PasswordUtils.hashPassword(currentPwd);
        if (!hashedInput.equals(u.getPasswordHash())) {
            showAlert("Error", "Mot de passe actuel incorrect.");
            return;
        }

        // 2. Au moins un champ doit changer
        boolean changeUsername = !newUsername.isEmpty() && !newUsername.equals(u.getUsername());
        boolean changeEmail    = !newEmail.isEmpty()    && !newEmail.equals(u.getEmail());
        boolean changePwd      = !newPwd.isEmpty()      && !PasswordUtils.hashPassword(newPwd).equals(u.getPasswordHash());
        if (!changeUsername && !changeEmail && !changePwd) {
            showAlert("Warning", "Remplissez au moins un champ à modifier.");
            return;
        }

        // 3. Mise à jour en base
        try (Connection conn = DatabaseConnection.getConnection()) {
            // 3a. Vérifier unicité username/email …
            if (changeUsername) {
                PreparedStatement ps = conn.prepareStatement(
                        "SELECT 1 FROM `User` WHERE username = ? AND id <> ?");
                ps.setString(1, newUsername);
                ps.setInt(2, u.getId());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        showAlert("Error", "Ce nom d'utilisateur est déjà pris.");
                        return;
                    }
                }
            }
            if (changeEmail) {
                PreparedStatement ps = conn.prepareStatement(
                        "SELECT 1 FROM `User` WHERE email = ? AND id <> ?");
                ps.setString(1, newEmail);
                ps.setInt(2, u.getId());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        showAlert("Error", "Cet email est déjà enregistré.");
                        return;
                    }
                }
            }

            // 3b. Exécuter l'UPDATE
            String sql = "UPDATE `User` SET username = ?, email = ?, password_hash = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, changeUsername ? newUsername : u.getUsername());
            stmt.setString(2, changeEmail    ? newEmail    : u.getEmail());
            String finalHash = changePwd
                    ? PasswordUtils.hashPassword(newPwd)
                    : u.getPasswordHash();
            stmt.setString(3, finalHash);
            stmt.setInt(4, u.getId());

            int updated = stmt.executeUpdate();
            if (updated > 0) {
                // 4. Mettre à jour l'objet existant et la session
                if (changeUsername) u.setUsername(newUsername);
                if (changeEmail)    u.setEmail(newEmail);
                if (changePwd)      u.setPasswordHash(finalHash);
                Session.getInstance().setCurrentUser(u);

                showAlert("Success", "Profil mis à jour !");
            } else {
                showAlert("Error", "Erreur lors de la mise à jour.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Erreur base de données.");
        }
    }


    private void showAlert(String title, String message) {
        Alert.AlertType type = Alert.AlertType.INFORMATION;
        if (title.equalsIgnoreCase("error")) {
            type = Alert.AlertType.ERROR;
        } else if (title.equalsIgnoreCase("warning")) {
            type = Alert.AlertType.WARNING;
        }
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}