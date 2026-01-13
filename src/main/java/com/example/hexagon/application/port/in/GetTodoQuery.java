package com.example.hexagon.application.port.in;

import com.example.hexagon.domain.Todo;
import java.util.List;
import java.util.Optional;

public interface GetTodoQuery {
    List<Todo> getTodos();

    Todo getTodo(Long id);
}
