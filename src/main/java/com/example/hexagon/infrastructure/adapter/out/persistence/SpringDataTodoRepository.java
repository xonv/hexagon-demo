package com.example.hexagon.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTodoRepository extends JpaRepository<TodoJpaEntity, Long> {
}
