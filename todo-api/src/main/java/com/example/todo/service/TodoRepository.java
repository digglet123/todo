package com.example.todo.service;

import com.example.todo.model.TodoTask;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TodoRepository extends ReactiveMongoRepository<TodoTask, String> {
}
