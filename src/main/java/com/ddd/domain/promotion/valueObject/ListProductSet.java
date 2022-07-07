package com.ddd.domain.promotion.valueObject;

import java.util.HashSet;
import java.util.List;

public class ListProductSet implements ProductSet {

    private List<String> productIds;

    public ListProductSet(List<String> productIds) {
        this.productIds = productIds;
    }

    @Override
    public boolean include(String productId) {
        return productIds.contains(productId);
    }

    @Override
    public boolean isAllIn(List<String> productIds) {
        return new HashSet<>(productIds).containsAll(this.productIds);
    }

    @Override
    public List<String> getProductIds() {
        return productIds;
    }
}
