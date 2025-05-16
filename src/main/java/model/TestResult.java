package model;

/**
 * Represents the result of executing a single test case on a user's submission.
 * Contains the input, expected output, actual output, pass status, and error messages if any.
 */
public class TestResult {

    /** The input data used for this test */
    private String input;

    /** The expected output for this test */
    private String expectedOutput;

    /** The actual output produced by the user's code */
    private String actualOutput;

    /** Indicates whether the test passed (true) or failed (false) */
    private boolean passed;

    /** Error message or standard error output if the program failed */
    private String errorMessage;

    /** Default constructor */
    public TestResult() {
    }

    /**
     * Parameterized constructor.
     *
     * @param input          The input data for the test
     * @param expectedOutput The expected output
     * @param actualOutput   The actual output produced by the user's code
     * @param passed         Whether the test passed or not
     * @param errorMessage   Error message if any, else null or empty
     */
    public TestResult(String input, String expectedOutput, String actualOutput, boolean passed, String errorMessage) {
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
        this.passed = passed;
        this.errorMessage = errorMessage;
    }

    // Getters and setters

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public String getActualOutput() {
        return actualOutput;
    }

    public void setActualOutput(String actualOutput) {
        this.actualOutput = actualOutput;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "input='" + input + '\'' +
                ", expectedOutput='" + expectedOutput + '\'' +
                ", actualOutput='" + actualOutput + '\'' +
                ", passed=" + passed +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
