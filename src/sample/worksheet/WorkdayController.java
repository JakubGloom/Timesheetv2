package sample.worksheet;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.admin.ManageEmployeesController;
import sample.datamdodel.*;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class WorkdayController implements Initializable {
    @FXML
    private Label loggedUserName;

    @FXML
    private Button buttonReport;

    @FXML
    private Button buttonKeywords;

    @FXML
    private Button buttonEmployees;

    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonRemove;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnSend;

    @FXML
    private Button btnCheck;

    @FXML
    private TableView<Event> tableEvents;

    @FXML
    private TableColumn<Event,String> columnName;

    @FXML
    private TableColumn<Event,Timestamp> columnStart;

    @FXML
    private TableColumn<Event,Timestamp> columnEndDate;

    @FXML
    private TableColumn<Event,Integer> columnTime;

    @FXML
    private TableColumn<Event, Integer> columnIDEv;

    @FXML
    private TableView<Task> tableViewTasks;

    @FXML
    private TableColumn<Task, Integer> columnID;

    @FXML
    private TableColumn<Task, String> columnTaskName;

    @FXML
    private TableColumn<Task, String> columnDescirption;

    @FXML
    private JFXDatePicker datePickerStartDate;

    @FXML
    private JFXTimePicker timePickerStartTime;

    @FXML
    private JFXDatePicker datePickerEndDate;

    @FXML
    private JFXTimePicker timePickerEndTime;

    @FXML
    private TextArea textAreaDisplayDescription;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        columnIDEv.setCellValueFactory(new PropertyValueFactory<>("idEvent"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("task"));
        columnStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        columnEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        columnID.setCellValueFactory(new PropertyValueFactory<>("idTask"));
        columnTaskName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        columnDescirption.setCellValueFactory(new PropertyValueFactory<>("Descirption"));

        timePickerStartTime.setIs24HourView(true);
        timePickerStartTime.editableProperty().setValue(false);
        timePickerEndTime.setIs24HourView(true);
        timePickerEndTime.editableProperty().setValue(false);


        loadEventData();
        loadTasks();
        initializeLoggedEmployeeData();

    }

    private void loadEventData() {
        try {
            ObservableList<Event> eventData = EventDAO.searchEvents();
            tableEvents.setItems(eventData);
        }catch(SQLException e){
                e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadTasks() {
        try {
            ObservableList<Task> taskData = TaskDAO.searchTasks();
            tableViewTasks.setItems(taskData);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logout(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("Login.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stageLogin = new Stage();

            stageLogin.setTitle("Login");
            stageLogin.setScene(new Scene(root1));
            stageLogin.setResizable(false);
            stageLogin.show();

            StageManager.stages.add(stageLogin);
            StageManager.closeStages(stageLogin);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openEmployeesWindow(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ManageEmployeesController.class.getResource("../admin/ManageEmployees.fxml"));
            Parent root1 = fxmlLoader.load();

            Stage stageEmployee = new Stage();
            StageManager.stages.add(stageEmployee);

            stageEmployee.setTitle("Employees");
            stageEmployee.setScene(new Scene(root1));
            stageEmployee.show();
            stageEmployee.setResizable(false);
            stageEmployee.setOnCloseRequest(event1 -> StageManager.closeStages(stageEmployee));
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openTasksWindow(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ManageEmployeesController.class.getResource("../admin/ManageTasks.fxml"));
            Parent root1 = fxmlLoader.load();

            Stage stageKeywords = new Stage();
            StageManager.stages.add(stageKeywords);

            stageKeywords.setTitle("Keywords");
            stageKeywords.setScene(new Scene(root1));
            stageKeywords.show();
            stageKeywords.setResizable(false);
            stageKeywords.setOnCloseRequest(event1 -> StageManager.closeStages(stageKeywords));
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void add() {
        LocalTime startTime = timePickerStartTime.getValue();
        LocalDate startDate = datePickerStartDate.getValue();
        String insertStart = startDate.toString() + " " + startTime.toString() + ":" + startTime.getSecond();
        Timestamp start = Timestamp.valueOf(insertStart);

        LocalTime endTime = timePickerEndTime.getValue();
        LocalDate endDate = datePickerEndDate.getValue();
        String insertEnd = endDate.toString() + " " + endTime.toString() + ":" + endTime.getSecond();
        Timestamp end = Timestamp.valueOf(insertEnd);

        int elapsedMinutes = (int) Duration.between(startTime, endTime).toMinutes();

        Task selectedTask = tableViewTasks.getSelectionModel().getSelectedItem();

        Event eventToInsert = new Event(start, end, elapsedMinutes, 0, Employee.loggedEmployee.getIdEmployee(), selectedTask);

        tableEvents.getItems().add(eventToInsert);

        EventDAO.insertEvent(eventToInsert);
    }

    @FXML
    private void showDescription() {
        if (Actions.validateSelections(tableViewTasks.getSelectionModel().getSelectedIndex())) {
            Task description = tableViewTasks.getSelectionModel().getSelectedItem();
            textAreaDisplayDescription.setText(description.getDescirption());
        }
    }

    @FXML
    private void deleteEvent() {
        int idEventToDelete = tableEvents.getSelectionModel().getSelectedItem().getIdEvent();
        try {
            System.out.println(idEventToDelete);
            EventDAO.deleteEvent(idEventToDelete);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Event selectedEvent = tableEvents.getSelectionModel().getSelectedItem();
        tableEvents.getItems().remove(selectedEvent);
    }

    public void initializeLoggedEmployeeData() {
        String loggedUser = "Logged as: " + Employee.loggedEmployee.getName() + " " + Employee.loggedEmployee.getSurname();
        loggedUserName.setText(loggedUser);
    }

}
