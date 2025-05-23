package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Button backButton;

    private User currentUser; // ✅ Ajouté pour stocker l'utilisateur

    public void initData(User user) { // ✅ Méthode appelée après la connexion
        this.currentUser = user;
    }

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
    private Button includeButton;

    @FXML
    private void handleIncludeClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/IncludeChoice.fxml"));
            Parent root = loader.load();

            IncludeChoiceController controller = loader.getController(); // ✅
            controller.initData(currentUser); // ✅ passage de l'utilisateur

            Stage stage = (Stage) includeButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Faites un choix");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la page include-choice.fxml");
        }
    }

    @FXML
    private void handleStdinClick() {
        System.out.println("➡ STDIN/STDOUT exercise selected.");
        // TODO : rediriger vers la page des exercices STDIN/STDOUT
    }
}
