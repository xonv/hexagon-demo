package com.example.hexagon.infrastructure.adapter.out.persistence;

import com.example.hexagon.application.port.out.TodoRepositoryPort;
import com.example.hexagon.domain.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TodoPersistenceAdapter implements TodoRepositoryPort {

    private final SpringDataTodoRepository todoRepository;

    @Override
    public Todo save(Todo todo) {
        TodoJpaEntity entity = mapToEntity(todo);
        TodoJpaEntity savedEntity = todoRepository.save(entity);
        return mapToDomain(savedEntity);
    }

    @Override
    public List<Todo> findAll() {
        return todoRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public java.util.Optional<Todo> findById(Long id) {
        return todoRepository.findById(id)
                .map(this::mapToDomain);
    }

    private TodoJpaEntity mapToEntity(Todo todo) {
        return TodoJpaEntity.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .completed(todo.isCompleted())
                .build();
    }

    private Todo mapToDomain(TodoJpaEntity entity) {
        return Todo.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .completed(entity.isCompleted())
                .build();
    }
}
