package com.example.todo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "todos")
public class TodoTask {

    public TodoTask(String id, String description, boolean completed) {
        this.id = id;
        this.description = description;
        this.completed = completed;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Id
    private String id;
    private String description;
    private boolean completed = false;
}
