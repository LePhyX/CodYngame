package model;

import java.util.List;

/**
 * Represents a user's submission for an exercise.
 * Contains the submitted code, the language, the results of all tests,
 * and an overall pass/fail status.
 */
public class Submission {

    /** Identifier of the exercise */
    private int exerciseId;

    /** The code submitted by the user */
    private String userCode;

    /** Programming language of the submitted code (e.g., "java", "c", "python") */
    private String language;

    /** List of results for each individual test */
    private List<TestResult> results;

    /** True if all tests passed, false otherwise */
    private boolean passed;

    /** Default constructor */
    public Submission() {
    }

    /**
     * Parameterized constructor.
     *
     * @param exerciseId Identifier of the exercise
     * @param userCode   Submitted source code
     * @param language   Programming language
     * @param results    List of test results
     * @param passed     Overall pass status
     */
    public Submission(int exerciseId, String userCode, String language, List<TestResult> results, boolean passed) {
        this.exerciseId = exerciseId;
        this.userCode = userCode;
        this.language = language;
        this.results = results;
        this.passed = passed;
    }

    // Getters and setters

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<TestResult> getResults() {
        return results;
    }

    public void setResults(List<TestResult> results) {
        this.results = results;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    @Override
    public String toString() {
        return "Submission{" +
                "exerciseId=" + exerciseId +
                ", language='" + language + '\'' +
                ", passed=" + passed +
                ", results=" + results +
                '}';
    }
}
