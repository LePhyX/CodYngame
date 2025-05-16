package com.example.projet_java;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterView extends Application {

    @Override
    public void start(Stage stage) {
        // Champs de saisie
        TextField usernameField = new TextField();
        usernameField.setPromptText("Nom d'utilisateur");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");

        Button registerButton = new Button("S'inscrire");

        Label messageLabel = new Label();

        // Action du bouton
        registerButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                messageLabel.setText("❌ Tous les champs sont obligatoires.");
                return;
            }

            String hashedPassword = PasswordUtils.hashPassword(password);
            User user = new User(username, email, hashedPassword);

            UserDAO dao = new UserDAO();
            boolean success = dao.addUser(user);

            if (success) {
                messageLabel.setText("✅ Inscription réussie !");
                usernameField.clear();
                emailField.clear();
                passwordField.clear();
            } else {
                messageLabel.setText("❌ Erreur lors de l'inscription.");
            }
        });

        VBox root = new VBox(10, usernameField, emailField, passwordField, registerButton, messageLabel);
        root.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        Scene scene = new Scene(root, 300, 250);
        stage.setTitle("Inscription");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
