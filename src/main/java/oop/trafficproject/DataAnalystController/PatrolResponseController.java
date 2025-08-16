package oop.trafficproject.DataAnalystController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PatrolResponseController {
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ComboBox<String> zoneCombo;
    @FXML
    private Button analyzeBtn;
    @FXML
    private BarChart<String, Number> metricsChart;
    @FXML
    private TableView<MetricItem> metricsTable;
    private final TableColumn<PatrolResponseController, String>metricColumn;
    private final TableColumn<PatrolResponseController, String>valueColumn;
    private final TableColumn<PatrolResponseController, String>detailsColumn;

    // Simulated data map (zone -> metric data)
    private final Map<String, Map<String, Double>> dataMap = new HashMap<>();

    public PatrolResponseController(TableColumn<PatrolResponseController, String> metricColumn, TableColumn<PatrolResponseController, String> valueColumn, TableColumn<PatrolResponseController, String> detailsColumn) {
        this.metricColumn = metricColumn;
        this.valueColumn = valueColumn;
        this.detailsColumn = detailsColumn;
    }

    // Inner class to represent metric items
        public record MetricItem(String metric, Number value, String details) {
    }

    @FXML
    public void initialize() {
        // Initialize ComboBox with zone options
        zoneCombo.setItems(FXCollections.observableArrayList("Downtown", "Highway 101", "Central Plaza", "All"));

        // Set default date range (e.g., last 30 days)
        LocalDate now = LocalDate.now();
        startDatePicker.setValue(now.minusDays(30));
        endDatePicker.setValue(now);

        // Initialize TableView columns
        metricColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMetric()));
        valueColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getValue()));
        detailsColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDetails()));

        // Load sample data
        loadSampleData();

        // Set initial chart and table data
        updateChartsAndTable();

        // Add listener to refresh data when filters change
        startDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> updateChartsAndTable());
        endDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> updateChartsAndTable());
        zoneCombo.valueProperty().addListener((obs, oldVal, newVal) -> updateChartsAndTable());

        // Set action for Analyze button
        analyzeBtn.setOnAction(event -> updateChartsAndTable());
    }

    private String getValue() {
        return null;
    }

    private String getDetails() {
        return null;
    }

    private String getMetric() {
        return null;
    }

    private void loadSampleData() {
        // Simulated data for different zones
        Map<String, Double> downtownData = new HashMap<>();
        downtownData.put("Response Time", 15.5);
        downtownData.put("Patrol Efficiency", 85.0);
        downtownData.put("Incident Resolution Rate", 90.0);
        dataMap.put("Downtown", downtownData);

        Map<String, Double> highwayData = new HashMap<>();
        highwayData.put("Response Time", 20.0);
        highwayData.put("Patrol Efficiency", 75.0);
        highwayData.put("Incident Resolution Rate", 80.0);
        dataMap.put("Highway 101", highwayData);

        Map<String, Double> centralData = new HashMap<>();
        centralData.put("Response Time", 12.0);
        centralData.put("Patrol Efficiency", 90.0);
        centralData.put("Incident Resolution Rate", 95.0);
        dataMap.put("Central Plaza", centralData);
    }

    private void updateChartsAndTable() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String selectedZone = zoneCombo.getValue();

        // Clear existing data
        metricsChart.getData().clear();
        metricsTable.getItems().clear();

        // Aggregate data based on selected zone
        Map<String, Double> aggregatedData = new HashMap<>();
        if (selectedZone == null || selectedZone.equals("All")) {
            // Aggregate data from all zones
            for (Map<String, Double> zoneData : dataMap.values()) {
                zoneData.forEach((metric, value) -> aggregatedData.merge(metric, value, Double::sum));
            }
            // Average the values if aggregating
            aggregatedData.replaceAll((k, v) -> v / dataMap.size());
        } else {
            // Use data for the selected zone
            aggregatedData.putAll(dataMap.getOrDefault(selectedZone, new HashMap<>()));
        }

        // Populate BarChart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Metrics");
        ObservableList<MetricItem> tableData = FXCollections.observableArrayList();

        for (Map.Entry<String, Double> entry : aggregatedData.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            tableData.add(new MetricItem(entry.getKey(), entry.getValue(), generateDetails(entry.getKey(), entry.getValue())));
        }

        metricsChart.getData().add(series);
        metricsTable.setItems(tableData);
    }

    private String generateDetails(String metric, double value) {
        switch (metric) {
            case "Response Time":
                return String.format("Average response time: %.1f minutes", value);
            case "Patrol Efficiency":
                return String.format("Efficiency rating: %.1f%%", value);
            case "Incident Resolution Rate":
                return String.format("Resolution rate: %.1f%%", value);
            default:
                return "N/A";
        }
    }
}