package com.ddd.domain.calculation.valueObject;

import com.ddd.domain.calculation.entity.PricedTransactionItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CalculatedResultItem {
    private final String id;
    private final BigDecimal originPrice;
    private final int count;
    private final List<AppliedPromotionRuleInfo> appliedPromotionRuleInfos;
    private BigDecimal finalPrice;

    private CalculatedResultItem(String id, BigDecimal originPrice, int count, List<AppliedPromotionRuleInfo> appliedPromotionRuleInfos, BigDecimal finalPrice) {
        this.id = id;
        this.originPrice = originPrice;
        this.count = count;
        this.appliedPromotionRuleInfos = appliedPromotionRuleInfos;
        this.finalPrice = finalPrice;
    }

    public static CalculatedResultItem createCalculatedResultItem(PricedTransactionItem it) {
        return new CalculatedResultItem(it.getId(), it.getPrice(), it.getCount(),
                new ArrayList<>(List.of(it.getAppliedPromotionRuleInfo())),
                it.getPrice().subtract(it.getAppliedPromotionRuleInfo().getPromotionDiscount()));
    }

    public String getId() {
        return id;
    }

    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public List<AppliedPromotionRuleInfo> getAppliedPromotionRuleInfos() {
        return appliedPromotionRuleInfos;
    }

    public void addAppliedPromotion(AppliedPromotionRuleInfo appliedPromotionRuleInfo) {
        appliedPromotionRuleInfos.add(appliedPromotionRuleInfo);
        finalPrice = finalPrice.subtract(appliedPromotionRuleInfo.getPromotionDiscount());
    }
}
