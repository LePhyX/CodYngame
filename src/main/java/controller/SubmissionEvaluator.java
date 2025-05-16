package controller;

import model.Exercise;
import model.Submission;
import model.TestResult;
import utils.CodeExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Handles the evaluation of a user's submission against
 * dynamically generated test cases based on the reference solution.
 */

public class SubmissionEvaluator {

    /**
     * Generates random inputs, evaluates both the user's code and the reference solution,
     * and compares their outputs to determine test success.
     *
     * @param exercise The exercise with the reference solution and language
     * @param userCode The code submitted by the user
     * @param testCount The number of test cases to generate and run
     * @return A Submission object containing test results and pass/fail status
     * @throws Exception If code execution fails
     */
    public static Submission evaluate(Exercise exercise, String userCode, int testCount) throws Exception {
        List<String> inputs = generateRandomInputs(testCount);
        List<TestResult> results = new ArrayList<>();
        boolean allPassed = true;

        for (String input : inputs) {
            // Run reference solution
            CodeExecutor.ExecutionResult expectedResult =
                    CodeExecutor.runCode(exercise.getSolutionCode(), exercise.getLanguage(), input);

            // Run user's code
            CodeExecutor.ExecutionResult userResult =
                    CodeExecutor.runCode(userCode, exercise.getLanguage(), input);

            boolean passed = userResult.stdout.strip().equals(expectedResult.stdout.strip());
            if (!passed) allPassed = false;

            TestResult result = new TestResult();
            result.setInput(input);
            result.setExpectedOutput(expectedResult.stdout);
            result.setActualOutput(userResult.stdout);
            result.setErrorMessage(userResult.stderr);
            result.setPassed(passed);

            results.add(result);
        }

        Submission submission = new Submission();
        submission.setExerciseId(exercise.getId());
        submission.setUserCode(userCode);
        submission.setLanguage(exercise.getLanguage());
        submission.setResults(results);
        submission.setPassed(allPassed);

        return submission;
    }



    // A modifier prochainement : c'est cette methode qui s'occupe de la generation des entrees 
    // pour le jeu de test donc elle est a adapter quand il y aura la bdd dispo


    /**
     * Generates a list of random input strings to be used in test cases.
     * For example, generating pairs of integers separated by space.
     *
     * @param count Number of test cases to generate
     * @return List of input strings
     */
    private static List<String> generateRandomInputs(int count) {
        List<String> inputs = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < count; i++) {
            int a = rand.nextInt(100);  // Change range and format as needed
            int b = rand.nextInt(100);
            inputs.add(a + " " + b);
        }

        return inputs;
    }
}