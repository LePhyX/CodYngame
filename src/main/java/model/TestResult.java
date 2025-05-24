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

    /**
     * Default constructor.
     */
    public TestResult() {}

    /**
     * Parameterized constructor.
     *
     * @param input          The input string passed to the program
     * @param expectedOutput The expected result
     * @param actualOutput   The actual output from the program
     * @param passed         true if the test passed
     * @param errorMessage   error output or exception details if the test failed
     */
    public TestResult(String input, String expectedOutput, String actualOutput, boolean passed, String errorMessage) {
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
        this.passed = passed;
        this.errorMessage = errorMessage;
    }

    /** @return the input used in the test */
    public String getInput() {
        return input;
    }

    /** @param input the input string to set */
    public void setInput(String input) {
        this.input = input;
    }

    /** @return the expected output */
    public String getExpectedOutput() {
        return expectedOutput;
    }

    /** @param expectedOutput the expected output string to set */
    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    /** @return the actual output produced by the code */
    public String getActualOutput() {
        return actualOutput;
    }

    /** @param actualOutput the actual result to set */
    public void setActualOutput(String actualOutput) {
        this.actualOutput = actualOutput;
    }

    /** @return true if the test passed, false otherwise */
    public boolean isPassed() {
        return passed;
    }

    /** @param passed test result flag (true if passed) */
    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    /** @return any error message or exception output */
    public String getErrorMessage() {
        return errorMessage;
    }

    /** @param errorMessage the error message to store */
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
