package oop.trafficproject.DataAnalystController;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

public class FilterDataController {
    private final TableColumn<FilterItem, String> filterTypeColumn;
    private final TableColumn<FilterItem, String> valueColumn;
    private final TableColumn<FilterItem, Button> actionsColumn;
    @FXML
    private TableView<FilterItem> filterTable;

    @FXML
    private Button addFilterBtn;

    @FXML
    private Button saveFiltersBtn;

    public FilterDataController(TableColumn<FilterItem, String> filterTypeColumn, TableColumn<FilterItem, String> valueColumn, TableColumn<FilterItem, Button> actionsColumn) {
        this.filterTypeColumn = filterTypeColumn;
        this.valueColumn = valueColumn;
        this.actionsColumn = actionsColumn;
    }

    @FXML
    public void initialize() {
        // Set up table columns
        filterTypeColumn.setCellValueFactory(cellData -> cellData.getValue().filterTypeProperty());
        filterTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        valueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        actionsColumn.setCellValueFactory(cellData -> cellData.getValue().actionsProperty());

        // Add delete button in Actions column
        actionsColumn.setCellFactory(new Callback<TableColumn<FilterItem, Button>, TableCell<FilterItem, Button>>() {
            @Override
            public javafx.scene.control.TableCell<FilterItem, Button> call(TableColumn<FilterItem, Button> param) {
                return new javafx.scene.control.TableCell<>() {
                    @Override
                    protected void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            item.setOnAction(e -> getTableView().getItems().remove(getIndex()));
                            setGraphic(item);
                        }
                    }
                };
            }
        });

        // Add filter button functionality
        addFilterBtn.setOnAction(e -> {
            filterTable.getItems().add(new FilterItem("Zone", ""));
        });

        // Save filters button functionality (just example)
        saveFiltersBtn.setOnAction(e -> {
            System.out.println("Filters saved: " + filterTable.getItems().size());
        });
    }
}
