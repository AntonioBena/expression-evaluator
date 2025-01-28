package com.expression.evaluator.repository;

import com.expression.evaluator.model.entity.ExpressionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpressionRepository extends JpaRepository<ExpressionEntity, Integer> {
    boolean existsByName(String name);
    Optional<ExpressionEntity> findByUuid(String uuid);
}