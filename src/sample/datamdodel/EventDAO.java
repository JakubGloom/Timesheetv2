package sample.datamdodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import sample.conectivity.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EventDAO {

    public static void insertEvent(Event eventToInsert) throws SQLException, ClassNotFoundException {
        String insertEvent = "INSERT INTO `databasetests`.`event` (`Start`, `End`, `Time`, `IsAccepted`, `idEmployee`, `idTask`) " +
                "VALUES ('" + eventToInsert.getStartDate() + "', '" + eventToInsert.getEndDate() + "', '" + eventToInsert.getTime() + "', " +
                "'" + eventToInsert.getIsAccepted() + "', '" + eventToInsert.getIdEmployee() + "', '" + eventToInsert.getTask().getIdTask() + "')";
            try {
                ConnectionManager.dbExecuteUpdate(insertEvent);
            }
            catch (SQLException e){
            System.out.print("Error occurred while INSERT Operation: " + e);
            throw e;
        }

    }

    public static void deleteEvent(int idEvent) throws SQLException, ClassNotFoundException {
        String eventStatement = "DELETE FROM event WHERE idEvent= " + idEvent;
        try {
            ConnectionManager.dbExecuteUpdate(eventStatement);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    public static ObservableList<Event> searchEvents(LocalDate localDate) throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT idEvent, task.name, Start, End, Time FROM event " +
                "JOIN task ON event.idTask=task.idTask WHERE idEmployee=" + Employee.loggedEmployee.getIdEmployee() +
                " AND " + "Start>=" + "'" + localDate + " 00:00:00' "
                + "AND " + "End<=" + "'" + localDate + " 23:59:59'";
        try {
            ResultSet rsTasks = ConnectionManager.dbExecuteQuery(selectStmt);

            ObservableList<Event> eventList = getEventList(rsTasks);
            return eventList;

        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }

    private static ObservableList<Event> getEventList(ResultSet rs) throws SQLException {
        ObservableList<Event> eventList = FXCollections.observableArrayList();

        while (rs.next()) {
            Event event = new Event(rs.getInt("idEvent"), rs.getTimestamp("Start"), rs.getTimestamp("End"),
                    rs.getInt("Time"), new Task(rs.getString("Name")));
            eventList.add(event);
        }
        return eventList;
    }

    public static boolean checkCurrentEvents(TableView<Event> tableView) {
        int count = 0;
        for (int i = 0; i < tableView.getItems().size(); i++) {
            if (tableView.getItems().get(i).getEndDate() == null)
                count++;
        }
        if(count!=0) {
            Actions.showAlert("You have: " + count + "unclosed events");
            return false;
        }
        Actions.showAlert("Every event is closed");
        return true;
    }
}
