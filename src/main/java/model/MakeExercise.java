package model;

import java.time.LocalDateTime;

/**
 * Represents an attempt made by a user on a specific exercise.
 * Stores the user ID, exercise ID, the timestamp when it started, and whether it was successful.
 */
public class MakeExercise {

    private int id;
    private int userId;
    private int exerciseId;
    private LocalDateTime beginsThe;
    private boolean success;

    /**
     * Default constructor.
     */
    public MakeExercise() {}

    /**
     * Constructs a MakeExercise object with the main fields.
     *
     * @param userId     the ID of the user
     * @param exerciseId the ID of the exercise
     * @param success    whether the exercise was completed successfully
     */
    public MakeExercise(int userId, int exerciseId, boolean success) {
        this.userId = userId;
        this.exerciseId = exerciseId;
        this.success = success;
    }

    /**
     * Returns the ID of this record.
     *
     * @return the makeExercise ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of this record.
     *
     * @param id the new makeExercise ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the user ID.
     *
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId the user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Returns the exercise ID.
     *
     * @return the exercise ID
     */
    public int getExerciseId() {
        return exerciseId;
    }

    /**
     * Sets the exercise ID.
     *
     * @param exerciseId the exercise ID to set
     */
    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    /**
     * Returns the timestamp when the user began the exercise.
     *
     * @return the start time
     */
    public LocalDateTime getBeginsThe() {
        return beginsThe;
    }

    /**
     * Sets the timestamp when the user began the exercise.
     *
     * @param beginsThe the start time to set
     */
    public void setBeginsThe(LocalDateTime beginsThe) {
        this.beginsThe = beginsThe;
    }

    /**
     * Returns whether the user successfully completed the exercise.
     *
     * @return true if successful, false otherwise
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets whether the exercise attempt was successful.
     *
     * @param success true if successful, false otherwise
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
