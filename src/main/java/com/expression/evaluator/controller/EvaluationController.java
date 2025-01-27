package com.expression.evaluator.controller;

import com.expression.evaluator.model.dto.RequestDto;
import com.expression.evaluator.service.EvaluationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/evaluate")
public class EvaluationController {

    private final EvaluationService evaluationService;

    @PostMapping
    public String evaluate(@RequestBody @Valid RequestDto request, @RequestParam String uuid) throws Exception {
        return evaluationService.evaluate(request, uuid);
    }
}