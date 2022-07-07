package com.ddd.domain.promotion.valueObject;

import java.util.HashSet;
import java.util.List;

public class ProductSet {
    private List<String> productIds;

    public ProductSet(List<String> productIds) {
        this.productIds = productIds;
    }

    public boolean include(String productId) {
        return productIds.contains(productId);
    }

    public boolean isAllIn(List<String> productIds) {
        return new HashSet<>(productIds).containsAll(this.productIds);
    }

    public List<String> getProductIds() {
        return productIds;
    }
}
