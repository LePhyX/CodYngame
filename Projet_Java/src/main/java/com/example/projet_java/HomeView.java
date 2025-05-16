package com.example.projet_java;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeView extends Application {

    @Override
    public void start(Stage stage) {
        Button logoutButton = new Button("Déconnexion");

        logoutButton.setOnAction(e -> {
            // Ferme la fenêtre actuelle
            stage.close();

            // Réouvre la fenêtre de connexion
            LoginView login = new LoginView();
            Stage loginStage = new Stage();
            try {
                login.start(loginStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox root = new VBox(20, logoutButton);
        root.setStyle("-fx-padding: 30; -fx-alignment: center;");
        Scene scene = new Scene(root, 300, 200);
        stage.setTitle("Accueil (temporaire)");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
