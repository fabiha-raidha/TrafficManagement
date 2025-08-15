package oop.trafficproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class CitizenReporterTrackReportController
{
    @javafx.fxml.FXML
    private TableColumn tagCol_fxid;
    @javafx.fxml.FXML
    private TableColumn reportIdCol_fxid;
    @javafx.fxml.FXML
    private TableColumn reportStatusCol_fxid;
    @javafx.fxml.FXML
    private TableView trackReportsTableView_fxid;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @Deprecated
    public void BackToDashboardOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void BackToCitizenDashboardOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CitizenReporter-dashboard-01.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}