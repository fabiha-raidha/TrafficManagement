package oop.trafficproject.TrafficSurgent;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.Arrays;

public class TrafficCongestionController {

    @FXML
    private ComboBox<String> zoneFilter;

    @FXML
    private Button refreshBtn;

    @FXML
    private StackPane mapContainer;

    @FXML
    private TextArea zoneInfo;

    @FXML
    private ProgressIndicator loadingIndicator;

    @FXML
    public void initialize() {
        // Initialize ComboBox with some example zones
        zoneFilter.getItems().addAll("Zone A", "Zone B", "Zone C", "Zone D");

        // Refresh button action
        refreshBtn.setOnAction(e -> handleRefresh());

        // Map click handling (just coordinates for demo)
        mapContainer.setOnMouseClicked(this::handleMapClick);

        // Initial map loading simulation
        loadMapData();
    }

    private void handleRefresh() {
        loadingIndicator.setVisible(true);
        zoneInfo.clear();

        // Simulate loading data (in real app, fetch congestion data here)
        loadMapData();
    }

    private void handleMapClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        // Update zone info with clicked coordinates
        zoneInfo.setText(String.format("Clicked on map at (%.1f, %.1f)\nZone: %s\nCongestion: %s",
                x, y,
                zoneFilter.getValue() != null ? zoneFilter.getValue() : "All Zones",
                randomCongestionLevel()));
    }

    private void loadMapData() {
        // Simulate map data loading
        loadingIndicator.setVisible(false);

        // Optionally, display default info
        zoneInfo.setText("Select a zone or click on the map to see congestion details.");
    }

    private String randomCongestionLevel() {
        // Simple simulation of congestion level
        String[] levels = {"Green (<30%)", "Yellow (30–70%)", "Red (>70%)"};
        int index = (int) (Math.random() * levels.length);
        return levels[index];
    }
}
