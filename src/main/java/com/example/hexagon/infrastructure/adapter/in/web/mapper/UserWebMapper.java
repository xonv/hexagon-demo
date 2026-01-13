package com.example.hexagon.infrastructure.adapter.in.web.mapper;

import com.example.hexagon.domain.User;
import com.example.hexagon.infrastructure.adapter.in.web.CreateUserRequest;
import com.example.hexagon.infrastructure.adapter.in.web.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserWebMapper {
    @Mapping(target = "id", ignore = true)
    User toDomain(CreateUserRequest request);

    UserResponse toResponse(User user);
}
