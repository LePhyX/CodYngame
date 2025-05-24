package model;

/**
 * Represents the association between an exercise and a programming language.
 * Used for the many-to-many relationship between Exercise and Language tables.
 */
public class ExerciseLanguage {

    private int exerciseId;
    private int languageId;

    /**
     * Default constructor.
     */
    public ExerciseLanguage() {}

    /**
     * Constructs a new ExerciseLanguage association.
     *
     * @param exerciseId the ID of the exercise
     * @param languageId the ID of the language
     */
    public ExerciseLanguage(int exerciseId, int languageId) {
        this.exerciseId = exerciseId;
        this.languageId = languageId;
    }

    /**
     * Gets the exercise ID.
     *
     * @return the exercise ID
     */
    public int getExerciseId() {
        return exerciseId;
    }

    /**
     * Sets the exercise ID.
     *
     * @param exerciseId the new exercise ID
     */
    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    /**
     * Gets the language ID.
     *
     * @return the language ID
     */
    public int getLanguageId() {
        return languageId;
    }

    /**
     * Sets the language ID.
     *
     * @param languageId the new language ID
     */
    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }
}
