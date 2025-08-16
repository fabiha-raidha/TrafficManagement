package oop.trafficproject.TrafficSurgent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PatrolScheduleController {
    @FXML
    private ToggleButton calendarViewBtn;
    @FXML
    private ToggleButton tableViewBtn;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private ComboBox<String> routeCombo;
    @FXML
    private ComboBox<String> officerCombo;
    @FXML
    private Button filterBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button editBtn;
    @FXML
    private StackPane calendarContainer;

    @FXML
    private TableView<PatrolItem> patrolTable;
    @FXML
    private TableColumn<PatrolItem, String> colDate;
    @FXML
    private TableColumn<PatrolItem, String> colRoute;
    @FXML
    private TableColumn<PatrolItem, String> colOfficer;

    @FXML
    private Label statusText; // Added to match FXML

    private ObservableList<PatrolItem> patrolData = FXCollections.observableArrayList();
    private boolean isCalendarView = true;

    public static class PatrolItem {
        private final String date;
        private final String route;
        private final String officer;

        public PatrolItem(String date, String route, String officer) {
            this.date = date;
            this.route = route;
            this.officer = officer;
        }

        public String getDate() { return date; }
        public String getRoute() { return route; }
        public String getOfficer() { return officer; }
    }

    @FXML
    public void initialize() {
        // Debug: Check for null fields
        if (calendarViewBtn == null || tableViewBtn == null || startDate == null || endDate == null ||
                routeCombo == null || officerCombo == null || filterBtn == null || addBtn == null ||
                editBtn == null || calendarContainer == null || patrolTable == null || colDate == null ||
                colRoute == null || colOfficer == null) {
            System.err.println("Error: Critical FXML elements are missing. Check PatrolSchedule.fxml.");
            setStatus("Error: FXML loading issue detected.");
            return;
        }

        // Initialize ComboBoxes
        routeCombo.setItems(FXCollections.observableArrayList("Route 1", "Route 2", "Route 3"));
        officerCombo.setItems(FXCollections.observableArrayList("Officer 1", "Officer 2", "Officer 3"));

        // Initialize TableView columns
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colRoute.setCellValueFactory(new PropertyValueFactory<>("route"));
        colOfficer.setCellValueFactory(new PropertyValueFactory<>("officer"));

        // Load sample data
        loadSampleData();

        // Set default date range
        LocalDate now = LocalDate.now();
        startDate.setValue(now.minusDays(7));
        endDate.setValue(now);

        // Set toggle button actions
        calendarViewBtn.setOnAction(event -> switchView(true));
        tableViewBtn.setOnAction(event -> switchView(false));
        calendarViewBtn.setSelected(true);

        // Set filter button action
        filterBtn.setOnAction(event -> filterSchedule());

        // Set add and edit button actions
        addBtn.setOnAction(event -> addPatrol());
        editBtn.setOnAction(event -> editSelectedPatrol());

        // Initialize with calendar view
        switchView(true);
    }

    private void loadSampleData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        patrolData.add(new PatrolItem(LocalDate.of(2025, 8, 7).format(formatter), "Route 1", "Officer 1"));
        patrolData.add(new PatrolItem(LocalDate.of(2025, 8, 9).format(formatter), "Route 2", "Officer 2"));
        patrolData.add(new PatrolItem(LocalDate.of(2025, 8, 10).format(formatter), "Route 3", "Officer 1"));
    }

    private void switchView(boolean toCalendar) {
        isCalendarView = toCalendar;
        calendarViewBtn.setSelected(toCalendar);
        tableViewBtn.setSelected(!toCalendar);

        if (toCalendar) {
            calendarContainer.getChildren().setAll(createCalendarPlaceholder());
            patrolTable.setVisible(false);
        } else {
            calendarContainer.getChildren().clear();
            patrolTable.setVisible(true);
            patrolTable.setItems(filterScheduleData(patrolData));
        }
    }

    private Node createCalendarPlaceholder() {
        Label placeholder = new Label("Calendar View Placeholder");
        placeholder.setTextFill(javafx.scene.paint.Color.web("#607d8b"));
        return placeholder;
    }

    private ObservableList<PatrolItem> filterScheduleData(ObservableList<PatrolItem> data) {
        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();
        String selectedRoute = routeCombo.getValue();
        String selectedOfficer = officerCombo.getValue();

        ObservableList<PatrolItem> filteredData = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (PatrolItem item : data) {
            LocalDate itemDate = LocalDate.parse(item.getDate(), formatter);
            boolean dateMatch = (start == null || !itemDate.isBefore(start)) && (end == null || !itemDate.isAfter(end));
            boolean routeMatch = selectedRoute == null || item.getRoute().equals(selectedRoute);
            boolean officerMatch = selectedOfficer == null || item.getOfficer().equals(selectedOfficer);

            if (dateMatch && routeMatch && officerMatch) {
                filteredData.add(item);
            }
        }
        return filteredData;
    }

    private void filterSchedule() {
        if (isCalendarView) {
            setStatus("Filtering applied to calendar view (simulated).");
        } else {
            patrolTable.setItems(filterScheduleData(patrolData));
            setStatus("Filter applied. " + patrolTable.getItems().size() + " patrols displayed.");
        }
    }

    private void addPatrol() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String newDate = now.format(formatter);
        PatrolItem newPatrol = new PatrolItem(newDate, "Route 1", "Officer 1");
        patrolData.add(newPatrol);
        if (!isCalendarView) {
            patrolTable.setItems(filterScheduleData(patrolData));
        }
        setStatus("New patrol added for " + newDate + ".");
    }

    private void editSelectedPatrol() {
        PatrolItem selectedItem = patrolTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            setStatus("Error: Please select a patrol to edit.");
            return;
        }
        String newOfficer = "Officer 3";
        int index = patrolData.indexOf(selectedItem);
        if (index >= 0) {
            patrolData.set(index, new PatrolItem(selectedItem.getDate(), selectedItem.getRoute(), newOfficer));
            if (!isCalendarView) {
                patrolTable.setItems(filterScheduleData(patrolData));
            }
            setStatus("Patrol edited. Officer updated to " + newOfficer + ".");
        }
    }

    private void setStatus(String message) {
        if (statusText != null) {
            statusText.setText(message);
        } else {
            System.err.println("Warning: statusText not available - " + message);
        }
    }
}