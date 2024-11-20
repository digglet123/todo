package com.example.todo.controller;

import com.example.todo.model.TodoTask;
import com.example.todo.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TodoService todoService;

    @Test
    public void testGetAllTodoTasks() {
        TodoTask task1 = new TodoTask("1", "Task 1", false);

        TodoTask task2 = new TodoTask("2", "Task 2", false);

        when(todoService.getAllTodoTasks()).thenReturn(Flux.just(task1, task2));

        webTestClient.get().uri("/todos")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TodoTask.class).hasSize(2);
    }

    @Test
    public void testGetTodoTasksById() {
        TodoTask task = new TodoTask("1", "Task 1", false);

        when(todoService.getTodoTaskById(anyString())).thenReturn(Mono.just(task));

        webTestClient.get().uri("/todos/{id}", "1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TodoTask.class);
    }

    @Test
    public void testSaveTodoTask() {
        TodoTask task = new TodoTask("1", "Task 1", false);

        when(todoService.saveTodoTask(any(TodoTask.class))).thenReturn(Mono.just(task));

        webTestClient.post().uri("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(task)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TodoTask.class);
    }

    @Test
    public void testSetTodoTaskCompleted() {
        TodoTask task = new TodoTask("1", "Task 1", true);

        when(todoService.setTodoTaskCompleted(anyString())).thenReturn(Mono.just(task));

        webTestClient.put().uri("/todos/{id}/complete", "1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TodoTask.class);
    }

    @Test
    public void testDeleteTodoTask() {
        when(todoService.deleteTodoTask(anyString())).thenReturn(Mono.empty());

        webTestClient.delete().uri("/todos/{id}", "1")
                .exchange()
                .expectStatus().isOk();
    }
}