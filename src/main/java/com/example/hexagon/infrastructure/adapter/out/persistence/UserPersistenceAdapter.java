package com.example.hexagon.infrastructure.adapter.out.persistence;

import com.example.hexagon.application.port.out.UserRepositoryPort;
import com.example.hexagon.domain.User;
import com.example.hexagon.infrastructure.adapter.out.persistence.mapper.UserPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepositoryPort {

    private final SpringDataUserRepository userRepository;
    private final UserPersistenceMapper userPersistenceMapper;

    @Override
    public User save(User user) {
        UserJpaEntity entity = userPersistenceMapper.toEntity(user);
        UserJpaEntity savedEntity = userRepository.save(entity);
        return userPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id)
                .map(userPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
