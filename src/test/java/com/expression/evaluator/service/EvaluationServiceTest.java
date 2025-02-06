package com.expression.evaluator.service;

import com.expression.evaluator.exception.expression.DuplicateExpressionException;
import com.expression.evaluator.exception.expression.ExpressionNotFoundException;
import com.expression.evaluator.model.dto.ExpressionDto;
import com.expression.evaluator.repository.ExpressionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Map;

import static com.expression.evaluator.Base.prepareRequest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EvaluationServiceTest {

    @Autowired
    private ExpressionRepository repository;

    private ExpressionService expressionService;
    private EvaluationService evaluation;

    @BeforeEach
    void setUp() {
        expressionService = new ExpressionService(repository);
        evaluation = new EvaluationService(repository);
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void test_should_save_new_expression() {
        var expressionDto = ExpressionDto.builder()
                .name("complex expression 1")
                .value("(customer.firstName != Antonio || customer.address.city == Rotterdam)")
                .build();

        var uuid = expressionService.createExpression(expressionDto);

        var expressionFromDb = repository.findByUuid(uuid);

        assertNotNull(expressionFromDb);
        assertEquals(uuid.length(), 36);
        assertEquals(uuid, expressionFromDb.get().getUuid());
        assertEquals(expressionDto.getName(), expressionFromDb.get().getName());
        assertEquals(expressionDto.getValue(), expressionFromDb.get().getValue());
    }

    @Test
    void test_should_not_save_new_expression_with_same_name() {
        var expressionDto1 = ExpressionDto.builder()
                .name("complex expression 1")
                .value("(customer.firstName != Antonio || customer.address.city == Rotterdam)")
                .build();

        var expressionDto2 = ExpressionDto.builder()
                .name("complex expression 1")
                .value("(customer.firstName != John || customer.address.city == Amsterdam)")
                .build();

        var uuid1 = expressionService.createExpression(expressionDto1);

        assertThrows(DuplicateExpressionException.class, () -> {
            var uuid2 = expressionService.createExpression(expressionDto2);
            assertNull(uuid2);
        });


        var expressions = repository.findAll();

        assertEquals(expressions.size(), 1);

        var expression = expressions.getFirst();
        assertEquals(uuid1.length(), 36);
        assertEquals(uuid1, expression.getUuid());
        assertEquals(expressionDto1.getName(), expression.getName());
        assertEquals(expressionDto1.getValue(), expression.getValue());
    }

    @Test
    void test_should_throw_ExpressionNotFoundException() {
        var expressionDto = ExpressionDto.builder()
                .name("complex expression 1")
                .value("(customer.firstName != Antonio || customer.address.city == Rotterdam)")
                .build();

        expressionService.createExpression(expressionDto);

        var dummyUuid = "DUMMY-UUID";
        var request = prepareRequest("dummy_request", Map.of("k1", false));

        assertThrows(ExpressionNotFoundException.class, () -> {
            var result = evaluation.evaluate(request, dummyUuid);

            assertNull(result);
        });
    }
}