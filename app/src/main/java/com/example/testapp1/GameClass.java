package com.example.testapp1;

import java.util.ArrayList;

public class GameClass {
    private String name;
    private String description;
    public ArrayList<TasksClass> tasks;

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

    public ArrayList<TasksClass> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<TasksClass> tasks) {
        this.tasks = tasks;
    }

    public GameClass(String name, String description) {
        tasks = new ArrayList<>(50);
        this.name = name;
        this.description = description;
    }
    public void AddTask(TasksClass task) {
        tasks.add(task);
    }
    public int getTasksCount() {
        return tasks.size();
    }
}
