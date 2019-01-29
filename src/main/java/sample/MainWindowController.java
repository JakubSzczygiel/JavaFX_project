package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    static final String EDIT_TASK_WINDOW_PATH="C:\\Users\\Jakub\\IdeaProjects\\JavaFX_z_pom\\src\\main\\resources\\EditTaskWindow.fxml";

    ControlsColorHandler controlsColorHandler = new ControlsColorHandler();
    ControlsDataReader controlsDataReader = new ControlsDataReader();
    ControlsDataWriter controlsDataWriter = new ControlsDataWriter();

    SqlHandler sqlHandler = new SqlHandler();
    Collection<Task> tasks;
    private Collection<Task> filteredTasksGlobalVariable = new ArrayList<>();
    Collection<String> statusSelectorList = new ArrayList<>();
    Collection<String> prioritySelectorList = new ArrayList<>();
    Collection<String> priorityFilterList = new ArrayList<>();
    Collection<String> statusFilterList = new ArrayList<>();
    ObservableList<Task> taskTable = FXCollections.observableArrayList();
    ObservableList<String> statusSelector = FXCollections.observableArrayList();
    ObservableList<String> prioritySelector = FXCollections.observableArrayList();
    ObservableList<String> statusFilter = FXCollections.observableArrayList();
    ObservableList<String> priorityFilter = FXCollections.observableArrayList();


    {
        tasks = sqlHandler.read();
        filteredTasksGlobalVariable.addAll(tasks);
        Collections.addAll(statusSelectorList, "created", "in-progress", "done");
        Collections.addAll(prioritySelectorList, "high", "medium", "low");
        Collections.addAll(statusFilterList, null, "created", "in-progress", "done");
        Collections.addAll(priorityFilterList, null, "high", "medium", "low");
    }

    //////////////////TABLE//////////////////////
    @FXML
    TableView<Task> taskTableView;
    @FXML
    TableColumn<Task, Date> taskTableViewDateColumn;
    @FXML
    TableColumn<Task, String> taskTableViewStatusColumn, taskTableViewPriorityColumn, taskTableViewDescriptionColumn;

    //////////////////ADD TASK//////////////////////
    @FXML
    DatePicker addTaskDate;
    @FXML
    ChoiceBox<String> addTaskStatus, addTaskPriority;
    @FXML
    TextField addTaskDescription;
    @FXML
    Button addTaskButton;

    //////////////////FILTERING//////////////////////
    @FXML
    DatePicker filterDateFrom, filterDateTo;
    @FXML
    TextField filterTaskDescription;
    @FXML
    ChoiceBox<String> filterTaskStatus, filterTaskPriority;
    @FXML
    Button filterApplyButton, filterResetButton, filterDateFromRemoveButton, filterDateToRemoveButton;

    //////////////////MENU//////////////////////
    @FXML
    MenuItem writeToExcelAllData, writeToExcelFilteredData, editSelectedTask, removeSelectedTask;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //////////////////ADD TASK//////////////////////
        statusSelector.addAll(statusSelectorList);
        addTaskStatus.getItems().addAll(statusSelector);
        prioritySelector.addAll(prioritySelectorList);
        addTaskPriority.getItems().addAll(prioritySelector);
        controlsDataWriter.setStartupValues("created", "medium", addTaskDate, addTaskDescription, addTaskPriority, addTaskStatus);


        //////////////////FILTERING//////////////////////
        statusFilter.addAll(statusFilterList);
        filterTaskStatus.getItems().addAll(statusFilter);
        priorityFilter.addAll(priorityFilterList);
        filterTaskPriority.getItems().addAll(priorityFilter);
        controlsDataWriter.resetValues(filterDateFrom, filterDateTo, filterTaskDescription, filterTaskStatus, filterTaskPriority);

        //////////////////TABLE//////////////////////
        taskTableViewDateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        taskTableViewStatusColumn.setCellValueFactory(new PropertyValueFactory<>("Status"));
        taskTableViewPriorityColumn.setCellValueFactory(new PropertyValueFactory<>("Priority"));
        taskTableViewDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        taskTable.addAll(tasks);
        taskTableView.setItems(taskTable);
    }

    @FXML
    public void clickAtRemoveSelectedTask() {
        Task taskToRemove = taskTableView.getSelectionModel().getSelectedItem();
        taskTableView.getItems().removeAll(taskToRemove);
        System.out.println("Task removed: " + taskToRemove);
        tasks.remove(taskToRemove);
        sqlHandler.removeTask(taskToRemove);
    }

    @FXML
    public void clickAtEditSelectedTask(ActionEvent actionEvent) {
        Task taskToEdit = taskTableView.getSelectionModel().getSelectedItem();
        if (taskTableView.getSelectionModel().getSelectedItem() != null) {
            try {
                URL url= Paths.get(EDIT_TASK_WINDOW_PATH).toUri().toURL();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(url);
                Parent tableViewParent = loader.load();
                Scene scene = new Scene(tableViewParent);

                EditTaskWindowController editTaskWindowController = loader.getController();
                editTaskWindowController.initData(taskToEdit,this);

                Stage editTaskStage = new Stage();
                editTaskStage.setTitle("Edit Task");
                editTaskStage.setScene(scene);
                editTaskStage.initModality(Modality.APPLICATION_MODAL);
                editTaskStage.setAlwaysOnTop(true);
                editTaskStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void clickAtWriteToExcelAlData() {
        Excel excel = new Excel();
        excel.write(tasks);
    }

    @FXML
    public void clickAtWriteToExcelFilteredData() {
        Excel excel = new Excel();
        excel.write(filteredTasksGlobalVariable);
    }

    @FXML
    public void clickAtAddTaskButton() {
        String date = controlsDataReader.getControlValue(addTaskDate);
        String status = controlsDataReader.getControlValue(addTaskStatus);
        String priority = controlsDataReader.getControlValue(addTaskPriority);
        String description = controlsDataReader.getControlValue(addTaskDescription);
        controlsColorHandler.adjustControlsColor(addTaskDate, addTaskStatus, addTaskPriority, addTaskDescription);

        if (controlsDataReader.areAllControlsFilledWithData(String.valueOf(date), status, priority, description)) {
            Task task = new Task(Date.valueOf(date), status, priority, description);
            int taskId = sqlHandler.writeOneElement(task);
            task.setTaskId(taskId);
            tasks.add(task);

            controlsDataWriter.setStartupValues("created", "medium", addTaskPriority, addTaskStatus, addTaskDate, addTaskDescription);
            refreshTable(tasks);
            searchTaskAccordingToFilters();

        } else {
            System.out.println("failure");
        }

    }

    @FXML
    public void clearFilterDateFrom() {
        filterDateFrom.setValue(null);
    }

    @FXML
    public void clearFilterDateTo() {
        filterDateTo.setValue(null);
    }

    @FXML
    public void resetFilterControls() {
        controlsDataWriter.resetValues(filterDateFrom, filterDateTo, filterTaskDescription, filterTaskStatus, filterTaskPriority);
        filteredTasksGlobalVariable = tasks;
        refreshTable(tasks);
    }

    public void refreshTable(Collection<Task> refreshedTasks) {
        taskTable.clear();
        taskTable.addAll(refreshedTasks);
        taskTableView.setItems(taskTable);
    }


    public void searchTaskAccordingToFilters() {
        Search search = new Search();
        Collection<Task> filteredTasks = new ArrayList<>();
        filteredTasks.addAll(tasks);
        String dateFrom = controlsDataReader.getControlValue(filterDateFrom);
        String dateTo = controlsDataReader.getControlValue(filterDateTo);
        String status = controlsDataReader.getControlValue(filterTaskStatus);
        String priority = controlsDataReader.getControlValue(filterTaskPriority);
        String description = controlsDataReader.getControlValue(filterTaskDescription);

        if (controlsDataReader.areAllControlsFilledWithData(description)) {
            filteredTasks = search.searchTaskForTaskDescription(filteredTasks, description);
        }
        if (controlsDataReader.areAllControlsFilledWithData(status)) {
            filteredTasks = search.searchTaskForTaskStatus(filteredTasks, status);
        }
        if (controlsDataReader.areAllControlsFilledWithData(priority)) {
            filteredTasks = search.searchTaskForTaskPriority(filteredTasks, priority);
        }
        if (controlsDataReader.areAllControlsFilledWithData(dateFrom, dateTo)) {
            filteredTasks = search.searchTaskForDateRange(filteredTasks, Date.valueOf(dateFrom), Date.valueOf(dateTo));
        } else if (controlsDataReader.areAllControlsFilledWithData(dateFrom) && !(controlsDataReader.areAllControlsFilledWithData(dateTo))) {
            filteredTasks = search.searchTaskAfterCertainDate(filteredTasks, Date.valueOf(dateFrom));
        } else if (controlsDataReader.areAllControlsFilledWithData(dateTo) && !(controlsDataReader.areAllControlsFilledWithData(dateFrom))) {
            filteredTasks = search.searchTaskBeforeCertainDate(filteredTasks, Date.valueOf(dateTo));
            refreshTable(filteredTasks);
        }
        refreshTable(filteredTasks);
        filteredTasksGlobalVariable = filteredTasks;
    }

}
