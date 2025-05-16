package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ExerciseResolutionView extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Enoncé de l'exercice
        TextArea exerciseDescription = new TextArea("Voici l'énoncé de l'exercice...");
        exerciseDescription.setWrapText(true);
        exerciseDescription.setEditable(false);
        exerciseDescription.setPrefHeight(100);

        // Choix du langage
        ComboBox<String> languageSelector = new ComboBox<>();
        languageSelector.getItems().addAll("Java", "C", "Python");
        languageSelector.getSelectionModel().selectFirst();

        // Editeur de code
        TextArea codeEditor = new TextArea();
        codeEditor.setPrefHeight(300);
        codeEditor.setPromptText("Écrivez votre code ici...");

        // Console de sortie
        TextArea consoleOutput = new TextArea();
        consoleOutput.setPrefHeight(150);
        consoleOutput.setEditable(false);

        // Bouton Submit
        Button submitButton = new Button("Submit");

        // Layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(
            new Label("Énoncé :"),
            exerciseDescription,
            new Label("Choisir le langage :"),
            languageSelector,
            new Label("Code :"),
            codeEditor,
            submitButton,
            new Label("Console :"),
            consoleOutput
        );

        // Scene
        Scene scene = new Scene(root, 800, 700);
        primaryStage.setTitle("Résolution d'exercice");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Action Submit
        submitButton.setOnAction(event -> {
            String selectedLang = languageSelector.getValue();
            String userCode = codeEditor.getText();

            // Ici tu appelleras ton moteur d’évaluation
            consoleOutput.setText("Code soumis en " + selectedLang + " :\n" + userCode);

            // Exemple : tu peux lancer SubmissionEvaluator ici et afficher le résultat dans consoleOutput
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
