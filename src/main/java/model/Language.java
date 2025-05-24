package model;

/**
 * Represents a programming language used in exercises.
 * Each language has a unique ID and a name.
 */
public class Language {

    private int id;
    private String name;

    /**
     * Default constructor.
     */
    public Language() {}

    /**
     * Constructs a language with the specified name.
     *
     * @param name the name of the language (e.g., "Java", "Python")
     */
    public Language(String name) {
        this.name = name;
    }

    /**
     * Returns the language ID.
     *
     * @return the ID of the language
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the language ID.
     *
     * @param id the new ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the language name.
     *
     * @return the name of the language
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the language name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name as string representation of this object.
     *
     * @return the name of the language
     */
    @Override
    public String toString() {
        return name; // or getName() if preferred
    }
}
