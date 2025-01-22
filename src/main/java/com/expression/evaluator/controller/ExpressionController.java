package com.expression.evaluator.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/expression")
public class ExpressionController {

    @PostMapping
    public String createExpression(){
        return null;
    }
}