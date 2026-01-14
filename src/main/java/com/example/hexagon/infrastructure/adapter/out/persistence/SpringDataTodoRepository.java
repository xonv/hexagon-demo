package com.example.hexagon.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SpringDataTodoRepository extends JpaRepository<TodoJpaEntity, Long> {

    @Override
    @EntityGraph(attributePaths = "user")
    List<TodoJpaEntity> findAll();

    @Override
    @EntityGraph(attributePaths = "user")
    Optional<TodoJpaEntity> findById(Long id);
}
