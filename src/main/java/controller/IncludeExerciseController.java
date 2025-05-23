package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Exercise;
import model.Language;
import fusion.FusionneurCode3;

public class IncludeExerciseController {

    @FXML
    private TextArea baseCodeArea;

    @FXML
    private Button backButton;
    @FXML
    private Label exerciseTitle;
    @FXML
    private TextArea exerciseDescription;
    @FXML
    private TextArea codeEditor;
    @FXML
    private TextArea outputArea;

    private Language selectedLanguage;
    private Exercise selectedExercise;

    public void initData(Language language, Exercise exercise) {
        System.out.println("3 EXO = " + exercise);
        System.out.println(" DESC = " + exercise.getDescription());

        this.selectedLanguage = language;
        this.selectedExercise = exercise;

        exerciseTitle.setText(exercise.getTitle());
        exerciseDescription.setText(exercise.getDescription());
        baseCodeArea.setText(exercise.getBaseCode());
        codeEditor.clear(); // éditeur vide pour saisir le code utilisateur

        outputArea.setText("");
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/IncludeChoice.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Faites un choix");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRun() {
        String userCode = codeEditor.getText();
        if (userCode == null || userCode.trim().isEmpty()) {
            outputArea.setText("Please write some code before running.");
            return;
        }

        try {
            String result = executeUserCode(userCode, selectedLanguage, selectedExercise);
            outputArea.setText(result);
        } catch (Exception e) {
            outputArea.setText("Erreur pendant l'exécution :\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private String executeUserCode(String code, Language lang, Exercise exercise) {
        try {
            FusionneurCode3 fusionneur = new FusionneurCode3();
            String langName = lang.getName().toLowerCase();

            FusionneurCode3.ResultatExecution resultat;
            String codeToExecute;

            // Traiter Python, C et Java de la même manière (sans fusion)
            if (langName.equals("python") || langName.equals("c") || langName.equals("java")) {
                String tests = genererTestsAutomatiques(lang, exercise);
                codeToExecute = code + "\n" + tests;

                resultat = fusionneur.executerCode(
                        lang.getName(),
                        codeToExecute,
                        "",
                        -1
                );
            } else {
                String tests = genererTestsAutomatiques(lang, exercise);
                codeToExecute = code + "\n" + tests;

                resultat = fusionneur.executerCode(
                        lang.getName(),
                        code,
                        exercise.getBaseCode(),
                        exercise.getLineCode()
                );
            }

            if (resultat.getCodeRetour() == 0) {
                String sortie = resultat.getSortieStandard().trim();
                String sortieAttendue = genererSortieAttendue(lang, exercise);

                if (sortieAttendue != null && sortiesEgales(sortieAttendue, sortie)) {
                    return " Fonction correcte !";
                } else {
                    return " Fonction incorrecte.";
                }
            } else {
                return " Erreur à l'exécution :\n\n" + resultat.getSortieErreur();
            }

        } catch (Exception e) {
            return " Erreur système : " + e.getMessage();
        }
    }

    private boolean sortiesEgales(String s1, String s2) {
        return s1.trim().equalsIgnoreCase(s2.trim());
    }

    private String genererTestsAutomatiques(Language lang, Exercise exercise) {
        int exoId = exercise.getId();
        String langName = lang.getName().toLowerCase();

        switch (langName) {
            case "python":
                if (exoId == 1) return "print(add(2, 3))\nprint(add(0, 0))";
                if (exoId == 2) return "print(is_even(2))\nprint(is_even(3))";
                break;
            case "java":
                if (exoId == 3) return "System.out.println(add(2, 3));\nSystem.out.println(add(0, 0));";
                if (exoId == 4) return "System.out.println(isEven(2));\nSystem.out.println(isEven(3));";
                break;
            case "php":
                if (exoId == 5) return "echo add(2, 3) . \"\\n\"; echo add(0, 0) . \"\\n\";";
                if (exoId == 6) return "echo is_even(2) ? 'true' : 'false'; echo \"\\n\"; echo is_even(3) ? 'true' : 'false';";
                break;
            case "c":
                // Pas de tests générés pour C, ils seront ajoutés directement via le main
                if (exoId == 7) return "";
                if (exoId == 8) return "";
                break;
            case "javascript":
                if (exoId == 9) return "console.log(add(2, 3)); console.log(add(0, 0));";
                if (exoId == 10) return "console.log(isEven(2)); console.log(isEven(3));";
                break;
        }
        return "";
    }

    private String genererSortieAttendue(Language lang, Exercise exercise) {
        int exoId = exercise.getId();
        String langName = lang.getName().toLowerCase();

        switch (langName) {
            case "python":
            case "java":
            case "php":
            case "c":
            case "javascript":
                if (exoId == 1 || exoId == 3 || exoId == 5 || exoId == 7 || exoId == 9) return "5\n0";
                if (exoId == 2 || exoId == 4 || exoId == 6 || exoId == 8 || exoId == 10) {
                    if (langName.equals("php") || langName.equals("c")) return "1\n0";
                    if (langName.equals("python") || langName.equals("javascript")) return "True\nFalse";
                    if (langName.equals("java")) return "true\nfalse";
                }
                break;
        }
        return null;
    }
}





