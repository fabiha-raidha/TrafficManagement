package oop.trafficproject.TrafficSurgent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;

import java.io.IOException;
import java.util.Objects;

public class tsDashboardController {

    @FXML private StackPane contentArea;

    @FXML private Button btnAssignFieldUnit;
    @FXML private Button btnExportData;
    @FXML private Button btnGenerateReport;
    @FXML private Button btnPatrolSchedule;
    @FXML private Button btnReportIncident;
    @FXML private Button btnResponseEmergency;
    @FXML private Button btnRoadClosures;
    @FXML private Button btnTrafficCongestion;

    @FXML
    public void initialize() {
        // Assign actions to buttons
        btnAssignFieldUnit.setOnAction(e -> loadFXML("assign-field-unit-view.fxml"));
        btnExportData.setOnAction(e -> loadFXML("export-data-view.fxml"));
        btnGenerateReport.setOnAction(e -> loadFXML("generate-report-view.fxml"));
        btnPatrolSchedule.setOnAction(e -> loadFXML("patrol-schedule.fxml"));
        btnReportIncident.setOnAction(e -> loadFXML("report-incident-view.fxml"));
        btnResponseEmergency.setOnAction(e -> loadFXML("response-emergency-view.fxml"));
        btnRoadClosures.setOnAction(e -> loadFXML("road-closures-view.fxml"));
        btnTrafficCongestion.setOnAction(e -> loadFXML("traffic-congestion-view.fxml"));
    }
    private void loadFXML(String fxmlFile) {
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
            contentArea.getChildren().clear();
            contentArea.getChildren().add(node);
        } catch (IOException ex) {
            ex.printStackTrace();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(new javafx.scene.control.Label("Failed to load " + fxmlFile));
        }
    }
}
