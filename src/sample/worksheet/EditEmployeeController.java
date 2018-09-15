package sample.worksheet;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import sample.datamdodel.Access;
import sample.datamdodel.Employee;
import sample.datamdodel.EmployeeDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class EditEmployeeController implements Initializable {

    @FXML
    private TextField textBoxName;

    @FXML
    private TextField textBoxSurname;

    @FXML
    private ChoiceBox<Access> choiceBoxAccountType;

    private Employee toEditEmployee;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBoxAccountType.setItems(FXCollections.observableArrayList(Access.values()));
    }

    @FXML
    private void updateEmployee(ActionEvent event) {
        if (validateFields()) {
            EmployeeDAO.updateEmployee(toEditEmployee.getIdEmployee(), textBoxName.getText(),
                    textBoxSurname.getText(), choiceBoxAccountType.getSelectionModel().getSelectedIndex() + 1);
            backToManageEmployees(event);
        }
    }

    private boolean validateFields() {
        if (textBoxName.getText().isEmpty() | textBoxSurname.getText().isEmpty() | choiceBoxAccountType.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please fill the required data to update Employee");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    private void backToManageEmployees(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void initializeToEdit(Employee toEditEmployee) {
        this.toEditEmployee = toEditEmployee;
        textBoxName.setText(toEditEmployee.getName());
        textBoxSurname.setText(toEditEmployee.getSurname());

        //To prawdopodobnie rozwiązuję Twój problem przy tworzeniu klasy "Account" i operacji dla niej
        //Do rozwiazania zostaje kwestia  private TableColumn<Employee, Integer> columnID; itp.
        Access admin = Access.valueOf(toEditEmployee.getAccess());
        choiceBoxAccountType.getSelectionModel().select(admin);
    }
}
