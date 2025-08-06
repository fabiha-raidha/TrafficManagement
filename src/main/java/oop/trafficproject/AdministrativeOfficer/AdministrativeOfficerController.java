package oop.trafficproject.AdministrativeOfficer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class AdministrativeOfficerController {

    @FXML
    private Button signOutButton;

    @FXML
    void onBackupButtonClicked(MouseEvent event) {

    }

    @FXML
    void onFeedbackButtonClicked(MouseEvent event) {

    }

    @FXML
    void onIncidentReportButtonClicked(MouseEvent event) {

    }

    @FXML
    void onLeaveTerminationButtonClicked(MouseEvent event) {

    }

    @FXML
    void onLogisticsButtonClicked(MouseEvent event) {

    }

    @FXML
    void onOverviewButtonClicked(MouseEvent event) {

    }

    @FXML
    public void onSignOutButtonClicked(MouseEvent event) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Sign Out Confirmation");
        confirmAlert.setHeaderText("Are you sure you want to sign out?");
        confirmAlert.setContentText("You will be redirected to the login screen.");

        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
                Parent loginRoot = loader.load();

                Stage currentStage = (Stage) signOutButton.getScene().getWindow();

                Scene loginScene = new Scene(loginRoot);

                currentStage.setScene(loginScene);
                currentStage.setTitle("Log In Menu");
                currentStage.centerOnScreen();

                showInfoAlert("Sign Out Successful", "You have successfully signed out.");

            } catch (IOException e) {
                e.printStackTrace();
                showErrorAlert("Error", "Failed to load login scene: " + e.getMessage());
            }
        }
    }

    private void showInfoAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void onUserManagementButtonClicked(MouseEvent event) {

    }

    @FXML
    void onZoneManagementButtonClicked(MouseEvent event) {

    }

}
