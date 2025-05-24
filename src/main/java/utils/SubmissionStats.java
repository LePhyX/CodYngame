package utils;

/**
 * Represents statistical data about a user's submissions for a specific exercise.
 * Includes total number of submissions, number of successful ones,
 * and the position (rank) of the first successful submission.
 */
public class SubmissionStats {

    /** The title of the exercise */
    private final String exerciseTitle;

    /** Total number of submissions for this exercise */
    private final int totalCount;

    /** Number of successful submissions */
    private final int successCount;

    /** Legacy field: ID (rank) of the first successful attempt (for backward compatibility) */
    private final int firstSuccessId;

    /** New field: semantic alias for first successful attempt rank */
    private final int firstAttemptId;

    /**
     * Constructor for SubmissionStats.
     *
     * @param exerciseTitle   Title of the exercise
     * @param totalCount      Total number of submissions
     * @param successCount    Number of successful submissions
     * @param firstSuccessId  Rank of the first successful submission (ROW_NUMBER)
     */
    public SubmissionStats(String exerciseTitle, int totalCount, int successCount, int firstSuccessId) {
        this.exerciseTitle  = exerciseTitle;
        this.totalCount     = totalCount;
        this.successCount   = successCount;
        this.firstSuccessId = firstSuccessId;
        this.firstAttemptId = firstSuccessId;  // new naming convention aligned
    }

    /**
     * @return Title of the exercise
     */
    public String getExerciseTitle() {
        return exerciseTitle;
    }

    /**
     * @return Total number of submissions
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @return Number of successful submissions
     */
    public int getSuccessCount() {
        return successCount;
    }

    /**
     * Legacy getter for first successful attempt ID.
     * @return Rank of the first success (for backward compatibility)
     */
    public int getFirstSuccessId() {
        return firstSuccessId;
    }

    /**
     * New semantic getter for the first success position.
     * @return Rank of the first successful attempt
     */
    public int getFirstAttemptId() {
        return firstAttemptId;
    }
}
