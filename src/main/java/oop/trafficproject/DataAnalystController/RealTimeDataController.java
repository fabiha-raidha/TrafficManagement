package oop.trafficproject.DataAnalystController;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.text.DecimalFormat;

public class RealTimeDataController {
    @FXML
    private TextField thresholdField;
    @FXML
    private StackPane metricsPane;
    @FXML
    private ComboBox<String> zoneCombo;
    @FXML
    private Button saveThresholdBtn;
    @FXML
    private ComboBox<String> dataStreamCombo;

    private Timeline timeline;
    private Label metricsLabel;

    @FXML
    public void initialize() {
        // Initialize ComboBox with data stream options
        dataStreamCombo.setItems(FXCollections.observableArrayList("Traffic Flow", "Congestion Level", "Incident Alerts"));

        // Initialize ComboBox with zone options
        zoneCombo.setItems(FXCollections.observableArrayList("Downtown", "Highway 101", "Central Plaza", "All"));

        // Initialize metrics label
        metricsLabel = new Label("Live metrics will appear here");
        metricsLabel.setTextFill(javafx.scene.paint.Color.web("#607d8b"));
        metricsPane.getChildren().setAll(metricsLabel);

        // Set default threshold
        thresholdField.setText("Congestion > 80%");

        // Initialize Timeline for real-time updates
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateMetrics()));
        timeline.setCycleCount(Animation.INDEFINITE);

        // Set action for Save button
        saveThresholdBtn.setOnAction(event -> handleSaveThreshold());
    }

    private void updateMetrics() {
        String dataStream = dataStreamCombo.getValue();
        String zone = zoneCombo.getValue();
        String threshold = thresholdField.getText();

        if (dataStream == null || zone == null || threshold == null || threshold.isEmpty()) {
            metricsLabel.setText("Select data stream, zone, and set threshold to start monitoring");
            return;
        }

        // Simulate real-time data
        DecimalFormat df = new DecimalFormat("#.##");
        double value = Math.random() * 100; // Random value between 0 and 100
        String unit = "%";
        String metricName = dataStream;

        if (dataStream.equals("Traffic Flow")) {
            value = Math.random() * 5000; // Vehicles per hour
            unit = "vehicles/h";
        } else if (dataStream.equals("Incident Alerts")) {
            value = Math.random() * 10; // Number of incidents
            unit = "incidents";
        }

        // Check threshold (simplified condition)
        boolean alert = false;
        if (threshold.contains(">")) {
            double thresholdValue = Double.parseDouble(threshold.replaceAll("[^0-9.]", ""));
            alert = value > thresholdValue;
        }

        String status = alert ? " (ALERT: Above threshold!)" : "";
        metricsLabel.setText(String.format("%s in %s: %.2f %s%s", metricName, zone, value, unit, status));
    }

    private void handleSaveThreshold() {
        String threshold = thresholdField.getText();
        if (threshold != null && !threshold.isEmpty()) {
            System.out.println("Threshold saved: " + threshold);
            if (!timeline.getStatus().equals(Animation.Status.RUNNING)) {
                timeline.play();
            }
        } else {
            System.out.println("Please enter a valid threshold");
            if (timeline.getStatus().equals(Animation.Status.RUNNING)) {
                timeline.stop();
            }
            metricsLabel.setText("Live metrics will appear here");
        }
    }
}