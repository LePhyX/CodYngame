package model;

/**
 * Represents a single test case for a programming exercise.
 * Stores the test ID, the input string, and the expected output.
 */
public class Test {

    /** Unique identifier of the test case */
    private int id;

    /** Input data provided to the program */
    private String input;

    /** Expected output the program should return */
    private String expectedOutput;

    /**
     * Default constructor.
     */
    public Test() {}

    /**
     * Full constructor.
     *
     * @param id             the unique ID of the test
     * @param input          the input string to pass to the program
     * @param expectedOutput the output expected from the program
     */
    public Test(int id, String input, String expectedOutput) {
        this.id = id;
        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    /** @return the test ID */
    public int getId() {
        return id;
    }

    /** @param id the test ID to set */
    public void setId(int id) {
        this.id = id;
    }

    /** @return the input string */
    public String getInput() {
        return input;
    }

    /** @param input the input string to set */
    public void setInput(String input) {
        this.input = input;
    }

    /** @return the expected output string */
    public String getExpectedOutput() {
        return expectedOutput;
    }

    /** @param expectedOutput the expected output to set */
    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", input='" + input + '\'' +
                ", expectedOutput='" + expectedOutput + '\'' +
                '}';
    }
}
