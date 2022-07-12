package com.ddd.domain.calculation.valueObject;

import java.math.BigDecimal;

public record AppliedPromotionInfo(Long id, PromotionInfo info, BigDecimal promotionDiscount) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppliedPromotionInfo that = (AppliedPromotionInfo) o;
        return id.equals(that.id) && info.equals(that.info) && promotionDiscount.compareTo(that.promotionDiscount) == 0;
    }
}
