package com.ddd.domain.promotion.enums;

public enum PromotionType {
    GENERIC("GENERIC"),
    CODE_RELATED("CODE_RELATED");

    private final String value;

    PromotionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
