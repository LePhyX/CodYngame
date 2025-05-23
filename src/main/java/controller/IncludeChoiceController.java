package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Exercise;
import model.ExerciseDAO;
import model.Language;
import model.LanguageDAO;
import utils.Session;

import java.net.URL;
import java.util.List;

public class IncludeChoiceController {

    @FXML private ComboBox<Language> languageComboBox;
    @FXML private TableView<Exercise> exerciseTable;
    @FXML private TableColumn<Exercise, String> titleColumn;
    @FXML private TableColumn<Exercise, String> typeColumn;
    @FXML private TableColumn<Exercise, Integer> difficultyColumn;

    private final LanguageDAO languageDAO = new LanguageDAO();
    private final ExerciseDAO exerciseDAO = new ExerciseDAO();

    @FXML private Button backButton;

    // ❌ Supprimé : private User currentUser;

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Dashboard.fxml"));
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
    public void initialize() {
        List<Language> languages = languageDAO.getAllLanguages();
        languageComboBox.setItems(FXCollections.observableArrayList(languages));

        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        difficultyColumn.setCellValueFactory(cellData -> cellData.getValue().difficultyProperty().asObject());

        languageComboBox.setOnAction(event -> {
            Language selectedLang = languageComboBox.getValue();
            if (selectedLang != null) {
                List<Exercise> exercises = exerciseDAO.getExercisesByLanguageAndType(selectedLang.getId(), "INCLUDE");
                exerciseTable.setItems(FXCollections.observableArrayList(exercises));
            }
        });
    }

    @FXML
    private void handleGo() {
        Language selectedLang = languageComboBox.getValue();
        Exercise selectedExercise = exerciseTable.getSelectionModel().getSelectedItem();

        if (selectedLang == null || selectedExercise == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select both a language and an exercise.");
            alert.showAndWait();
            return;
        }

        try {
            System.out.println("Chemin réel : " + getClass().getResource("/ui/IncludeExercise.fxml"));

            URL fxmlUrl = getClass().getResource("/ui/IncludeExercise.fxml");
            System.out.println("FXML URL: " + fxmlUrl);
            System.out.println("FXML Exists: " + (fxmlUrl != null));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/IncludeExercise.fxml"));
            Parent root = loader.load();

            IncludeExerciseController controller = loader.getController();
            controller.initData(selectedLang, selectedExercise); // ✅ utilisateur non passé manuellement

            Stage stage = (Stage) languageComboBox.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Solve Exercise");
            stage.show();

        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de IncludeExercise.fxml : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
