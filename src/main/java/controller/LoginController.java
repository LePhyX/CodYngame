package controller;

import model.User;
import model.UserDAO;
import utils.PasswordUtils;
import utils.Session;

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
    private final UserDAO dao = new UserDAO();

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
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String hash = PasswordUtils.hashPassword(password);

        if (isSignupMode) {
            String email = emailField.getText().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Tous les champs sont requis !");
                return;
            }

            if (dao.userExists(username)) {
                errorLabel.setText("Ce nom d'utilisateur est déjà utilisé.");
                return;
            }

            User newUser = new User(username, email, hash);
            if (dao.addUser(newUser)) {
                errorLabel.setText("Compte créé avec succès !");
                toggleMode();  // revient en mode connexion
            } else {
                errorLabel.setText("Erreur inconnue lors de l'inscription.");
            }

        } else {
            // Mode connexion
            if (dao.checkLogin(username, hash)) {
                // 1) Récupérer l'objet User complet
                User user = dao.getUserByUsername(username);
                // 2) Le stocker dans la session
                Session.getInstance().setCurrentUser(user);
                // 3) Charger la Home
                loadHome();
            } else {
                errorLabel.setText("Nom d'utilisateur ou mot de passe incorrect.");
            }
        }
    }

    /**
     * Redirection vers la page d'accueil après connexion
     */
    private void loadHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/home.fxml"));
            Parent root = loader.load();

            // Plus besoin de passer manuellement le username :
            // HomeController controller = loader.getController();
            // controller.setUsername(username);

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