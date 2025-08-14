package oop.trafficproject.TrafficSurgent;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;

import java.time.LocalDate;

public class RoadClosuresController {

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> reasonCombo;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Button submitBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private Label statusText;

    @FXML
    private StackPane mapContainer;

    // To store selected map coordinates
    private double selectedX = -1;
    private double selectedY = -1;

    @FXML
    public void initialize() {
        // Initialize ComboBox with some example reasons
        reasonCombo.getItems().addAll("Accident", "Construction", "Event");

        // Set button actions
        submitBtn.setOnAction(e -> handleSubmit());
        clearBtn.setOnAction(e -> handleClear());

        // Map click handling
        mapContainer.setOnMouseClicked(this::handleMapClick);
    }

    private void handleSubmit() {
        String location = searchField.getText().trim();
        String reason = reasonCombo.getValue();
        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();

        // Validation
        if (location.isEmpty()) {
            statusText.setText("Please enter a location.");
            return;
        }
        if (reason == null || reason.isEmpty()) {
            statusText.setText("Please select a reason.");
            return;
        }
        if (start == null || end == null) {
            statusText.setText("Please select start and end dates.");
            return;
        }
        if (end.isBefore(start)) {
            statusText.setText("End date cannot be before start date.");
            return;
        }
        if (selectedX == -1 || selectedY == -1) {
            statusText.setText("Please select a closure point on the map.");
            return;
        }

        // Success: You can now save this data to DB or process
        statusText.setText(String.format(
                "Road closure submitted!\nLocation: %s\nReason: %s\nStart: %s\nEnd: %s\nMap Coords: (%.1f, %.1f)",
                location, reason, start, end, selectedX, selectedY
        ));
    }

    private void handleClear() {
        searchField.clear();
        reasonCombo.setValue(null);
        startDate.setValue(null);
        endDate.setValue(null);
        selectedX = -1;
        selectedY = -1;
        statusText.setText("");
    }

    private void handleMapClick(MouseEvent event) {
        selectedX = event.getX();
        selectedY = event.getY();
        statusText.setText(String.format("Selected map point at (%.1f, %.1f)", selectedX, selectedY));
    }
}
