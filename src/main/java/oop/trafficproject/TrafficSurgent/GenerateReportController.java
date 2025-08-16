package oop.trafficproject.TrafficSurgent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GenerateReportController {
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Button generateBtn;
    @FXML
    private Button downloadPdfBtn;
    @FXML
    private TableView<ReportItem> reportTable;

    @FXML
    private TableColumn<ReportItem, String> colType;
    @FXML
    private TableColumn<ReportItem, Integer> colCount;
    @FXML
    private TableColumn<ReportItem, String> colDate;

    @FXML
    private Label statusText; // Critical field, ensured to be present

    private ObservableList<ReportItem> reportData = FXCollections.observableArrayList();

    public static class ReportItem {
        private final String type;
        private final int count;
        private final String date;

        public ReportItem(String type, int count, String date) {
            this.type = type;
            this.count = count;
            this.date = date;
        }

        public String getType() { return type; }
        public int getCount() { return count; }
        public String getDate() { return date; }
    }

    @FXML
    public void initialize() {
        if (startDate == null || endDate == null || generateBtn == null || downloadPdfBtn == null ||
                reportTable == null || colType == null || colCount == null || colDate == null) {
            System.err.println("Error: Critical FXML elements are missing. Check GenerateReport.fxml.");
            setStatus("Error: FXML loading issue detected.");
            return;
        }

        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadSampleData();
        reportTable.setItems(reportData);

        LocalDate now = LocalDate.now();
        startDate.setValue(now.minusDays(7));
        endDate.setValue(now);

        generateBtn.setOnAction(event -> generateReport());
        downloadPdfBtn.setOnAction(event -> downloadPdf());
    }

    private void loadSampleData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        reportData.add(new ReportItem("Accident", 5, LocalDate.of(2025, 8, 7).format(formatter)));
        reportData.add(new ReportItem("Patrol Log", 3, LocalDate.of(2025, 8, 9).format(formatter)));
        reportData.add(new ReportItem("Camera Alert", 2, LocalDate.of(2025, 8, 10).format(formatter)));
    }

    private void generateReport() {
        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();

        if (start == null || end == null) {
            setStatus("Error: Please select both start and end dates.");
            return;
        }
        if (end.isBefore(start)) {
            setStatus("Error: End date must be after start date.");
            return;
        }

        ObservableList<ReportItem> filteredData = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (ReportItem item : reportData) {
            LocalDate itemDate = LocalDate.parse(item.getDate(), formatter);
            if (!itemDate.isBefore(start) && !itemDate.isAfter(end)) {
                filteredData.add(item);
            }
        }

        reportTable.setItems(filteredData);
        setStatus("Report generated for " + start.format(formatter) + " to " + end.format(formatter) + ".");
    }

    private void downloadPdf() {
        ObservableList<ReportItem> data = reportTable.getItems();
        if (data.isEmpty()) {
            setStatus("Error: No data to export.");
            return;
        }

        StringBuilder pdfContent = new StringBuilder("Shift Summary Report\n---------------\n");
        for (ReportItem item : data) {
            pdfContent.append(String.format("Type: %s, Count: %d, Date: %s\n", item.getType(), item.getCount(), item.getDate()));
        }
        System.out.println("PDF Export (simulated):\n" + pdfContent.toString());
        setStatus("PDF export simulated for " + data.size() + " entries.");
    }

    private void setStatus(String message) {
        if (statusText != null) {
            statusText.setText(message);
        } else {
            System.err.println("Warning: statusText not available - " + message);
        }
    }
}