module oop.trafficproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens oop.trafficproject to javafx.fxml;
    opens oop.trafficproject.itofficer to javafx.fxml, java.base;
    opens oop.trafficproject.trafficupdate_genuser to javafx.fxml, java.base;
    exports oop.trafficproject;
    exports oop.trafficproject.GeneralFXML;
    opens oop.trafficproject.GeneralFXML to javafx.fxml;
    exports oop.trafficproject.AdministrativeOfficer;
    opens oop.trafficproject.AdministrativeOfficer to javafx.fxml;
}