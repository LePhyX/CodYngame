package ui;

import org.fxmisc.richtext.CodeArea;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX application that provides the user interface for solving a coding exercise.
 * <p>
 * This view includes:
 * <ul>
 *   <li>A read-only text area displaying the exercise description.</li>
 *   <li>A combo box to select the programming language (Java, C, Python).</li>
 *   <li>A text area for the user to write their code.</li>
 *   <li>A console output area to display execution results or messages.</li>
 *   <li>A submit button to trigger the code evaluation.</li>
 * </ul>
 */
public class ExerciseResolutionView extends Application {

    /**
     * The main entry point for all JavaFX applications.
     * Sets up the UI components and event handlers.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {
        // Exercise description displayed in a read-only text area
        TextArea exerciseDescription = new TextArea("Voici l'énoncé de l'exercice...");
        exerciseDescription.setWrapText(true);
        exerciseDescription.setEditable(false);
        exerciseDescription.setPrefHeight(100);

        // Language selector combo box with predefined options
        ComboBox<String> languageSelector = new ComboBox<>();
        languageSelector.getItems().addAll("Java", "C", "Python");
        languageSelector.getSelectionModel().selectFirst();

        // Code editor text area where users write their code
        CodeArea codeEditor = new CodeArea();
        codeEditor.setPrefHeight(300);
        codeEditor.replaceText("// Write your code here...");

        // Console output text area for showing execution output or error messages
        TextArea consoleOutput = new TextArea();
        consoleOutput.setPrefHeight(150);
        consoleOutput.setEditable(false);

        // Submit button to trigger code submission and evaluation
        Button submitButton = new Button("Submit");

        // Layout container with spacing and padding
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

        // Set up the scene with the root layout and window size
        Scene scene = new Scene(root, 800, 700);
        primaryStage.setTitle("Résolution d'exercice");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Event handler for the submit button
        submitButton.setOnAction(event -> {
            // Get the selected programming language
            String selectedLang = languageSelector.getValue();
            // Get the user-written code from the editor
            String userCode = codeEditor.getText();

            // Display a simple message in the console output (placeholder for real evaluation)
            consoleOutput.setText("Code submitted in " + selectedLang + ":\n" + userCode);

            // TODO: Call the code evaluation engine and display real results here
        });
    }

    /**
     * The main method which launches the JavaFX application.
     *
     * @param args the command line arguments (not used)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
