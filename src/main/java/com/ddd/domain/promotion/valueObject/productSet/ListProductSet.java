package com.ddd.domain.promotion.valueObject.productSet;

import java.util.HashSet;
import java.util.List;

public record ListProductSet(List<String> productIds) implements ProductSet {

    @Override
    public boolean include(String productId) {
        return productIds.contains(productId);
    }

    @Override
    public boolean isAllIn(List<String> productIds) {
        return new HashSet<>(productIds).containsAll(this.productIds);
    }
}
