package oop.trafficproject.DataAnalystController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class DataExportController {
    private final TableColumn<ExportDataItem, String> incidentIdColumn;
    private final TableColumn<ExportDataItem, String> typeColumn;
    private final TableColumn<ExportDataItem, String> dateColumn;
    private final TableColumn<ExportDataItem, String> zoneColumn;
    @FXML
    private CheckBox incidentsCheck;
    @FXML
    private CheckBox cameraMetadataCheck;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Button exportBtn;
    @FXML
    private CheckBox patrolLogsCheck;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private ComboBox<String> zoneCombo;
    @FXML
    private TableView<ExportDataItem> exportTable;

    // Simulated data list
    private final ObservableList<ExportDataItem> dataList = FXCollections.observableArrayList();

    public DataExportController(TableColumn<ExportDataItem, String> incidentIdColumn, TableColumn<ExportDataItem, String> typeColumn, TableColumn<ExportDataItem, String> dateColumn, TableColumn<ExportDataItem, String> zoneColumn) {
        this.incidentIdColumn = incidentIdColumn;
        this.typeColumn = typeColumn;
        this.dateColumn = dateColumn;
        this.zoneColumn = zoneColumn;
    }

    public Button getExportBtn() {
        return exportBtn;
    }

    public void setExportBtn(Button exportBtn) {
        this.exportBtn = exportBtn;
    }

    // Inner class to represent export data items
        public record ExportDataItem(String incidentId, String type, String date, String zone) {
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
        incidentIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().incidentId()));
        typeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().type()));
        dateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().date()));
        zoneColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().zone()));

        // Load sample data
        loadSampleData();

        // Set initial TableView data
        exportTable.setItems(dataList);

        // Add listener to refresh data when filters change
        startDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> filterAndUpdateTable());
        endDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> filterAndUpdateTable());
        zoneCombo.valueProperty().addListener((obs, oldVal, newVal) -> filterAndUpdateTable());
        incidentsCheck.selectedProperty().addListener((obs, oldVal, newVal) -> filterAndUpdateTable());
        patrolLogsCheck.selectedProperty().addListener((obs, oldVal, newVal) -> filterAndUpdateTable());
        cameraMetadataCheck.selectedProperty().addListener((obs, oldVal, newVal) -> filterAndUpdateTable());
    }

    private void loadSampleData() {
        dataList.add(new ExportDataItem("INC001", "Accident", "2025-08-01", "Downtown"));
        dataList.add(new ExportDataItem("INC002", "Patrol Log", "2025-08-05", "Highway 101"));
        dataList.add(new ExportDataItem("INC003", "Camera Metadata", "2025-08-10", "Central Plaza"));
        dataList.add(new ExportDataItem("INC004", "Accident", "2025-08-12", "Downtown"));
    }

    private void filterAndUpdateTable() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String selectedZone = zoneCombo.getValue();
        boolean includeIncidents = incidentsCheck.isSelected();
        boolean includePatrolLogs = patrolLogsCheck.isSelected();
        boolean includeCameraMetadata = cameraMetadataCheck.isSelected();

        ObservableList<ExportDataItem> filteredList = FXCollections.observableArrayList();

        for (ExportDataItem item : dataList) {
            LocalDate itemDate = LocalDate.parse(item.date());
            boolean dateInRange = !itemDate.isBefore(startDate) && !itemDate.isAfter(endDate);
            boolean zoneMatches = selectedZone == null || selectedZone.equals("All") || item.zone().equals(selectedZone);
            boolean typeMatches = (includeIncidents && item.type().equals("Accident")) ||
                    (includePatrolLogs && item.type().equals("Patrol Log")) ||
                    (includeCameraMetadata && item.type().equals("Camera Metadata"));

            if (dateInRange && zoneMatches && typeMatches) {
                filteredList.add(item);
            }
        }

        exportTable.setItems(filteredList);
    }

    @FXML
    private void handleExportAction() {
        // Simulate export action
        ObservableList<ExportDataItem> selectedData = exportTable.getItems();
        if (selectedData.isEmpty()) {
            System.out.println("No data to export!");
            return;
        }

        StringBuilder exportContent = new StringBuilder("Exported Data:\n");
        for (ExportDataItem item : selectedData) {
            exportContent.append(String.format("ID: %s, Type: %s, Date: %s, Zone: %s\n",
                    item.incidentId(), item.type(), item.date(), item.zone()));
        }
        System.out.println(exportContent.toString());
        // In a real application, you could save this to a file or send it to a server
    }
}