package sample.worksheet;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.conectivity.ConnectionManager;
import sample.datamdodel.Employee;
import sample.datamdodel.EmployeeDAO;

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

    private Employee loggedEmployee;
    private ResultSet result;

    @FXML
    public void onButtonClicked(ActionEvent event) throws SQLException{
        if (this.isLoginValid()) {
            System.out.println("Login success");
            openWorkdayScene(event, loggedEmployee);
        } else {
            textFieldLogin.clear();
            passwordFieldPassword.clear();
            if (noConnection)
                System.out.println("No connection to the Database");
            else
                labelDisplayStatus.setText("Invalid username or password !");
        }
    }

    private boolean isLoginValid() throws SQLException {
        try (PreparedStatement loginQuery = connection.dbConnect().prepareStatement("SELECT idEmployee FROM employee WHERE Login=? AND Password=?")) {
            loginQuery.setString(1, textFieldLogin.getText());
            loginQuery.setString(2, passwordFieldPassword.getText());
            result = loginQuery.executeQuery();
            if(result.next()) {
                loggedEmployee = EmployeeDAO.searchEmployee(result.getInt("idEmployee"));
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


    private void openWorkdayScene(ActionEvent event, Employee loggedEmployee){
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("Workday.fxml"));
            Parent root = loader.load();

            WorkdayController workdayController =loader.getController();
            workdayController.initializeLoggedEmployeeData(loggedEmployee);

            Stage stage=new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}