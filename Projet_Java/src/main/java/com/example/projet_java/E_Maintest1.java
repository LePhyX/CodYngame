package com.example.projet_java;

import java.sql.Connection;

public class E_Maintest1 {
    public static void main(String[] args) {
        try {
            // Connexion à la base de données
            Connection conn = DatabaseConnection.getConnection();

            // Lecture des fichiers PDF
            byte[] enoncePdf = FileUtils.readFileAsBytes("C:/Users/Administrateur/Desktop/export/TD8_Java_.pdf");
            byte[] solutionPdf = FileUtils.readFileAsBytes("C:/Users/Administrateur/Desktop/export/TD8_Java_Sol.pdf");

            // Création d’un objet Exercise
            Exercise exo = new Exercise(
                    "Addition de matrices",                          // Titre
                    "Implémente une addition de matrices 3x3",       // Description
                    "INCLUDE",                                       // Type
                    enoncePdf,                                       // PDF énoncé
                    solutionPdf,                                     // PDF solution
                    "code de la solution ici...",                    // Solution modèle
                    1                                                // Difficulté (1 = facile)
            );

            // Insertion dans la base via DAO
            ExerciseDAO dao = new ExerciseDAO(conn);
            System.out.println("Connecté à : " + conn.getCatalog()); // nom de la base
            boolean success = dao.insertExercise(exo);

            // Affichage résultat
            if (success) {
                System.out.println(" Exercice inséré dans la BDD !");
            } else {
                System.out.println(" Insertion échouée.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
