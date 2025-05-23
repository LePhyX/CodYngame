package model;

public class User {
    private int id;
    private String username;
    private String email;
    private String passwordHash;
    private int score;
    private java.sql.Timestamp createdAt;

    public User() {}

    // Constructeur utilisé lors de l'inscription
    public User(String username, String email, String passwordHash) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.score = 0;
    }

    // ✅ Constructeur utilisé pour charger un User depuis la BDD
    public User(int id, String username, String email, String passwordHash, int score) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.score = score;
    }

    public java.sql.Timestamp getCreatedAt(){return createdAt;}

    public void setCreatedAt(java.sql.Timestamp createdAt) {    this.createdAt = createdAt;}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
}
