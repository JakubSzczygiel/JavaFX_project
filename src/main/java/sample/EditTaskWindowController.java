package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;


public class EditTaskWindowController implements Initializable  {

    ControlsColorHandler controlsColorHandler = new ControlsColorHandler();
    ControlsDataReader controlsDataReader = new ControlsDataReader();

    SqlHandler sqlHandler = new SqlHandler();
    private Task editedTask;
    MainWindowController rootController;
    private int editedTaskId;

    Collection<String> statusSelectorList = new ArrayList<>();
    Collection<String> prioritySelectorList = new ArrayList<>();
    ObservableList<String> statusSelector = FXCollections.observableArrayList();
    ObservableList<String> prioritySelector = FXCollections.observableArrayList();

    {
        Collections.addAll(statusSelectorList, "created", "in-progress", "done");
        Collections.addAll(prioritySelectorList, "high", "medium", "low");
    }

    @FXML
    DatePicker editTaskDate;
    @FXML
    ChoiceBox<String> editTaskStatus, editTaskPriority;
    @FXML
    TextField editTaskDescription;
    @FXML
    Button editTaskButton;

    public void initData(Task task, MainWindowController mainWindowController) {
        this.editedTask = task;
        editTaskDate.setValue(editedTask.getDate().toLocalDate());
        editTaskStatus.setValue(editedTask.getStatus());
        editTaskPriority.setValue(editedTask.getPriority());
        editTaskDescription.setText(editedTask.getDescription());
        this.editedTaskId = task.getTaskId();
        this.rootController=mainWindowController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statusSelector.addAll(statusSelectorList);
        editTaskStatus.getItems().addAll(statusSelector);
        prioritySelector.addAll(prioritySelectorList);
        editTaskPriority.getItems().addAll(prioritySelector);
    }

    @FXML
    public void clickAtEditTaskButton(ActionEvent event) {
        String date = controlsDataReader.getControlValue(editTaskDate);
        String status = controlsDataReader.getControlValue(editTaskStatus);
        String priority = controlsDataReader.getControlValue(editTaskPriority);
        String description = controlsDataReader.getControlValue(editTaskDescription);
        controlsColorHandler.adjustControlsColor(editTaskDate, editTaskStatus, editTaskPriority, editTaskDescription);

        if (controlsDataReader.areAllControlsFilledWithData(date, status, priority, description)) {
            sqlHandler.changeTask(this.editedTaskId, date, status, priority, description);
            rootController.tasks=rootController.sqlHandler.read();
            rootController.refreshTable(rootController.tasks);
            rootController.searchTaskAccordingToFilters();
            Stage stage = (Stage) editTaskButton.getScene().getWindow();
            stage.close();


        } else {
            System.out.println("failure");
        }
    }
}
