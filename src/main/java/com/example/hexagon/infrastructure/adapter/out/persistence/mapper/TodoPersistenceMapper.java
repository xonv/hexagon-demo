package com.example.hexagon.infrastructure.adapter.out.persistence.mapper;

import com.example.hexagon.domain.Todo;
import com.example.hexagon.infrastructure.adapter.out.persistence.TodoJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoPersistenceMapper {
    TodoJpaEntity toEntity(Todo todo);

    Todo toDomain(TodoJpaEntity entity);
}
