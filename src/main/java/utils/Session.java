package utils;

import model.User;

/**
 * Singleton class that manages the current user session.
 * This is used to keep track of the logged-in user throughout the application.
 */
public class Session {

    /** The single instance of the session (singleton pattern) */
    private static final Session INSTANCE = new Session();

    /** Private constructor to prevent external instantiation */
    private Session() {}

    /**
     * Provides access to the singleton instance of the session.
     *
     * @return The shared Session instance
     */
    public static Session getInstance() {
        return INSTANCE;
    }

    // ========================
    //   Session State
    // ========================

    /** The currently logged-in user */
    private User currentUser;

    /**
     * Gets the current logged-in user.
     *
     * @return The current user object, or null if no user is logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current logged-in user.
     *
     * @param user The user to associate with the session
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}
