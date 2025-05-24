package utils;

import javafx.beans.property.*;

/**
 * Represents a row in the submissions table.
 * Stores statistics about a user's submissions for a given exercise.
 */
public class SubmissionRow {

    /** The name or title of the exercise */
    private final StringProperty exercise = new SimpleStringProperty();

    /** Total number of submissions made for the exercise */
    private final IntegerProperty total = new SimpleIntegerProperty();

    /** Number of successful submissions */
    private final IntegerProperty success = new SimpleIntegerProperty();

    /** Legacy property: kept for compatibility if used elsewhere */
    private final IntegerProperty firstId = new SimpleIntegerProperty();

    /** ID of the first successful submission (used in current logic) */
    private final IntegerProperty firstAttempt = new SimpleIntegerProperty();

    /**
     * Constructor initializing all fields.
     *
     * @param exo   The exercise title
     * @param tot   Total submissions
     * @param suc   Number of successes
     * @param first ID of the first successful submission
     */
    public SubmissionRow(String exo, int tot, int suc, int first) {
        exercise.set(exo);
        total.set(tot);
        success.set(suc);
        firstId.set(first);       // legacy compatibility
        firstAttempt.set(first);  // primary usage
    }

    // === Property accessors for TableView binding ===

    /**
     * Returns the exercise name property.
     */
    public StringProperty exerciseProperty() {
        return exercise;
    }

    /**
     * Returns the total submission count property.
     */
    public IntegerProperty totalProperty() {
        return total;
    }

    /**
     * Returns the successful submission count property.
     */
    public IntegerProperty successProperty() {
        return success;
    }

    /**
     * Legacy method for backward compatibility.
     * Use {@link #firstAttemptProperty()} for updated logic.
     */
    public IntegerProperty firstIdProperty() {
        return firstId;
    }

    /**
     * Returns the first successful attempt ID property.
     * Used for displaying in the fourth column of the submission table.
     */
    public IntegerProperty firstAttemptProperty() {
        return firstAttempt;
    }
}
