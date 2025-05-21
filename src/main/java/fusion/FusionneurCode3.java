package fusion;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

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

        // ✅ Indentation automatique pour Python
        if (langage.equalsIgnoreCase("python")) {
            String[] lignes = codeUtilisateur.split("\r?\n");
            StringBuilder codeIndente = new StringBuilder();
            for (String ligne : lignes) {
                if (ligne.trim().isEmpty()) {
                    codeIndente.append("\n");
                } else {
                    codeIndente.append("    ").append(ligne).append("\n");
                }
            }
            codeUtilisateur = codeIndente.toString();
        }

        // DEBUG
        System.out.println("========== DEBUG FUSION ==========");
        System.out.println("Langage : " + langage);
        System.out.println("Ligne d'insertion : " + ligneInsertion);
        System.out.println("Code de base (avant fusion) :");
        String[] debugLignes = codeBase.split("\n");
        for (int i = 0; i < debugLignes.length; i++) {
            System.out.println(i + " > " + debugLignes[i]);
        }
        System.out.println("Code utilisateur indenté :");
        System.out.println(codeUtilisateur);
        System.out.println("==================================");

        String codeFinal = insererALigne(codeBase, codeUtilisateur, ligneInsertion);

        // ✅ Ajout automatique d'un appel de test pour Python
        if (langage.equalsIgnoreCase("python")) {
            codeFinal += "\nprint(add(2, 3))\n";
            codeFinal += "print(add(0, 0))\n";
        }

        System.out.println("=== CODE FINAL ===");
        System.out.println(codeFinal);
        System.out.println("==================");

        String ext;
        String commandeCompilation = null;
        String commandeExecution;

        Path tempDir = Files.createTempDirectory("fusion_");
        File tempFile;

        switch (langage.toLowerCase()) {
            case "java":
                ext = ".java";
                String className = "Main";
                codeFinal = codeFinal.replace("CLASSNAME", className);
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
                if (os.contains("win")) {
                    commandeExecution = "python " + tempFile.getAbsolutePath();
                } else {
                    commandeExecution = "python3 " + tempFile.getAbsolutePath();
                }
                break;

            case "c":
                ext = ".c";
                tempFile = new File(tempDir.toFile(), "program" + ext);
                Files.writeString(tempFile.toPath(), codeFinal);
                File outputExe = new File(tempDir.toFile(), "program.out");
                commandeCompilation = "gcc " + tempFile.getAbsolutePath() + " -o " + outputExe.getAbsolutePath();
                commandeExecution = outputExe.getAbsolutePath();
                break;

            default:
                throw new IllegalArgumentException("Langage non pris en charge: " + langage);
        }

        if (commandeCompilation != null) {
            Process compileProcess = Runtime.getRuntime().exec(commandeCompilation);
            compileProcess.waitFor();
            if (compileProcess.exitValue() != 0) {
                String err = new String(compileProcess.getErrorStream().readAllBytes());
                return new ResultatExecution("", err, compileProcess.exitValue());
            }
        }

        Process execProcess = Runtime.getRuntime().exec(commandeExecution);
        execProcess.waitFor();

        String stdout = new String(execProcess.getInputStream().readAllBytes());
        String stderr = new String(execProcess.getErrorStream().readAllBytes());
        int code = execProcess.exitValue();

        return new ResultatExecution(stdout, stderr, code);
    }

    private String insererALigne(String codeBase, String codeAjout, int ligne) {
        String[] lignes = codeBase.split("\n");
        StringBuilder resultat = new StringBuilder();

        for (int i = 0; i < lignes.length; i++) {
            resultat.append(lignes[i]).append("\n");
            if (i == ligne - 1) { // insère après la ligne "ligne - 1"
                resultat.append(codeAjout).append("\n");
            }
        }

        if (ligne >= lignes.length) {
            resultat.append(codeAjout).append("\n");
        }

        return resultat.toString();
    }
}
