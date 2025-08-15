package oop.trafficproject.DataAnalystController;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class FilterItem {
    private final StringProperty filterType;
    private final StringProperty value;
    private final SimpleObjectProperty<Button> actions;

    public FilterItem(String filterType, String value) {
        this.filterType = new SimpleStringProperty(filterType);
        this.value = new SimpleStringProperty(value);
        this.actions = new SimpleObjectProperty<>(new Button("Delete"));
    }

    public StringProperty filterTypeProperty() { return filterType; }
    public StringProperty valueProperty() { return value; }
    public SimpleObjectProperty<Button> actionsProperty() { return actions; }

    public String getFilterType() { return filterType.get(); }
    public void setFilterType(String filterType) { this.filterType.set(filterType); }

    public String getValue() { return value.get(); }
    public void setValue(String value) { this.value.set(value); }

    public String getIncidentType() {
        return null;
    }

    public int getCount() {
        return 0;
    }

    public String getZone() {
        return null;
    }
}
