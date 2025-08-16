package oop.trafficproject.TrafficSurgent;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

public class DataExportController {
    @FXML
    private ComboBox<String> cameraCombo;
    @FXML
    private Button playBtn;
    @FXML
    private Button pauseBtn;
    @FXML
    private Button fullBtn;
    @FXML
    private Button startRecBtn;
    @FXML
    private Button stopRecBtn;
    @FXML
    private Button snapshotBtn;
    @FXML
    private Label statusText;
    @FXML
    private StackPane stackPane; // Ensure this matches the FXML fx:id

    private Timeline timeline;
    private Label liveFeedLabel;
    private boolean isPlaying = false;
    private boolean isRecording = false;
    private Stage primaryStage;

    @FXML
    public void initialize() {
        // Initialize camera options
        cameraCombo.setItems(FXCollections.observableArrayList("Camera 1", "Camera 2", "Camera 3"));

        // Debug: Check if stackPane is injected
        if (stackPane == null) {
            System.err.println("Error: stackPane is not injected! Check FXML and controller linkage.");
            statusText.setText("Error: Live feed container not found.");
            return; // Exit if stackPane is null
        }

        // Initialize live feed label with null check
        if (!stackPane.getChildren().isEmpty()) {
            liveFeedLabel = (Label) stackPane.getChildren().getFirst();
            liveFeedLabel.setText("Live feed will appear here");
        } else {
            System.err.println("Error: stackPane has no children!");
            statusText.setText("Error: Live feed label not found.");
            return;
        }

        // Temporary reference to ensure StackPane is used (prevents import from being disabled)
        stackPane.setStyle("-fx-border-color: transparent;"); // Non-intrusive style

        // Initialize Timeline for live feed simulation
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateLiveFeed()));
        timeline.setCycleCount(Animation.INDEFINITE);

        // Set button actions
        playBtn.setOnAction(event -> handlePlay());
        pauseBtn.setOnAction(event -> handlePause());
        fullBtn.setOnAction(event -> handleFullscreen());
        startRecBtn.setOnAction(event -> handleStartRecording());
        stopRecBtn.setOnAction(event -> handleStopRecording());
        snapshotBtn.setOnAction(event -> handleSnapshot());

        // Disable pause, stop, and snapshot initially
        pauseBtn.setDisable(true);
        stopRecBtn.setDisable(true);
        snapshotBtn.setDisable(true);

        // Get the primary stage (assuming set via application)
        primaryStage = (Stage) stackPane.getScene().getWindow();
        if (primaryStage == null) {
            System.err.println("Error: primaryStage is null");
            statusText.setText("Error: Stage not found.");
        }
    }

    private void updateLiveFeed() {
        if (isPlaying && cameraCombo.getValue() != null) {
            DecimalFormat df = new DecimalFormat("#.##");
            double value = Math.random() * 100; // Simulated metric
            liveFeedLabel.setText(String.format("%s Feed: %.2f%% Activity", cameraCombo.getValue(), value));
        }
    }

    private void handlePlay() {
        if (cameraCombo.getValue() == null) {
            statusText.setText("Error: Please select a camera.");
            return;
        }
        isPlaying = true;
        timeline.play();
        playBtn.setDisable(true);
        pauseBtn.setDisable(false);
        startRecBtn.setDisable(false);
        snapshotBtn.setDisable(false);
        statusText.setText("Live feed started for " + cameraCombo.getValue());
    }

    private void handlePause() {
        isPlaying = false;
        timeline.pause();
        playBtn.setDisable(false);
        pauseBtn.setDisable(true);
        statusText.setText("Live feed paused.");
    }

    private void handleFullscreen() {
        if (primaryStage.isFullScreen()) {
            primaryStage.setFullScreen(false);
            statusText.setText("Exited fullscreen mode.");
        } else {
            primaryStage.setFullScreen(true);
            statusText.setText("Entered fullscreen mode.");
        }
    }

    private void handleStartRecording() {
        if (isRecording || !isPlaying) {
            statusText.setText("Error: Already recording or feed not playing.");
            return;
        }
        isRecording = true;
        startRecBtn.setDisable(true);
        stopRecBtn.setDisable(false);
        statusText.setText("Recording started for " + cameraCombo.getValue());
    }

    private void handleStopRecording() {
        if (!isRecording) {
            statusText.setText("Error: No recording in progress.");
            return;
        }
        isRecording = false;
        startRecBtn.setDisable(false);
        stopRecBtn.setDisable(true);
        statusText.setText("Recording stopped for " + cameraCombo.getValue());
    }

    private void handleSnapshot() {
        if (!isPlaying) {
            statusText.setText("Error: Feed not playing.");
            return;
        }
        String snapshot = "Snapshot captured from " + cameraCombo.getValue() + " at " + LocalDateTime.now();
        statusText.setText(snapshot);
        System.out.println("Snapshot saved: " + snapshot); // Simulate saving
    }
}