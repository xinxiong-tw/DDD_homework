package com.ddd.domain.promotion.valueObject;

import java.util.List;
import java.util.Optional;

public interface ProductSet {
    public boolean include(String productId);

    public boolean isAllIn(List<String> productIds);

    public List<String> getProductIds();

    public static ProductSet of(List<String> ids) {
        return Optional.ofNullable(ids).map(it -> (ProductSet) new ListProductSet(ids)).orElseGet(NullProductSet::new);
    };

}
