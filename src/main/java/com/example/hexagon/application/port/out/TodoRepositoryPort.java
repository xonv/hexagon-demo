package com.example.hexagon.application.port.out;

import com.example.hexagon.domain.Todo;
import java.util.List;

public interface TodoRepositoryPort {
    Todo save(Todo todo);

    List<Todo> findAll();

    java.util.Optional<Todo> findById(Long id);
}
