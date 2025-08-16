package oop.trafficproject;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Report implements Serializable {
    private String reportID, status, reportType, location, citizenID, message;
    private LocalDate publicDate;


    public String generateReportID(){







        return UUID.randomUUID().toString().substring(0,8).toUpperCase();
    }


    public Report(String reportID, String status, String reportType, String location, String citizenID, String message, LocalDate publicDate) {
        this.reportID = generateReportID();
        this.status = status;
        this.reportType = reportType;
        this.location = location;
        this.citizenID = citizenID;
        this.message = message;
        this.publicDate = LocalDate.now();
    }

    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCitizenID() {
        return citizenID;
    }

    public void setCitizenID(String citizenID) {
        this.citizenID = citizenID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(LocalDate publicDate) {
        this.publicDate = publicDate;
    }

    public Report() {



    }
}
