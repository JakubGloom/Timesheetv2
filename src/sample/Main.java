package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("worksheet/Login.fxml"));
        primaryStage.setTitle("login");
        primaryStage.setScene(new Scene(root, 350, 150));
        primaryStage.resizableProperty().setValue(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
