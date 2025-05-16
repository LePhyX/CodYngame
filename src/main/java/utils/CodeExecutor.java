package utils;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Utility class responsible for compiling and executing user-submitted code
 * in various programming languages.
 */
public class CodeExecutor {

    /**
     * Holds the result of code execution.
     */
    public static class ExecutionResult {
        /** Output written to standard output (stdout) by the program */
        public String stdout;

        /** Output written to standard error (stderr) by the program */
        public String stderr;

        /** Exit code of the process (0 usually means success) */
        public int exitCode;
    }

    /**
     * Compiles and executes the provided source code in the selected language,
     * feeding the given input to the program.
     *
     * @param code     The source code written by the user
     * @param language The language of the code ("java", "c", "python")
     * @param input    Input string to send to standard input (stdin)
     * @return An ExecutionResult containing stdout, stderr, and the exit code
     * @throws IOException If an I/O error occurs
     * @throws InterruptedException If the execution is interrupted
     */
    public static ExecutionResult runCode(String code, String language, String input)
            throws IOException, InterruptedException {

        // Create a temporary directory to store source and compiled files
        Path tempDir = Files.createTempDirectory("codeyngame_");

        File sourceFile;
        List<String> compileCmd = new ArrayList<>();
        List<String> execCmd = new ArrayList<>();

        // Determine how to compile and execute based on the language
        switch (language.toLowerCase()) {
            case "java":
                // Save code to Main.java
                sourceFile = tempDir.resolve("Main.java").toFile();
                Files.writeString(sourceFile.toPath(), code);

                // Compile using javac
                compileCmd = List.of("javac", sourceFile.getAbsolutePath());

                // Command to run compiled Java class
                execCmd = List.of("java", "-cp", tempDir.toString(), "Main");
                break;

            case "c":
                // Save code to main.c
                sourceFile = tempDir.resolve("main.c").toFile();
                Files.writeString(sourceFile.toPath(), code);

                // Compile using gcc
                compileCmd = List.of("gcc", sourceFile.getAbsolutePath(), "-o",
                                     tempDir.resolve("main").toString());

                // Command to run compiled C program
                execCmd = List.of(tempDir.resolve("main").toString());
                break;

            case "python":
                // Save code to main.py (Python is interpreted, no compile step)
                sourceFile = tempDir.resolve("main.py").toFile();
                Files.writeString(sourceFile.toPath(), code);

                // Command to interpret Python script
                execCmd = List.of("python3", sourceFile.getAbsolutePath());
                break;

            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }

        // Compile the code if a compile command is defined
        if (!compileCmd.isEmpty()) {
            Process compile = new ProcessBuilder(compileCmd)
                    .redirectErrorStream(true)
                    .start();
            compile.waitFor(); // Wait for the compiler to finish
        }

        // Execute the program
        ProcessBuilder execBuilder = new ProcessBuilder(execCmd);
        Process process = execBuilder.start();

        // Feed the input string to the program's standard input
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(process.getOutputStream()))) {
            writer.write(input);
            writer.flush();
        }

        // Capture the standard output and standard error of the process
        String stdout = new String(process.getInputStream().readAllBytes());
        String stderr = new String(process.getErrorStream().readAllBytes());

        // Wait for the process to finish and retrieve the exit code
        int exitCode = process.waitFor();

        // Package the results into an ExecutionResult instance
        ExecutionResult result = new ExecutionResult();
        result.stdout = stdout;
        result.stderr = stderr;
        result.exitCode = exitCode;

        return result;
    }
}
