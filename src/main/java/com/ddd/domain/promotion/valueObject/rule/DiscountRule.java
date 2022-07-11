package com.ddd.domain.promotion.valueObject.rule;

import com.ddd.domain.calculation.entity.PricedTransactionItem;
import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.promotion.valueObject.productSet.ProductSet;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public class DiscountRule implements PromotionRule {
    private BigDecimal discountRate;
    private ProductSet discountableProductSet;

    @Override
    public TransactionContext applyRule(TransactionContext transactionContext) {
        transactionContext.addNextPricedTransactionItems(calculateNextPrice(transactionContext));
        return transactionContext;
    }

    private List<PricedTransactionItem> calculateNextPrice(TransactionContext transactionContext) {
        return transactionContext.getItems().stream()
                .map(it -> PricedTransactionItem.builder()
                        .id(it.getId())
                        .price(getDiscountedPrice(it))
                        .count(it.getCount())
                        .build()
                ).toList();
    }

    private BigDecimal getDiscountedPrice(PricedTransactionItem it) {
        BigDecimal newPrice = it.getPrice();
        if (discountableProductSet.include(it.getId())) {
            newPrice = it.getPrice().multiply(BigDecimal.ONE.subtract(discountRate));
        }
        return newPrice;
    }
}
