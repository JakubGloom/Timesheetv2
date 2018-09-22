package sample.datamdodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import sample.conectivity.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EventDAO{
    
    
    
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

    private static ObservableList<Event> getEventList(ResultSet rs) throws SQLException {
        ObservableList<Event> eventList = FXCollections.observableArrayList();

        while (rs.next()) {
            Event event = new Event(rs.getInt("idEvent"), rs.getTimestamp("Start"), rs.getTimestamp("End"),
                    rs.getInt("Time"), new Task(rs.getString("Name")));
            eventList.add(event);
        }
        return eventList;
    }

    public static void checkCurrentEvents(TableView<Event> tableView) {
        int count = 0;
        for (int i = 0; i < tableView.getItems().size(); i++) {
            if (tableView.getItems().get(i).getIdEvent()==0)
                count++;
        }
        if(count==1)
            Actions.showAlert("You have 1 event to send");
        if (count > 1)
            Actions.showAlert("You have: " + count + " events to send");
        if (count==0){
            Actions.showInfo("Every event have been send");
        }
    }
    
    public static void sendEvents(ObservableList<Event> toSend){
        for (Event toSendEvent: toSend) {
            if (ifContains(toSendEvent.getIdEvent())) {
                try {
                    insertEvent(toSendEvent);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean ifContains(int id){
        String checkStmt = "SELECT * FROM event WHERE idEvent=" + id;
        System.out.println(id);
        try {
            ResultSet rsEvent = ConnectionManager.dbExecuteQuery(checkStmt);
            if (rsEvent.next()){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static ObservableList<Event> singleReport(Employee selectedEmployee) throws SQLException, ClassNotFoundException {

        String reportStmt = "SELECT idEvent, task.name, Start, End, Time FROM event " +
        "JOIN task ON event.idTask=task.idTask WHERE idEmployee=" + selectedEmployee.getIdEmployee() +
                " AND " + "Start>=" + "'" + LocalDate.now() + " 00:00:00' "
                + "AND " + "End<=" + "'" + LocalDate.now() + " 23:59:59'";
        try {
            ResultSet rsEvent = ConnectionManager.dbExecuteQuery(reportStmt);

            ObservableList<Event> eventList = getEventList(rsEvent);
            return eventList;

        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        } catch (ClassNotFoundException e){
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
}
