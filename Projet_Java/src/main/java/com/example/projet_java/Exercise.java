package com.example.projet_java;


public class Exercise {
    private int id;
    private String title;
    private String description;
    private String type;
    private byte[] exercisePdf;
    private byte[] solutionPdf;
    private String solution;
    private int difficulty;
    private int attemptsCount;
    private int successCount;

    public Exercise() {}

    public Exercise(String title, String description, String type, byte[] exercisePdf,
                    byte[] solutionPdf, String solution, int difficulty) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.exercisePdf = exercisePdf;
        this.solutionPdf = solutionPdf;
        this.solution = solution;
        this.difficulty = difficulty;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public byte[] getExercisePdf() { return exercisePdf; }
    public void setExercisePdf(byte[] exercisePdf) { this.exercisePdf = exercisePdf; }

    public byte[] getSolutionPdf() { return solutionPdf; }
    public void setSolutionPdf(byte[] solutionPdf) { this.solutionPdf = solutionPdf; }

    public String getSolution() { return solution; }
    public void setSolution(String solution) { this.solution = solution; }

    public int getDifficulty() { return difficulty; }
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }

    public int getAttemptsCount() { return attemptsCount; }
    public void setAttemptsCount(int attemptsCount) { this.attemptsCount = attemptsCount; }

    public int getSuccessCount() { return successCount; }
    public void setSuccessCount(int successCount) { this.successCount = successCount; }
}