package utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class that merges user-submitted code with a predefined base,
 * automatically generates test cases, compiles if necessary, and executes the final result.
 */
public class FusionneurCode3 {

    /**
     * Class to hold the result of the code execution, including standard output,
     * standard error, and the process exit code.
     */
    public static class ResultatExecution {
        private String sortieStandard;
        private String sortieErreur;
        private int codeRetour;

        public ResultatExecution(String out, String err, int code) {
            this.sortieStandard = out;
            this.sortieErreur = err;
            this.codeRetour = code;
        }

        public String getSortieStandard() { return sortieStandard; }
        public String getSortieErreur() { return sortieErreur; }
        public int getCodeRetour() { return codeRetour; }
    }

    /**
     * Compiles and executes code submitted by the user, optionally injecting it into a base template.
     *
     * @param langage         The programming language (e.g. "java", "python", "c", "php", "javascript")
     * @param codeUtilisateur The user-submitted function or code snippet
     * @param codeBase        Optional base code to inject into (used when not empty)
     * @param ligneInsertion  The line number in base code to insert the user code
     * @return                An execution result with output, error, and exit code
     */
    public ResultatExecution executerCode(String langage, String codeUtilisateur, String codeBase, int ligneInsertion)
            throws IOException, InterruptedException {

        String codeFinal = "";

        // === PYTHON ===
        if (langage.equalsIgnoreCase("python")) {
            codeFinal = codeUtilisateur;
            String nomFonction = extraireNomFonctionPython(codeUtilisateur);
            if (nomFonction == null) throw new IllegalArgumentException("Cannot find a Python function.");

            String params = extraireParametresFonctionPython(codeUtilisateur, nomFonction);
            int nbArgs = params.isEmpty() ? 0 : params.split(",").length;

            StringBuilder testsCode = new StringBuilder("\n# Automatic tests\n");
            if (nbArgs == 2) {
                testsCode.append("print(").append(nomFonction).append("(2, 3))\n");
                testsCode.append("print(").append(nomFonction).append("(1, 2))\n");
            } else if (nbArgs == 1) {
                testsCode.append("print(").append(nomFonction).append("(2))\n");
                testsCode.append("print(").append(nomFonction).append("(3))\n");
            } else {
                testsCode.append("print(").append(nomFonction).append("())\n");
            }

            codeFinal += testsCode.toString();
        }

        // === C === (when no base code is provided)
        else if (langage.equalsIgnoreCase("c") && (codeBase == null || codeBase.trim().isEmpty())) {
            codeFinal = codeUtilisateur;
            String nomFonction = extraireNomFonctionC(codeUtilisateur);
            if (nomFonction == null) throw new IllegalArgumentException("Cannot find a C function.");

            boolean hasStdioInclude = codeUtilisateur.contains("#include <stdio.h>");
            boolean hasMainFunction = codeUtilisateur.contains("main(");

            if (!hasMainFunction) {
                int nbArgs = compterArguments(codeUtilisateur, nomFonction);
                StringBuilder mainCode = new StringBuilder();
                if (!hasStdioInclude) mainCode.append("#include <stdio.h>\n");

                mainCode.append("int main() {\n");
                if (nbArgs == 2) {
                    mainCode.append("    printf(\"%d\\n\", ").append(nomFonction).append("(2, 3));\n");
                    mainCode.append("    printf(\"%d\\n\", ").append(nomFonction).append("(1, 2));\n");
                } else if (nbArgs == 1) {
                    mainCode.append("    printf(\"%d\\n\", ").append(nomFonction).append("(2));\n");
                    mainCode.append("    printf(\"%d\\n\", ").append(nomFonction).append("(3));\n");
                } else {
                    mainCode.append("    printf(\"%d\\n\", ").append(nomFonction).append("());\n");
                }
                mainCode.append("    return 0;\n}\n");

                codeFinal += "\n" + mainCode;
            }
        }

        // === JAVA === (when no base code is provided)
        else if (langage.equalsIgnoreCase("java") && (codeBase == null || codeBase.trim().isEmpty())) {
            String methodName = extraireNomFonctionJava(codeUtilisateur);
            if (methodName == null) {
                throw new IllegalArgumentException("Cannot find a Java method in the user code.");
            }

            int nbArgs = compterArguments(codeUtilisateur, methodName);

            StringBuilder javaCode = new StringBuilder();
            javaCode.append("public class Main {\n\n");

            String[] lignesCode = codeUtilisateur.split("\\r?\\n");
            for (String ligne : lignesCode) {
                ligne = ligne.trim();
                if (!ligne.startsWith("System.out.println")) {
                    javaCode.append("    ").append(ligne).append("\n");
                }
            }

            javaCode.append("\n    public static void main(String[] args) {\n");
            if (nbArgs == 2) {
                javaCode.append("        System.out.println(").append(methodName).append("(2, 3));\n");
                javaCode.append("        System.out.println(").append(methodName).append("(1, 2));\n");
            } else if (nbArgs == 1) {
                javaCode.append("        System.out.println(").append(methodName).append("(2));\n");
                javaCode.append("        System.out.println(").append(methodName).append("(3));\n");
            } else {
                javaCode.append("        System.out.println(").append(methodName).append("());\n");
            }
            javaCode.append("    }\n}\n");

            codeFinal = javaCode.toString();
        }

        // === JAVASCRIPT ===
        else if (langage.equalsIgnoreCase("javascript") && (codeBase == null || codeBase.trim().isEmpty())) {
            String methodName = extraireNomFonctionJavaScript(codeUtilisateur);
            if (methodName == null) {
                throw new IllegalArgumentException("Cannot find a JavaScript function in the user code.");
            }

            int nbArgs = codeUtilisateur.contains(",") ? 2 : 1;

            StringBuilder jsCode = new StringBuilder();
            jsCode.append(codeUtilisateur);
            if (nbArgs == 2) {
                jsCode.append("\nconsole.log(").append(methodName).append("(2, 3));\n");
                jsCode.append("console.log(").append(methodName).append("(1, 2));\n");
            } else {
                jsCode.append("\nconsole.log(").append(methodName).append("(2));\n");
                jsCode.append("console.log(").append(methodName).append("(3));\n");
            }

            codeFinal = jsCode.toString();
        }

        // === PHP ===
        else if (langage.equalsIgnoreCase("php") && (codeBase == null || codeBase.trim().isEmpty())) {
            String functionName = extraireNomFonctionPHP(codeUtilisateur);
            if (functionName == null) {
                throw new IllegalArgumentException("Cannot find a PHP function in the user code.");
            }

            int nbArgs = compterArguments(codeUtilisateur, functionName);

            StringBuilder phpCode = new StringBuilder("<?php\n");
            phpCode.append(codeUtilisateur).append("\n");

            if (nbArgs == 2) {
                phpCode.append("echo ").append(functionName).append("(2, 3) ? \"1\\n\" : \"0\\n\";\n");
                phpCode.append("echo ").append(functionName).append("(1, 2) ? \"1\\n\" : \"0\\n\";\n");
            } else if (nbArgs == 1) {
                phpCode.append("echo ").append(functionName).append("(2) ? \"1\\n\" : \"0\\n\";\n");
                phpCode.append("echo ").append(functionName).append("(3) ? \"1\\n\" : \"0\\n\";\n");
            } else {
                phpCode.append("echo ").append(functionName).append("() ? \"1\\n\" : \"0\\n\";\n");
            }

            phpCode.append("?>");
            codeFinal = phpCode.toString();
        }


        // DEBUG: display the final code to be compiled and run
        System.out.println("=== CODE FINAL ===");
        System.out.println(codeFinal);

        // Compilation and execution logic
        String ext;
        String compileCommand = null;
        String runCommand;
        Path tempDir = Files.createTempDirectory("fusion_");
        File tempFile;
        File outputExe = null;

        switch (langage.toLowerCase()) {
            case "java":
                ext = ".java";
                tempFile = new File(tempDir.toFile(), "Main" + ext);
                Files.writeString(tempFile.toPath(), codeFinal);
                compileCommand = "javac " + tempFile.getAbsolutePath();
                runCommand = "java -cp " + tempDir + " Main";
                break;

            case "python":
                ext = ".py";
                tempFile = new File(tempDir.toFile(), "script" + ext);
                Files.writeString(tempFile.toPath(), codeFinal);
                String os = System.getProperty("os.name").toLowerCase();
                runCommand = os.contains("win") ? "python " + tempFile.getAbsolutePath()
                        : "python3 " + tempFile.getAbsolutePath();
                break;

            case "c":
                ext = ".c";
                tempFile = new File(tempDir.toFile(), "program" + ext);
                Files.writeString(tempFile.toPath(), codeFinal);
                outputExe = new File(tempDir.toFile(), "program.exe");
                compileCommand = "gcc " + tempFile.getAbsolutePath() + " -o " + outputExe.getAbsolutePath();
                runCommand = outputExe.getAbsolutePath();
                break;

            case "javascript":
                ext = ".js";
                tempFile = new File(tempDir.toFile(), "script" + ext);
                Files.writeString(tempFile.toPath(), codeFinal);
                runCommand = "node " + tempFile.getAbsolutePath();
                break;

            case "php":
                ext = ".php";
                tempFile = new File(tempDir.toFile(), "script" + ext);
                Files.writeString(tempFile.toPath(), codeFinal);
                runCommand = "php " + tempFile.getAbsolutePath();
                break;

            default:
                throw new IllegalArgumentException("Unsupported language: " + langage);
        }

        // Compile if required
        if (compileCommand != null) {
            Process processCompile = Runtime.getRuntime().exec(compileCommand);
            int exitCode = processCompile.waitFor();
            if (exitCode != 0) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(processCompile.getErrorStream()));
                StringBuilder errorOutput = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                    errorOutput.append(line).append("\n");
                return new ResultatExecution("", errorOutput.toString(), exitCode);
            }
        }

        // Run the compiled or interpreted program
        Process process = Runtime.getRuntime().exec(runCommand);
        process.waitFor();

        BufferedReader stdReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder stdOutput = new StringBuilder();
        String line;
        while ((line = stdReader.readLine()) != null)
            stdOutput.append(line).append("\n");

        BufferedReader errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        StringBuilder errOutput = new StringBuilder();
        while ((line = errReader.readLine()) != null)
            errOutput.append(line).append("\n");

        // Cleanup temporary directory
        try {
            if (tempDir.toFile().exists()) {
                deleteDirectory(tempDir.toFile());
            }
        } catch (Exception e) {
            System.err.println("Cleanup error: " + e.getMessage());
        }

        return new ResultatExecution(stdOutput.toString(), errOutput.toString(), process.exitValue());
    }

    // ========================
    //        UTILITIES
    // ========================

    /** Extracts function name from Python code */
    private String extraireNomFonctionPython(String code) {
        Pattern pattern = Pattern.compile("def\\s+(\\w+)\\s*\\(");
        Matcher matcher = pattern.matcher(code);
        return matcher.find() ? matcher.group(1) : null;
    }

    /** Extracts parameters of a Python function */
    private String extraireParametresFonctionPython(String code, String nomFonction) {
        Pattern pattern = Pattern.compile("def\\s+" + nomFonction + "\\s*\\(([^)]*)\\)");
        Matcher matcher = pattern.matcher(code);
        return matcher.find() ? matcher.group(1).trim() : "";
    }

    /** Extracts C function name from code */
    private String extraireNomFonctionC(String code) {
        Pattern def = Pattern.compile("\\w+\\s+(\\w+)\\s*\\([^)]*\\)\\s*\\{");
        Matcher matcherDef = def.matcher(code);
        if (matcherDef.find()) return matcherDef.group(1);

        Pattern decl = Pattern.compile("\\w+\\s+(\\w+)\\s*\\([^)]*\\)\\s*;");
        Matcher matcherDecl = decl.matcher(code);
        return matcherDecl.find() ? matcherDecl.group(1) : null;
    }

    /** Extracts function name from Java method */
    private String extraireNomFonctionJava(String code) {
        Pattern pattern = Pattern.compile("(public|private|protected|static|\\s)+\\s+[\\w<>\\[\\]]+\\s+(\\w+)\\s*\\(");
        Matcher matcher = pattern.matcher(code);
        return matcher.find() ? matcher.group(2) : null;
    }

    /**
     * Inserts a block of code at a specified line in a base code string.
     *
     * @param codeBase      The original code into which to insert
     * @param codeAInserer  The code block to insert
     * @param ligneInsertion Line number where to insert the new code
     * @return Combined code with the inserted snippet
     */
    private String insererALigne(String codeBase, String codeAInserer, int ligneInsertion) {
        if (ligneInsertion < 0) return codeAInserer + "\n" + codeBase;

        String[] lignes = codeBase.split("\n");
        StringBuilder resultat = new StringBuilder();
        for (int i = 0; i < lignes.length; i++) {
            if (i == ligneInsertion) resultat.append(codeAInserer).append("\n");
            resultat.append(lignes[i]).append("\n");
        }
        if (ligneInsertion >= lignes.length) resultat.append(codeAInserer).append("\n");
        return resultat.toString();
    }

    /** Extracts JavaScript function name */
    private String extraireNomFonctionJavaScript(String code) {
        Pattern pattern = Pattern.compile("function\\s+(\\w+)\\s*\\(");
        Matcher matcher = pattern.matcher(code);
        return matcher.find() ? matcher.group(1) : null;
    }

    /** Extracts PHP function name */
    private String extraireNomFonctionPHP(String code) {
        Pattern pattern = Pattern.compile("function\\s+(\\w+)\\s*\\(");
        Matcher matcher = pattern.matcher(code);
        return matcher.find() ? matcher.group(1) : null;
    }

    /** Counts the number of arguments in a function call */
    private int compterArguments(String code, String functionName) {
        Pattern pattern = Pattern.compile(functionName + "\\s*\\(([^)]*)\\)");
        Matcher matcher = pattern.matcher(code);
        if (matcher.find()) {
            String params = matcher.group(1).trim();
            if (params.isEmpty()) return 0;
            return params.split(",").length;
        }
        return 0;
    }

    /**
     * Recursively deletes a directory and its content.
     *
     * @param file The root directory or file to delete
     * @throws IOException If a file or directory cannot be deleted
     */
    private void deleteDirectory(File file) throws IOException {
        if (file.isDirectory()) {
            File[] entries = file.listFiles();
            if (entries != null) {
                for (File entry : entries) deleteDirectory(entry);
            }
        }
        if (!file.delete()) throw new IOException("Unable to delete " + file);
    }
}
