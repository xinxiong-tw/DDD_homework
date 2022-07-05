package com.ddd.domain.calculation.entity;

import lombok.Builder;
import java.math.BigDecimal;

@Builder
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
