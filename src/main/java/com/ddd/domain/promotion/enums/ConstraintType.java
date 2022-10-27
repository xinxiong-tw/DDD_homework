package com.ddd.domain.promotion.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ConstraintType {
    AMOUNT("amount"),
    CHANNEL("channel"),
    ITEM("item"),
    CUSTOMER_ROLE("customer_role"),
    COMPOSED("composed");

    private final String value;

    ConstraintType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
