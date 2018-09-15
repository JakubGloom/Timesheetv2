package sample.worksheet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.datamdodel.Task;
import sample.datamdodel.TaskDAO;

public class EditTaskController {

    @FXML
    private TextField textBoxName;

    @FXML
    private TextArea textAreaDescription;

    private Task toEditTask;

    @FXML
    private void updateTask(ActionEvent event) {
        if (validateFields()) {
            TaskDAO.updateTask(toEditTask.getIdTask(), textBoxName.getText(), textAreaDescription.getText());
            backToManageTasks(event);
        }
    }

    private boolean validateFields() {
        if (textBoxName.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please fill the required data to update Task");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    private void backToManageTasks(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void initializeToEdit(Task toEditTask) {
        this.toEditTask = toEditTask;
        textBoxName.setText(toEditTask.getName());
    }


}
