package oop.trafficproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

// oop.trafficproject.CitizenReporterDashboard01Controller //
public class RoadMaintenanceOfficerDashboard01Controller
{
    @javafx.fxml.FXML
    private Label nameShowLabelFXID;
    @javafx.fxml.FXML
    private Label idShowLabelFXID;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void AssignToCrewOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RoadMaintenanceOfficer-AssignToCrew.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void LogDelayOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void CitizenReportsOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void AssignedZonesOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void DailyLogOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void CitizenFeedbackOnAction(ActionEvent actionEvent) {
    }

    @Deprecated
    public void MaintenanceDashBoardOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void UpdateStatusOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void CheckMaintenanceTaskListOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RoadMaintenanceOfficer-CheckMaintenanceTaskList.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void BackToAltLoginDashBoardOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AlterNativeLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}