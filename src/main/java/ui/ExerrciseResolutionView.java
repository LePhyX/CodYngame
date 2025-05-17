package com.syntaxeditor;

import javafx.application.Application;
import javafx.geometry.Insets;
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


public class MainApp extends Application {

    private final CodeArea codeArea = new CodeArea();
    private final Label languageLabel = new Label("Language: None");
    private String currentLanguage = "";
    private final Map<String, Button> languageButtons = new HashMap<>();
    private Button activeButton = null;

    @Override
    public void start(Stage primaryStage) {
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

        // Root layout
        BorderPane root = new BorderPane();
        root.setTop(buttonBar);
        root.setCenter(scrollPane);
        root.setBottom(statusBar);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Code Editor with Syntax Highlighting");
        primaryStage.setScene(scene);
        primaryStage.show();

        codeArea.getStylesheets().add(getClass().getResource("/editor.css").toExternalForm());

    }

    private void switchLanguage(String language) {
        currentLanguage = language;
        languageLabel.setText("Language: " + language);
        // → Syntax highlighting will be handled here later
        if (currentLanguage.equals("Java")) {
            codeArea.textProperty().addListener((obs, oldText, newText) -> {
            codeArea.setStyleSpans(0, computeJavaHighlighting(newText));
            });
        }

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

    public static void main(String[] args) {
        launch(args);
    }
}
