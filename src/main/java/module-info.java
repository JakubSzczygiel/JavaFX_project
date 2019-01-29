module JavaFX.todolist {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires org.apache.commons.collections4;
    requires poi.ooxml;
    requires poi.ooxml.schemas;
    requires poi;
    requires xmlbeans;
    requires org.apache.commons.compress;

    opens sample;
}