package com.expression.evaluator.service;

import com.expression.evaluator.exception.expression.DuplicateExpressionException;
import com.expression.evaluator.model.dto.ExpressionDto;
import com.expression.evaluator.repository.ExpressionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.expression.evaluator.utils.Utils.mapToEntity;

@Service
@AllArgsConstructor
public class ExpressionService {

    private final ExpressionRepository expressionRepository;

    public String createExpression(ExpressionDto dto){
        var exp = mapToEntity(dto);

        boolean expressionExists = expressionRepository.existsByName(exp.getName());
        if (expressionExists){
            throw new DuplicateExpressionException("Expression with name: " + dto.getName() + " already exists!");
        }

        exp.setUuid(UUID.randomUUID().toString());
        var saved = expressionRepository.save(exp);
        return saved.getUuid();
    }
}