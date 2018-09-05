package sample.worksheet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.conectivity.ConnectionManager;
import sample.datamdodel.Employee;
import sample.datamdodel.EmployeeDAO;
import sample.datamdodel.StageManager;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    private ConnectionManager connection = new ConnectionManager();
    private boolean noConnection;


    @FXML
    private Label labelDisplayStatus;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private TextField textFieldLogin;

    private ResultSet result;

    @FXML
    public void onButtonClicked(ActionEvent event) {
        if (this.isLoginValid()) {
            System.out.println("Login success");
            openWorkdayScene(event);
        } else {
            textFieldLogin.clear();
            passwordFieldPassword.clear();
            if (noConnection)
                System.out.println("No connection to the Database");
            else
                labelDisplayStatus.setText("Invalid username or password !");
        }
    }

    private boolean isLoginValid() {
        try (PreparedStatement loginQuery = ConnectionManager.dbConnect().prepareStatement("SELECT idEmployee FROM employee WHERE Login=? AND Password=?")) {
            loginQuery.setString(1, textFieldLogin.getText());
            loginQuery.setString(2, passwordFieldPassword.getText());
            result = loginQuery.executeQuery();
            if(result.next()) {
                Employee.loggedEmployee = EmployeeDAO.searchEmployee(result.getInt("idEmployee"));
                return true;
            }
            } catch (SQLException e) {
                this.noConnection = true;
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally{
            try{result.close();}catch(SQLException e){}
        }
        return false;
    }


    private void openWorkdayScene(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("Workday.fxml"));
            Parent root = loader.load();

            Stage stageWorkday = new Stage();
            StageManager.stages.add(stageWorkday);
            stageWorkday.setScene(new Scene(root));
            stageWorkday.setResizable(false);
            stageWorkday.show();

            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}