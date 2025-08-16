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

public class CitizenReportFeedbackHistoryController
{
    @javafx.fxml.FXML
    private TableView feedbackHistoryTableView_fxid;
    @javafx.fxml.FXML
    private TableColumn<Report, LocalDate> dateCol_fxid;
    @javafx.fxml.FXML
    private TableColumn<Report,String> taskIDCol_fxid;
    @javafx.fxml.FXML
    private TableColumn<Report,String> ratingCol_fxid;
    @javafx.fxml.FXML
    private TableColumn<Report,String> commentCol_fxid;


    @javafx.fxml.FXML
    public void initialize() {
        dateCol_fxid.setCellValueFactory(new PropertyValueFactory<>("publicDate"));
        taskIDCol_fxid.setCellValueFactory(new PropertyValueFactory<>("reportID"));

        commentCol_fxid.setCellValueFactory(new PropertyValueFactory<>("message"));

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream("reports.bin");
            ois =new ObjectInputStream(fis);
            try {
                feedbackHistoryTableView_fxid.getItems().clear();

                while (true){
                    Report report1 = (Report)ois.readObject();
                    //if (report1.getStatus().equals(comboboxfxid.getValue))
                    feedbackHistoryTableView_fxid.getItems().add(report1);

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
}