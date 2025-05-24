package utils;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Utility class responsible for compiling and executing user-submitted source code
 * in various programming languages (Java, C, Python).
 */
public class CodeExecutor {

    /**
     * Represents the result of code execution.
     * Contains standard output, standard error, and the process exit code.
     */
    public static class ExecutionResult {
        /** Standard output produced by the executed code */
        public String stdout;

        /** Standard error output produced by the executed code */
        public String stderr;

        /** Exit code of the execution process (0 usually means success) */
        public int exitCode;
    }

    /**
     * Compiles and executes the provided source code in the given language,
     * optionally passing input to the standard input of the process.
     *
     * @param code     The source code to execute
     * @param language The programming language ("java", "c", "python")
     * @param input    Input string to provide to the program via stdin
     * @return An {@link ExecutionResult} containing stdout, stderr, and exit code
     * @throws IOException          If an I/O error occurs during execution
     * @throws InterruptedException If the process is interrupted
     */
    public static ExecutionResult runCode(String code, String language, String input)
            throws IOException, InterruptedException {

        // Create a temporary directory to work in
        Path tempDir = Files.createTempDirectory("codeyngame_");

        File sourceFile;
        List<String> compileCmd = new ArrayList<>();
        List<String> execCmd = new ArrayList<>();

        // Determine behavior based on the language
        switch (language.toLowerCase()) {
            case "java":
                // Write Java code to Main.java
                sourceFile = tempDir.resolve("Main.java").toFile();
                Files.writeString(sourceFile.toPath(), code);

                // Compile with javac
                compileCmd = List.of("javac", sourceFile.getAbsolutePath());

                // Execute the compiled class
                execCmd = List.of("java", "-cp", tempDir.toString(), "Main");
                break;

            case "c":
                // Write C code to main.c
                sourceFile = tempDir.resolve("main.c").toFile();
                Files.writeString(sourceFile.toPath(), code);

                // Compile with gcc
                compileCmd = List.of("gcc", sourceFile.getAbsolutePath(), "-o",
                        tempDir.resolve("main").toString());

                // Execute compiled binary
                execCmd = List.of(tempDir.resolve("main").toString());
                break;

            case "python":
                // Write Python code to main.py (no compilation needed)
                sourceFile = tempDir.resolve("main.py").toFile();
                Files.writeString(sourceFile.toPath(), code);

                // Execute using python interpreter
                execCmd = List.of("python3", sourceFile.getAbsolutePath());
                break;

            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }

        // Compile the source code if required
        if (!compileCmd.isEmpty()) {
            Process compile = new ProcessBuilder(compileCmd)
                    .redirectErrorStream(true)
                    .start();
            compile.waitFor(); // Wait for compilation to complete
        }

        // Execute the compiled/interpreted code
        ProcessBuilder execBuilder = new ProcessBuilder(execCmd);
        Process process = execBuilder.start();

        // Send the provided input to the program's standard input
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(process.getOutputStream()))) {
            writer.write(input);
            writer.flush();
        }

        // Capture output and error streams
        String stdout = new String(process.getInputStream().readAllBytes());
        String stderr = new String(process.getErrorStream().readAllBytes());

        // Wait for process to terminate and get the exit code
        int exitCode = process.waitFor();

        // Populate result object
        ExecutionResult result = new ExecutionResult();
        result.stdout = stdout;
        result.stderr = stderr;
        result.exitCode = exitCode;

        return result;
    }
}
