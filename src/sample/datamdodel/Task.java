package sample.datamdodel;

public class Task {
    private int idTask;
    private String name;
    private String description;

    public Task(int idTask, String name, String description) {
        this.idTask = idTask;
        this.name = name;
        this.description = description;
    }

    public Task(String name) {
        this.name = name;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return getName();
    }
}
