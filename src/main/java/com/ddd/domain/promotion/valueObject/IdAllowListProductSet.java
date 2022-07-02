package com.ddd.domain.promotion.valueObject;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class IdAllowListProductSet extends ProductSet{
    private List<String> productIds;

    @Override
    public boolean include(String productId) {
        if (productIds.isEmpty()) return false;
        return productIds.contains(productId);
    }
}
