package com.ddd.domain.calculation.valueObject;

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

    private AppliedPromotionInfo appliedPromotionInfo;

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
        return count == that.count && id.equals(that.id) && price.compareTo(that.price) == 0 && Objects.equals(appliedPromotionInfo, that.appliedPromotionInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, count, appliedPromotionInfo);
    }

    @Override
    public String toString() {
        return "PricedTransactionItem{" +
                "id='" + id + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", appliedPromotionInfo=" + appliedPromotionInfo +
                '}';
    }
}
