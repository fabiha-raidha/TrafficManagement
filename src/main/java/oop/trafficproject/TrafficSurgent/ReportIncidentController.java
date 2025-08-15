package oop.trafficproject.TrafficSurgent;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

public class ReportIncidentController {
    @FXML
    private ComboBox<String> locationCombo;
    @FXML
    private ComboBox<String> typeCombo;
    @FXML
    private ComboBox<String> severityCombo;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button submitBtn;
    @FXML
    private Label statusText;

    @FXML
    public void initialize() {
        // Initialize ComboBox options
        locationCombo.setItems(javafx.collections.FXCollections.observableArrayList(
                "Intersection A", "Highway B", "Downtown C"));
        typeCombo.setItems(javafx.collections.FXCollections.observableArrayList(
                "Accident", "Obstruction", "Police Activity"));
        severityCombo.setItems(javafx.collections.FXCollections.observableArrayList(
                "Low", "Medium", "High"));

        // Set action for Cancel button
        cancelBtn.setOnAction(event -> resetForm());

        // Set action for Submit button
        submitBtn.setOnAction(event -> submitIncident());
    }

    private void resetForm() {
        locationCombo.setValue(null);
        typeCombo.setValue(null);
        severityCombo.setValue(null);
        descriptionArea.setText("");
        setStatus("Form reset.");
    }

    private void submitIncident() {
        String location = locationCombo.getValue();
        String type = typeCombo.getValue();
        String severity = severityCombo.getValue();
        String description = descriptionArea.getText().trim();

        if (location == null || type == null || severity == null || description.isEmpty()) {
            setStatus("Error: All fields are required.");
            return;
        }

        if (description.length() > 500) {
            setStatus("Error: Description exceeds 500 characters.");
            return;
        }

        // Simulate incident submission
        String report = String.format("Incident Reported: Location=%s, Type=%s, Severity=%s, Description=%s",
                location, type, severity, description);
        System.out.println(report);
        setStatus("Incident submitted successfully.");

        // Reset form after successful submission
        resetForm();
    }

    private void setStatus(String message) {
        if (statusText != null) {
            statusText.setText(message);
        } else {
            System.err.println("Warning: statusText not available - " + message);
        }
    }
}