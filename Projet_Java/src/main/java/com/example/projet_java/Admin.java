package com.example.projet_java;

public class Admin extends User {
    private int adminId;

    public Admin() {}

    public Admin(int adminId, String username, String email, String passwordHash) {
        super(username, email, passwordHash);
        this.adminId = adminId;
    }

    public int getAdminId() { return adminId; }
    public void setAdminId(int adminId) { this.adminId = adminId; }
}