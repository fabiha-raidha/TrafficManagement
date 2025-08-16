package oop.trafficproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class RoadMainDailyLogController
{
    @javafx.fxml.FXML
    private ComboBox statusComboBox_fxid;
    @javafx.fxml.FXML
    private TableColumn reportIdCol_fxid;
    @javafx.fxml.FXML
    private TableColumn submissionDateCol_fxid;
    @javafx.fxml.FXML
    private TextArea myreportMessegeTextAreaFXID;
    @javafx.fxml.FXML
    private TableColumn typeCol_fxid;
    @javafx.fxml.FXML
    private TableColumn locationCol_fxid;
    @javafx.fxml.FXML
    private TableView dailyLogTableView_fxid;
    @javafx.fxml.FXML
    private TableColumn currentstatusCol_fxid;
    @javafx.fxml.FXML
    private ComboBox taskListComboBox_fxid;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void BackToCitizenDashboardOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RoadMaintenanceOfficer-dashboard-01.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void View_dailyLog_OnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void Refresh_OnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void Submit_report_OnAction(ActionEvent actionEvent) {
    }
}