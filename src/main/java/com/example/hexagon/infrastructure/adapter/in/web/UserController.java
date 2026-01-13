package com.example.hexagon.infrastructure.adapter.in.web;

import com.example.hexagon.application.port.in.CreateUserUseCase;
import com.example.hexagon.application.port.in.GetUserQuery;
import com.example.hexagon.domain.User;
import com.example.hexagon.infrastructure.adapter.in.web.mapper.UserWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final CreateUserUseCase createUserUseCase;
    private final GetUserQuery getUserQuery;
    private final UserWebMapper userWebMapper;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        User user = userWebMapper.toDomain(request);
        User createdUser = createUserUseCase.createUser(user);
        return ResponseEntity.ok(userWebMapper.toResponse(createdUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        User user = getUserQuery.getUser(id);
        return ResponseEntity.ok(userWebMapper.toResponse(user));
    }
}
