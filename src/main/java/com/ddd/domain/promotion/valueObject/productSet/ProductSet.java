package com.ddd.domain.promotion.valueObject.productSet;

import java.util.List;
import java.util.Optional;

public interface ProductSet {
    boolean include(String productId);

    boolean isAllIn(List<String> productIds);

    List<String> productIds();

    static ProductSet of(List<String> ids) {
        return Optional.ofNullable(ids).map(it -> {
            if (it.isEmpty()) {
                return new NullProductSet();
            }
            return new ListProductSet(ids);
        }).orElseGet(NullProductSet::new);
    }
}
