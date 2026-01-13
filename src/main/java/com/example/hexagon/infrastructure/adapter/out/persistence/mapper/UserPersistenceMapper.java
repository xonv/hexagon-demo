package com.example.hexagon.infrastructure.adapter.out.persistence.mapper;

import com.example.hexagon.domain.User;
import com.example.hexagon.infrastructure.adapter.out.persistence.UserJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {
    UserJpaEntity toEntity(User user);

    User toDomain(UserJpaEntity entity);
}
