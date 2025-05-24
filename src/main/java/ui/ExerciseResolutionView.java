package ui;

import org.fxmisc.richtext.CodeArea;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX application that provides the user interface for solving a coding exercise.
 *
 * <p>This view includes:
 * <ul>
 *   <li>A read-only text area displaying the exercise description</li>
 *   <li>A combo box to select the programming language (Java, C, Python)</li>
 *   <li>A code editor using RichTextFX</li>
 *   <li>A console output area for result/error messages</li>
 *   <li>A button to submit and evaluate the code</li>
 * </ul>
 */
public class ExerciseResolutionView extends Application {

    /**
     * The main entry point for all JavaFX applications.
     * Sets up the UI components and event handlers.
     *
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        // Read-only text area for the exercise instructions
        TextArea exerciseDescription = new TextArea("Here is the exercise description...");
        exerciseDescription.setWrapText(true);
        exerciseDescription.setEditable(false);
        exerciseDescription.setPrefHeight(100);

        // ComboBox for language selection
        ComboBox<String> languageSelector = new ComboBox<>();
        languageSelector.getItems().addAll("Java", "C", "Python");
        languageSelector.getSelectionModel().selectFirst();

        // Rich text editor for user code input
        CodeArea codeEditor = new CodeArea();
        codeEditor.setPrefHeight(300);
        codeEditor.replaceText("// Write your code here...");

        // Console area for displaying execution output or errors
        TextArea consoleOutput = new TextArea();
        consoleOutput.setPrefHeight(150);
        consoleOutput.setEditable(false);

        // Submit button to trigger evaluation
        Button submitButton = new Button("Submit");

        // Root layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(
                new Label("Exercise:"),
                exerciseDescription,
                new Label("Select Language:"),
                languageSelector,
                new Label("Code:"),
                codeEditor,
                submitButton,
                new Label("Console Output:"),
                consoleOutput
        );

        // Scene setup
        Scene scene = new Scene(root, 800, 700);
        primaryStage.setTitle("Exercise Solver");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Event: when "Submit" is clicked
        submitButton.setOnAction(event -> {
            String selectedLang = languageSelector.getValue();
            String userCode = codeEditor.getText();

            // TODO: Replace with actual backend integration for code evaluation
            consoleOutput.setText("Code submitted in " + selectedLang + ":\n" + userCode);
        });
    }

    /**
     * Main method to launch the JavaFX application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
