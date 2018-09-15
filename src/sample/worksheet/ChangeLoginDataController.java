package sample.worksheet;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import sample.datamdodel.Actions;
import sample.datamdodel.Employee;

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
        if (validateOldPassword()){
            if (validateNewPasswords()){
                if (isNewLogin()){

                }
                else{

                }
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
        if (passwordFieldNewPassword.getText().equals(passwordFieldRepeat.getText())) {
            return true;
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
