package com.example.projet_java;


public class TestResult {
    private int id;
    private int submissionId;
    private int testCaseId;
    private String inputData;
    private String expectedOutput;
    private String actualOutput;
    private boolean success;

    public TestResult() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSubmissionId() { return submissionId; }
    public void setSubmissionId(int submissionId) { this.submissionId = submissionId; }

    public int getTestCaseId() { return testCaseId; }
    public void setTestCaseId(int testCaseId) { this.testCaseId = testCaseId; }

    public String getInputData() { return inputData; }
    public void setInputData(String inputData) { this.inputData = inputData; }

    public String getExpectedOutput() { return expectedOutput; }
    public void setExpectedOutput(String expectedOutput) { this.expectedOutput = expectedOutput; }

    public String getActualOutput() { return actualOutput; }
    public void setActualOutput(String actualOutput) { this.actualOutput = actualOutput; }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
}
