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

public class CitizenReportLocalUpdateController
{
    @javafx.fxml.FXML
    private TableColumn<Report,String> tagCol_fxid;
    @javafx.fxml.FXML
    private TableColumn<Report,String> reportStatusCol_fxid;
    @javafx.fxml.FXML
    private TableView<Report> localUpdateTableView_fxid;
    @javafx.fxml.FXML
    private TableColumn<Report,String> reportIDCol_fxid;
    @javafx.fxml.FXML
    private TextArea localUpdate_Lable_fxid;

    @javafx.fxml.FXML
    public void initialize() {
        tagCol_fxid.setCellValueFactory(new PropertyValueFactory<>("message"));
        reportStatusCol_fxid.setCellValueFactory(new PropertyValueFactory<>("status"));
        reportIDCol_fxid.setCellValueFactory(new PropertyValueFactory<>("reportID"));

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream("reports.bin");
            ois =new ObjectInputStream(fis);
            try {
                localUpdateTableView_fxid.getItems().clear();

                while (true){
                    Report report1 = (Report)ois.readObject();
                    //if (report1.getStatus().equals(comboboxfxid.getValue))
                    localUpdateTableView_fxid.getItems().add(report1);

                }

            }catch (EOFException e){}



        } catch (Exception e){}
    }

    @javafx.fxml.FXML
    public void LocalUpdate_view_OnAction(ActionEvent actionEvent) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream("reports.bin");
            ois =new ObjectInputStream(fis);
            try {
                localUpdateTableView_fxid.getItems().clear();
                while (true){
                    Report report1 = (Report)ois.readObject();
                    //if (report1.getStatus().equals(comboboxfxid.getValue))
                    if (report1.getReportID().equals(localUpdate_Lable_fxid.getText())){

                        localUpdateTableView_fxid.getItems().add(report1);


                    }

                }

            }catch (EOFException e){}



        } catch (Exception e){}
    }

    @Deprecated
    public void BackToDashboardOnAction(ActionEvent actionEvent) {
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