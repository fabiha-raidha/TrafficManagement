package oop.trafficproject.TrafficSurgent;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.nio.charset.StandardCharsets;
import java.text.CollationElementIterator;

public class AssignFieldUnitController {
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Incident> incidentTable;
    @FXML
    private Button refreshBtn;

    // Define columns for the TableView
    @FXML
    private TableColumn<Incident, String> colId;
    @FXML
    private TableColumn<Incident, String> colLocation;
    @FXML
    private TableColumn<Incident, String> colType;
    @FXML
    private TableColumn<Incident, String> colSeverity;
    @FXML
    private TableColumn<Incident, String> colStatus;
    @FXML
    private TableColumn<Incident, ComboBox<String>> colAssign;

    public TextField getSearchField() {
        return searchField;
    }

    public void setSearchField(TextField searchField) {
        this.searchField = searchField;
    }

    public TableView<Incident> getIncidentTable() {
        return incidentTable;
    }

    public void setIncidentTable(TableView<Incident> incidentTable) {
        this.incidentTable = incidentTable;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Button getRefreshBtn() {
        return refreshBtn;
    }

    public void setRefreshBtn(Button refreshBtn) {
        this.refreshBtn = refreshBtn;
    }

    public TableColumn<Incident, String> getColId() {
        return colId;
    }

    public void setColId(TableColumn<Incident, String> colId) {
        this.colId = colId;
    }

    public TableColumn<Incident, String> getColLocation() {
        return colLocation;
    }

    public void setColLocation(TableColumn<Incident, String> colLocation) {
        this.colLocation = colLocation;
    }

    public TableColumn<Incident, String> getColType() {
        return colType;
    }

    public void setColType(TableColumn<Incident, String> colType) {
        this.colType = colType;
    }

    public TableColumn<Incident, String> getColSeverity() {
        return colSeverity;
    }

    public void setColSeverity(TableColumn<Incident, String> colSeverity) {
        this.colSeverity = colSeverity;
    }

    public TableColumn<Incident, String> getColStatus() {
        return colStatus;
    }

    public void setColStatus(TableColumn<Incident, String> colStatus) {
        this.colStatus = colStatus;
    }

    public TableColumn<Incident, ComboBox<String>> getColAssign() {
        return colAssign;
    }

    public void setColAssign(TableColumn<Incident, ComboBox<String>> colAssign) {
        this.colAssign = colAssign;
    }

    public ObservableList<Incident> getIncidentList() {
        return incidentList;
    }

    // Simulated data list
    private final ObservableList<Incident> incidentList = FXCollections.observableArrayList();

    // Inner class to represent incident items
    public static class Incident {
        private final String id;
        private final String location;
        private final String type;
        private final String severity;
        private String status;
        private final ComboBox<String> assignUnit;

        public Incident(String id, String location, String type, String severity, String status) {
            this.id = id;
            this.location = location;
            this.type = type;
            this.severity = severity;
            this.status = status;
            this.assignUnit = new ComboBox<>(FXCollections.observableArrayList("Unit 1", "Unit 2", "Unit 3"));
        }

        public String getId() { return id; }
        public String getLocation() { return location; }
        public String getType() { return type; }
        public String getSeverity() { return severity; }
        public String getStatus() { return status; }
        public ComboBox<String> getAssignUnit() { return assignUnit; }
        public void setStatus(String status) { this.status = status; }
    }
    private String statusText;
    @FXML
    public void initialize() {
        // Initialize TableView columns
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colSeverity.setCellValueFactory(new PropertyValueFactory<>("severity"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Customize the Assign Unit column with ComboBox
        colAssign.setCellFactory(new Callback<TableColumn<Incident, ComboBox<String>>, TableCell<Incident, ComboBox<String>>>() {
            @Override
            public TableCell<Incident, ComboBox<String>> call(TableColumn<Incident, ComboBox<String>> param) {
                return new TableCell<Incident, ComboBox<String>>() {
                    private final ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList("Unit 1", "Unit 2", "Unit 3"));

                    {
                        comboBox.setOnAction(event -> {
                            Incident incident = getTableView().getItems().get(getIndex());
                            if (comboBox.getValue() != null) {
                                incident.setStatus("Assigned to " + comboBox.getValue());
                                getTableView().refresh();
                            }
                        });
                    }

                    @Override
                    protected void updateItem(ComboBox<String> item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || getItem() == null) {
                            setGraphic(null);
                        } else {
                            setGraphic(comboBox);
                            comboBox.setValue(getItem().getValue());
                        }
                    }
                };
            }
        });
        colAssign.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAssignUnit()));

        // Load sample data
        loadSampleData();

        // Set initial TableView data
        incidentTable.setItems(incidentList);

        // Add listener for search field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterIncidents(newValue));

        // Set action for Refresh button
        refreshBtn.setOnAction(event -> refreshTable());
    }

    private void loadSampleData() {
        incidentList.add(new Incident("INC001", "Downtown", "Accident", "High", "Open"));
        incidentList.add(new Incident("INC002", "Highway 101", "Obstruction", "Medium", "Open"));
        incidentList.add(new Incident("INC003", "Central Plaza", "Police Activity", "Low", "Open"));
    }

    private void filterIncidents(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            incidentTable.setItems(incidentList);
        } else {
            ObservableList<Incident> filteredList = FXCollections.observableArrayList();
            String lowerCaseFilter = searchText.toLowerCase();
            for (Incident incident : incidentList) {
                if (incident.getId().toLowerCase().contains(lowerCaseFilter) ||
                        incident.getLocation().toLowerCase().contains(lowerCaseFilter)) {
                    filteredList.add(incident);
                }
            }
            incidentTable.setItems(filteredList);
        }
    }

    private void refreshTable() {
        incidentList.clear();
        loadSampleData();
        incidentTable.setItems(incidentList);
        searchField.clear();
        statusText=("Table refreshed!");
    }
}