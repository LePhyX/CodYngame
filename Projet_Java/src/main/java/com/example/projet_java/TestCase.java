package com.example.projet_java;


public class TestCase {
    private int id;
    private int exerciseId;
    private String inputData;
    private String expectedOutput;

    public TestCase() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getExerciseId() { return exerciseId; }
    public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }

    public String getInputData() { return inputData; }
    public void setInputData(String inputData) { this.inputData = inputData; }

    public String getExpectedOutput() { return expectedOutput; }
    public void setExpectedOutput(String expectedOutput) { this.expectedOutput = expectedOutput; }
}
