package com.ddd.domain.promotion.valueObject;

import java.util.List;

public class NullProductSet implements ProductSet {
    @Override
    public boolean include(String productId) {
        return true;
    }

    @Override
    public boolean isAllIn(List<String> productIds) {
        return true;
    }

    @Override
    public List<String> getProductIds() {
        return null;
    }
}
