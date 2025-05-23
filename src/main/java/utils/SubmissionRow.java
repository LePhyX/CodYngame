package utils;

import javafx.beans.property.*;

public class SubmissionRow {
    private final StringProperty  exercise      = new SimpleStringProperty();
    private final IntegerProperty total         = new SimpleIntegerProperty();
    private final IntegerProperty success       = new SimpleIntegerProperty();
    private final IntegerProperty firstId       = new SimpleIntegerProperty();  // on garde pour l’historique
    private final IntegerProperty firstAttempt  = new SimpleIntegerProperty();  // nouvelle propriété

    public SubmissionRow(String exo, int tot, int suc, int first) {
        exercise.set(exo);
        total.set(tot);
        success.set(suc);
        firstId.set(first);        // pour compat
        firstAttempt.set(first);   // la vraie sémantique
    }

    // propriétés existantes
    public StringProperty  exerciseProperty() { return exercise; }
    public IntegerProperty totalProperty()    { return total; }
    public IntegerProperty successProperty()  { return success; }
    /** Ancienne méthode, conservée pour l’historique si tu l’utilisais ailleurs */
    public IntegerProperty firstIdProperty()  { return firstId; }

    // nouvelle API :
    /** Renvoie l’ID de la première réussite (pour ton 4ᵉ colonne) */
    public IntegerProperty firstAttemptProperty() { return firstAttempt; }
}
