package sample.admin;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.datamdodel.Access;
import sample.datamdodel.Employee;
import sample.datamdodel.EmployeeDAO;
import javafx.event.ActionEvent;

import java.io.IOException;
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

        try {
            backToManageEmployees(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validateFields() {
        return false;
    }

    @FXML
    private void backToManageEmployees(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void initializeToEdit(Employee toEditEmployee) {
        this.toEditEmployee = toEditEmployee;
        textBoxName.promptTextProperty().setValue(toEditEmployee.getName());
        textBoxSurname.promptTextProperty().setValue(toEditEmployee.getSurname());

        //To prawdopodobnie rozwiązuję Twój problem przy tworzeniu klasy "Account" i operacji dla niej
        //Do rozwiazania zostaje kwestia  private TableColumn<Employee, Integer> columnID; itp.
        Access admin = Access.valueOf(toEditEmployee.getAccess());
        choiceBoxAccountType.getSelectionModel().select(admin);
    }
}
