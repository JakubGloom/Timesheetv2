package sample.worksheet;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.admin.ManageEmployeesController;
import sample.conectivity.ConnectionManager;
import sample.datamdodel.Employee;
import sample.datamdodel.Event;
import sample.datamdodel.Task;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

    private ObservableList<Event> data = FXCollections.observableArrayList();
    private ConnectionManager myConn;
    private Connection conn;
    private ResultSet rs;

    private Employee loggedEmployee;


    @Override
    public void initialize(URL location, ResourceBundle resources){
        columnName.setCellValueFactory(new PropertyValueFactory<>("task"));
        columnStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        columnEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    }

    private void loadDataFromDatabase(Employee loggedEmployee) throws ClassNotFoundException {
        try {
            rs = ConnectionManager.dbExecuteQuery("SELECT task.name, Start, End, Time FROM event JOIN task ON event.idTask=task.idTask WHERE idEmployee="+loggedEmployee.getIdEmployee());
            while(rs.next()) {
                data.add(new Event(rs.getTimestamp("Start"),rs.getTimestamp("End"),rs.getInt("Time"), new Task(rs.getString("Name"))));
            }
        }catch(SQLException e){
                e.printStackTrace();
        }
        tableEvents.setItems(data);
    }

    @FXML
    private void logout(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("Login.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
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

            ManageEmployeesController manageEmployeesController = fxmlLoader.getController();
            manageEmployeesController.setLoggedEmployee(loggedEmployee);

            Stage stage = new Stage();
            stage.setTitle("Employees");
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setResizable(false);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void refresh(){
        data.clear();
    }

    public void initializeLoggedEmployeeData(Employee loggedEmployee){
        String loggedUser;
        this.loggedEmployee=loggedEmployee;
        try {
            loadDataFromDatabase(this.loggedEmployee);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        loggedUser = "Logged as: "+loggedEmployee.getName() + " " + loggedEmployee.getSurname();
        loggedUserName.setText(loggedUser);
    }

    public void setLoggedEmployee(Employee loggedEmployee) {
        this.loggedEmployee = loggedEmployee;
    }

}
