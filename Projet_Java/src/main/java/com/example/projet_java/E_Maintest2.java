package com.example.projet_java;
import java.awt.Desktop;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.File;

/**
 * Classe principale pour tester la lecture d’un exercice depuis la base de données
 * et écrire ses fichiers PDF (énoncé + solution) sur le disque.
 */




public class E_Maintest2 {

    public static void main(String[] args) {
        try {
            // Connexion à la BDD
            Connection conn = DatabaseConnection.getConnection();

            // ID de l’exercice à extraire
            int exerciseId = 1;

            // Requête SQL
            String sql = "SELECT exercise_image, solution_image FROM Exercise WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, exerciseId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                byte[] exoPdf = rs.getBytes("exercise_image");
                byte[] solPdf = rs.getBytes("solution_image");

                // Chemins de sortie
                String pathExo = "C:/Users/Administrateur/Desktop/export/TD8_Java_.pdf";
                String pathSol = "C:/Users/Administrateur/Desktop/export/TD8_Java_Sol.pdf";

                // Écriture des fichiers PDF
                try (FileOutputStream fos1 = new FileOutputStream(pathExo);
                     FileOutputStream fos2 = new FileOutputStream(pathSol)) {

                    fos1.write(exoPdf);
                    fos2.write(solPdf);
                    System.out.println("✅ Les fichiers PDF ont été extraits avec succès !");
                }

                // Ouverture automatique
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    File fichierExo = new File(pathExo);
                    File fichierSol = new File(pathSol);

                    if (fichierExo.exists()) desktop.open(fichierExo);
                    if (fichierSol.exists()) desktop.open(fichierSol);
                }

            } else {
                System.out.println("❌ Aucun exercice trouvé avec l’ID : " + exerciseId);
            }

        } catch (IOException e) {
            System.err.println("❌ Erreur d’écriture ou d’ouverture de fichier : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
