package com.example.hexagon.application.port.in;

import com.example.hexagon.domain.Todo;

public interface UpdateTodoUseCase {
    Todo updateTodo(Long id, String title, boolean completed);
}
