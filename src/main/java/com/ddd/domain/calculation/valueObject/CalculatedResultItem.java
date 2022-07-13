package com.ddd.domain.calculation.valueObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CalculatedResultItem {
    private final String id;
    private final BigDecimal originPrice;
    private final int count;
    private final List<AppliedPromotionInfo> appliedPromotionInfos;
    private BigDecimal finalPrice;

    public CalculatedResultItem(String id, BigDecimal originPrice, int count, List<AppliedPromotionInfo> appliedPromotionInfos, BigDecimal finalPrice) {
        this.id = id;
        this.originPrice = originPrice;
        this.count = count;
        this.appliedPromotionInfos = appliedPromotionInfos;
        this.finalPrice = finalPrice;
    }

    public static CalculatedResultItem createCalculatedResultItem(PricedTransactionItem it) {
        return new CalculatedResultItem(it.getId(), it.getPrice(), it.getCount(),
                new ArrayList<>(),
                it.getPrice());
    }

    public String getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public BigDecimal getOriginTotalPrice() {
        return originPrice.multiply(new BigDecimal(count));
    }

    public BigDecimal getFinalTotalPrice() {
        return finalPrice.multiply(new BigDecimal(count));
    }

    public List<AppliedPromotionInfo> getAppliedPromotionRuleInfos() {
        return appliedPromotionInfos;
    }

    public void addAppliedPromotion(AppliedPromotionInfo appliedPromotionInfo) {
        appliedPromotionInfos.add(appliedPromotionInfo);
        finalPrice = finalPrice.subtract(appliedPromotionInfo.promotionDiscount());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculatedResultItem that = (CalculatedResultItem) o;
        return count == that.count && id.equals(that.id) && originPrice.compareTo(that.originPrice) == 0 && appliedPromotionInfos.equals(that.appliedPromotionInfos) && finalPrice.compareTo(that.finalPrice) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originPrice, count, appliedPromotionInfos, finalPrice);
    }

    @Override
    public String toString() {
        return "CalculatedResultItem{" +
                "id='" + id + '\'' +
                ", originPrice=" + originPrice +
                ", count=" + count +
                ", appliedPromotionRuleInfos=" + appliedPromotionInfos +
                ", finalPrice=" + finalPrice +
                '}';
    }
}
