package sample.worksheet;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.datamdodel.Actions;
import sample.datamdodel.StageManager;
import sample.datamdodel.Task;
import sample.datamdodel.TaskDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManageTasksController implements Initializable {

    private static int selection;
    @FXML
    private TextField textBoxName;
    @FXML
    private TextArea textAreaDescription;
    @FXML
    private TextArea textAreaShowDescription;
    @FXML
    private TableView<Task> tableViewTask;
    @FXML
    private TableColumn<Task, Integer> columnID;
    @FXML
    private TableColumn<Task, String> columnName;
    @FXML
    private TableColumn<Task, String> columnDescirption;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnID.setCellValueFactory(new PropertyValueFactory<>("idTask"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        columnDescirption.setCellValueFactory(new PropertyValueFactory<>("Descirption"));
        try {
            loadTasks();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addTask() {
        if (validateFields()) {
            try {
                Task newTask = new Task(textBoxName.getText(), textAreaDescription.getText());
                if (ifExist(newTask)) {
                    TaskDAO.insertTask(newTask.getName(), newTask.getDescirption());
                    System.out.println("Keyword został dodadny pomyślnie");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void openWorkdayScene(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Workday.fxml"));
            Parent root = loader.load();

            Stage stageWorkday = new Stage();
            stageWorkday.setScene(new Scene(root));
            StageManager.closeStages(stageWorkday);
            stageWorkday.setResizable(false);
            stageWorkday.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openEditTask() {
        Task toEditTask = tableViewTask.getSelectionModel().getSelectedItem();
        selection = tableViewTask.getSelectionModel().getSelectedIndex();
        if (Actions.validateSelections(selection)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditTask.fxml"));
                Parent root = loader.load();

                EditTaskController editTaskController = loader.getController();
                editTaskController.initializeToEdit(toEditTask);

                Stage editStage = new Stage();
                StageManager.stages.add(editStage);
                editStage.setScene(new Scene(root));
                editStage.setResizable(false);
                editStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void loadTasks() throws SQLException, ClassNotFoundException {
        ObservableList<Task> taskData = TaskDAO.searchTasks();
        tableViewTask.setItems(taskData);
    }

    @FXML
    private void deleteTask() {
        selection = tableViewTask.getSelectionModel().getSelectedIndex();
        if (Actions.validateSelections(selection)) {
            int selectedRowID = tableViewTask.getSelectionModel().getSelectedItem().getIdTask();
            try {
                TaskDAO.deleteTaskWithId(selectedRowID);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Task selectedTask = tableViewTask.getSelectionModel().getSelectedItem();
            tableViewTask.getItems().remove(selectedTask);
        }

    }

    private boolean validateFields() {
        if (textBoxName.getText().isEmpty()) {
            Actions.showAlert("Please fill the required data to create new Task");
            return false;
        }
        return true;
    }

    @FXML
    private void showDescription() {
        selection = tableViewTask.getSelectionModel().getSelectedIndex();
        if (Actions.validateSelections(selection)) {
            Task description = tableViewTask.getSelectionModel().getSelectedItem();
            textAreaShowDescription.setText(description.getDescirption());
        }
    }

    private boolean ifExist(Task task) {
        for (Task name : tableViewTask.getItems())
            if (name.getName().equals(task.getName())) {
                Actions.showAlert(task.getName() + ", already exist");
                textBoxName.clear();
                textAreaDescription.clear();
                return false;
            }
        return true;
    }

}
