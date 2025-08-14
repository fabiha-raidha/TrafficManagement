package oop.trafficproject.DataAnalystController;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaDashboardViewController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(DaDashboardViewController.class.getName());

    // FXML Components
    @FXML private StackPane contentArea;
    @FXML private VBox welcomeScreen;
    @FXML private VBox loadingIndicator;
    @FXML private AnchorPane dynamicContent;
    @FXML private Text statusText;

    // Navigation Buttons
    @FXML private Button alertBtn;
    @FXML private Button analyseBtn;
    @FXML private Button exportBtn;
    @FXML private Button filterBtn;
    @FXML private Button reportBtn;
    @FXML private Button patrolBtn;
    @FXML private Button realtimeBtn;
    @FXML private Button shareBtn;

    // Error Handling and State Management
    private Button currentActiveButton;
    private Node currentLoadedContent;
    private final Map<String, Node> contentCache = new HashMap<>();
    private PauseTransition statusResetTransition;

    // FXML File Mappings
    private static final Map<String, String> FXML_MAPPINGS = Map.of(
            "alert", "allert-view.fxml",
            "analyse", "analyse-incident-view.fxml",
            "export", "data-export.fxml",
            "filter", "filter-data-view.fxml",
            "report", "generate-report.fxml",
            "patrol", "patrol-response-view.fxml",
            "realtime", "Real-time-data-view.fxml",
            "share", "share-data-view.fxml"
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setupInitialState();
            setupStatusResetTransition();
            setupButtonStyles();
            logInfo("Dashboard initialized successfully");
        } catch (Exception e) {
            handleCriticalError("Failed to initialize dashboard", e);
        }
    }

    /**
     * Setup initial dashboard state
     */
    private void setupInitialState() {
        try {
            showWelcomeScreen();
            updateStatus("Dashboard ready", StatusType.SUCCESS);
        } catch (Exception e) {
            handleError("Failed to setup initial state", e);
        }
    }

    /**
     * Setup automatic status text reset
     */
    private void setupStatusResetTransition() {
        statusResetTransition = new PauseTransition(Duration.seconds(5));
        statusResetTransition.setOnFinished(e -> updateStatus("Ready", StatusType.NORMAL));
    }

    /**
     * Setup button hover effects and initial styles
     */
    private void setupButtonStyles() {
        Button[] buttons = {alertBtn, analyseBtn, exportBtn, filterBtn,
                reportBtn, patrolBtn, realtimeBtn, shareBtn};

        for (Button button : buttons) {
            if (button != null) {
                setupButtonHoverEffect(button);
            }
        }
    }

    /**
     * Add hover effects to navigation buttons
     */
    private void setupButtonHoverEffect(Button button) {
        String originalStyle = button.getStyle();
        String hoverStyle = originalStyle.replace("-fx-background-color: #ffffff;",
                "-fx-background-color: #f8f9fa;") + " -fx-scale-x: 1.05; -fx-scale-y: 1.05;";

        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
        button.setOnMouseExited(e -> {
            if (button != currentActiveButton) {
                button.setStyle(originalStyle);
            }
        });
    }

    // Navigation Methods with Error Handling
    @FXML
    private void loadAlertView() {
        loadViewSafely("alert", alertBtn);
    }

    @FXML
    private void loadAnalyseIncidentView() {
        loadViewSafely("analyse", analyseBtn);
    }

    @FXML
    private void loadDataExportView() {
        loadViewSafely("export", exportBtn);
    }

    @FXML
    private void loadFilterDataView() {
        loadViewSafely("filter", filterBtn);
    }

    @FXML
    private void loadGenerateReportView() {
        loadViewSafely("report", reportBtn);
    }

    @FXML
    private void loadPatrolResponseView() {
        loadViewSafely("patrol", patrolBtn);
    }

    @FXML
    private void loadRealTimeDataView() {
        loadViewSafely("realtime", realtimeBtn);
    }

    @FXML
    private void loadShareDataView() {
        loadViewSafely("share", shareBtn);
    }

    /**
     * Safely load a view with comprehensive error handling
     */
    private void loadViewSafely(String viewKey, Button sourceButton) {
        if (sourceButton == null) {
            handleError("Source button is null for view: " + viewKey, null);
            return;
        }

        // Prevent multiple rapid clicks
        if (sourceButton.isDisabled()) {
            return;
        }

        try {
            // Disable button temporarily
            sourceButton.setDisable(true);


            // Show loading state
            showLoadingState(true);
            updateStatus("Loading " + getViewDisplayName(viewKey) + "...", StatusType.LOADING);

            // Create background task for loading
            Task<Node> loadTask = new Task<Node>() {
                @Override
                protected Node call() throws Exception {
                    return loadViewContent(viewKey);
                }

                @Override
                protected void succeeded() {
                    try {
                        Node content = getValue();
                        if (content != null) {
                            displayLoadedContent(content, sourceButton);
                            updateStatus(getViewDisplayName(viewKey) + " loaded successfully", StatusType.SUCCESS);
                        } else {
                            throw new RuntimeException("Loaded content is null");
                        }
                    } catch (Exception e) {
                        failed();
                        handleViewLoadError(viewKey, e);
                    } finally {
                        showLoadingState(false);
                        sourceButton.setDisable(false);
                    }
                }

                @Override
                protected void failed() {
                    showLoadingState(false);
                    sourceButton.setDisable(false);
                    Throwable exception = getException();
                    handleViewLoadError(viewKey, exception != null ? (Exception) exception :
                            new RuntimeException("Unknown error occurred"));
                }
            };

            // Run task in background thread
            Thread loadThread = new Thread(loadTask);
            loadThread.setDaemon(true);
            loadThread.start();

        } catch (Exception e) {
            sourceButton.setDisable(false);
            showLoadingState(false);
            handleViewLoadError(viewKey, e);
        }
    }

    /**
     * Load view content with caching
     */
    private Node loadViewContent(String viewKey) throws IOException {
        // Check cache first
        if (contentCache.containsKey(viewKey)) {
            logInfo("Loading " + viewKey + " from cache");
            return contentCache.get(viewKey);
        }

        String fxmlFile = FXML_MAPPINGS.get(viewKey);
        if (fxmlFile == null) {
            throw new IllegalArgumentException("Unknown view key: " + viewKey);
        }

        // Validate file exists
        URL fxmlUrl = getClass().getResource("/" + fxmlFile);
        if (fxmlUrl == null) {
            fxmlUrl = getClass().getResource(fxmlFile);
            if (fxmlUrl == null) {
                throw new IOException("FXML file not found: " + fxmlFile);
            }
        }

        // Load FXML
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Node content = loader.load();

        // Cache the loaded content
        contentCache.put(viewKey, content);
        logInfo("Successfully loaded and cached: " + fxmlFile);

        return content;
    }

    /**
     * Display loaded content with smooth transition
     */
    private void displayLoadedContent(Node content, Button sourceButton) {
        try {
            // Update active button styling
            updateActiveButton(sourceButton);

            // Clear existing content
            dynamicContent.getChildren().clear();

            // Add new content
            dynamicContent.getChildren().add(content);
            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);

            // Show content with fade transition
            showContentWithTransition();
            currentLoadedContent = content;

        } catch (Exception e) {
            handleError("Failed to display loaded content", e);
        }
    }

    /**
     * Show content with smooth fade transition
     */
    private void showContentWithTransition() {
        welcomeScreen.setVisible(false);
        dynamicContent.setVisible(true);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), dynamicContent);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    /**
     * Update active button styling
     */
    private void updateActiveButton(Button newActiveButton) {
        // Reset previous active button
        if (currentActiveButton != null) {
            resetButtonStyle(currentActiveButton);
        }

        // Set new active button
        currentActiveButton = newActiveButton;
        if (currentActiveButton != null) {
            setActiveButtonStyle(currentActiveButton);
        }
    }

    /**
     * Set active button style
     */
    private void setActiveButtonStyle(Button button) {
        String activeStyle = button.getStyle().replace("-fx-background-color: #ffffff;",
                "-fx-background-color: #e3f2fd;") + " -fx-border-color: #667eea; -fx-border-width: 2;";
        button.setStyle(activeStyle);
    }

    /**
     * Reset button to default style
     */
    private void resetButtonStyle(Button button) {
        String originalStyle = "-fx-background-color: #ffffff; -fx-text-fill: #667eea; " +
                "-fx-font-weight: bold; -fx-background-radius: 20; -fx-cursor: hand; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);";
        button.setStyle(originalStyle);
    }

    /**
     * Show/hide loading indicator
     */
    private void showLoadingState(boolean show) {
        loadingIndicator.setVisible(show);
        if (show) {
            welcomeScreen.setVisible(false);
            dynamicContent.setVisible(false);
        }
    }

    /**
     * Show welcome screen
     */
    private void showWelcomeScreen() {
        welcomeScreen.setVisible(true);
        dynamicContent.setVisible(false);
        loadingIndicator.setVisible(false);

        // Reset active button
        if (currentActiveButton != null) {
            resetButtonStyle(currentActiveButton);
            currentActiveButton = null;
        }
    }

    /**
     * Update status text with different types
     */
    private void updateStatus(String message, StatusType type) {
        if (statusText != null) {
            statusText.setText(message);

            // Set color based on status type
            switch (type) {
                case SUCCESS:
                    statusText.setStyle("-fx-fill: #28a745;");
                    break;
                case ERROR:
                    statusText.setStyle("-fx-fill: #dc3545;");
                    break;
                case WARNING:
                    statusText.setStyle("-fx-fill: #ffc107;");
                    break;
                case LOADING:
                    statusText.setStyle("-fx-fill: #17a2b8;");
                    break;
                default:
                    statusText.setStyle("-fx-fill: #ffffff;");
            }

            // Auto-reset status after delay (except for errors)
            if (type != StatusType.ERROR && statusResetTransition != null) {
                statusResetTransition.stop();
                statusResetTransition.play();
            }
        }
    }

    /**
     * Handle view loading errors
     */
    private void handleViewLoadError(String viewKey, Exception e) {
        String viewName = getViewDisplayName(viewKey);
        String errorMessage = "Failed to load " + viewName;

        logError(errorMessage, e);
        updateStatus("Error loading " + viewName, StatusType.ERROR);

        // Show user-friendly error dialog
        showErrorDialog("Load Error",
                "Unable to load " + viewName + " module.",
                "Please check if the file exists and try again.",
                e);
    }

    /**
     * Handle general errors
     */
    private void handleError(String message, Exception e) {
        logError(message, e);
        updateStatus("Error occurred", StatusType.ERROR);
        showErrorDialog("Error", message, "An unexpected error occurred.", e);
    }

    /**
     * Handle critical errors that affect dashboard functionality
     */
    private void handleCriticalError(String message, Exception e) {
        logError("CRITICAL: " + message, e);
        updateStatus("Critical Error", StatusType.ERROR);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Critical Error");
        alert.setHeaderText("Dashboard Critical Error");
        alert.setContentText(message + "\n\nThe dashboard may not function properly. " +
                "Please restart the application.");

        if (e != null) {
            alert.getDialogPane().setExpandableContent(createExceptionTextArea(e));
        }

        alert.showAndWait();
    }

    /**
     * Show user-friendly error dialog
     */
    private void showErrorDialog(String title, String header, String content, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        // Add technical details if exception exists
        if (e != null) {
            alert.getDialogPane().setExpandableContent(createExceptionTextArea(e));
        }

        // Add retry button for view loading errors
        if (title.equals("Load Error")) {
            ButtonType retryButton = new ButtonType("Retry");
            alert.getButtonTypes().add(retryButton);
        }

        alert.showAndWait().ifPresent(response -> {
            if (response.getText().equals("Retry")) {
                showWelcomeScreen();
            }
        });
    }

    /**
     * Create expandable text area for exception details
     */
    private javafx.scene.control.TextArea createExceptionTextArea(Exception e) {
        javafx.scene.control.TextArea textArea = new javafx.scene.control.TextArea();
        textArea.setText(getStackTrace(e));
        textArea.setEditable(false);
        textArea.setPrefRowCount(10);
        textArea.setPrefColumnCount(50);
        return textArea;
    }

    /**
     * Get formatted stack trace
     */
    private String getStackTrace(Exception e) {
        java.io.StringWriter sw = new java.io.StringWriter();
        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * Get user-friendly display name for view
     */
    private String getViewDisplayName(String viewKey) {
        return switch (viewKey) {
            case "alert" -> "Alerts";
            case "analyse" -> "Incident Analysis";
            case "export" -> "Data Export";
            case "filter" -> "Data Filter";
            case "report" -> "Report Generation";
            case "patrol" -> "Patrol Response";
            case "realtime" -> "Real-time Data";
            case "share" -> "Data Sharing";
            default -> "Unknown View";
        };
    }

    /**
     * Logging methods
     */
    private void logInfo(String message) {
        LOGGER.info(message);
    }

    private void logError(String message, Exception e) {
        if (e != null) {
            LOGGER.log(Level.SEVERE, message, e);
        } else {
            LOGGER.severe(message);
        }
    }

    /**
     * Status types for different message categories
     */
    private enum StatusType {
        NORMAL, SUCCESS, ERROR, WARNING, LOADING
    }

    /**
     * Clear cache method (useful for development/refresh)
     */
    public void clearCache() {
        contentCache.clear();
        logInfo("Content cache cleared");
        updateStatus("Cache cleared", StatusType.SUCCESS);
    }

    /**
     * Get current loaded view key
     */
    public String getCurrentViewKey() {
        if (currentLoadedContent == null) {
            return null;
        }

        return contentCache.entrySet().stream()
                .filter(entry -> entry.getValue() == currentLoadedContent)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    /**
     * Check if specific view is loaded
     */
    public boolean isViewLoaded(String viewKey) {
        return contentCache.containsKey(viewKey);
    }
}