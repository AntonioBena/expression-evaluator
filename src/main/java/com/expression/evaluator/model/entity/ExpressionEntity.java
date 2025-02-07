package com.expression.evaluator.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "evaluation_expression")
public class ExpressionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "expression_uuid")
    private String uuid;
    private String name;
    @Column(name = "expression_value", columnDefinition = "CLOB")
    private String value;
}