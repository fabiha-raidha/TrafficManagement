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

public class CitizenReportersTrackReportController
{
    @javafx.fxml.FXML
    private TableColumn<Report,String> reportIdCol_fxid;
    @javafx.fxml.FXML
    private TableColumn<Report, LocalDate> submissionDateCol_fxid;
    @javafx.fxml.FXML
    private TextArea myreportMessegeTextAreaFXID;
    @javafx.fxml.FXML
    private TableColumn<Report,String> typeCol_fxid;
    @javafx.fxml.FXML
    private TableColumn<Report,String> locationCol_fxid;
    @javafx.fxml.FXML
    private TableView<Report> myReportTableView_fxid;
    @javafx.fxml.FXML
    private TableColumn<Report,String> currentstatusCol_fxid;

    @javafx.fxml.FXML
    public void initialize() {
        reportIdCol_fxid.setCellValueFactory(new PropertyValueFactory<>("reportID"));
        submissionDateCol_fxid.setCellValueFactory(new PropertyValueFactory<>("publicDate"));
        typeCol_fxid.setCellValueFactory(new PropertyValueFactory<>("reportType"));
        locationCol_fxid.setCellValueFactory(new PropertyValueFactory<>("location"));
        currentstatusCol_fxid.setCellValueFactory(new PropertyValueFactory<>("status"));

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream("reports.bin");
            ois =new ObjectInputStream(fis);
            try {
                myReportTableView_fxid.getItems().clear();

                while (true){
                    Report report1 = (Report)ois.readObject();
                    //if (report1.getStatus().equals(comboboxfxid.getValue))
                    myReportTableView_fxid.getItems().add(report1);

                }

            }catch (EOFException e){}



        } catch (Exception e){}



    }

    @javafx.fxml.FXML
    public void BackToCitizenDashboardOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CitizenReporter-dashboard-01.fxml"));
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
                myReportTableView_fxid.getItems().clear();

                while (true){
                    Report report1 = (Report)ois.readObject();
                    //if (report1.getStatus().equals(comboboxfxid.getValue))
                    myReportTableView_fxid.getItems().add(report1);

                }

            }catch (EOFException e){}



        } catch (Exception e){}
    }

    @Deprecated
    public void View_report_OnAction(ActionEvent actionEvent) {


    }

    @javafx.fxml.FXML
    public void Track_report_OnAction(ActionEvent actionEvent) {
        if (myreportMessegeTextAreaFXID.getText().isEmpty()) {

            Utility.errorMessage01("Input Report ID First");
            return;
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream("reports.bin");
            ois =new ObjectInputStream(fis);
            try {
                myReportTableView_fxid.getItems().clear();
                while (true){
                    Report report1 = (Report)ois.readObject();
                    //if (report1.getStatus().equals(comboboxfxid.getValue))
                    if (report1.getReportID().equals(myreportMessegeTextAreaFXID.getText())){

                        myReportTableView_fxid.getItems().add(report1);


                    }

                }

            }catch (EOFException e){}



        } catch (Exception e){}
    }
}