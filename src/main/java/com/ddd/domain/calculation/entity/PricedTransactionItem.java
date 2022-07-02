package com.ddd.domain.calculation.entity;

import lombok.Builder;

@Builder
public class PricedTransactionItem {
    private String id;
    private Float price;
    private Float total;
    private int count;

    public String getId() {
        return id;
    }
}
