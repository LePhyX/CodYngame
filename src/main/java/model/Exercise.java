package model;

import java.util.List;

/**
 * Represents a programming exercise including its metadata, language,
 * description, reference solution, and associated test cases.
 */
public class Exercise {

    /** Unique identifier of the exercise */
    private int id;

    /** Title or name of the exercise */
    private String title;

    /** Programming language used in the exercise (e.g., "java", "c", "python") */
    private String language;

    /** Textual description of the exercise given to the user */
    private String description;

    /** Optional reference solution (may be used for verification or hints) */
    private String solutionCode;

    /** List of test cases used to evaluate the user's code */
    private List<TestCase> testCases;

    // Constructors
    public Exercise() {}

    public Exercise(int id, String title, String language, String description, String solutionCode, List<TestCase> testCases) {
        this.id = id;
        this.title = title;
        this.language = language;
        this.description = description;
        this.solutionCode = solutionCode;
        this.testCases = testCases;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSolutionCode() {
        return solutionCode;
    }

    public void setSolutionCode(String solutionCode) {
        this.solutionCode = solutionCode;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCase> testCases) {
        this.testCases = testCases;
    }
}
