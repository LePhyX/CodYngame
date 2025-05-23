package utils;

import model.User;



public class Session {
    // 1. Singleton
    private static final Session INSTANCE = new Session();

    private Session() {
    }

    public static Session getInstance() {
        return INSTANCE;
    }

    // 2. État stocké
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}