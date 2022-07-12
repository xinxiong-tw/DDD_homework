package com.ddd.domain.calculation.entity;

import com.ddd.domain.calculation.valueObject.AppliedPromotionRuleInfo;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Builder
@Getter
public class PricedTransactionItem {
    private String id;
    private BigDecimal price;
    private int count;

    private AppliedPromotionRuleInfo appliedPromotionRuleInfo;

    public String getId() {
        return id;
    }

    public BigDecimal getTotalPrice() {
        return price.multiply(new BigDecimal(count));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PricedTransactionItem that = (PricedTransactionItem) o;
        return count == that.count && Objects.equals(id, that.id) && price.compareTo(that.getPrice()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, count);
    }

}
