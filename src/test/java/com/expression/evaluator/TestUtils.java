package com.expression.evaluator;

import com.expression.evaluator.model.entity.ExpressionEntity;

import java.util.Map;

public class TestUtils {

    public static Map<String, Object> prepareAddress(String city, int houseNumber, String street, String zipCode) {
        return Map.of(
                "city", city,
                "zipCode", zipCode,
                "street", street,
                "houseNumber", houseNumber
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

    public static ExpressionEntity prepareExpressionEntity(String expressionName, String value) {
        return ExpressionEntity.builder()
                .name(expressionName)
                .value(value)
                .build();
    }
}