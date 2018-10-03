package sample.worksheet;

import com.jfoenix.controls.JFXDatePicker;
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
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import sample.datamdodel.Employee;
import sample.datamdodel.EmployeeDAO;
import sample.datamdodel.Event;
import sample.datamdodel.EventDAO;

import java.io.IOException;
import java.net.URL;
import java.security.Timestamp;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReportDailyController implements Initializable {


    @FXML
    private TreeTableView<Event> treeTableViewReport;

    @FXML
    private TreeTableColumn<Event, String> columnEmployee;

    @FXML
    private TreeTableColumn<Event, String> columnTask;

    @FXML
    private TreeTableColumn<Event, Timestamp> columnStartDate;

    @FXML
    private TreeTableColumn<Event, Timestamp> columnEndDate;

    @FXML
    private TreeTableColumn<Event, Integer> columnMinutes;


    @FXML
    private TableView<Employee> tableViewEmployeeToPick;

    @FXML
    private TableColumn<Employee, String> columnName;

    @FXML
    private TableColumn<Employee, String> columnSurname;

    @FXML
    private TableColumn<Employee, String> columnIdEmployee;

    @FXML
    private JFXDatePicker datePickedPickDateForReport;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnIdEmployee.setCellValueFactory(new PropertyValueFactory<>("Employee"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        columnSurname.setCellValueFactory(new PropertyValueFactory<>("Surname"));

        columnEmployee.setCellValueFactory(new TreeItemPropertyValueFactory<>("fullName"));
        columnTask.setCellValueFactory(new TreeItemPropertyValueFactory<>("task"));
        columnStartDate.setCellValueFactory(new TreeItemPropertyValueFactory<>("startDate"));
        columnEndDate.setCellValueFactory(new TreeItemPropertyValueFactory<>("endDate"));
        columnMinutes.setCellValueFactory(new TreeItemPropertyValueFactory<>("time"));

        tableViewEmployeeToPick.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableViewEmployeeToPick.getSelectionModel().setCellSelectionEnabled(true);

        datePickedPickDateForReport.setValue(LocalDate.now());

        try {
            loadEmployees();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadEmployees() throws SQLException, ClassNotFoundException {
        try {
            ObservableList<Employee> empData = EmployeeDAO.searchEmployeesNameSurnameId();
            tableViewEmployeeToPick.setItems(empData);
        } catch (SQLException e){
            System.out.println("Error occurred while getting employees information from DB" + e);
            throw e;
        }
    }

    @FXML
    public  void openWorkdayScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Workday.fxml"));
            Parent root = loader.load();

            Stage stageWorkday = new Stage();
            stageWorkday.setScene(new Scene(root));
            stageWorkday.setResizable(false);
            stageWorkday.show();

            WorkdayController workdayController = loader.getController();
            stageWorkday.setOnCloseRequest(eventClose -> workdayController.send());

            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void reportForOnePickedEmployee(){

        TreeItem<Event> itemRoot = new TreeItem<>();
        ObservableList<Employee> selectedEmployees = tableViewEmployeeToPick.getSelectionModel().getSelectedItems();

        for (Employee selectedEmployee: selectedEmployees) { ;
            Event eventsFromEmployee = new Event(selectedEmployee);
            TreeItem<Event> itemBranch = new TreeItem<>(eventsFromEmployee);

            ArrayList<TreeItem<Event>> eventsToInsert = new ArrayList<>();
            ObservableList<Event> employeeEvents = null;

            try {
                employeeEvents = EventDAO.singleReport(selectedEmployee, datePickedPickDateForReport.getValue());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            for (Event eventToInsert: employeeEvents) {
                TreeItem<Event> events = new TreeItem<>(eventToInsert);
                eventsToInsert.add(events);
            }

            itemBranch.getChildren().setAll(eventsToInsert);
            itemRoot.getChildren().add(itemBranch);
        }

        treeTableViewReport.setRoot(itemRoot);
        treeTableViewReport.setShowRoot(false);
    }

    @FXML
    public void reportForAll(){
        ObservableList<Employee> employeesEvents = tableViewEmployeeToPick.getItems();
        TreeItem<Event> itemRoot = new TreeItem<>();
        for (Employee employeeToInsert: employeesEvents) {
            Event eventsFromEmployee = new Event(employeeToInsert);

            ArrayList<TreeItem<Event>> eventsToInsert = new ArrayList<>();
            TreeItem<Event>itemBranch = new TreeItem<>(eventsFromEmployee);
            ObservableList<Event> employeeEvents = null;

            try {
                employeeEvents = EventDAO.singleReport(employeeToInsert,datePickedPickDateForReport.getValue());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            for (Event employeeEventsToInsert : employeeEvents) {
                TreeItem<Event> events = new TreeItem<>(employeeEventsToInsert);
                eventsToInsert.add(events);
            }

            itemBranch.getChildren().setAll(eventsToInsert);
            itemRoot.getChildren().add(itemBranch);
        }
        treeTableViewReport.setRoot(itemRoot);
        treeTableViewReport.setShowRoot(false);
    }

}
