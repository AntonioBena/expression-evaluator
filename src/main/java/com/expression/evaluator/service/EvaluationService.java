package com.expression.evaluator.service;

import com.expression.evaluator.exception.expression.ExpressionNotFoundException;
import com.expression.evaluator.model.dto.RequestDto;
import com.expression.evaluator.repository.ExpressionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EvaluationService {

    private final ExpressionRepository expressionRepository;

    public String evaluate(RequestDto request, String uuid) throws Exception {
        var evaluator = new ExpressionEvaluator();

        var expression = expressionRepository.findByUuid(uuid)
                .orElseThrow(() -> new ExpressionNotFoundException("Requested expression does not exist!"));

        var evaluated = evaluator.evaluate(expression.getValue(), request);
        return "Evaluation result of expression: " + expression + " is: " + evaluated;
    }
}