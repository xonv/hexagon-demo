package com.example.hexagon.application.port.in;

import com.example.hexagon.domain.User;

public interface GetUserQuery {
    User getUser(Long id);
}
