package model;


import java.time.LocalDateTime;

public class MakeExercise {
    private int id;
    private int userId;
    private int exerciseId;
    private LocalDateTime beginsThe;
    private boolean success;

    public MakeExercise() {}

    public MakeExercise(int userId, int exerciseId, boolean success) {
        this.userId = userId;
        this.exerciseId = exerciseId;
        this.success = success;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getExerciseId() { return exerciseId; }
    public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }

    public LocalDateTime getBeginsThe() { return beginsThe; }
    public void setBeginsThe(LocalDateTime beginsThe) { this.beginsThe = beginsThe; }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
}
