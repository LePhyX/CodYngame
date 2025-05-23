package utils;

public class SubmissionStats {
    private final String exerciseTitle;
    private final int totalCount;
    private final int successCount;
    private final int firstSuccessId;   // historique
    private final int firstAttemptId;   // nouvelle sémantique

    public SubmissionStats(String exerciseTitle, int totalCount, int successCount, int firstSuccessId) {
        this.exerciseTitle  = exerciseTitle;
        this.totalCount     = totalCount;
        this.successCount   = successCount;
        this.firstSuccessId = firstSuccessId;
        this.firstAttemptId = firstSuccessId;  // on aligne les deux
    }

    public String getExerciseTitle()   { return exerciseTitle; }
    public int    getTotalCount()      { return totalCount; }
    public int    getSuccessCount()    { return successCount; }
    /** Ancienne méthode, conservée pour rétro-compatibilité */
    public int    getFirstSuccessId()  { return firstSuccessId; }
    /** Nouvelle méthode, plus parlante pour la première réussite */
    public int    getFirstAttemptId()  { return firstAttemptId; }
}
