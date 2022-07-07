package com.ddd.domain.calculation.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@EqualsAndHashCode
@Getter
public class PricedTransactionItem {
    private String id;
    private BigDecimal price;
    private int count;

    public String getId() {
        return id;
    }

    public BigDecimal getTotalPrice() {
        return price.multiply(new BigDecimal(count));
    }
}
