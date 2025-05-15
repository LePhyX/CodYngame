package com.example.projet_java;


public class ExerciseLanguage {
    private int exerciseId;
    private int languageId;

    public ExerciseLanguage() {}

    public ExerciseLanguage(int exerciseId, int languageId) {
        this.exerciseId = exerciseId;
        this.languageId = languageId;
    }

    public int getExerciseId() { return exerciseId; }
    public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }

    public int getLanguageId() { return languageId; }
    public void setLanguageId(int languageId) { this.languageId = languageId; }
}