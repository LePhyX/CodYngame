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
import controller.HomeController;

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
            if (dao.checkLogin(username, hash)) {
                errorLabel.setText(" Connexion réussie !");
                loadHome(username); //  redirection vers page d'accueil
            } else {
                errorLabel.setText(" Nom d'utilisateur ou mot de passe incorrect.");
            }
        }

        System.out.println(" Username: " + username);
        System.out.println(" Hash généré : " + hash);
    }

    /**
     * Redirection vers la page d'accueil après connexion
     */
    private void loadHome(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/home.fxml"));
            Parent root = loader.load();

            // Injecte le nom dans le contrôleur
            HomeController controller = loader.getController();
            controller.setUsername(username);

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
