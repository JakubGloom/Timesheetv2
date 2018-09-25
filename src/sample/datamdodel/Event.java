package sample.datamdodel;

import java.sql.Timestamp;

public class Event{

    private int idEvent;
    private Timestamp startDate;
    private Timestamp endDate;
    private int time;
    private int isAccepted = 0;
    private int idEmployee;
    private int idTask;

    private Task task;
    private Employee employee;

    public Event(int idEvent, Timestamp startDate, Timestamp endDate, int time, Task task) {
        this.idEvent = idEvent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.time = time;
        this.task = task;
    }

    public Event(Timestamp startDate, Timestamp endDate, int time, int isAccepted, int idEmployee, Task task) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.time = time;
        this.isAccepted = isAccepted;
        this.idEmployee = idEmployee;
        this.task = task;
    }

    public Event(Employee employee, int idEvent, Timestamp startDate, Timestamp endDate, int time, Task task){
        this.employee = employee;
        this.idEvent = idEvent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.time = time;
        this.task = task;

    }

    public Event(Employee employee) {
        this.employee = employee;
    }



    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public int getTime() { return time; }

    public void setTime(int time) {
        this.time = time;
    }

    public int getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(int isAccepted) {
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getFullName(){
        return employee.getFullName();
    }

    @Override
    public String toString() {
        return "Event{" +
                "idEvent=" + idEvent +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", time=" + time +
                ", isAccepted=" + isAccepted +
                ", idEmployee=" + idEmployee +
                ", idTask=" + idTask +
                ", task=" + task +
                ", employee=" + employee +
                '}';
    }
}
