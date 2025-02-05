package com.expression.evaluator.controller;

import com.expression.evaluator.service.EvaluationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/evaluate")
public class EvaluationController {

    private final EvaluationService evaluationService;

    @PostMapping
    public String evaluate(@RequestBody Map<String, Object> request, @RequestParam String uuid) throws Exception {
        return evaluationService.evaluate(request, uuid);
    }
}