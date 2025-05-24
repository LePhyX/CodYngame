package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Utility class for reading files from the file system.
 */
public class FileUtils {

    /**
     * Reads the contents of a file located at the specified path
     * and returns it as a byte array.
     *
     * @param path the file path (e.g., "pdf/exo1.pdf")
     * @return a byte array containing the file's binary content
     * @throws IOException if the file cannot be found or read
     */
    public static byte[] readFileAsBytes(String path) throws IOException {
        File file = new File(path);

        // Use try-with-resources to ensure the input stream is closed automatically
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            return data;
        }
    }
}
