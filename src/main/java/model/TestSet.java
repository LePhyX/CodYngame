package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of test cases for a programming exercise.
 * Useful for grouping multiple {@link Test} instances in one structure.
 */
public class TestSet {

    /** Internal list of test cases */
    private List<Test> tests;

    /**
     * Constructs an empty TestSet.
     */
    public TestSet() {
        tests = new ArrayList<>();
    }

    /**
     * Adds a test case to the current set.
     *
     * @param test the {@link Test} to add
     */
    public void addTest(Test test) {
        tests.add(test);
    }

    /**
     * Returns all test cases in the set.
     *
     * @return a {@link List} of {@link Test} objects
     */
    public List<Test> getTests() {
        return tests;
    }

    /**
     * Replaces the current list of test cases with a new one.
     *
     * @param tests the new {@link List} of {@link Test} objects
     */
    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    /**
     * Returns the number of test cases currently in the set.
     *
     * @return the size of the test set
     */
    public int size() {
        return tests.size();
    }
}
