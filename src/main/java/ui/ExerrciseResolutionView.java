package ui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Collections;


public class ExerrciseResolutionView extends Application {

    
    private final CodeArea codeArea = new CodeArea();
    private final Label languageLabel = new Label("Language: None");
    private String currentLanguage = "";
    private final Map<String, Button> languageButtons = new HashMap<>();
    private Button activeButton = null;
    private ChangeListener<String> currentListener;
    private final TextArea exerciseStatement = new TextArea();
    private final Button submitButton = new Button("Submit");
    private final Button resetButton = new Button("Reset");



    @Override
    public void start(Stage primaryStage) {

        // Exercise statement area (read-only)
        exerciseStatement.setEditable(false);
        exerciseStatement.setWrapText(true);
        exerciseStatement.setText("Exercise will appear here.\nWrite your code below.\nDes vélos en l'aiiiiiir.");
        exerciseStatement.setPrefHeight(100);


        // Setup code editor area
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.setWrapText(true);
        ScrollPane scrollPane = new ScrollPane(codeArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Label showing selected language
        languageLabel.setPadding(new Insets(5));

        // Top bar with language buttons
        HBox buttonBar = new HBox(10);
        buttonBar.setPadding(new Insets(10));
        buttonBar.setStyle("-fx-background-color: #333;");
        buttonBar.setPrefHeight(50);

        String[] languages = {"Java", "Python", "C", "PHP", "JavaScript"};

        for (String lang : languages) {
            Button btn = new Button(lang);
            btn.setMaxHeight(Double.MAX_VALUE);
            btn.setStyle(getDefaultButtonStyle());

            btn.setOnAction(e -> {
                switchLanguage(lang);
                updateButtonStyles(btn);
            });

            buttonBar.getChildren().add(btn);
            languageButtons.put(lang, btn);
        }

        // Bottom status bar
        HBox statusBar = new HBox(languageLabel);
        statusBar.setPadding(new Insets(5));
        statusBar.setStyle("-fx-background-color: #eee;");

        // Bottom control bar with Submit and Reset buttons
        HBox controlBar = new HBox(10, submitButton, resetButton);
        controlBar.setPadding(new Insets(10));
        controlBar.setStyle("-fx-background-color: #ddd;");
        controlBar.setSpacing(10);
        submitButton.setOnAction(e -> onSubmit());
        resetButton.setOnAction(e -> codeArea.clear());

        // Top section with statement and language buttons
        VBox topSection = new VBox(exerciseStatement, buttonBar);

        // Middle section with code area and control buttons
        VBox codeSection = new VBox(scrollPane, controlBar);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);


        // SplitPane for resizable vertical layout
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.getItems().addAll(topSection, codeSection);
        splitPane.setDividerPositions(0.25); // 25% for the top section initially

        BorderPane root = new BorderPane();
        root.setCenter(splitPane);
        root.setBottom(statusBar);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("CodYngame");
        primaryStage.setScene(scene);
        primaryStage.show();

        codeArea.getStylesheets().add(getClass().getResource("/editor.css").toExternalForm());

    }

    private void onSubmit() {
        // TODO: replace with real execution system
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Submission");
        alert.setHeaderText("Submitting your code...");
        alert.setContentText("Your code will be evaluated (not yet implemented).");
        alert.showAndWait();
    }


    private void applyHighlighting(String text) {
    StyleSpans<Collection<String>> spans;
    switch (currentLanguage) {
        case "Java":
            spans = computeJavaHighlighting(text);
            break;
        case "Python":
            spans = computePythonHighlighting(text);
            break;
        case "C":
            spans = computeCHighlighting(text);
            break;
        case "PHP":
            spans = computePHPHighlighting(text);
            break;
        case "JavaScript":
            spans = computeJavaScriptHighlighting(text);
            break;
        default:
            spans = new StyleSpansBuilder<Collection<String>>()
                    .add(Collections.emptyList(), text.length())
                    .create();
    }
    codeArea.setStyleSpans(0, spans);
}



