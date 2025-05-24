package model;

import java.time.LocalDateTime;

/**
 * Represents a code submission made by a user for a specific exercise.
 * Stores the submitted code, result, success status, and creation timestamp.
 */
public class Submission {

    private int id;
    private int userId;
    private int exerciseId;
    private int languageId;
    private String code;
    private String result;
    private boolean success;
    private LocalDateTime createdAt;

    /**
     * Default constructor.
     */
    public Submission() {}

    /**
     * Gets the submission ID.
     *
     * @return the ID of the submission
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the submission ID.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the user ID who made the submission.
     *
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID for this submission.
     *
     * @param userId the user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the exercise ID the submission is related to.
     *
     * @return the exercise ID
     */
    public int getExerciseId() {
        return exerciseId;
    }

    /**
     * Sets the exercise ID for this submission.
     *
     * @param exerciseId the exercise ID to set
     */
    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    /**
     * Gets the language ID used in the submission.
     *
     * @return the language ID
     */
    public int getLanguageId() {
        return languageId;
    }

    /**
     * Sets the language ID for the submission.
     *
     * @param languageId the language ID to set
     */
    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    /**
     * Gets the source code submitted.
     *
     * @return the source code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the submitted source code.
     *
     * @param code the source code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the result output of the code execution.
     *
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * Sets the result of the submission.
     *
     * @param result the result string
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * Returns whether the submission was successful.
     *
     * @return true if successful, false otherwise
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success status of the submission.
     *
     * @param success true if successful
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Gets the timestamp of when the submission was created.
     *
     * @return the creation date and time
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the timestamp for the submission creation.
     *
     * @param createdAt the LocalDateTime to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
