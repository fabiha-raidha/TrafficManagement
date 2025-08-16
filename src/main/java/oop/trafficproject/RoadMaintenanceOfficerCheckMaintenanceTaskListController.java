package oop.trafficproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;

public class RoadMaintenanceOfficerCheckMaintenanceTaskListController
{
    @javafx.fxml.FXML
    private TableColumn<Report, String> messageCol_fxid;
    @javafx.fxml.FXML
    private TableColumn<Report, String> citizenIDCol_fxid;
    @javafx.fxml.FXML
    private TableColumn<Report, String> reportIdCol_fxid;
    @javafx.fxml.FXML
    private TableColumn<Report, String> reportTypeCol_fxid;
    @javafx.fxml.FXML
    private TableColumn<Report, String> statusCol_fxid;
    @javafx.fxml.FXML
    private TableColumn<Report, String> locationCol_fxid;
    @javafx.fxml.FXML
    private TableView<Report> checkTaskListTabileView_fxid;
    @javafx.fxml.FXML
    private TableColumn<Report, LocalDate> localDateCol_fxid;
    @javafx.fxml.FXML
    private TextArea taskListTextAreaFXID;

    @javafx.fxml.FXML
    public void initialize() {
        checkTaskListTabileView_fxid.getItems().clear();
        // String reportID, String status, String reportType, String location,
        // String citizenID, String message, LocalDate publicDate
        messageCol_fxid.setCellValueFactory(new PropertyValueFactory<>("message"));
        reportIdCol_fxid.setCellValueFactory(new PropertyValueFactory<>("reportID"));
        statusCol_fxid.setCellValueFactory(new PropertyValueFactory<>("status"));
        reportTypeCol_fxid.setCellValueFactory(new PropertyValueFactory<>("reportType"));
        locationCol_fxid.setCellValueFactory(new PropertyValueFactory<>("location"));
        citizenIDCol_fxid.setCellValueFactory(new PropertyValueFactory<>("citizenID"));
        localDateCol_fxid.setCellValueFactory(new PropertyValueFactory<>("publicDate"));

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream("reports.bin");
            ois =new ObjectInputStream(fis);
            try {
                checkTaskListTabileView_fxid.getItems().clear();

                while (true){
                    Report report1 = (Report)ois.readObject();
                    //if (report1.getStatus().equals(comboboxfxid.getValue))
                    checkTaskListTabileView_fxid.getItems().add(report1);

                }

            }catch (EOFException e){}



        } catch (Exception e){}



    }


    @Deprecated
    public void BackToAssignCrewOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RoadMaintenanceOfficer-AssignToCrew.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void BackToDashboardOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RoadMaintenanceOfficer-dashboard-01.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @Deprecated
    public void BackToUpdateStatusOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RoadMaintenanceOfficer-dashboard-01.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Deprecated
    public void ViewTaskListOnAction(ActionEvent actionEvent) {

    }

    @javafx.fxml.FXML
    public void Refresh_OnAction(ActionEvent actionEvent) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream("reports.bin");
            ois =new ObjectInputStream(fis);
            try {
                checkTaskListTabileView_fxid.getItems().clear();

                while (true){
                    Report report1 = (Report)ois.readObject();
                    //if (report1.getStatus().equals(comboboxfxid.getValue))
                    checkTaskListTabileView_fxid.getItems().add(report1);

                }

            }catch (EOFException e){}



        } catch (Exception e){}
    }

    @javafx.fxml.FXML
    public void View_TaskListreport_OnAction(ActionEvent actionEvent) {
        if (taskListTextAreaFXID.getText().isEmpty()){

            Utility.errorMessage01("Input Report ID First");
            return;
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream("reports.bin");
            ois =new ObjectInputStream(fis);
            try {
                checkTaskListTabileView_fxid.getItems().clear();
                while (true){
                    Report report1 = (Report)ois.readObject();
                    //if (report1.getStatus().equals(comboboxfxid.getValue))
                    if (report1.getReportID().equals(taskListTextAreaFXID.getText())){

                        checkTaskListTabileView_fxid.getItems().add(report1);


                    }

                }

            }catch (EOFException e){}



        } catch (Exception e){}
    }
}