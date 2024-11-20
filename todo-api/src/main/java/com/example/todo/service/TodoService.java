package com.example.todo.service;

import com.example.todo.model.TodoTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public Flux<TodoTask> getAllTodoTasks() {
        return todoRepository.findAll();
    }

    public Mono<TodoTask> getTodoTaskById(String id) {
        return todoRepository.findById(id);
    }

    public Mono<TodoTask> saveTodoTask(TodoTask todoTask) {
        return todoRepository.save(todoTask);
    }

    public Mono<Void> deleteTodoTask(String id) {
        return todoRepository.deleteById(id);
    }

    public Mono<TodoTask> setTodoTaskCompleted(String id) {
        return todoRepository.findById(id)
                .map(todoTask -> {
                    todoTask.setCompletedStatus(true);
                    return todoTask;
                })
                .flatMap(todoRepository::save);
    }

}
