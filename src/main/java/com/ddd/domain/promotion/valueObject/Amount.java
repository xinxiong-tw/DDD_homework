package com.ddd.domain.promotion.valueObject;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class Amount {
    private BigDecimal discountAmount;
}
