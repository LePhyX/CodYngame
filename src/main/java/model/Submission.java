package model;

import java.time.LocalDateTime;

public class Submission {
    private int id;
    private int userId;
    private int exerciseId;
    private int languageId;
    private String code;
    private String result;
    private boolean success;
    private LocalDateTime createdAt;

    public Submission() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getExerciseId() { return exerciseId; }
    public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }

    public int getLanguageId() { return languageId; }
    public void setLanguageId(int languageId) { this.languageId = languageId; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
