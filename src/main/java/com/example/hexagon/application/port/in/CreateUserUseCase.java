package com.example.hexagon.application.port.in;

import com.example.hexagon.domain.User;

public interface CreateUserUseCase {
    User createUser(User user);
}
