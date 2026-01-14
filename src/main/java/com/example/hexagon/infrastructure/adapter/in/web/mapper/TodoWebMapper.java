package com.example.hexagon.infrastructure.adapter.in.web.mapper;

import com.example.hexagon.domain.Todo;
import com.example.hexagon.infrastructure.adapter.in.web.CreateTodoRequest;
import com.example.hexagon.infrastructure.adapter.in.web.TodoResponse;
import com.example.hexagon.infrastructure.adapter.in.web.UpdateTodoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TodoWebMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "completed", constant = "false")
    Todo toDomain(CreateTodoRequest request);

    @Mapping(target = "id", ignore = true) // ID is passed separately in update
    @Mapping(target = "owner", ignore = true) // Owner cannot be changed via update
    Todo toDomain(UpdateTodoRequest request);

    @Mapping(target = "ownerUsername", source = "owner.username")
    @Mapping(target = "ownerEmail", source = "owner.email")
    TodoResponse toResponse(Todo todo);
}
