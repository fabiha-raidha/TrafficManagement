package oop.trafficproject.AdministrativeOfficer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import oop.trafficproject.User;

import java.io.*;
import java.util.ArrayList;

public class UserManagementAO {

    @FXML
    private TableColumn<User, String> departmentColumn;

    @FXML
    private ComboBox<String> departmentEditDeleteCB;

    @FXML
    private ComboBox<String> departmentFilterCB;

    @FXML
    private TableColumn<User, String> designationColumn;

    @FXML
    private TextField designationEditDeleteTF;

    @FXML
    private TableColumn<User, Integer> employeeIDColumn;

    @FXML
    private TextField employeeIDEditDeleteTF;

    @FXML
    private TextField employeeIDFilterTF;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TextField nameEditDeleteTF;

    @FXML
    private TextField nameFilterTF;

    @FXML
    private TableColumn<User, String> statusColumn;

    @FXML
    private ComboBox<String> statusEditDeleteCB;

    @FXML
    private TableView<User> userManagementTableView;

    @FXML
    private TableColumn<User, String> zoneColumn;

    @FXML
    private ComboBox<String> zoneEditDeleteCB;

    @FXML
    private ComboBox<String> zoneFilterCB;

    private ObservableList<User> masterUserList = FXCollections.observableArrayList();
    private FilteredList<User> filteredData;

    private final String DATA_FILE_PATH = "data/users.bin";

    @FXML
    public void initialize() {
        employeeIDColumn.setCellValueFactory(new PropertyValueFactory<>("empID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        zoneColumn.setCellValueFactory(new PropertyValueFactory<>("assignedZone"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        designationColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));

        loadUserFromBinFile();

        filteredData = new FilteredList<>(masterUserList, p -> true);
        userManagementTableView.setItems(filteredData);

        userManagementTableView.setItems(masterUserList);

        zoneFilterCB.getItems().addAll("Uttara", "Gulshan", "Bashundhara", "Banani", "Mirpur", "Mohakhali");
        departmentFilterCB.getItems().addAll("IT", "Administration", "Maintenance", "Police", "Planning");
        statusEditDeleteCB.getItems().addAll("Active", "Leave", "Terminated");
        zoneEditDeleteCB.getItems().addAll(zoneFilterCB.getItems());
        departmentEditDeleteCB.getItems().addAll(departmentFilterCB.getItems());
        statusEditDeleteCB.getItems().addAll("Active", "Leave", "Terminated");

        userManagementTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldUser, newUser) -> {if (newUser != null) populateEditFields(newUser); });

    }

    private void populateEditFields(User user) {
        employeeIDEditDeleteTF.setText(String.valueOf(user.getEmpId()));
        nameEditDeleteTF.setText(user.getName());
        departmentEditDeleteCB.setValue(user.getDepartment());
        zoneEditDeleteCB.setValue(user.getAssignedZone());
        statusEditDeleteCB.setValue(user.getStatus());
        designationEditDeleteTF.setText(user.getDesignation());
    }


    @FXML
    void onDeleteButtonClicked(ActionEvent event) {
        User selectedUser = userManagementTableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) { showAlert(Alert.AlertType.WARNING, "No user selected", "Please select a user"); return;}

        masterUserList.remove(selectedUser);
        userManagementTableView.setItems(masterUserList);
        saveUsersToBinFile();

        showAlert(Alert.AlertType.INFORMATION, "Success", "User deleted successfully");
    }

    public void saveUsersToBinFile() {
        File file = new File(DATA_FILE_PATH);
        try {
            file.getParentFile().mkdirs();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(new ArrayList<>(masterUserList));
            }
        } catch (IOException e) {e.printStackTrace();}
    }

    @SuppressWarnings("unchecked")
    public  void loadUserFromBinFile() {
        File file = new File(DATA_FILE_PATH);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            ArrayList<User> loadUsers = (ArrayList<User>) ois.readObject();
            masterUserList.setAll(loadUsers);
        } catch (IOException | ClassNotFoundException e) {e.printStackTrace();}
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void onEditButtonClicked(ActionEvent event) {
        User selectedUser = userManagementTableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) { showAlert(Alert.AlertType.WARNING, "No user selected", "Please select a user");
            return;}

        selectedUser.setName(nameEditDeleteTF.getText());
        selectedUser.setDepartment(departmentEditDeleteCB.getValue());
        selectedUser.setAssignedZone(zoneEditDeleteCB.getValue());
        selectedUser.setStatus(statusEditDeleteCB.getValue());
        selectedUser.setDesignation(designationEditDeleteTF.getText());

        userManagementTableView.refresh();

        saveUsersToBinFile();

        showAlert(Alert.AlertType.INFORMATION, "Success", "User updates successfully");
    }

    @FXML
    void onFilterButtonClicked(ActionEvent event) {
        String selectedZone = zoneFilterCB.getValue();
        String selectedDepartment = departmentFilterCB.getValue();
        String empIDText = employeeIDFilterTF.getText();
        String nameText = nameFilterTF.getText();


        ObservableList<User> filteredList = masterUserList.filtered(user -> {
            boolean matchesZone = selectedZone == null || selectedZone.isEmpty() || user.getAssignedZone().equalsIgnoreCase(selectedZone);

            boolean matchesDepartment = selectedDepartment == null || selectedDepartment.isEmpty() || user.getDepartment().equalsIgnoreCase(selectedDepartment);

            boolean matchesEmpID = empIDText == null || empIDText.isEmpty() || user.getEmpId() == Integer.parseInt(empIDText);

            boolean matchesName = nameText== null || nameText.isEmpty() || user.getName().toLowerCase().contains(nameText.toLowerCase());

            return matchesZone && matchesDepartment && matchesEmpID && matchesName;
        });

        userManagementTableView.setItems(filteredList);
    }

}
