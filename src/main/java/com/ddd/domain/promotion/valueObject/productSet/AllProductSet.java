package com.ddd.domain.promotion.valueObject.productSet;

import java.util.List;

public class AllProductSet implements ProductSet {
    @Override
    public boolean include(String productId) {
        return true;
    }

    @Override
    public boolean isAllIn(List<String> productIds) {
        return false;
    }

    @Override
    public List<String> productIds() {
        return null;
    }
}
