package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection (set) of test cases for a programming exercise.
 * Provides methods to add and retrieve tests.
 */
public class TestSet {

    /** List of individual tests */
    private List<Test> tests;

    /**
     * Default constructor initializes the internal list.
     */
    public TestSet() {
        tests = new ArrayList<>();
    }

    /**
     * Adds a test case to the set.
     *
     * @param test The Test instance to add
     */
    public void addTest(Test test) {
        tests.add(test);
    }

    /**
     * Returns the list of all tests.
     *
     * @return List of Test objects
     */
    public List<Test> getTests() {
        return tests;
    }

    /**
     * Sets the list of tests (replaces the existing list).
     *
     * @param tests List of Test objects to set
     */
    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    /**
     * Returns the number of tests in this set.
     *
     * @return Number of tests
     */
    public int size() {
        return tests.size();
    }
}
