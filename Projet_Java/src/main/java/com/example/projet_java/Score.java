package com.example.projet_java;


public class Score {
    private int id;
    private int userId;
    private int exerciseId;
    private int score;
    private boolean achieved;

    public Score() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getExerciseId() { return exerciseId; }
    public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public boolean isAchieved() { return achieved; }
    public void setAchieved(boolean achieved) { this.achieved = achieved; }
}