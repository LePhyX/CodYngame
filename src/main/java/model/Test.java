package model;

/**
 * Represents a single test case for a programming exercise.
 * Contains the input, the expected output, and an optional identifier.
 */
public class Test {

    /** Unique identifier or number of the test case */
    private int id;

    /** Input data provided to the program for this test */
    private String input;

    /** Expected output that the program should produce */
    private String expectedOutput;

    /**
     * Default constructor.
     */
    public Test() {}

    /**
     * Parameterized constructor.
     * 
     * @param id            Unique identifier of the test case
     * @param input         Input string for the test
     * @param expectedOutput Expected output string for the test
     */
    public Test(int id, String input, String expectedOutput) {
        this.id = id;
        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", input='" + input + '\'' +
                ", expectedOutput='" + epxectedOutput + '\'' +
                '}';
    }
}