    private void switchLanguage(String language) {
        currentLanguage = language;
        languageLabel.setText("Language: " + language);

        // Remove old listener if it exists
        if (currentListener != null) {
            codeArea.textProperty().removeListener(currentListener);
        }

        // Create new listener depending on the language
        currentListener = (obs, oldText, newText) -> {
            applyHighlighting(newText);
        };
        codeArea.textProperty().addListener(currentListener);

        // Immediately apply highlighting to current content
        applyHighlighting(codeArea.getText());
    }


    private void updateButtonStyles(Button selectedButton) {
        if (activeButton != null) {
            activeButton.setStyle(getDefaultButtonStyle());
        }
        selectedButton.setStyle(getSelectedButtonStyle());
        activeButton = selectedButton;
    }

    private String getDefaultButtonStyle() {
        return "-fx-background-color: #555; -fx-text-fill: white;";
    }

    private String getSelectedButtonStyle() {
        return "-fx-background-color: #888; -fx-text-fill: white;";
    }

    // Java syntax highlighting regex patterns
    private static final String[] JAVA_KEYWORDS = new String[]{
            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
            "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
            "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int",
            "interface", "long", "native", "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this",
            "throw", "throws", "transient", "try", "void", "volatile", "while"
    };

    // Python syntax highlighting regex patterns
    private static final String[] PYTHON_KEYWORDS = new String[]{
            "False", "class", "finally", "is", "return", "None", "continue", "for", "lambda", "try",
            "True", "def", "from", "nonlocal", "while", "and", "del", "global", "not", "with",
            "as", "elif", "if", "or", "yield", "assert", "else", "import", "pass", "break", "except", "in", "raise"
    };

    // C syntax highlighting regex pattern
    private static final String[] C_KEYWORDS = {
            "auto", "break", "case", "char", "const", "continue", "default", "do", "double", "else",
            "enum", "extern", "float", "for", "goto", "if", "inline", "int", "long", "register", "restrict",
            "return", "short", "signed", "sizeof", "static", "struct", "switch", "typedef", "union",
            "unsigned", "void", "volatile", "while", "_Alignas", "_Alignof", "_Atomic", "_Bool",
            "_Complex", "_Generic", "_Imaginary", "_Noreturn", "_Static_assert", "_Thread_local"
    };

    // PHP syntax highlighting regex pattern
    private static final String[] PHP_KEYWORDS = {
            "abstract", "and", "array", "as", "break", "callable", "case", "catch", "class", "clone",
            "const", "continue", "declare", "default", "do", "echo", "else", "elseif", "empty",
            "enddeclare", "endfor", "endforeach", "endif", "endswitch", "endwhile", "eval", "exit",
            "extends", "final", "finally", "fn", "for", "foreach", "function", "global", "goto",
            "if", "implements", "include", "include_once", "instanceof", "insteadof", "interface",
            "isset", "list", "namespace", "new", "or", "print", "private", "protected", "public",
            "require", "require_once", "return", "static", "switch", "throw", "trait", "try",
            "unset", "use", "var", "while", "xor", "yield"
    };

    // JavaScript syntax highlighting regex pattern
    private static final String[] JS_KEYWORDS = {
            "break", "case", "catch", "class", "const", "continue", "debugger", "default", "delete",
            "do", "else", "export", "extends", "finally", "for", "function", "if", "import", "in",
            "instanceof", "let", "new", "return", "super", "switch", "this", "throw", "try", "typeof",
            "var", "void", "while", "with", "yield", "await", "enum", "implements", "interface", "package", "private", "protected", "public", "static"
    };



    //RegEx Java
    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", JAVA_KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    private static final Pattern JAVA_PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    // RegEx Python
    private static final String PY_KEYWORD_PATTERN = "\\b(" + String.join("|", PYTHON_KEYWORDS) + ")\\b";
    private static final String PY_COMMENT_PATTERN = "#[^\n]*";
    private static final String PY_STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"|'([^'\\\\]|\\\\.)*'";

