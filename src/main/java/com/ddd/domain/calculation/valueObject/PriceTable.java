package com.ddd.domain.calculation.valueObject;

import java.math.BigDecimal;
import java.util.Map;

public class PriceTable {

    private final Map<String, BigDecimal> prices;

    public PriceTable(Map<String, BigDecimal> prices) {
        this.prices = prices;
    }

    public BigDecimal getPriceById(String id) {
        return prices.get(id);
    }
}
