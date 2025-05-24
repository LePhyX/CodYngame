package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.SubmissionDAO;
import utils.SubmissionRow;
import utils.SubmissionStats;
import model.DatabaseConnection;
import model.User;
import utils.Session;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller for the submission statistics view.
 * Displays a table of exercises with submission stats for the current user.
 */
public class SubmissionController {

    @FXML private TableView<SubmissionRow> table;
    @FXML private TableColumn<SubmissionRow, String> exerciseCol;
    @FXML private TableColumn<SubmissionRow, Integer> totalCol;
    @FXML private TableColumn<SubmissionRow, Integer> successCol;
    @FXML private TableColumn<SubmissionRow, Integer> firstAttemptCol;

    /**
     * Initializes the submission table with statistics grouped by exercise for the current user.
     */
    @FXML
    public void initialize() {
        User currentUser = Session.getInstance().getCurrentUser();

        exerciseCol.setCellValueFactory(cell -> cell.getValue().exerciseProperty());
        totalCol.setCellValueFactory(cell -> cell.getValue().totalProperty().asObject());
        successCol.setCellValueFactory(cell -> cell.getValue().successProperty().asObject());
        firstAttemptCol.setCellValueFactory(cell -> cell.getValue().firstAttemptProperty().asObject());

        try (Connection conn = DatabaseConnection.getConnection()) {
            SubmissionDAO dao = new SubmissionDAO(conn);
            List<SubmissionStats> statsList = dao.getStatsByUserGroupedByExercise(currentUser.getId());

            ObservableList<SubmissionRow> data = FXCollections.observableArrayList();
            for (SubmissionStats st : statsList) {
                System.out.println("Stat: " + st.getExerciseTitle() +
                        " | Total: " + st.getTotalCount() +
                        " | Successes: " + st.getSuccessCount() +
                        " | First success on attempt: " + st.getFirstAttemptId());

                data.add(new SubmissionRow(
                        st.getExerciseTitle(),
                        st.getTotalCount(),
                        st.getSuccessCount(),
                        st.getFirstAttemptId()
                ));
            }

            table.setItems(data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the Back button click.
     * Returns to the profile view.
     */
    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ui/profil.fxml"));
            Stage stage = (Stage) table.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("My Profile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