    private static final Pattern PYTHON_PATTERN = Pattern.compile(
            "(?<KEYWORD>" + PY_KEYWORD_PATTERN + ")"
            + "|(?<COMMENT>" + PY_COMMENT_PATTERN + ")"
            + "|(?<STRING>" + PY_STRING_PATTERN + ")"
    );

    // RegEx C
    private static final String C_KEYWORD_PATTERN = "\\b(" + String.join("|", C_KEYWORDS) + ")\\b";
    private static final String C_COMMENT_PATTERN = "//[^\n]*|/\\*(.|\\R)*?\\*/";
    private static final String C_STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";

    private static final Pattern C_PATTERN = Pattern.compile(
        "(?<KEYWORD>" + C_KEYWORD_PATTERN + ")"
        + "|(?<COMMENT>" + C_COMMENT_PATTERN + ")"
        + "|(?<STRING>" + C_STRING_PATTERN + ")"
    );

    // RegEx PHP
    private static final String PHP_KEYWORD_PATTERN = "\\b(" + String.join("|", PHP_KEYWORDS) + ")\\b";
    private static final String PHP_COMMENT_PATTERN = "//[^\n]*|/\\*(.|\\R)*?\\*/|#[^\n]*";
    private static final String PHP_STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"|'([^'\\\\]|\\\\.)*'";

    private static final Pattern PHP_PATTERN = Pattern.compile(
        "(?<KEYWORD>" + PHP_KEYWORD_PATTERN + ")"
        + "|(?<COMMENT>" + PHP_COMMENT_PATTERN + ")"
        + "|(?<STRING>" + PHP_STRING_PATTERN + ")"
    );

    // RegEx JavaScript
    private static final String JS_KEYWORD_PATTERN = "\\b(" + String.join("|", JS_KEYWORDS) + ")\\b";
    private static final String JS_COMMENT_PATTERN = "//[^\n]*|/\\*(.|\\R)*?\\*/";
    private static final String JS_STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"|'([^'\\\\]|\\\\.)*'|`([^`\\\\]|\\\\.)*`";

    private static final Pattern JS_PATTERN = Pattern.compile(
        "(?<KEYWORD>" + JS_KEYWORD_PATTERN + ")"
        + "|(?<COMMENT>" + JS_COMMENT_PATTERN + ")"
        + "|(?<STRING>" + JS_STRING_PATTERN + ")"
    );


    // Compute and apply style spans based on Java regex
    private StyleSpans<Collection<String>> computeJavaHighlighting(String text) {
        Matcher matcher = JAVA_PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                    matcher.group("PAREN") != null ? "paren" :
                    matcher.group("BRACE") != null ? "brace" :
                    matcher.group("BRACKET") != null ? "bracket" :
                    matcher.group("SEMICOLON") != null ? "semicolon" :
                    matcher.group("STRING") != null ? "string" :
                    matcher.group("COMMENT") != null ? "comment" :
                    null;
            assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private StyleSpans<Collection<String>> computePythonHighlighting(String text) {
        Matcher matcher = PYTHON_PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                    matcher.group("COMMENT") != null ? "comment" :
                    matcher.group("STRING") != null ? "string" :
                    null;
            assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private StyleSpans<Collection<String>> computeCHighlighting(String text) {
        Matcher matcher = C_PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

        while (matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                    matcher.group("COMMENT") != null ? "comment" :
                    matcher.group("STRING") != null ? "string" :
                    null;

            assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }

        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private StyleSpans<Collection<String>> computePHPHighlighting(String text) {
        Matcher matcher = PHP_PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

        while (matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                    matcher.group("COMMENT") != null ? "comment" :
                    matcher.group("STRING") != null ? "string" :
                    null;

            assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }

        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private StyleSpans<Collection<String>> computeJavaScriptHighlighting(String text) {
        Matcher matcher = JS_PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

        while (matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                    matcher.group("COMMENT") != null ? "comment" :
                    matcher.group("STRING") != null ? "string" :
                    null;

            assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }

        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
