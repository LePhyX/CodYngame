package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
