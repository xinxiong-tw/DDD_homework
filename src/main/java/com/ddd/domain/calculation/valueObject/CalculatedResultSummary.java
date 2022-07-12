package com.ddd.domain.calculation.valueObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public record CalculatedResultSummary(BigDecimal originTotalPrice, List<AppliedPromotionInfo> promotions,
                                      BigDecimal discountedTotalPrice) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculatedResultSummary that = (CalculatedResultSummary) o;
        return originTotalPrice.compareTo(that.originTotalPrice) == 0 && promotions.equals(that.promotions) && discountedTotalPrice.compareTo(that.discountedTotalPrice) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(originTotalPrice, promotions, discountedTotalPrice);
    }
}
