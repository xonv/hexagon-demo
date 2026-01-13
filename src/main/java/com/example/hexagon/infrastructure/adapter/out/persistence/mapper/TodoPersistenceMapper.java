package com.example.hexagon.infrastructure.adapter.out.persistence.mapper;

import com.example.hexagon.domain.Todo;
import com.example.hexagon.infrastructure.adapter.out.persistence.TodoJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoPersistenceMapper {
    @org.mapstruct.Mapping(source = "userId", target = "user.id")
    TodoJpaEntity toEntity(Todo todo);

    @org.mapstruct.Mapping(source = "user.id", target = "userId")
    Todo toDomain(TodoJpaEntity entity);
}
