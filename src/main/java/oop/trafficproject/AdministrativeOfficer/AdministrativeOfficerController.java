package oop.trafficproject.AdministrativeOfficer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AdministrativeOfficerController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button logOutButton;

    @FXML
    private AnchorPane contentArea;

    @FXML
    void onBackupButtonClicked(MouseEvent event) {

    }

    @FXML
    void onBackupRestoreButtonClicked(ActionEvent event) {

    }

    @FXML
    void onFeedbackButtonClicked(ActionEvent event) {

    }

    @FXML
    void onIncidentButtonClicked(ActionEvent event) throws IOException {


    }

    @FXML
    void onIncidentReportButtonClicked(MouseEvent event) {

    }

    @FXML
    void onLeaveTerminationButtonClicked(ActionEvent event) {

    }

    @FXML
    void onLogOutButtonClicked(ActionEvent event) {

    }

    @FXML
    void onLogisticsButtonClicked(ActionEvent event) {

    }

    @FXML
    void onOverviewButtonClicked(ActionEvent event) {

    }

    @FXML
    void onSignOutButtonClicked(MouseEvent event) {

    }

    @FXML
    void onUserManagementButtonClicked(ActionEvent event) throws IOException {
        Parent userManagementRoot = FXMLLoader.load(getClass().getResource("/oop/trafficproject/AdminsitrativeOfficer/UserManagementAO.fxml"));

        contentArea.getChildren().setAll(userManagementRoot);

    }

    @FXML
    void onZoneManagementButtonClicked(ActionEvent event) {

    }

}
