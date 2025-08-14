package oop.trafficproject.DataAnalystController;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
public class GenerateReportController {
    @FXML
    private ComboBox<String> reportTypeCombo;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ComboBox<String> zoneCombo;
    @FXML
    private Button generateBtn;
    @FXML
    private TableView<ReportItem> reportTable;
    @FXML
    private Button exportCSVBtn;
    @FXML
    private Button exportPDFBtn;
    private final TableColumn<FilterItem, String> incidentTypeColumn;
    private final TableColumn<FilterItem, String> countColumn;
    private final TableColumn<FilterItem, String> zoneColumn;


    // Simulated data list
    private final ObservableList<ReportItem> dataList = FXCollections.observableArrayList();

    public GenerateReportController(TableColumn<FilterItem, String> incidentTypeColumn, TableColumn<FilterItem, String> countColumn, TableColumn<FilterItem, String> zoneColumn) {
        this.incidentTypeColumn = incidentTypeColumn;
        this.countColumn = countColumn;
        this.zoneColumn = zoneColumn;
    }

    // Inner class to represent report items
        public record ReportItem(String incidentType, int count, String zone) {
    }

    @FXML
    public void initialize() {
        // Initialize ComboBox with report type options
        reportTypeCombo.setItems(FXCollections.observableArrayList("Incident Summary", "Patrol Effectiveness", "Camera Usage"));

        // Initialize ComboBox with zone options
        zoneCombo.setItems(FXCollections.observableArrayList("Downtown", "Highway 101", "Central Plaza", "All"));

        // Set default date range (e.g., last 30 days)
        LocalDate now = LocalDate.now();
        startDatePicker.setValue(now.minusDays(30));
        endDatePicker.setValue(now);

        // Initialize TableView columns
        incidentTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIncidentType()));
        countColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()).asObject().asString());
        zoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getZone()));

        // Load sample data
        loadSampleData();

        // Set initial TableView data
        filterAndUpdateTable();

        // Add listeners to refresh data when filters change
        startDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> filterAndUpdateTable());
        endDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> filterAndUpdateTable());
        zoneCombo.valueProperty().addListener((obs, oldVal, newVal) -> filterAndUpdateTable());
        reportTypeCombo.valueProperty().addListener((obs, oldVal, newVal) -> filterAndUpdateTable());

        // Set action for Generate button
        generateBtn.setOnAction(event -> filterAndUpdateTable());

        // Set actions for export buttons
        exportCSVBtn.setOnAction(event -> exportAsCSV());
        exportPDFBtn.setOnAction(event -> exportAsPDF());
    }

    private void loadSampleData() {
        dataList.add(new ReportItem("Accident", 5, "Downtown"));
        dataList.add(new ReportItem("Patrol Effectiveness", 3, "Highway 101"));
        dataList.add(new ReportItem("Camera Usage", 2, "Central Plaza"));
        dataList.add(new ReportItem("Accident", 7, "Downtown"));
    }

    private void filterAndUpdateTable() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String selectedReportType = reportTypeCombo.getValue();
        String selectedZone = zoneCombo.getValue();

        ObservableList<ReportItem> filteredList = FXCollections.observableArrayList();

        for (ReportItem item : dataList) {
            LocalDate itemDate = LocalDate.now(); // Simulate date for simplicity; replace with real data
            boolean dateInRange = !itemDate.isBefore(startDate) && !itemDate.isAfter(endDate);
            boolean typeMatches = selectedReportType == null || selectedReportType.equals("All") || item.incidentType().equals(selectedReportType);
            boolean zoneMatches = selectedZone == null || selectedZone.equals("All") || item.zone().equals(selectedZone);

            if (dateInRange && typeMatches && zoneMatches) {
                filteredList.add(item);
            }
        }

        reportTable.setItems(filteredList);
    }

    private void exportAsCSV() {
        ObservableList<ReportItem> data = reportTable.getItems();
        if (data.isEmpty()) {
            System.out.println("No data to export as CSV!");
            return;
        }

        StringBuilder csvContent = new StringBuilder("Incident Type,Count,Zone\n");
        for (ReportItem item : data) {
            csvContent.append(String.format("%s,%d,%s\n", item.incidentType(), item.count(), item.zone()));
        }
        System.out.println("CSV Export:\n" + csvContent.toString());
        // In a real application, use Files.writeString to save to a file
    }

    private void exportAsPDF() {
        ObservableList<ReportItem> data = reportTable.getItems();
        if (data.isEmpty()) {
            System.out.println("No data to export as PDF!");
            return;
        }

        StringBuilder pdfContent = new StringBuilder("Report Data\n---------------\n");
        for (ReportItem item : data) {
            pdfContent.append(String.format("Type: %s, Count: %d, Zone: %s\n", item.incidentType(), item.count(), item.zone()));
        }
        System.out.println("PDF Export (simulated):\n" + pdfContent.toString());
        // In a real application, integrate a PDF library (e.g., iText) to generate a PDF file
    }
}