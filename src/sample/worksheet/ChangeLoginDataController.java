package sample.worksheet;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import sample.datamdodel.Actions;
import sample.datamdodel.Employee;
import sample.datamdodel.EmployeeDAO;

public class ChangeLoginDataController {
    @FXML
    private JFXTextField textFieldNewLogin;

    @FXML
    private JFXPasswordField passwordFieldOldPassword;

    @FXML
    private JFXPasswordField passwordFieldNewPassword;

    @FXML
    private JFXPasswordField passwordFieldRepeat;

    @FXML
    private void change(){
        if (validateOldPassword()&& validateNewPasswords()) {
            if (isNewLogin()) {
                EmployeeDAO.updateEmployeePasswordAndLogin(textFieldNewLogin.getText(), passwordFieldNewPassword.getText());
                Actions.showInfo("Successfully changed to a new password and login");
            }else{
                EmployeeDAO.updateEmployeePassword(passwordFieldNewPassword.getText());
                Actions.showInfo("Successfully changed to a new password");
            }
        }
        else{
            if (isNewLogin()){
                EmployeeDAO.updateEmployeeLogin(textFieldNewLogin.getText());
                Actions.showInfo("Successfully changed to a new login");
            }
        }
    }

    private boolean validateOldPassword(){
       if (!passwordFieldOldPassword.getText().isEmpty()) {
           if (passwordFieldOldPassword.getText().equals(Employee.loggedEmployee.getPassword())) {
               return true;
           }
           else{
               Actions.showAlert("Old password don't match");
               return false;
           }
       }
       Actions.showAlert("Password field cannot be empty");
       return false;
    }

    private boolean validateNewPasswords(){
        if (!passwordFieldNewPassword.getText().isEmpty()&&!passwordFieldRepeat.getText().isEmpty()) {
            if (passwordFieldNewPassword.getText().equals(passwordFieldRepeat.getText())) {
                return true;
            }
            else{
                Actions.showAlert("Password field cannot be empty");
            }
        }
        Actions.showAlert("New passwords don't match");
        return false;
    }

    private boolean isNewLogin(){
        if (!textFieldNewLogin.getText().isEmpty())
            return true;
        return false;
    }
}
