package model;

import javafx.beans.property.*;

/**
 * Represents a coding exercise.
 * Each exercise has properties such as title, description, type, difficulty,
 * optional PDF files, and extra data for INCLUDE-type exercises.
 */
public class Exercise {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final ObjectProperty<byte[]> exercisePdf = new SimpleObjectProperty<>();
    private final ObjectProperty<byte[]> solutionPdf = new SimpleObjectProperty<>();
    private final StringProperty solution = new SimpleStringProperty();
    private final IntegerProperty difficulty = new SimpleIntegerProperty();

    // === INCLUDE mode-specific fields ===
    private final StringProperty baseCode = new SimpleStringProperty();
    private final IntegerProperty lineCode = new SimpleIntegerProperty();

    /**
     * Default constructor.
     */
    public Exercise() {}

    /**
     * Full constructor with all exercise details.
     *
     * @param title        The title of the exercise
     * @param description  The description/instructions
     * @param type         The exercise type (e.g., INCLUDE, STDIN)
     * @param exercisePdf  The PDF for the exercise statement (optional)
     * @param solutionPdf  The PDF for the solution (optional)
     * @param solution     The reference solution as code
     * @param difficulty   The difficulty level (e.g., 1 to 5)
     */
    public Exercise(String title, String description, String type, byte[] exercisePdf,
                    byte[] solutionPdf, String solution, int difficulty) {
        this.title.set(title);
        this.description.set(description);
        this.type.set(type);
        this.exercisePdf.set(exercisePdf);
        this.solutionPdf.set(solutionPdf);
        this.solution.set(solution);
        this.difficulty.set(difficulty);
    }

    /**
     * Simplified constructor used for list display or queries.
     *
     * @param id         The ID of the exercise
     * @param title      The title
     * @param type       The type of exercise
     * @param difficulty The difficulty level
     */
    public Exercise(int id, String title, String type, int difficulty) {
        this.id.set(id);
        this.title.set(title);
        this.type.set(type);
        this.difficulty.set(difficulty);
    }

    // --- ID ---
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    // --- Title ---
    public String getTitle() { return title.get(); }
    public void setTitle(String title) { this.title.set(title); }
    public StringProperty titleProperty() { return title; }

    // --- Description ---
    public String getDescription() { return description.get(); }
    public void setDescription(String description) { this.description.set(description); }
    public StringProperty descriptionProperty() { return description; }

    // --- Type ---
    public String getType() { return type.get(); }
    public void setType(String type) { this.type.set(type); }
    public StringProperty typeProperty() { return type; }

    // --- Exercise PDF ---
    public byte[] getExercisePdf() { return exercisePdf.get(); }
    public void setExercisePdf(byte[] exercisePdf) { this.exercisePdf.set(exercisePdf); }
    public ObjectProperty<byte[]> exercisePdfProperty() { return exercisePdf; }

    // --- Solution PDF ---
    public byte[] getSolutionPdf() { return solutionPdf.get(); }
    public void setSolutionPdf(byte[] solutionPdf) { this.solutionPdf.set(solutionPdf); }
    public ObjectProperty<byte[]> solutionPdfProperty() { return solutionPdf; }

    // --- Solution ---
    public String getSolution() { return solution.get(); }
    public void setSolution(String solution) { this.solution.set(solution); }
    public StringProperty solutionProperty() { return solution; }

    // --- Difficulty ---
    public int getDifficulty() { return difficulty.get(); }
    public void setDifficulty(int difficulty) { this.difficulty.set(difficulty); }
    public IntegerProperty difficultyProperty() { return difficulty; }

    // --- INCLUDE Mode: Base Code ---
    public String getBaseCode() { return baseCode.get(); }
    public void setBaseCode(String baseCode) { this.baseCode.set(baseCode); }
    public StringProperty baseCodeProperty() { return baseCode; }

    // --- INCLUDE Mode: Line of insertion ---
    public int getLineCode() { return lineCode.get(); }
    public void setLineCode(int lineCode) { this.lineCode.set(lineCode); }
    public IntegerProperty lineCodeProperty() { return lineCode; }
}
