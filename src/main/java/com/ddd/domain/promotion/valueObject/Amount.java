package com.ddd.domain.promotion.valueObject;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class Amount {
    private BigDecimal maxAmount;
    private BigDecimal discountAmount;
}
