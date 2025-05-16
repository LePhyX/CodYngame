package com.example.projet_java;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView extends Application {

    @Override
    public void start(Stage stage) {
        TextField usernameField = new TextField();
        usernameField.setPromptText("Nom d'utilisateur");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");

        Button loginButton = new Button("Se connecter");
        Label messageLabel = new Label();

        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("❌ Veuillez remplir tous les champs.");
                return;
            }

            UserDAO dao = new UserDAO();
            String hashedPassword = PasswordUtils.hashPassword(password);
            boolean isValid = dao.checkLogin(username, hashedPassword);

            if (isValid) {
                stage.close(); // Ferme la fenêtre actuelle

                // Ouvre HomeView
                HomeView home = new HomeView();
                Stage homeStage = new Stage();
                try {
                    home.start(homeStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
             else {
                messageLabel.setText("❌ Identifiants incorrects.");
            }
        });

        VBox root = new VBox(10, usernameField, passwordField, loginButton, messageLabel);
        root.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        Scene scene = new Scene(root, 300, 200);
        stage.setTitle("Connexion");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
