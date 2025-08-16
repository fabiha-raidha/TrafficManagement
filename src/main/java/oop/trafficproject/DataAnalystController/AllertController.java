package oop.trafficproject.DataAnalystController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AllertController {

    @FXML
    private ComboBox<String> conditionCombo;

    @FXML
    private TextField valueField;

    @FXML
    private ComboBox<String> zoneCombo;

    @FXML
    private Button saveAlertRuleBtn;

    @FXML
    private TableView<AlertItem> alertTable;

    @FXML
    private Button viewDetailsBtn;

    private final ObservableList<AlertItem> alertData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize ComboBoxes
        conditionCombo.setItems(FXCollections.observableArrayList(
                "Accidents >", "Congestion >"
        ));
        zoneCombo.setItems(FXCollections.observableArrayList(
                "Downtown", "Highway 101", "Central Plaza", "All"
        ));

        // Initialize TableView
        TableColumn<AlertItem, String> alertCol = new TableColumn<>("Alert");
        alertCol.setCellValueFactory(new PropertyValueFactory<>("alertMessage"));
        alertCol.setPrefWidth(420);

        TableColumn<AlertItem, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setPrefWidth(180);

        TableColumn<AlertItem, String> zoneCol = new TableColumn<>("Zone");
        zoneCol.setCellValueFactory(new PropertyValueFactory<>("zone"));
        zoneCol.setPrefWidth(180);

        alertTable.getColumns().clear();
        alertTable.getColumns().addAll(alertCol, dateCol, zoneCol);
        alertTable.setItems(alertData);

        // Button actions
        saveAlertRuleBtn.setOnAction(e -> saveAlertRule());
        viewDetailsBtn.setOnAction(e -> viewAlertDetails());
    }

    private void saveAlertRule() {
        String condition = conditionCombo.getValue();
        String value = valueField.getText();
        String zone = zoneCombo.getValue();

        if (condition == null || value.isEmpty() || zone == null) {
            showAlert(AlertType.WARNING, "Validation Error", "Please fill all fields!");
            return;
        }

        // Generate a sample alert message (you can integrate real backend logic here)
        String alertMessage = condition + " " + value;
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        alertData.add(new AlertItem(alertMessage, date, zone));

        // Clear input fields
        conditionCombo.getSelectionModel().clearSelection();
        valueField.clear();
        zoneCombo.getSelectionModel().clearSelection();

        showAlert(AlertType.INFORMATION, "Success", "Alert rule saved!");
    }

    private void viewAlertDetails() {
        AlertItem selected = alertTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(AlertType.WARNING, "No Selection", "Please select an alert to view details!");
            return;
        }

        String details = "Alert: " + selected.getAlertMessage() + "\n"
                + "Date: " + selected.getDate() + "\n"
                + "Zone: " + selected.getZone();

        showAlert(AlertType.INFORMATION, "Alert Details", details);
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Inner class for TableView data
    public static class AlertItem {
        private final String alertMessage;
        private final String date;
        private final String zone;

        public AlertItem(String alertMessage, String date, String zone) {
            this.alertMessage = alertMessage;
            this.date = date;
            this.zone = zone;
        }

        public String getAlertMessage() {
            return alertMessage;
        }

        public String getDate() {
            return date;
        }

        public String getZone() {
            return zone;
        }
    }
}
