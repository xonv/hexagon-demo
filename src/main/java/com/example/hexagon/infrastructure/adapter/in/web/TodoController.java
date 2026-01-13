package com.example.hexagon.infrastructure.adapter.in.web;

import com.example.hexagon.application.port.in.CreateTodoUseCase;
import com.example.hexagon.application.port.in.GetTodoQuery;
import com.example.hexagon.domain.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final CreateTodoUseCase createTodoUseCase;
    private final GetTodoQuery getTodoQuery;
    private final com.example.hexagon.application.port.in.UpdateTodoUseCase updateTodoUseCase;

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@RequestBody @jakarta.validation.Valid CreateTodoRequest request) {
        Todo todo = Todo.builder()
                .title(request.getTitle())
                .completed(false)
                .build();

        Todo createdTodo = createTodoUseCase.createTodo(todo);
        return ResponseEntity.ok(mapToResponse(createdTodo));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getTodos() {
        List<TodoResponse> response = getTodoQuery.getTodos().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodo(@PathVariable Long id) {
        Todo todo = getTodoQuery.getTodo(id);
        return ResponseEntity.ok(mapToResponse(todo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable Long id,
            @RequestBody @jakarta.validation.Valid UpdateTodoRequest request) {
        Todo updatedTodo = updateTodoUseCase.updateTodo(id, request.getTitle(), request.isCompleted());
        return ResponseEntity.ok(mapToResponse(updatedTodo));
    }

    private TodoResponse mapToResponse(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .completed(todo.isCompleted())
                .build();
    }
}
