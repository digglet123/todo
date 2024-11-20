package com.example.todo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "todos")
public class TodoTask {

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getCompletedStatus() {
        return completed;
    }

    public void setCompletedStatus(boolean completed) {
        this.completed = completed;
    }

    @Id
    private String id;
    private String description;
    private boolean completed = false;
}