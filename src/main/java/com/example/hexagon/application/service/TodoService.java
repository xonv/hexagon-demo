package com.example.hexagon.application.service;

import com.example.hexagon.application.port.in.CreateTodoUseCase;
import com.example.hexagon.application.port.in.GetTodoQuery;
import com.example.hexagon.application.port.out.TodoRepositoryPort;
import com.example.hexagon.domain.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.example.hexagon.application.port.in.UpdateTodoUseCase;
import com.example.hexagon.domain.exception.TodoNotFoundException;

@Service
@RequiredArgsConstructor
public class TodoService implements CreateTodoUseCase, GetTodoQuery, UpdateTodoUseCase {

    private final TodoRepositoryPort todoRepositoryPort;
    private final com.example.hexagon.application.port.out.UserRepositoryPort userRepositoryPort;

    @Override
    public Todo createTodo(Todo todo) {
        if (!userRepositoryPort.existsById(todo.getUserId())) {
            throw new com.example.hexagon.domain.exception.UserNotFoundException(todo.getUserId());
        }
        return todoRepositoryPort.save(todo);
    }

    @Override
    public List<Todo> getTodos() {
        return todoRepositoryPort.findAll();
    }

    @Override
    public Todo getTodo(Long id) {
        return todoRepositoryPort.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
    }

    @Override
    public Todo updateTodo(Long id, String title, boolean completed) {
        Todo existingTodo = getTodo(id);
        existingTodo.setTitle(title);
        existingTodo.setCompleted(completed);
        return todoRepositoryPort.save(existingTodo);
    }
}
