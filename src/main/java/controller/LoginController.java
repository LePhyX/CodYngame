package controller;

import model.User;
import model.UserDAO;
import utils.PasswordUtils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button actionButton;
    @FXML private Button switchModeButton;
    @FXML private Label modeLabel;

    private boolean isSignupMode = false;

    @FXML
    public void initialize() {
        updateModeUI();
    }

    @FXML
    private void toggleMode() {
        isSignupMode = !isSignupMode;
        updateModeUI();
    }

    private void updateModeUI() {
        if (isSignupMode) {
            modeLabel.setText("Créer un compte");
            emailField.setVisible(true);
            emailField.setManaged(true);
            actionButton.setText("S'inscrire");
            switchModeButton.setText("Déjà inscrit ? Se connecter");
        } else {
            modeLabel.setText("Connexion");
            emailField.setVisible(false);
            emailField.setManaged(false);
            actionButton.setText("Connexion");
            switchModeButton.setText("Pas encore de compte ? S'inscrire");
        }
        errorLabel.setText("");
    }

    @FXML
    private void handleAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String hash = PasswordUtils.hashPassword(password);
        UserDAO dao = new UserDAO();

        System.out.println("=== Tentative de connexion ===");
        System.out.println("Username : " + username);
        System.out.println("Password : " + password);
        System.out.println("Hash généré : " + hash);

        if (isSignupMode) {
            String email = emailField.getText();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                errorLabel.setText(" Tous les champs sont requis !");
                return;
            }

            if (dao.userExists(username)) {
                errorLabel.setText(" Ce nom d'utilisateur est déjà utilisé.");
                return;
            }

            User user = new User(username, email, hash);
            if (dao.addUser(user)) {
                errorLabel.setText(" Compte créé avec succès !");
                toggleMode(); // revenir à la connexion
            } else {
                errorLabel.setText(" Erreur inconnue lors de l'inscription.");
            }

        } else {
            User user = dao.checkLogin(username, hash); // ✅ récupère un User complet
            if (user != null) {
                System.out.println("Connexion réussie. Utilisateur ID : " + user.getId());
                errorLabel.setText(" Connexion réussie !");
                loadHome(user); // ✅ transmet le User
            } else {
                System.out.println("Connexion échouée : mauvais identifiants.");
                errorLabel.setText(" Nom d'utilisateur ou mot de passe incorrect.");
            }
        }
    }

    private void loadHome(User user) {
        try {
            // ✅ Charger la bonne page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Dashboard.fxml"));
            Parent root = loader.load();

            // ✅ Récupérer le bon contrôleur
            DashboardController controller = loader.getController();
            controller.initData(user); // envoie l'utilisateur connecté

            Stage stage = (Stage) actionButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Accueil");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Erreur lors du chargement de l'accueil.");
        }
    }

}
