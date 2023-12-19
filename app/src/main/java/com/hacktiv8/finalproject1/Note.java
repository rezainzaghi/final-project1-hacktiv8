package com.hacktiv8.finalproject1;

public class Note {
    String TaskName;
    String TaskDescription;

    public Note () {
        super();
    }

    public Note (String taskTitle, String taskDescription) {
        super();
        this.TaskName = taskTitle;
        this.TaskDescription = taskDescription;
    }


    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskTitle) {
        this.TaskName = taskTitle;
    }

    public String getTaskDescription() {
        return TaskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.TaskDescription = taskDescription;
    }
}

