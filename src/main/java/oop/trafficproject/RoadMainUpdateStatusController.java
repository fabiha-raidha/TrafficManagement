package oop.trafficproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;

public class RoadMainUpdateStatusController
{
    @javafx.fxml.FXML
    private ComboBox<String> statusComboBox_fxid;
    @javafx.fxml.FXML
    private TextArea updateStatTextAreaFXID;
    @javafx.fxml.FXML
    private TableColumn<Report, String> reportIdCol_fxid;
    @javafx.fxml.FXML
    private TableColumn<Report, LocalDate> submissionDateCol_fxid;
    @javafx.fxml.FXML
    private TableColumn<Report, String> typeCol_fxid;
    @javafx.fxml.FXML
    private TableColumn<Report, String> locationCol_fxid;
    @javafx.fxml.FXML
    private TableView<Report> updateStatusTableView_fxid;
    @javafx.fxml.FXML
    private TableColumn<Report, String> currentstatusCol_fxid;

    @javafx.fxml.FXML
    public void initialize() {

        reportIdCol_fxid.setCellValueFactory(new PropertyValueFactory<>("reportID"));
        submissionDateCol_fxid.setCellValueFactory(new PropertyValueFactory<>("publicDate"));
        typeCol_fxid.setCellValueFactory(new PropertyValueFactory<>("reportType"));
        locationCol_fxid.setCellValueFactory(new PropertyValueFactory<>("location"));
        currentstatusCol_fxid.setCellValueFactory(new PropertyValueFactory<>("status"));

        statusComboBox_fxid.getItems().addAll("In Progress","Completed");

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream("reports.bin");
            ois =new ObjectInputStream(fis);
            try {
                updateStatusTableView_fxid.getItems().clear();

                while (true){
                    Report report1 = (Report)ois.readObject();
                    //if (report1.getStatus().equals(comboboxfxid.getValue))
                    updateStatusTableView_fxid.getItems().add(report1);

                }

            }catch (EOFException e){}



        } catch (Exception e){}
    }

    @javafx.fxml.FXML
    public void BackToCitizenDashboardOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RoadMaintenanceOfficer-dashboard-01.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void Refresh_OnAction(ActionEvent actionEvent) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream("reports.bin");
            ois =new ObjectInputStream(fis);
            try {
                updateStatusTableView_fxid.getItems().clear();

                while (true){
                    Report report1 = (Report)ois.readObject();
                    //if (report1.getStatus().equals(comboboxfxid.getValue))
                    updateStatusTableView_fxid.getItems().add(report1);

                }

            }catch (EOFException e){}



        } catch (Exception e){}
    }

    @javafx.fxml.FXML
    public void Change_status_OnAction(ActionEvent actionEvent) {
        if (statusComboBox_fxid.getValue() == null) {

            Utility.errorMessage01("Select A Type From ComboBox");
            return;
        }
        if (updateStatTextAreaFXID.getText().isEmpty()) {

            Utility.errorMessage01("Input A Report ID");
            return;
        }

        FileOutputStream fos = null;
        ObjectOutputStream obs = null;
        try {
            // String reportID, String status,
            // String reportType, String location,
            // String citizenID, String message, LocalDate publicDate
            Report rep = new Report(null, "Pending", statusComboBox_fxid.getValue(), null, "2420829", updateStatTextAreaFXID.getText(), LocalDate.now());
            File file = new File("reports.bin");
            boolean abc = file.exists() && file.length() > 0;
            fos = new FileOutputStream("reports.bin", abc);
            obs = abc ? new Appendable(fos) : new ObjectOutputStream(fos);
            obs.writeObject(rep);
            Utility.successMessage01("Update Status Successfully");


        } catch (Exception e){}}
}

