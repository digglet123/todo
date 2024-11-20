package com.example.todo.controller;

import com.example.todo.model.TodoTask;
import com.example.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<TodoTask> getAllTodoTasks() {
        return todoService.getAllTodoTasks();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TodoTask> getTodoTasksById(@PathVariable String id) {
        return todoService.getTodoTaskById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TodoTask> saveTodoTask(@RequestBody TodoTask todoTask) {
        return todoService.saveTodoTask(todoTask);
    }

    @PutMapping(value = "/{id}/complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TodoTask> setTodoTaskCompleted(@PathVariable String id) {
        return todoService.setTodoTaskCompleted(id);
    }

    @DeleteMapping(value = "/{id}")
    public Mono<Void> deleteTodoTask(@PathVariable String id) {
        return todoService.deleteTodoTask(id);
    }
}
