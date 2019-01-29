package sample;

import java.sql.Date;

public class Task {

    private String description;
    private Date date;
    //private TaskPriority priority;
    private String priority;
    private String status;
    private int taskId;


    public Task(Date date, String status, String priority, String description) {
        this.description = description;
        this.status = status;
        this.date = date;
        this.priority = priority;
    }

    public Task(Date date, String status, String priority, String description, int taskId) {
        this.description = description;
        this.status = status;
        this.date = date;
        this.priority = priority;
        this.taskId = taskId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public String getPriority() {
        return priority;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }


    public Object[] getObjectArrayFromTaskFields() {
        Object[] objectArrayFromTaskFields = {date, status, priority, description};
        return objectArrayFromTaskFields;
    }

    @Override
    public String toString() {
        return taskId + "|" + date + "|" + priority + "|" + description + "|" + status + "\n";
    }
}
