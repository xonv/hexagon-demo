package com.example.hexagon.application.port.in;

import com.example.hexagon.domain.Todo;

public interface CreateTodoUseCase {
    Todo createTodo(Todo todo);
}
