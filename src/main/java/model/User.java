package model;

import java.sql.Timestamp;

/**
 * Represents a user of the application.
 * Stores basic credentials, score, and registration date.
 */
public class User {

    private int id;
    private String username;
    private String email;
    private String passwordHash;
    private int score;
    private Timestamp createdAt;

    /**
     * Default constructor.
     */
    public User() {}

    /**
     * Constructor used during user registration.
     *
     * @param username     the username chosen by the user
     * @param email        the email address
     * @param passwordHash the hashed password
     */
    public User(String username, String email, String passwordHash) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.score = 0;
    }

    /**
     * Constructor used to load a user from the database.
     *
     * @param id           the user ID
     * @param username     the username
     * @param email        the email
     * @param passwordHash the hashed password
     * @param score        the current score
     */
    public User(int id, String username, String email, String passwordHash, int score) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.score = score;
    }

    /**
     * Gets the user's creation timestamp.
     *
     * @return the registration timestamp
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the registration timestamp.
     *
     * @param createdAt the timestamp to set
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /** @return the user ID */
    public int getId() {
        return id;
    }

    /** @param id the user ID to set */
    public void setId(int id) {
        this.id = id;
    }

    /** @return the username */
    public String getUsername() {
        return username;
    }

    /** @param username the username to set */
    public void setUsername(String username) {
        this.username = username;
    }

    /** @return the email address */
    public String getEmail() {
        return email;
    }

    /** @param email the email address to set */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return the hashed password */
    public String getPasswordHash() {
        return passwordHash;
    }

    /** @param passwordHash the password hash to set */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /** @return the user's score */
    public int getScore() {
        return score;
    }

    /** @param score the score to set */
    public void setScore(int score) {
        this.score = score;
    }
}
