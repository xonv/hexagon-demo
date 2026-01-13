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
    private final com.example.hexagon.infrastructure.adapter.out.persistence.mapper.TodoPersistenceMapper todoPersistenceMapper;

    @Override
    public Todo save(Todo todo) {
        TodoJpaEntity entity = todoPersistenceMapper.toEntity(todo);
        TodoJpaEntity savedEntity = todoRepository.save(entity);
        return todoPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public List<Todo> findAll() {
        return todoRepository.findAll().stream()
                .map(todoPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public java.util.Optional<Todo> findById(Long id) {
        return todoRepository.findById(id)
                .map(todoPersistenceMapper::toDomain);
    }
}
