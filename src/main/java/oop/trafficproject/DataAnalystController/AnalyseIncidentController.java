package oop.trafficproject.DataAnalystController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class AnalyseIncidentController {

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<String> incidentTypeCombo;

    @FXML
    private ComboBox<String> zoneCombo;

    @FXML
    private Button applyBtn;

    @FXML
    private LineChart<String, Number> trendChart;

    private final Random random = new Random();

    @FXML
    public void initialize() {
        // Initialize ComboBoxes
        incidentTypeCombo.setItems(FXCollections.observableArrayList(
                "Accident", "Obstruction", "Police Activity", "All"
        ));
        zoneCombo.setItems(FXCollections.observableArrayList(
                "Downtown", "Highway 101", "Central Plaza", "All"
        ));

        // Set default dates
        endDatePicker.setValue(LocalDate.now());
        startDatePicker.setValue(LocalDate.now().minusDays(7));

        // Button action
        applyBtn.setOnAction(e -> applyFilters());

        // Initial chart load
        applyFilters();
    }

    private void applyFilters() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (startDate == null || endDate == null) {
            showAlert(AlertType.WARNING, "Validation Error", "Please select both start and end dates.");
            return;
        }

        if (endDate.isBefore(startDate)) {
            showAlert(AlertType.WARNING, "Validation Error", "End date cannot be before start date.");
            return;
        }

        String incidentType = incidentTypeCombo.getValue() != null ? incidentTypeCombo.getValue() : "All";
        String zone = zoneCombo.getValue() != null ? zoneCombo.getValue() : "All";

        // Generate sample data (replace with real backend data)
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(incidentType + " - " + zone);

        LocalDate current = startDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");
        while (!current.isAfter(endDate)) {
            int incidents = random.nextInt(10) + 1; // Random data for demo
            series.getData().add(new XYChart.Data<>(current.format(formatter), incidents));
            current = current.plusDays(1);
        }

        trendChart.getData().clear();
        trendChart.getData().add(series);
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
