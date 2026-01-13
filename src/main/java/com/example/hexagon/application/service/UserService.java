package com.example.hexagon.application.service;

import com.example.hexagon.application.port.in.CreateUserUseCase;
import com.example.hexagon.application.port.in.GetUserQuery;
import com.example.hexagon.application.port.out.UserRepositoryPort;
import com.example.hexagon.domain.User;
import com.example.hexagon.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements CreateUserUseCase, GetUserQuery {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public User createUser(User user) {
        return userRepositoryPort.save(user);
    }

    @Override
    public User getUser(Long id) {
        return userRepositoryPort.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
