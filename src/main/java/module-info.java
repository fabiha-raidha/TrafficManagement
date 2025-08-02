module oop.trafficproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens oop.trafficproject to javafx.fxml;
    opens oop.trafficproject.itofficer to javafx.fxml, java.base;
    opens oop.trafficproject.trafficupdate_genuser to javafx.fxml, java.base;
    exports oop.trafficproject;
}