package com.example.projet_java;

import java.io.File;              // Pour représenter un fichier dans le système
import java.io.FileInputStream;   // Pour lire un fichier en tant que flux de données
import java.io.IOException;       // Pour gérer les erreurs de lecture

/**
 * Classe utilitaire pour charger un fichier (ex : PDF)
 * et le transformer en tableau de bytes (byte[])
 * Cela permet de stocker le contenu du fichier dans une base de données (BLOB)
 */
public class FileUtils {

    /**
     * Méthode pour lire un fichier à partir de son chemin
     * et retourner son contenu sous forme de tableau de bytes.
     *
     * @param path Chemin du fichier à lire (ex : "pdf/exo1.pdf")
     * @return Un tableau de bytes contenant tout le fichier
     * @throws IOException Si le fichier est introuvable ou illisible
     */
    public static byte[] readFileAsBytes(String path) throws IOException {
        File file = new File(path);  // Crée un objet File à partir du chemin

        // try-with-resources : ferme automatiquement le flux après utilisation
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] data = new byte[(int) file.length()];  // Crée un tableau de bytes à la taille du fichier
            fis.read(data);                               // Remplit le tableau avec le contenu du fichier
            return data;                                  // Retourne le fichier sous forme binaire
        }
    }
}