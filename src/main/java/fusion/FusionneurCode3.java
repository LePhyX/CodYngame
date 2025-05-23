package fusion;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FusionneurCode3 {

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

    public ResultatExecution executerCode(String langage, String codeUtilisateur, String codeBase, int ligneInsertion) throws IOException, InterruptedException {

            String codeFinal = ""; // 🟢 ajoute ça ici


            // === PYTHON ===
        if (langage.equalsIgnoreCase("python")) {
            codeFinal = codeUtilisateur;
            String nomFonction = extraireNomFonctionPython(codeUtilisateur);
            if (nomFonction == null) throw new IllegalArgumentException("Impossible de trouver une fonction Python.");

            String params = extraireParametresFonctionPython(codeUtilisateur, nomFonction);
            int nbArgs = params.isEmpty() ? 0 : params.split(",").length;

            StringBuilder testsCode = new StringBuilder("\n# Tests automatiques\n");
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

        // === C (sans base)
        else if (langage.equalsIgnoreCase("c") && (codeBase == null || codeBase.trim().isEmpty())) {
            codeFinal = codeUtilisateur;
            String nomFonction = extraireNomFonctionC(codeUtilisateur);
            if (nomFonction == null) throw new IllegalArgumentException("Impossible de trouver une fonction C.");

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

                mainCode.append("    return 0;\n");
                mainCode.append("}\n");

                codeFinal += "\n" + mainCode;
            }

        }

        // === JAVA (sans base)
        else if (langage.equalsIgnoreCase("java") && (codeBase == null || codeBase.trim().isEmpty())) {
            String methodName = extraireNomFonctionJava(codeUtilisateur);
            if (methodName == null) {
                throw new IllegalArgumentException("Impossible de trouver une méthode Java dans le code utilisateur.");
            }

            int nbArgs = compterArguments(codeUtilisateur, methodName); // Ajoute cette méthode si absente

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
            javaCode.append("    }\n");
            javaCode.append("}\n");

            codeFinal = javaCode.toString();
        }


        // === JAVASCRIPT ===
        else if (langage.equalsIgnoreCase("javascript") && (codeBase == null || codeBase.trim().isEmpty())) {
            String methodName = extraireNomFonctionJavaScript(codeUtilisateur);
            if (methodName == null) {
                throw new IllegalArgumentException("Impossible de trouver une fonction JavaScript dans le code utilisateur.");
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
                throw new IllegalArgumentException("Impossible de trouver une fonction PHP dans le code utilisateur.");
            }

            int nbArgs = compterArguments(codeUtilisateur, functionName);

            StringBuilder phpCode = new StringBuilder("<?php\n");
            phpCode.append(codeUtilisateur);
            phpCode.append("\n");

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


        // DEBUG
        System.out.println("=== CODE FINAL ===");
        System.out.println(codeFinal);

        // Compilation et exécution
        String ext;
        String commandeCompilation = null;
        String commandeExecution;
        Path tempDir = Files.createTempDirectory("fusion_");
        File tempFile;
        File outputExe = null;

        switch (langage.toLowerCase()) {
            case "java":
                ext = ".java";
                String className = "Main";
                tempFile = new File(tempDir.toFile(), className + ext);
                Files.writeString(tempFile.toPath(), codeFinal);
                commandeCompilation = "javac " + tempFile.getAbsolutePath();
                commandeExecution = "java -cp " + tempDir + " " + className;
                break;

            case "python":
                ext = ".py";
                tempFile = new File(tempDir.toFile(), "script" + ext);
                Files.writeString(tempFile.toPath(), codeFinal);
                String os = System.getProperty("os.name").toLowerCase();
                commandeExecution = os.contains("win") ? "python " + tempFile.getAbsolutePath() : "python3 " + tempFile.getAbsolutePath();
                break;

            case "c":
                ext = ".c";
                tempFile = new File(tempDir.toFile(), "program" + ext);
                Files.writeString(tempFile.toPath(), codeFinal);
                outputExe = new File(tempDir.toFile(), "program.exe");
                commandeCompilation = "gcc " + tempFile.getAbsolutePath() + " -o " + outputExe.getAbsolutePath();
                commandeExecution = outputExe.getAbsolutePath();
                break;

            case "javascript":
                ext = ".js";
                tempFile = new File(tempDir.toFile(), "script" + ext);
                Files.writeString(tempFile.toPath(), codeFinal);
                commandeExecution = "node " + tempFile.getAbsolutePath();
                break;

            case "php":
                ext = ".php";
                tempFile = new File(tempDir.toFile(), "script" + ext);
                Files.writeString(tempFile.toPath(), codeFinal);
                commandeExecution = "php " + tempFile.getAbsolutePath();
                break;

            default:
                throw new IllegalArgumentException("Langage non pris en charge: " + langage);
        }

        if (commandeCompilation != null) {
            Process processCompil = Runtime.getRuntime().exec(commandeCompilation);
            int exitCode = processCompil.waitFor();
            if (exitCode != 0) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(processCompil.getErrorStream()));
                StringBuilder errorOutput = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) errorOutput.append(line).append("\n");
                return new ResultatExecution("", errorOutput.toString(), exitCode);
            }
        }

        Process process = Runtime.getRuntime().exec(commandeExecution);
        process.waitFor();

        BufferedReader stdReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder stdOutput = new StringBuilder();
        String line;
        while ((line = stdReader.readLine()) != null) stdOutput.append(line).append("\n");

        BufferedReader errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        StringBuilder errOutput = new StringBuilder();
        while ((line = errReader.readLine()) != null) errOutput.append(line).append("\n");

        try {
            if (tempDir.toFile().exists()) deleteDirectory(tempDir.toFile());
        } catch (Exception e) {
            System.err.println("Erreur lors du nettoyage : " + e.getMessage());
        }

        return new ResultatExecution(stdOutput.toString(), errOutput.toString(), process.exitValue());
    }

    // === UTILITAIRES

    private String extraireNomFonctionPython(String code) {
        Pattern pattern = Pattern.compile("def\\s+(\\w+)\\s*\\(");
        Matcher matcher = pattern.matcher(code);
        return matcher.find() ? matcher.group(1) : null;
    }

    private String extraireParametresFonctionPython(String code, String nomFonction) {
        Pattern pattern = Pattern.compile("def\\s+" + nomFonction + "\\s*\\(([^)]*)\\)");
        Matcher matcher = pattern.matcher(code);
        return matcher.find() ? matcher.group(1).trim() : "";
    }

    private String extraireNomFonctionC(String code) {
        Pattern patternDefinition = Pattern.compile("\\w+\\s+(\\w+)\\s*\\([^)]*\\)\\s*\\{");
        Matcher matcherDef = patternDefinition.matcher(code);
        if (matcherDef.find()) return matcherDef.group(1);

        Pattern patternDeclaration = Pattern.compile("\\w+\\s+(\\w+)\\s*\\([^)]*\\)\\s*;");
        Matcher matcherDecl = patternDeclaration.matcher(code);
        return matcherDecl.find() ? matcherDecl.group(1) : null;
    }

    private String extraireNomFonctionJava(String code) {
        Pattern pattern = Pattern.compile("(public|private|protected|static|\\s)+\\s+[\\w<>\\[\\]]+\\s+(\\w+)\\s*\\(");
        Matcher matcher = pattern.matcher(code);
        return matcher.find() ? matcher.group(2) : null;
    }

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
    private String extraireNomFonctionJavaScript(String code) {
        Pattern pattern = Pattern.compile("function\\s+(\\w+)\\s*\\(");
        Matcher matcher = pattern.matcher(code);
        return matcher.find() ? matcher.group(1) : null;
    }

    private String extraireNomFonctionPHP(String code) {
        Pattern pattern = Pattern.compile("function\\s+(\\w+)\\s*\\(");
        Matcher matcher = pattern.matcher(code);
        return matcher.find() ? matcher.group(1) : null;
    }
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


    private void deleteDirectory(File file) throws IOException {
        if (file.isDirectory()) {
            File[] entries = file.listFiles();
            if (entries != null) {
                for (File entry : entries) deleteDirectory(entry);
            }
        }
        if (!file.delete()) throw new IOException("Impossible de supprimer " + file);
    }
}
