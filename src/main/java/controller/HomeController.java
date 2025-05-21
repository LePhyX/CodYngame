package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HomeController {

    @FXML private Label helloLabel;
    @FXML private Button exercisesButton; //  Nécessaire car référencé dans le FXML

    private String username = "User"; // Valeur par défaut

    public void setUsername(String username) {
        this.username = username;
        if (helloLabel != null) {
            helloLabel.setText("Hello " + username + " !");
        }
    }

    @FXML
    public void initialize() {
        helloLabel.setText("Hello " + username + " !");
    }



    @FXML
    private void handleExercisesClick() {
        try {
            System.out.println(">> clique détecté");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/dashboard.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) exercisesButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
            stage.show();

        } catch (Exception e) {
            System.out.println("❌ Erreur chargement FXML : ");
            e.printStackTrace();
        }
    }


}
