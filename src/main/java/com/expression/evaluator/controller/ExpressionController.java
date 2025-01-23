package com.expression.evaluator.controller;

import com.expression.evaluator.model.dto.ExpressionDto;
import com.expression.evaluator.service.ExpressionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/expression")
public class ExpressionController {

    private final ExpressionService expressionService;

    @PostMapping
    public String createExpression(@RequestBody @Valid ExpressionDto dto){
        return expressionService.createExpression(dto);
    }
}