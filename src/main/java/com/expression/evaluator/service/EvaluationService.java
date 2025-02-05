package com.expression.evaluator.service;

import com.expression.evaluator.evaluator.ExpressionEvaluator;
import com.expression.evaluator.exception.expression.ExpressionNotFoundException;
import com.expression.evaluator.exception.field.InvalidFieldException;
import com.expression.evaluator.repository.ExpressionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class EvaluationService {

    private final ExpressionRepository expressionRepository;

    public String evaluate(Map<String, Object> request, String uuid) throws Exception {
        if(request.isEmpty()) throw new InvalidFieldException("Object does not contain any key or value!");

        var evaluator = new ExpressionEvaluator();

        var expression = expressionRepository.findByUuid(uuid)
                .orElseThrow(() -> new ExpressionNotFoundException("Requested expression does not exist!"));

        var evaluated = evaluator.evaluate(expression.getValue(), request);
        return "Evaluation result of expression: " + expression.getValue() + " is: " + evaluated;
    }
}