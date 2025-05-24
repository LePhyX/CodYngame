package model;

/**
 * Represents an administrator user.
 * Inherits from the base {@link User} class and adds an admin-specific identifier.
 */
public class Admin extends User {
    private int adminId;

    /**
     * Default constructor.
     */
    public Admin() {}

    /**
     * Constructs an Admin object with all necessary fields.
     *
     * @param adminId      The unique identifier for the admin.
     * @param username     The admin's username.
     * @param email        The admin's email.
     * @param passwordHash The hashed password.
     */
    public Admin(int adminId, String username, String email, String passwordHash) {
        super(username, email, passwordHash);
        this.adminId = adminId;
    }

    /**
     * Returns the admin ID.
     *
     * @return the admin ID.
     */
    public int getAdminId() {
        return adminId;
    }

    /**
     * Sets the admin ID.
     *
     * @param adminId the new admin ID.
     */
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
}
