package sample.datamdodel;

import javafx.stage.Stage;

import java.util.ArrayList;

public class StageManager {

    public static ArrayList<Stage> stages = new ArrayList(); //początkowe rowiązanie problemu kaskadowego zamykania okien

    public static void closeStages(Stage stage){

        int indexOfAStageToClose = stages.indexOf(stage);
        for (int i = indexOfAStageToClose+1; i<stages.size(); i++){
            stages.get(i).close();
            stages.remove(i);
        }
    }

}
