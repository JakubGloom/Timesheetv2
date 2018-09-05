package sample.datamdodel;

public class Task {
    private int idTask;
    private String name;
    private String descirption;

    public Task(String name, String descirption) {
        this.name = name;
        this.descirption = descirption;
    }

    public Task(int idTask, String name, String description) {
        this.idTask = idTask;
        this.name = name;
        this.descirption = description;
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

    public String getDescirption() {
        return descirption;
    }

    public void setDescription(String description) {
        this.descirption = description;
    }


    @Override
    public String toString() {
        return name;
    }
}
