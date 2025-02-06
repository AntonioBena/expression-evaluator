package com.expression.evaluator;

import com.expression.evaluator.model.dto.ExpressionDto;
import com.expression.evaluator.repository.ExpressionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class Base {

    @Autowired
    private ExpressionRepository expressionRepository;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        expressionRepository.deleteAll();
    }

    public ExpressionDto prepareExpression(String name, String value){
        return ExpressionDto.builder()
                .name(name)
                .value(value)
                .build();
    }


    public static Map<String, Object> prepareAddress(String city, int houseNumber, String street, String zipCode) {
        return Map.of(
                "city", city,
                "zipCode", zipCode,
                "street", street,
                "houseNumber", houseNumber
        );
    }

    public static Map<String, Object> prepareCustomer(String firstName, String lastName, int salary, String type) {
        return Map.of(
                "firstName", firstName,
                "lastName", lastName,
                "salary", salary,
                "type", type
        );
    }

    public static Map<String, Object> prepareCustomer(String firstName, String lastName, int salary, String type, Map<String, Object> address) {
        return Map.of(
                "firstName", firstName,
                "lastName", lastName,
                "address", address,
                "salary", salary,
                "type", type
        );
    }

    public static Map<String, Object> prepareVehicleWithVehicleDefaults(String model, int km, boolean isNew, Map<String, Object> owner) {
        return Map.of(
                "model", model,
                "kmDriven", km,
                "isNew", isNew,
                "vehicleOwner", owner,
                "vehicleExtendedInfo", Map.of(
                        "wheelsSize", 19,
                        "exhaustType", "SPORT",
                        "isKmh", true,
                        "frontTyreInfo", Map.of(
                                "tireType", "MUD_AND_SNOW",
                                "tireWidth", 225,
                                "tireHeight", 40,
                                "tireDiameter", 19
                        ),
                        "backTyreInfo", Map.of(
                                "frontTyreInfo", Map.of(
                                        "tireType", "MUD_AND_SNOW",
                                        "tireWidth", 255,
                                        "tireHeight", 35,
                                        "tireDiameter", 19
                                )
                        )
                )
        );
    }

    public static Map<String, Object> prepareRequest(String keyName, Map<String, Object> customerMap) {
        return Map.of(keyName, customerMap);
    }
}
