package com.expression.evaluator.utils;

import com.expression.evaluator.model.dto.ExpressionDto;
import com.expression.evaluator.model.entity.ExpressionEntity;

public class Utils {

    private Utils() {
    }

    public static ExpressionDto mapToDto(ExpressionEntity entity){
        return ExpressionDto.builder()
                .uuid(entity.getUuid())
                .name(entity.getName())
                .value(entity.getValue())
                .build();
    }

    public static ExpressionEntity mapToEntity(ExpressionDto dto){
        return ExpressionEntity.builder()
                .value(dto.getValue())
                .name(dto.getName())
                .build();
    }
}