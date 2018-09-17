package sample.datamdodel;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Actions {

    public static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH/mm");

    public static void showAlert(String massage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(massage);
        alert.showAndWait();
    }

    public static void showInfo(String massage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(massage);
        alert.showAndWait();
    }

    public static void warning(){
        // lepszym pomysłem jest utworzenie zwyklego stage'a
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setResizable(false);
        alert.setContentText("Closing applications will cause loss " +
                "to any unsaved data, do you wanna proceed?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            Platform.exit();
        }
        else{
            alert.close();
        }
    }

    public static boolean validateSelections(int selectedItems) {
        if (selectedItems < 0) {
            Actions.showAlert("No selection was made");
            return false;
        }
        return true;
    }
}
