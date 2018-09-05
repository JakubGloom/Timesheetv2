package sample.datamdodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.conectivity.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskDAO {


    public static ObservableList<Task> searchTasks() throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM task";

        try {
            ResultSet rsTasks = ConnectionManager.dbExecuteQuery(selectStmt);

            ObservableList<Task> taskList = getTaskList(rsTasks);
            return taskList;

        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }

    private static ObservableList<Task> getTaskList(ResultSet rs) throws SQLException {

        ObservableList<Task> taskList = FXCollections.observableArrayList();

        while (rs.next()) {
            Task task = new Task(rs.getInt("idTask"), rs.getString("Name"), rs.getString("Descirption"));

            taskList.add(task);
        }
        return taskList;
    }

    public static void insertTask(String name, String description) throws SQLException, ClassNotFoundException {

        String insertTask = "INSERT INTO `databasetests`.`task` (`Name`, `Descirption`)VALUES" + "('" + name + "','" + description + "' )";
        try {
            ConnectionManager.dbExecuteUpdate(insertTask);
        } catch (SQLException e) {
            System.out.print("Error occurred while insert Operation: " + e);
            throw e;
        }
    }

    public static void deleteTaskWithId(int idTask) throws SQLException, ClassNotFoundException {
        String employeeStmt = "DELETE FROM task WHERE idTask= " + idTask;

        try {
            ConnectionManager.dbExecuteUpdate(employeeStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    public static void updateTask(int idTaskToEdit, String name, String description) {
        {
            String updateStmt = "UPDATE task set task.Name = '" + name + "'" +
                    " , task.Descirption ='" + description + "'" +
                    "where task.idTask =" + idTaskToEdit;
            try {
                ConnectionManager.dbExecuteUpdate(updateStmt);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
