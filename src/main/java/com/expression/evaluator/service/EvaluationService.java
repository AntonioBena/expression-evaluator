package com.expression.evaluator.service;

import com.expression.evaluator.model.dto.RequestDto;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {

    public String evaluate(RequestDto request){
        return "evaluated";
    }
}
