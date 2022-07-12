package com.ddd.domain.calculation.valueObject;

import java.math.BigDecimal;

public class AppliedPromotionRuleInfo {
    private Long id;
    private PromotionInfo info;
    private BigDecimal promotionDiscount;

    public Long getId() {
        return id;
    }

    public PromotionInfo getInfo() {
        return info;
    }

    public BigDecimal getPromotionDiscount() {
        return promotionDiscount;
    }
}
