package oop.trafficproject.TrafficSurgent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ResponseEmergencyController {
    @FXML
    private ListView<String> alertList;
    @FXML
    private TextField detailLocation;
    @FXML
    private TextField detailType;
    @FXML
    private TextField detailSeverity;
    @FXML
    private TextArea detailAction;
    @FXML
    private ComboBox<String> unitCombo;
    @FXML
    private Button ackActionBtn;
    @FXML
    private Label statusText; // Ensured to be present

    private ObservableList<String> alerts = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Debug check for null fields
        if (alertList == null || detailLocation == null || detailType == null || detailSeverity == null ||
                detailAction == null || unitCombo == null || ackActionBtn == null) {
            System.err.println("Error: One or more FXML elements are missing. Check ResponseEmergency.fxml.");
            setStatus("Error: FXML loading failed.");
            return;
        }

        // Initialize alert list with sample data
        alerts.add("Intersection A - Accident");
        alerts.add("Highway B - Obstruction");
        alerts.add("Downtown C - Police Activity");
        alertList.setItems(alerts);

        // Initialize unit combo
        unitCombo.setItems(FXCollections.observableArrayList("Unit 1", "Unit 2", "Unit 3"));

        // Set selection listener for alertList
        alertList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                updateDetails(newVal);
            } else {
                clearDetails();
            }
        });

        // Set action for Acknowledge & Take Action button
        ackActionBtn.setOnAction(event -> acknowledgeAction());
    }

    private void updateDetails(String alert) {
        String[] parts = alert.split(" - ");
        if (parts.length == 2) {
            detailLocation.setText(parts[0]);
            detailType.setText(parts[1]);
            detailSeverity.setText(getSeverity(parts[1]));
            detailAction.setText(getAction(parts[1]));
        }
    }

    private String getSeverity(String type) {
        switch (type) {
            case "Accident": return "High";
            case "Obstruction": return "Medium";
            case "Police Activity": return "Low";
            default: return "Unknown";
        }
    }

    private String getAction(String type) {
        switch (type) {
            case "Accident": return "Dispatch medical and traffic units.";
            case "Obstruction": return "Clear road and assess damage.";
            case "Police Activity": return "Coordinate with local police.";
            default: return "Take appropriate action.";
        }
    }

    private void clearDetails() {
        detailLocation.setText("");
        detailType.setText("");
        detailSeverity.setText("");
        detailAction.setText("");
        unitCombo.setValue(null);
    }

    private void acknowledgeAction() {
        String selectedAlert = alertList.getSelectionModel().getSelectedItem();
        String selectedUnit = unitCombo.getValue();

        if (selectedAlert == null) {
            setStatus("Error: Please select an alert to acknowledge.");
            return;
        }
        if (selectedUnit == null) {
            setStatus("Error: Please select a unit to assign.");
            return;
        }

        String actionMsg = String.format("Alert '%s' acknowledged. Assigned to %s.", selectedAlert, selectedUnit);
        System.out.println(actionMsg);
        alerts.remove(selectedAlert);
        clearDetails();
        setStatus(actionMsg);
    }

    private void setStatus(String message) {
        if (statusText != null) {
            statusText.setText(message);
        } else {
            System.err.println("Warning: statusText not available - " + message);
        }
    }
}