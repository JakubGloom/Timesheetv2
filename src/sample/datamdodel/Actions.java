package sample.datamdodel;

import javafx.scene.control.Alert;

import java.time.format.DateTimeFormatter;

public class Actions {
    public static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH/mm");
    public static void showAlert(String massage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(massage);
        alert.showAndWait();
    }

    public static boolean validateSelections(int selectedItems) {
        if (selectedItems < 0) {
            Actions.showAlert("No selection was made");
            return false;
        }
        return true;
    }
}
