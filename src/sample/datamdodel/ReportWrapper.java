package sample.datamdodel;

import java.sql.Timestamp;

public class ReportWrapper {
    private Employee employee;
    private Event event;

    public ReportWrapper(Employee employee, Event event) {
        this.employee = employee;
        this.event = event;
    }

    public ReportWrapper(Event event) {
        this.event = event;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Timestamp getEndDate(){
       return event.getEndDate();
    }

    public Timestamp getStartDate(){
        return event.getStartDate();
    }

    public Task getTask(){
        return event.getTask();
    }

    public String getFullName(){
        return employee.getFullName();
    }

    public int getTime() { return event.getTime();
    }
}
