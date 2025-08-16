package oop.trafficproject;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class GiveFeedBack implements Serializable {
    private String reportID, reportStatus, feedBackType, citizenID, feedBackMessage;
    private LocalDate submitDate;


    public String generateReportID() {


        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public GiveFeedBack(String reportID, String reportStatus, String feedBackType, String citizenID, String feedBackMessage, LocalDate submitDate) {
        this.reportID = generateReportID();
        this.reportStatus = reportStatus;
        this.feedBackType = feedBackType;
        this.citizenID = citizenID;
        this.feedBackMessage = feedBackMessage;
        this.submitDate = LocalDate.now();
    }

    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getFeedBackType() {
        return feedBackType;
    }

    public void setFeedBackType(String feedBackType) {
        this.feedBackType = feedBackType;
    }

    public String getCitizenID() {
        return citizenID;
    }

    public void setCitizenID(String citizenID) {
        this.citizenID = citizenID;
    }

    public String getFeedBackMessage() {
        return feedBackMessage;
    }

    public void setFeedBackMessage(String feedBackMessage) {
        this.feedBackMessage = feedBackMessage;
    }

    public LocalDate getSubmitDate() {
        return submitDate;
    }

    public GiveFeedBack() {
    }

    public void setSubmitDate(LocalDate submitDate) {
        this.submitDate = submitDate;


    }
}
