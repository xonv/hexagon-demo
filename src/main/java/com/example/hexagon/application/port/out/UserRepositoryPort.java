package com.example.hexagon.application.port.out;

import com.example.hexagon.domain.User;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);

    Optional<User> findById(Long id);

    boolean existsById(Long id);
}
