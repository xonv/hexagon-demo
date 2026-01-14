package com.example.hexagon.infrastructure.adapter.out.persistence.mapper;

import com.example.hexagon.domain.Todo;
import com.example.hexagon.infrastructure.adapter.out.persistence.TodoJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { UserPersistenceMapper.class })
public interface TodoPersistenceMapper {
    @org.mapstruct.Mapping(source = "owner", target = "user")
    TodoJpaEntity toEntity(Todo todo);

    @org.mapstruct.Mapping(source = "user", target = "owner")
    Todo toDomain(TodoJpaEntity entity);
}
