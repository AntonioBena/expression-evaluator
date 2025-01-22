package com.expression.evaluator.controller;

import com.expression.evaluator.model.dto.RequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/evaluate")
public class EvaluationController {

    @PostMapping
    public RequestDto newCompanyRegistrationRequest(@RequestBody @Valid RequestDto request){
        return request;
    }
}