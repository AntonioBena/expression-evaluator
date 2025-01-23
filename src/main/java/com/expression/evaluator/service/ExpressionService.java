package com.expression.evaluator.service;

import com.expression.evaluator.model.dto.ExpressionDto;
import com.expression.evaluator.model.entity.ExpressionEntity;
import com.expression.evaluator.repository.ExpressionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ExpressionService {

    private final ExpressionRepository expressionRepository;

    public String createExpression(ExpressionDto dto){
        var exp = mapToEntity(dto);
        exp.setUuid(UUID.randomUUID().toString());

        var saved = expressionRepository.save(exp);
        return saved.getUuid();
    }

    private ExpressionDto mapToDto(ExpressionEntity entity){
        return ExpressionDto.builder()
                .uuid(entity.getUuid())
                .name(entity.getName())
                .value(entity.getValue())
                .build();
    }

    private ExpressionEntity mapToEntity(ExpressionDto dto){
        return ExpressionEntity.builder()
                .value(dto.getValue())
                .name(dto.getName())
                .build();
    }
}